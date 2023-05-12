package gui;

import java.io.File;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resources.Taal;

public class DetailSpelers extends GridPane
{

	private final DomeinController dc;


	private TextField txfGebruikersnaam;
	private TextField txfGeboortejaar;
	private Button btnAdd;
	private int i=0;

	public DetailSpelers(DomeinController dc)
	{
		this.dc = dc;
		buildGui();
	}

	private void buildGui()
	{
		
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));


		Label lblGegevens = new Label(String.format("%s", Taal.getString("data")));
		lblGegevens.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		lblGegevens.setUnderline(true);

		Label lblGebruikersnaam = new Label(String.format("%s:", Taal.getString("username")));
		Label lblGeboortejaar = new Label(String.format("%s:", Taal.getString("birthyear")));
		Label lblAantalSpelers = new Label(String.format("%s: %d", Taal.getString("numberOfPlayers"), dc.geefAantalSpelers()));
		Label lblToegevoegdeSpelers = new Label(String.format("%s:", Taal.getString("addedPlayers")));

		lblGebruikersnaam.setFont(Font.font("Helvetica",FontWeight.BOLD,BASELINE_OFFSET_SAME_AS_HEIGHT));
		lblGeboortejaar.setFont(Font.font("Helvetica",FontWeight.BOLD,BASELINE_OFFSET_SAME_AS_HEIGHT));
		lblAantalSpelers.setFont(Font.font("Helvetica", FontWeight.BOLD,BASELINE_OFFSET_SAME_AS_HEIGHT));
		lblToegevoegdeSpelers.setFont(Font.font("Helvetica",FontWeight.BOLD,BASELINE_OFFSET_SAME_AS_HEIGHT));

		txfGebruikersnaam = new TextField("");
		txfGeboortejaar = new TextField("");

		btnAdd = new Button(Taal.getString("addPlayer"));
		Button btnStartSpel = new Button(Taal.getString("playGame"));
		Button btnKeerTerug = new Button(Taal.getString("goBack"));
		Button btnClear1 = new Button();
		Button btnClear2 = new Button();

		HBox buttons = new HBox(btnAdd, btnStartSpel);
		buttons.setSpacing(10);

		//opmaak clear1

		File clearFile1 = new File("src/resources/img/background_misc/unchecked.png");
		ImageView imageView1 = new ImageView(new Image(clearFile1.toURI().toString()));
		imageView1.setFitHeight(10);
		imageView1.setFitWidth(10);

		btnClear1.setOnAction(e -> txfGebruikersnaam.clear());
		btnClear1.setGraphic(imageView1);

		//opmaak clear2

		File clearFile2 = new File("src/resources/img/background_misc/unchecked.png");
		ImageView imageView2 = new ImageView(new Image(clearFile2.toURI().toString()));
		imageView2.setFitHeight(10);
		imageView2.setFitWidth(10);

		btnClear2.setOnAction(e -> txfGeboortejaar.clear());
		btnClear2.setGraphic(imageView2);

		btnAdd.setFont(Font.font("Helvetica"));
		btnStartSpel.setFont(Font.font("Helvetica",BASELINE_OFFSET_SAME_AS_HEIGHT));
		btnStartSpel.setDisable(true);
		btnStartSpel.setOnAction(this::drukStartSpel); // spel starten met gekozen spelers
		btnKeerTerug.setFont(Font.font("Helvetica"));

		this.add(lblGegevens, 1, 0);

		this.add(lblGebruikersnaam, 0, 2);
		this.add(txfGebruikersnaam, 1, 2);
		this.add(btnClear1, 2, 2);

		this.add(lblGeboortejaar, 0, 3);
		this.add(txfGeboortejaar, 1, 3);
		this.add(btnClear2, 2, 3);

		this.add(lblAantalSpelers, 0, 4);
		this.add(buttons, 1, 4);


		this.add(lblToegevoegdeSpelers, 0, 5);

		//spelers toevoegen en valideren

		btnAdd.setOnAction(ev ->
		{
			try
			{
				if (txfGebruikersnaam.getText().isEmpty() || txfGeboortejaar.getText().isEmpty())
				{// lege velden
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle(Taal.getString("alert"));
					alert.setHeaderText(Taal.getString("wrongData"));
					alert.setContentText(Taal.getString("allFieldsFilled"));
					alert.showAndWait();
					return;
				}
				dc.voegSpelerToe(txfGebruikersnaam.getText(), Integer.parseInt(txfGeboortejaar.getText()));
				lblAantalSpelers
						.setText((String.format("%s: %d", Taal.getString("numberOfPlayers"), dc.geefAantalSpelers())));

				//aagemeldespelers
				Text user = new Text(String.format("%d: %s - %s", i+1,txfGebruikersnaam.getText(), Integer.parseInt(txfGeboortejaar.getText())));
				this.add(user, 0, 6+i);
				i++; //index

			
				
		

				
				if (dc.geefAantalSpelers() == Spel.MAX_AANTAL_SPELERS) // max spelers,
					btnAdd.setDisable(true);

				if (dc.geefAantalSpelers() >= Spel.MIN_AANTAL_SPELERS) // minimum spelers
					btnStartSpel.setDisable(false);

				txfGebruikersnaam.clear();
				txfGeboortejaar.clear();
				// velden legen

			}
			catch (Exception e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(Taal.getString("alert"));
				alert.setHeaderText(Taal.getString("wrongData"));
				alert.setContentText(e.getMessage());
				alert.showAndWait();

				// velden legen
				txfGebruikersnaam.clear();
				txfGeboortejaar.clear();
			}
		});
	}


	public TextField getTxfGebruikersnaam()
	{
		return txfGebruikersnaam;
	}

	public TextField getTxfGeboortejaar()
	{
		return txfGeboortejaar;
	}
	
	public Button getBtnAdd()
	{
		return btnAdd;
	}

	private void drukStartSpel(ActionEvent event)
	{ //naar volgende scherm
		SpeelSpelScherm spelbord = new SpeelSpelScherm(dc);
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(spelbord, stage.getWidth(), stage.getHeight());
		stage.setTitle(Taal.getString("game"));
		stage.setScene(scene);
	}
}
