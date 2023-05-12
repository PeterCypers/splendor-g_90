package gui;

import java.io.File;
import java.io.FileNotFoundException;

import domein.Kleur;
import dto.SpelVoorwerpDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EdeleNode extends StackPane {
	private static final int NOBLE_WIDTH = 128;
	private static final int NOBLE_HEIGHT = 128;
	private static final int NOBLE_SIZE = 40;
	private static final int NOBLE_FONTSIZE = 28;

	public EdeleNode(SpelVoorwerpDTO edele) {

		ImageView nobleImage = new ImageView();
		try {
			File backgroundFile = new File(edele.foto());
			if (!backgroundFile.exists()) {
				throw new FileNotFoundException("Image file not found: " + edele.foto());
			}
			Image backgroundImage = new Image(backgroundFile.toURI().toString());
			nobleImage = new ImageView(backgroundImage);
		} catch (Exception e) {
			System.out.println("Probleem bij het inladen van achtergrond foto voor de edele");
			System.err.println("Unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}

		// Adds a white background for the prestigepoints and the colorBonus
		Rectangle whiteBackground = new Rectangle(NOBLE_SIZE / 1.5, NOBLE_WIDTH);
		whiteBackground.setFill(Color.WHITE);
		whiteBackground.setOpacity(0.75);
		StackPane.setAlignment(whiteBackground, Pos.CENTER_LEFT);

		// Adds the prestigepoints to the development card
		Text prestigePointsText = new Text(Integer.toString(edele.prestigepunten()));
		prestigePointsText.setFont(Font.font("Arial", FontWeight.BOLD, NOBLE_FONTSIZE));
		prestigePointsText.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 1;");
		StackPane.setAlignment(prestigePointsText, Pos.TOP_LEFT);
		StackPane.setMargin(prestigePointsText, new Insets(4));

		// Create an ImageView to display the noble image
		nobleImage.setFitWidth(NOBLE_WIDTH);
		nobleImage.setFitHeight(NOBLE_HEIGHT);

		// Adds the costs with the costs images behind it at the bottom
		VBox costBox = new VBox();
		costBox.setSpacing(-8);
		costBox.setAlignment(Pos.BOTTOM_LEFT);

		// Load the costs and images for those costs of the noble
		for (int i = 0; i < edele.kosten().length; i++) {
			if (edele.kosten()[i] != 0) {
				Kleur kleur = Kleur.valueOf(i);

				ImageView costImageView = new ImageView();
				try {
					File costFile = new File("src/resources/img/requirements/rectangle_" + kleur.kind() + ".png");
					if (!costFile.exists()) {
						throw new FileNotFoundException("Image file not found: " + edele.foto());
					}
					Image costImage = new Image(costFile.toURI().toString());
					costImageView = new ImageView(costImage);
				} catch (Exception e) {
					System.out.println("");
					System.err.println("Unexpected error occurred: " + e.getMessage());
					e.printStackTrace();
				}

				costImageView.setFitWidth(NOBLE_SIZE / 1.5);
				costImageView.setFitHeight(NOBLE_SIZE / 1.5);

				Text costText = new Text(Integer.toString(edele.kosten()[i]));
				costText.setFont(Font.font("Arial", FontWeight.BOLD, NOBLE_FONTSIZE / 1.5));
				costText.setFill(Color.WHITE);
				costText.setStroke(Color.BLACK);
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.BOTTOM_LEFT);
				StackPane.setMargin(costText, new Insets(0, 0, 4, 8));
				costStackPane.setPrefSize(NOBLE_SIZE, NOBLE_SIZE);
				costBox.getChildren().add(costStackPane);
			}
		}

		// Add child nodes
		getChildren().addAll(nobleImage, whiteBackground, prestigePointsText, costBox);
	}

}
