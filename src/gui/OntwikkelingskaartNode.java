package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import domein.Kleur;
import domein.Ontwikkelingskaart;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OntwikkelingskaartNode extends StackPane {
	private final int DEV_CARD_WIDTH = 128;
	private final int DEV_CARD_HEIGHT = 256;
	private final int DEV_CARD_SIZE = 40;
	private final int DEV_CARD_FONTSIZE = 28;
	private final int DEV_CARD_MARGIN = 4;

	public OntwikkelingskaartNode(Ontwikkelingskaart ontwikkelingskaart) {
		// Load background image for development card
		ImageView backgroundImageView = new ImageView();
		File backgroundFile = new File(ontwikkelingskaart.getFotoOntwikkelingskaart());
		if (backgroundFile.exists()) {
			try (InputStream inputStream = new FileInputStream(backgroundFile)) {
				Image backgroundImage = new Image(inputStream);
				backgroundImageView = new ImageView(backgroundImage);
				backgroundImageView.setFitWidth(DEV_CARD_WIDTH);
				backgroundImageView.setFitHeight(DEV_CARD_HEIGHT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(
					"Background image file of development card does not exist: " + backgroundFile.getAbsolutePath());
		}

		// Load color bonus image
		ImageView colorBonusImageView = new ImageView();
		File colorBonusFile = new File(
				String.format("src/resources/img/gems/%s.png", ontwikkelingskaart.getKleurBonus().kind()));
		if (colorBonusFile.exists()) {
			try (InputStream is = new FileInputStream(colorBonusFile)) {
				Image colorBonusImage = new Image(is);
				colorBonusImageView = new ImageView(colorBonusImage);
				colorBonusImageView.setFitWidth(DEV_CARD_SIZE);
				colorBonusImageView.setFitHeight(DEV_CARD_SIZE);
				StackPane.setAlignment(colorBonusImageView, Pos.TOP_RIGHT);
			} catch (IOException e) {
				System.err.println("Error loading color bonus image: " + e.getMessage());
			}
		} else {
			System.err.println("Color bonus image file not found: " + colorBonusFile.getPath());
		}

		// Adds the prestigepoints to the development card
		Text prestigePointsText = new Text(String.format("%d", ontwikkelingskaart.getPrestigepunten()));
		// prestigePointsText.getStyleClass().add("prestige-points-text");
		prestigePointsText.setFont(Font.font("Arial", FontWeight.BOLD, DEV_CARD_FONTSIZE));
		prestigePointsText.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 1;");
		StackPane.setAlignment(prestigePointsText, Pos.TOP_LEFT);
		StackPane.setMargin(prestigePointsText, new Insets(DEV_CARD_MARGIN));

		// Adds a white background for the prestigepoints and the colorBonus
		Rectangle colorBonusBackground = new Rectangle(DEV_CARD_WIDTH, DEV_CARD_SIZE);
		colorBonusBackground.setFill(Color.WHITE);
		colorBonusBackground.setOpacity(0.75);
		StackPane.setAlignment(colorBonusBackground, Pos.TOP_CENTER);

		// Adds the costs with the costs images behind it at the bottom
		HBox costBox = new HBox();
		costBox.setAlignment(Pos.BOTTOM_LEFT);

		VBox firstThreeCostsBox = new VBox();
		firstThreeCostsBox.setAlignment(Pos.BOTTOM_LEFT);

		VBox remainingTwoCostsBox = new VBox();
		remainingTwoCostsBox.setAlignment(Pos.BOTTOM_LEFT);

		int firstThreeCostsCount = 0;
		for (int i = 0; i < ontwikkelingskaart.getKosten().length; i++) {
			int costValue = ontwikkelingskaart.getKosten()[i];
			if (costValue > 0) {
				Kleur kleur = Kleur.valueOf(i);
				File costFile = new File(String.format("src/resources/img/costs/circle_%s.png", kleur.kind()));
				Image costImage = new Image(costFile.toURI().toString());
				ImageView costImageView = new ImageView(costImage);
				costImageView.setFitWidth(DEV_CARD_SIZE);
				costImageView.setFitHeight(DEV_CARD_SIZE);

				String cost = Integer.toString(costValue);
				Text costText = new Text(cost);
				costText.setFont(Font.font("Arial", FontWeight.BOLD, DEV_CARD_FONTSIZE / 1.25));
				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.CENTER);
				costStackPane.setPrefSize(DEV_CARD_SIZE, DEV_CARD_SIZE);

				if (firstThreeCostsCount < 3) {
					firstThreeCostsBox.getChildren().add(costStackPane);
					firstThreeCostsCount++;
				} else {
					remainingTwoCostsBox.getChildren().add(costStackPane);
				}
			}
		}

		costBox.getChildren().addAll(firstThreeCostsBox, remainingTwoCostsBox);

		// Add child nodes
		getChildren().addAll(backgroundImageView, colorBonusBackground, prestigePointsText, costBox,
				colorBonusImageView);

		// Add child nodes
		setPrefSize(backgroundImageView.getFitWidth(), backgroundImageView.getFitHeight());

		// Create a rectangle with rounded corners as a clip
		Rectangle clip = new Rectangle(DEV_CARD_WIDTH, DEV_CARD_HEIGHT);
		clip.setArcWidth(20);
		clip.setArcHeight(20);
		setClip(clip);

		// Set the background of the stack pane to white
		setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
