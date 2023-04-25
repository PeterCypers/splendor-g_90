package gui;

import domein.Spel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SpelersToevoegenScherm extends GridPane {

	private int aantalSpelers;

	public SpelersToevoegenScherm() {
		aantalSpelers = 0;
		buildGui();
	}

	private void buildGui() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));

		Label lblAantalSpelers = new Label("Aantal spelers: " + aantalSpelers);

		Label lblGebruikersnaam = new Label("Gebruikersnaam:");
		TextField fldGebruikersnaam = new TextField();

		Label lblGeboorteJaar = new Label("Geboorte Jaar:");
		TextField fldGeboorteJaar = new TextField();

		Button addButton = new Button("Speler Toevoegen");

		Button startSpel = new Button("Start Spel");
		startSpel.setDisable(true);

		addButton.setOnAction(e -> {
			if (fldGebruikersnaam.getText().isEmpty() || fldGeboorteJaar.getText().isEmpty()) {
				// lege velden
				return;
			}
			
			//TODO: valideren met database?


			aantalSpelers++;

			if (aantalSpelers == Spel.MIN_AANTAL_SPELERS) // max spelers, 
				addButton.setDisable(true);
			// TODO: speler toevoegen

			

			if (aantalSpelers >= Spel.MAX_AANTAL_SPELERS) // minimum spelers
				startSpel.setDisable(false);


			// velden legen
			fldGebruikersnaam.clear();
			fldGeboorteJaar.clear();
		});
		this.add(lblAantalSpelers, 0, 0);
		this.add(lblGebruikersnaam, 0, 1);
		this.add(fldGebruikersnaam, 1, 1);
		this.add(lblGeboorteJaar, 0, 2);
		this.add(fldGeboorteJaar, 1, 2);
		this.add(addButton, 0, 3, 2, 1);
		this.add(startSpel, 0, 4, 2, 1);
		//        this.setHalignment(addButton, Pos.CENTER);
	}
}


