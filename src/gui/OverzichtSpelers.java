package gui;

import domein.DomeinController;
import domein.Speler;
import domein.SpelerRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.Taal;

//Dit paneel toont een overzicht van de bestaande rekeningen
//De hoofdlayout is een VBox
public class OverzichtSpelers extends VBox {

	SpelerRepository spelerRepo = new SpelerRepository();
	private final DomeinController dc;

	//observableList
	ObservableList<String> playerInfo = FXCollections.observableArrayList();

	// Dit DetailPaneel wordt verwittigd wanneer de selectie wijzigt
	private DetailSpelers details;

	// Een ListView is een component die een lijst kan tonen.
	private ListView<String> lijst;

	// Een OverzichtPaneel heeft zowel een controller als een DetailPaneel nodig
	// Deze komen binnen als argumenten in de constructor



	public OverzichtSpelers(DomeinController dc, DetailSpelers ds) {
		this.dc = dc;
		this.details = ds;
		buildGui();
		toonSpelers();

	}

	private void buildGui() {
		this.setPadding(new Insets(20, 10, 50, 10));
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		Label lblOverzicht = new Label(String.format("%s", Taal.getString("players")));
		lblOverzicht.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		lblOverzicht.setUnderline(true);
		lblOverzicht.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(lblOverzicht);

		lijst = new ListView<>();
		this.getChildren().add(lijst);

	}

	public void toonSpelers() {

		for (Speler speler : spelerRepo.getSpelers()) {
			String gebruikersNaam = speler.getGebruikersnaam();
			int geboorteJaar = speler.getGeboortejaar();
			String playerString = String.format("%s - %d", gebruikersNaam, geboorteJaar);

			playerInfo.add(playerString);

		}

		lijst.setItems(playerInfo);

		lijst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
		{
			String[] parts = newValue.split(" - ");
			details.getTxfGebruikersnaam().setText(parts[0]);
			details.getTxfGeboortejaar().setText(parts[1]);		
			
		
//		if(playerInfo.equals()) {
//			playerInfo.remove(newValue);
//		}
		});

	}

}
