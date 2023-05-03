package gui;

import java.io.File;
import java.util.List;

import domein.DomeinController;
import domein.Edele;
import domein.Kleur;
import domein.Ontwikkelingskaart;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SpeelSpelScherm extends BorderPane {
	private final DomeinController dc;

	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
		dc.voegSpelerToe("user1", 2002);
		dc.voegSpelerToe("user2", 2000);
		dc.startNieuwSpel();
		buildGui();
	}

	private void buildGui() {
		/*------------------------------------------CREATE THE CENTER------------------------------------------*/
		BorderPane center = new BorderPane();
		center.setStyle("-fx-background-color: #008080;");
		this.setCenter(center);

		/*------------------------------------------NOBLE------------------------------------------*/
		List<Edele> edelen = dc.getEdelen();
		HBox noblesBox = new HBox();

		// loop through the nobles and create a NobleNode for each one
		for (Edele edele : edelen) {
			NobleNode nobleNode = new NobleNode(edele);
			noblesBox.getChildren().add(nobleNode);
		}

		noblesBox.setSpacing(10);

		if (edelen.size() < 5) {
			noblesBox.setPadding(new Insets(10, 10, 10, 128 + 28));
		} else {
			noblesBox.setPadding(new Insets(10));
		}

		center.setTop(noblesBox);

		/*------------------------------------------DECK CARDS------------------------------------------*/
		VBox stapelFotosBox = new VBox();

		// Load the image of the deck
		File backgroundFile1 = new File("src/resources/img/card_stacks/level1.png");
		Image backgroundImage1 = new Image(backgroundFile1.toURI().toString());
		ImageView stapel1 = new ImageView(backgroundImage1);
		File backgroundFile2 = new File("src/resources/img/card_stacks/level2.png");
		Image backgroundImage2 = new Image(backgroundFile2.toURI().toString());
		ImageView stapel2 = new ImageView(backgroundImage2);
		File backgroundFile3 = new File("src/resources/img/card_stacks/level3.png");
		Image backgroundImage3 = new Image(backgroundFile3.toURI().toString());
		ImageView stapel3 = new ImageView(backgroundImage3);

		stapel1.setFitWidth(128);
		stapel1.setFitHeight(256);

		stapel2.setFitWidth(128);
		stapel2.setFitHeight(256);

		stapel3.setFitWidth(128);
		stapel3.setFitHeight(256);

		stapelFotosBox.getChildren().addAll(stapel3, stapel2, stapel1);

		stapelFotosBox.setSpacing(10);
		stapelFotosBox.setPadding(new Insets(10));

		center.setLeft(stapelFotosBox);
		/*------------------------------------------DEVELOPMENT CARD------------------------------------------*/
		// Add development cards
		Ontwikkelingskaart[][] niveaus = { dc.getNiveau3Zichtbaar(), dc.getNiveau2Zichtbaar(),
				dc.getNiveau1Zichtbaar() };

		GridPane gridPane = new GridPane();

		// Define the number of columns and rows in the grid
		int numColumns = 4;
		int numRows = 3;

		// Add DevelopmentCardNodes to the grid
		for (int i = 0; i < numRows * numColumns; i++) {
			int row = i / numColumns;
			int col = i % numColumns;
			Ontwikkelingskaart[] niveau = niveaus[row];
			DevelopmentCardNode devCardNode = new DevelopmentCardNode(niveau[col]);
			gridPane.add(devCardNode, col, row);
		}

		// Set some padding and gaps between cells in the grid
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		center.setCenter(gridPane);

		/*------------------------------------------GEMS------------------------------------------*/

		/*------------------------------------------PLAYER INFO------------------------------------------*/
		// top
		Pane top = new Pane();
		Label lblTop = new Label("TOP");
		top.getChildren().add(lblTop);
		BorderPane.setAlignment(lblTop, Pos.CENTER);
		this.setTop(top);

		// bottom
		Pane bot = new Pane();
		Label lblBot = new Label("BOT");
		bot.getChildren().add(lblBot);
		BorderPane.setAlignment(lblBot, Pos.CENTER);
		this.setBottom(lblBot);

		// left
		Pane left = new Pane();
		Label lblLeft = new Label("LEFT");
		left.getChildren().add(lblLeft);
		BorderPane.setAlignment(lblLeft, Pos.CENTER);
		this.setLeft(lblLeft);

		// right
		Pane right = new Pane();
		Label lblRight = new Label("RIGHT");
		right.getChildren().add(lblRight);
		BorderPane.setAlignment(lblRight, Pos.CENTER);
		this.setRight(lblRight);

	}

	public class NobleNode extends StackPane {
		private static final int NOBLE_WIDTH = 128;
		private static final int NOBLE_HEIGHT = 128;
		private static final int NOBLE_SIZE = 40;
		private static final int NOBLE_FONTSIZE = 28;

		public NobleNode(Edele edele) {
			// Load the image of the noble
			File backgroundFile = new File(edele.getEdeleFoto());
			Image backgroundImage = new Image(backgroundFile.toURI().toString());
			ImageView nobleImage = new ImageView(backgroundImage);

			// Adds a white background for the prestigepoints and the colorBonus
			Rectangle whiteBackground = new Rectangle(NOBLE_SIZE / 1.5, NOBLE_WIDTH);
			whiteBackground.setFill(Color.WHITE);
			whiteBackground.setOpacity(0.75);
			StackPane.setAlignment(whiteBackground, Pos.CENTER_LEFT);

			// Adds the prestigepoints to the development card
			Text prestigePointsText = new Text(Integer.toString(edele.getPrestigepunten()));
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
			for (int i = 0; i < edele.getKosten().length; i++) {
				if (edele.getKosten()[i] != 0) {
					Kleur kleur = Kleur.valueOf(i);
					File costFile = new File("src/resources/img/requirements/rectangle_" + kleur.kind() + ".png");
					Image costImage = new Image(costFile.toURI().toString());
					ImageView costImageView = new ImageView(costImage);
					costImageView.setFitWidth(NOBLE_SIZE / 1.5);
					costImageView.setFitHeight(NOBLE_SIZE / 1.5);

					Text costText = new Text(Integer.toString(edele.getKosten()[i]));
					costText.setFont(Font.font("Arial", FontWeight.BOLD, NOBLE_FONTSIZE / 1.5));
					costText.setFill(Color.WHITE);
					costText.setStroke(Color.BLACK);
					costText.setStrokeWidth(1);

					StackPane costStackPane = new StackPane(costImageView, costText);
					costStackPane.setAlignment(Pos.BOTTOM_LEFT);
					StackPane.setMargin(costText, new Insets(0, 0, 0, 8));
					costStackPane.setPrefSize(NOBLE_SIZE, NOBLE_SIZE);
					costBox.getChildren().add(costStackPane);
				}
			}

			// Add child nodes
			getChildren().addAll(nobleImage, whiteBackground, prestigePointsText, costBox);
		}
	}

	public class DevelopmentCardNode extends StackPane {
		private final int DEV_CARD_WIDTH = 128;
		private final int DEV_CARD_HEIGHT = 256;
		private final int DEV_CARD_SIZE = 40;
		private final int DEV_CARD_FONTSIZE = 28;

		public DevelopmentCardNode(Ontwikkelingskaart ontwikkelingskaart) {
			// Load background image for development card
			File backgroundFile = new File(ontwikkelingskaart.getFotoOntwikkelingskaart());
			Image backgroundImage = new Image(backgroundFile.toURI().toString());
			ImageView backgroundImageView = new ImageView(backgroundImage);
			backgroundImageView.setFitWidth(DEV_CARD_WIDTH);
			backgroundImageView.setFitHeight(DEV_CARD_HEIGHT);

			// Load color bonus image
			File colorBonusFile = new File(
					String.format("src/resources/img/gems/%s.png", ontwikkelingskaart.getKleurBonus().kind()));
			Image colorBonusImage = new Image(colorBonusFile.toURI().toString());
			ImageView colorBonusImageView = new ImageView(colorBonusImage);
			colorBonusImageView.setFitWidth(DEV_CARD_SIZE);
			colorBonusImageView.setFitHeight(DEV_CARD_SIZE);
			StackPane.setAlignment(colorBonusImageView, Pos.TOP_RIGHT);

			// Adds the prestigepoints to the development card
			Text prestigePointsText = new Text(Integer.toString(ontwikkelingskaart.getPrestigepunten()));
			prestigePointsText.setFont(Font.font("Arial", FontWeight.BOLD, DEV_CARD_FONTSIZE));
			prestigePointsText.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 1;");
			StackPane.setAlignment(prestigePointsText, Pos.TOP_LEFT);
			StackPane.setMargin(prestigePointsText, new Insets(4));

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

			for (int i = 0; i < ontwikkelingskaart.getKosten().length; i++) {
				Kleur kleur = Kleur.valueOf(i);
				File costFile = new File(String.format("src/resources/img/costs/circle_%s.png", kleur.kind()));
				Image costImage = new Image(costFile.toURI().toString());
				ImageView costImageView = new ImageView(costImage);
				costImageView.setFitWidth(DEV_CARD_SIZE);
				costImageView.setFitHeight(DEV_CARD_SIZE);

				String cost = Integer.toString(ontwikkelingskaart.getKosten()[i]);
				Text costText = new Text(cost);
				costText.setFont(Font.font("Arial", FontWeight.BOLD, DEV_CARD_FONTSIZE / 1.25));
				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.CENTER);
				costStackPane.setPrefSize(DEV_CARD_SIZE, DEV_CARD_SIZE);

				if (i < 3) {
					firstThreeCostsBox.getChildren().add(costStackPane);
				} else {
					remainingTwoCostsBox.getChildren().add(costStackPane);
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
}
