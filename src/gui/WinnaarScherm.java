package gui;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import resources.Taal;

public class WinnaarScherm extends StackPane {
	private final DomeinController dc;
	private List<SpelerDTO> spelerDTOs;

	private GridPane gridWinnaars;
	private final int WEIGHT = 26;

	public WinnaarScherm(DomeinController dc) {
		this.dc = dc;
		buildGui();
	}

	private void buildGui() {
		// Load the GIF image
		File vuurwerk = new File("src/resources/img/background_misc/vuurwerk.gif");
		Image vuurwerkImg = new Image(vuurwerk.toURI().toString());

		// Create an ImageView to display the GIF
		ImageView imageView = new ImageView(vuurwerkImg);
		imageView.setOpacity(0.3);

		VBox vbox = new VBox();
		vbox.setSpacing(20);
		vbox.setPadding(new Insets(40));
		vbox.setAlignment(Pos.CENTER);
		// titel
		Label lblTitel = new Label(String.format("%s", Taal.getString("victory")));
		lblTitel.setFont(Font.font("Helvetica", FontWeight.BOLD, 75));

		// aangemelde speler DTOS ophalen
		spelerDTOs = dc.getAangemeldeSpelers();

		Collections.sort(spelerDTOs, new Comparator<SpelerDTO>() {
			public int compare(SpelerDTO s1, SpelerDTO s2) {

				if (dc.bepaalWinnaar().contains(s1)) {
					return -1; // s1 should come first in the list
				} else if (dc.bepaalWinnaar().contains(s2)) {
					return 1; // s2 should come first in the list
				} else {
					// Neither s1 nor s2 is the winner, compare their aantalPrestigepunten
					return s2.aantalPrestigepunten() - s1.aantalPrestigepunten();
				}

			}
		});

		// winnaars grid
		gridWinnaars = new GridPane();
		gridWinnaars.setAlignment(Pos.CENTER);
		gridWinnaars.setHgap(10);
		gridWinnaars.setVgap(10);
		gridWinnaars.setPadding(new Insets(20));

		Text[] s = new Text[spelerDTOs.size()];

		for (int i = 0; i < spelerDTOs.size(); i++) {
			s[i] = new Text(String.format("%d. %s:", i + 1, spelerDTOs.get(i).gebruikersnaam()));
			gridWinnaars.add(s[i], 0, i);

		}

		Text[] p = new Text[spelerDTOs.size()];
		for (int i = 0; i < spelerDTOs.size(); i++) {
			p[i] = new Text(String.format("%d points", spelerDTOs.get(i).aantalPrestigepunten()));
			gridWinnaars.add(p[i], 1, i);

		}

		Text[] w = new Text[spelerDTOs.size()];
		for (int i = 0; i < spelerDTOs.size(); i++) {
			w[i] = new Text("");
			gridWinnaars.add(w[i], 2, i);
			for (SpelerDTO w2 : dc.bepaalWinnaar()) {
				if (spelerDTOs.get(i).gebruikersnaam().equals(w2.gebruikersnaam())) {
					w[i].setText(String.format("!!! %s !!!", Taal.getString("winner").toUpperCase()));
				}
			}
		}

		for (int i = 0; i < spelerDTOs.size(); i++) {
			s[i].setFont(Font.font("Helvetica", WEIGHT - 2 * i));
			p[i].setFont(Font.font("Helvetica", WEIGHT - 2 * i));
			w[i].setFont(Font.font("Helvetica", FontWeight.BOLD, WEIGHT - 2 * i));

		}

		gridWinnaars.setAlignment(Pos.CENTER);

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();

		// Set the width of the columns
		col1.setPercentWidth(2);
		col2.setPercentWidth(2);
		col3.setPercentWidth(2);

		// Set the alignment of the second column to center
		col1.setHalignment(HPos.CENTER);
		col2.setHalignment(HPos.CENTER);
		col3.setHalignment(HPos.CENTER);

		// Add the column constraints to the grid pane
		gridWinnaars.getColumnConstraints().addAll(col1, col2, col3);

		// toevoegen aan VBox
		vbox.getChildren().addAll(lblTitel, gridWinnaars);
		getChildren().addAll(imageView, vbox);
	}
	// TEST
}
