package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import domein.Kleur;
import domein.Ontwikkelingskaart;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SpelerNode extends StackPane {
	private static final int PLAYER_BOX_WIDTH = 256 + 32;
	private static final int PLAYER_BOX_HEIGHT = 128;
	private static final int PLAYER_BOX_ARC_WIDTH = 20;
	private static final int PLAYER_BOX_ARC_HEIGHT = 20;
	private static final int PLAYER_BOX_STROKE_WIDTH = 15;
	private static final int STAR_IMAGE_SIZE = 40;
	private static final int REQUIREMENTS_SIZE = 40;
	private static final int PLAYER_FONTSIZE = 28;

	public SpelerNode(SpelerDTO speler) {

		/*--------------------------RECTANGLE AS BACKGROUND--------------------------*/
		Rectangle playerInfoBackground = new Rectangle(PLAYER_BOX_WIDTH, PLAYER_BOX_HEIGHT);
		playerInfoBackground.setArcWidth(PLAYER_BOX_ARC_WIDTH);
		playerInfoBackground.setArcHeight(PLAYER_BOX_ARC_HEIGHT);
		playerInfoBackground.setFill(Color.web("#bf9e8e"));
		playerInfoBackground.setStrokeWidth(PLAYER_BOX_STROKE_WIDTH);
		if (speler.aanDeBeurt()) {
			playerInfoBackground.setStyle("-fx-stroke: #d13111;");
		} else {
			playerInfoBackground.setStyle("-fx-stroke: #00162b;");
		}

		/*--------------------------NAME AND BIRTH YEAR--------------------------*/
		// create a label with the player's name
		Label playerNameLabel = new Label(speler.gebruikersnaam() + " - " + speler.geboortejaar());
		playerNameLabel.setPadding(new Insets(5, 20, 5, 20));
		playerNameLabel.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #00162b; -fx-text-fill: white;"
						+ "-fx-background-radius:  0 0 10 10;");
		StackPane.setAlignment(playerNameLabel, Pos.TOP_CENTER);

		/*--------------------------PRESTIGE POINTS--------------------------*/
		// create a label with the player's prestige points
		StackPane prestigepoints = new StackPane();

		// create prestigepoinstlabel
		Label prestigepointsLabel = new Label(Integer.toString(speler.aantalPrestigepunten()));
		prestigepointsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

		// star image for behind the prestigepoints
		ImageView starImageView = new ImageView();
		File starFile = new File("src/resources/img/other_misc/star-solid.png");
		if (starFile.exists()) {
			try (InputStream inputStream = new FileInputStream(starFile)) {
				Image backgroundImage = new Image(inputStream);
				starImageView = new ImageView(backgroundImage);
				starImageView.setFitWidth(STAR_IMAGE_SIZE);
				starImageView.setFitHeight(STAR_IMAGE_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err
					.println("Background image file of development card does not exist: " + starFile.getAbsolutePath());
		}

		StackPane.setAlignment(prestigepoints, Pos.CENTER_RIGHT);

		/*--------------------------DEV CARDS--------------------------*/
		// add the development cards that the players has
		HBox devCardsInHand = new HBox();
		// devCardsInHand.setSpacing(-8);
		// devCardsInHand.setAlignment(Pos.BOTTOM_CENTER);

		int[] amounts = new int[5];

		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = speler.ontwikkelingskaartenInHand();

		for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
			amounts[ontwk.getKleurBonus().getKleur()]++;
		}

		// Load the costs and images for those costs of the noble
		for (int i = 0; i < 5; i++) {
			Kleur kleur = Kleur.valueOf(i);
			File costFile = new File("src/resources/img/requirements/rectangle_" + kleur.kind() + ".png");
			Image costImage = new Image(costFile.toURI().toString());
			ImageView requirementsImageView = new ImageView(costImage);
			requirementsImageView.setFitWidth(REQUIREMENTS_SIZE / 1.5);
			requirementsImageView.setFitHeight(REQUIREMENTS_SIZE / 1.5);

			Text amountText = new Text(Integer.toString(amounts[i]));
			amountText.setFont(Font.font("Arial", FontWeight.BOLD, PLAYER_FONTSIZE / 1.5));
			amountText.setFill(Color.WHITE);
			amountText.setStroke(Color.BLACK);
			amountText.setStrokeWidth(1);

			StackPane costStackPane = new StackPane(requirementsImageView, amountText);
			// costStackPane.setAlignment(Pos.BOTTOM_LEFT);
			// StackPane.setMargin(amountText, new Insets(0, 0, 4, 8));
			costStackPane.setPrefSize(REQUIREMENTS_SIZE, REQUIREMENTS_SIZE);
			devCardsInHand.getChildren().add(costStackPane);

		}

		StackPane.setAlignment(devCardsInHand, Pos.CENTER);

		/*--------------------------GEMS--------------------------*/
		// add the gems the player has
		HBox gemsInHand = new HBox();

		HashMap<Kleur, Integer> edelsteenfichesInHand = speler.edelsteenfichesInHand();

		for (Kleur kleur : Kleur.values()) {
			File gemTokenFile = new File("src/resources/img/tokens/token_" + kleur.kind() + ".png");
			Image gemTokenImage = new Image(gemTokenFile.toURI().toString());
			ImageView gemTokenImageView = new ImageView(gemTokenImage);
			gemTokenImageView.setFitWidth(REQUIREMENTS_SIZE / 1.5);
			gemTokenImageView.setFitHeight(REQUIREMENTS_SIZE / 1.5);

			Integer amount = edelsteenfichesInHand.get(kleur);
			if (amount == null) {
				amount = 0;
			}

			Text amountText = new Text(Integer.toString(amount));
			amountText.setFont(Font.font("Arial", FontWeight.BOLD, PLAYER_FONTSIZE / 1.5));
			amountText.setFill(Color.WHITE);
			amountText.setStroke(Color.BLACK);
			amountText.setStrokeWidth(1);

			StackPane gemStackPane = new StackPane(gemTokenImageView, amountText);
			gemStackPane.setPrefSize(REQUIREMENTS_SIZE, REQUIREMENTS_SIZE);
			gemsInHand.getChildren().add(gemStackPane);

		}

		StackPane.setAlignment(gemsInHand, Pos.CENTER);

		/*--------------------------NOBLES--------------------------*/
		// shows nobles in hand
		// TODO

		/*--------------------------ADDING--------------------------*/
		VBox playerInfoItems = new VBox();
		playerInfoItems.setAlignment(Pos.BOTTOM_CENTER);
		VBox.setMargin(playerInfoItems, new Insets(15));
		playerInfoItems.setPadding(new Insets(15));

		HBox playerInfoItems2 = new HBox();

		playerInfoItems.getChildren().add(devCardsInHand);
		playerInfoItems.getChildren().add(gemsInHand);

		playerInfoItems2.getChildren().add(playerInfoItems);
		playerInfoItems2.getChildren().add(prestigepoints);

		/*--------------------------ADDING 2--------------------------*/
		// add the star image behind the prestigepoints
		prestigepoints.getChildren().addAll(starImageView, prestigepointsLabel);

		// add the rest of items to the player box
		getChildren().addAll(playerInfoBackground, playerNameLabel, playerInfoItems2);

	}
}
