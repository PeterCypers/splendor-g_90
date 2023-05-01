package gui;

import domein.DomeinController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.Taal;

public class OverzichtSpelers extends VBox {

	private final DomeinController dc;
	// Dit DetailPaneel wordt verwittigd wanneer de selectie wijzigt
	private final DetailSpelers details;
	// Een ListView is een component die een lijst kan tonen.
	private ListView<String> lijst;

	// Een OverzichtPaneel heeft zowel een controller als een DetailPaneel nodig
	// Deze komen binnen als argumenten in de constructor
	public OverzichtSpelers(DomeinController dc, DetailSpelers details) {
		this.dc = dc;
		this.details = details;
		buildGui();
	}

	private void buildGui() {
		this.setPadding(new Insets(20, 10, 50, 10));
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);

		Label lblOverzicht = new Label(String.format("%s: ", Taal.getString("players")));
		lblOverzicht.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
		this.getChildren().add(lblOverzicht);

	}
}
