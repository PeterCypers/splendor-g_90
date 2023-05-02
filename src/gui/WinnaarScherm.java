package gui;

import domein.DomeinController;
import domein.Speler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class WinnaarScherm extends VBox {
	private final DomeinController dc;


	public WinnaarScherm(DomeinController dc) {
		this.dc = dc;
		buildGui();
	}

	private void buildGui() {
		setSpacing(20);
		setPadding(new Insets(40));



//		// Create a ListView to display the players and their points
//		ListView<String> listView = new ListView<>();
//		for (Speler speler : dc.aangemeldeSpelers) {
//			listView.getItems().add(speler.getNaam() + " - " + speler.getPoints());
//		}
//
//		// Create a Label to display the number of players
//		Label countLabel = new Label("Number of players: " + dc.aangemeldeSpelers.size());
//		countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
//
//		// Add the ListView and Label to the VBox
//		getChildren().addAll(listView, countLabel);
//	}


	//         Label winnaarLabel = new Label(String.format("%s:", Taal.getString("victory")));
	//		Label lblWinnaarTitel = new Label(String.format("WINNAAR%s:", dc.bepaalWinnaar().size()>1?"S":""));
	//		lblWinnaarTitel.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
	//		getChildren().add(lblWinnaarTitel);
	//		setAlignment(Pos.CENTER); // align label to center
	//		
	//		Label lblWinnaars = new Label(String.format("%s", dc.bepaalWinnaar()));
	//		getChildren().add(lblWinnaars);
	//		setAlignment(Pos.CENTER);
	//		
	//		ListView<Speler> spelers;

}
}
