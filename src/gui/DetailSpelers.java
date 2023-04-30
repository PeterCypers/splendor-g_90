package gui;

import domein.DomeinController;
import domein.Spel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resources.Taal;

public class DetailSpelers extends GridPane {



	private final DomeinController dc;

	private TextField txfGebruikersnaam;
	private TextField txfGeboortejaar;
	public DetailSpelers(DomeinController dc) {
		this.dc = dc;
		buildGui();
	}

	private void buildGui() {

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));

		Label lblAantalSpelers = new Label(Taal.getString("amountOfPlayers") +0);
		
		Label lblGebruikersnaam = new Label(String.format("%s:", Taal.getString("username")));
		Label lblGeboortejaar = new Label(String.format("%s:", Taal.getString("birthyear")));
		Label lblGegevens = new Label("Gegevens:");

		lblGegevens.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
		lblAantalSpelers.setFont(Font.font("Helvetica"));
		lblGebruikersnaam.setFont(Font.font("Helvetica"));
		lblGeboortejaar.setFont(Font.font("Helvetica"));

		Button btnAdd = new Button(Taal.getString("addPlayer"));
		Button btnStartSpel = new Button(Taal.getString("startGame"));
		Button btnKeerTerug = new Button(Taal.getString("goBack"));
		Button btnClear1 = new Button("X");
		Button btnClear2 = new Button("X");

		btnClear1.setFont(Font.font("Helvetica"));
		btnClear1.setOnAction(e ->{
			txfGebruikersnaam.clear();
		});
		btnClear2.setFont(Font.font("Helvetica"));
		btnClear2.setOnAction(e ->{
			txfGeboortejaar.clear();
		});


		btnAdd.setFont(Font.font("Helvetica"));
		btnStartSpel.setFont(Font.font("Helvetica"));
		btnStartSpel.setDisable(true);
		//		btnStartSpel.setOnAction(this::drukStartSpel); // spel starten met gekozen spelers
		btnKeerTerug.setFont(Font.font("Helvetica"));
		btnKeerTerug.setOnAction(this::drukKeerTerug);

		txfGebruikersnaam = new TextField();
		txfGeboortejaar = new TextField();


		//this.add(btnKeerTerug, 0, 0);
		this.add(lblGegevens, 0, 0, 2, 1);
		this.add(lblAantalSpelers, 0, 4);
		this.add(lblGebruikersnaam, 0, 2);
		this.add(txfGebruikersnaam, 1, 2);
		this.add(btnClear1, 2, 2,1,1);
		this.add(lblGeboortejaar, 0, 3);
		this.add(txfGeboortejaar, 1, 3);
		this.add(btnClear2, 2, 3,1,1);
		this.add(btnAdd, 1, 4, 2, 1);
		this.add(btnStartSpel, 1, 5, 2, 1);

		btnAdd.setOnAction(e -> {
			try {
				if (txfGebruikersnaam.getText().isEmpty() || txfGeboortejaar.getText().isEmpty()) {// lege velden
					return;
				}
				dc.voegSpelerToe(txfGebruikersnaam.getText(), Integer.parseInt(txfGeboortejaar.getText()));
				lblAantalSpelers.setText(Taal.getString("amountOfPlayers") + dc.geefAantalSpelers());
				if (dc.geefAantalSpelers() == Spel.MAX_AANTAL_SPELERS) // max spelers,
					btnAdd.setDisable(true);

				if (dc.geefAantalSpelers() >= Spel.MIN_AANTAL_SPELERS) // minimum spelers
					btnStartSpel.setDisable(false);

				txfGebruikersnaam.clear();
				txfGeboortejaar.clear();
				// velden legen

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert");
				alert.setHeaderText("Foute gegevens");
				alert.setContentText(e2.getMessage());
				alert.showAndWait();

				txfGebruikersnaam.clear();
				txfGeboortejaar.clear();
				// velden legen
			}
		});



	}

	private void drukKeerTerug(ActionEvent event) {
		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm(dc);
		Scene scene = new Scene(taalKeuze);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	private void drukStartSpel(ActionEvent event) {
		dc.startNieuwSpel();
	}

	// Deze methode wordt aangeroepen door het OverzichtPaneel 
	// wanneer er een andere user geselecteerd werd  
	public void update()
	{
		//		List<Speler> spelersInRepository = SpelerRepository.getSpelers();
		//		RekeningDTO infoVanHuidigeRekening = alleInfoVanRekeningen[indexVanDeRekening];

		txfGebruikersnaam.setText(null);
		txfGeboortejaar.setText(null);

	}
}
