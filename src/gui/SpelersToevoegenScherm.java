package gui;

import domein.Spel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SpelersToevoegenScherm extends GridPane {
	int aantalSpelers = 0;

	public SpelersToevoegenScherm() {
		buildGui();
	}

	private void buildGui() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));


		Label lblAantalSpelers = new Label("Aantal spelers: " + aantalSpelers);
		lblAantalSpelers.setFont(Font.font("Helvetica"));

		Label lblGebruikersnaam = new Label("Gebruikersnaam:");
		lblGebruikersnaam.setFont(Font.font("Helvetica"));
		TextField txfGebruikersnaam = new TextField();

		Label lblGeboortejaar = new Label("Geboortejaar:");
		lblGeboortejaar.setFont(Font.font("Helvetica"));
		TextField txfGeboorteJaar = new TextField();

		Button btnAdd = new Button("Speler Toevoegen");
		btnAdd.setFont(Font.font("Helvetica"));

		Button btnStartSpel = new Button("Start Spel");
		btnStartSpel.setFont(Font.font("Helvetica"));
		btnStartSpel.setDisable(true);

		Button btnKeerTerug = new Button("Keer Terug");
		btnKeerTerug.setFont(Font.font("Helvetica"));
		btnKeerTerug.setOnAction(this::buttonPushed);

		btnAdd
		.setOnAction(e -> {
			if (txfGebruikersnaam.getText().isEmpty() || txfGeboorteJaar.getText().isEmpty()) {// lege velden
				return;
			}

			//TODO: valideren met database? 
			//TODO: dc.voegSpelerToe(txfGebruikersnaam.getText(), Integer.parseInt(txfGeboorteJaar.getText()));

			if (aantalSpelers == Spel.MAX_AANTAL_SPELERS) // max spelers, 
				btnAdd.setDisable(true);

			if (aantalSpelers >= Spel.MIN_AANTAL_SPELERS) // minimum spelers
				btnStartSpel.setDisable(false);

			// velden legen
			txfGebruikersnaam.clear();
			txfGeboorteJaar.clear();
		});

		this.add(btnKeerTerug, 0,0);
		this.add(lblAantalSpelers, 1, 0);
		this.add(lblGebruikersnaam, 1, 1);
		this.add(txfGebruikersnaam, 2, 1);
		this.add(lblGeboortejaar, 1, 2);
		this.add(txfGeboorteJaar, 2, 2);
		this.add(btnAdd, 1, 3, 2, 1);
		this.add(btnStartSpel, 1, 4, 2, 1);

	}
	private void buttonPushed(ActionEvent event) {
		TaalKeuzeScherm root = new TaalKeuzeScherm();
		Scene scene = new Scene(root, 800, 600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}


