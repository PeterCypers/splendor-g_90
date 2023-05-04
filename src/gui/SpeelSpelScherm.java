package gui;

import java.io.File;
import java.util.HashMap;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SpeelSpelScherm extends BorderPane {
	private final DomeinController dc;

	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
//		dc.voegSpelerToe("user1", 2002);
//		dc.voegSpelerToe("user2", 2000);
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
			EdeleNode edeleNode = new EdeleNode(edele);
			noblesBox.getChildren().add(edeleNode);
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

		GridPane ontwikkelingskaartGridPane = new GridPane();

		// Define the number of columns and rows in the grid
		int numColumns = 4;
		int numRows = 3;

		// Add OntwikkelingskaartNodes to the grid
		for (int i = 0; i < numRows * numColumns; i++) {
			int row = i / numColumns;
			int col = i % numColumns;
			Ontwikkelingskaart[] niveau = niveaus[row];
			OntwikkelingskaartNode devCardNode = new OntwikkelingskaartNode(niveau[col]);
			ontwikkelingskaartGridPane.add(devCardNode, col, row);
		}

		// Set some padding and gaps between cells in the grid
		ontwikkelingskaartGridPane.setPadding(new Insets(10));
		ontwikkelingskaartGridPane.setHgap(10);
		ontwikkelingskaartGridPane.setVgap(10);

		center.setCenter(ontwikkelingskaartGridPane);

		/*------------------------------------------GEMS------------------------------------------*/
		VBox gemsBox = new VBox();

		HashMap<Kleur, Integer> fichestapels = dc.getFicheStapels();

		int GEMS_WIDTH_AND_HEIGHT = 128;
		int GEMS_FONTSIZE = 56;
		int GEMS_STROKE = 2;

		for (Kleur kleur : Kleur.values()) {
			// Create a new gem image view
			File gemFile = new File(String.format("src/resources/img/tokens/token_%s.png", kleur.kind()));
			Image gemImage = new Image(gemFile.toURI().toString());
			ImageView gemImageView = new ImageView(gemImage);

			int amount = fichestapels.get(kleur);

			if (amount > 0) {
				// Set the size of the gem image view
				gemImageView.setFitWidth(GEMS_WIDTH_AND_HEIGHT);
				gemImageView.setFitHeight(GEMS_WIDTH_AND_HEIGHT);

				// Create a new VBox to contain the gem image view and the amount text
				StackPane gemBox = new StackPane();
				gemBox.setAlignment(Pos.CENTER);

				// Create the amount text and style it
				Text amountText = new Text(Integer.toString(amount));
				amountText.setFont(Font.font("Arial", FontWeight.BOLD, GEMS_FONTSIZE));
				amountText.setFill(Color.BLACK);
				amountText.setStroke(Color.WHITE);
				amountText.setStrokeWidth(GEMS_STROKE);

				// Add the gem image view and the amount text to the VBox
				gemBox.getChildren().addAll(gemImageView, amountText);

				// Add the VBox containing the gem to the gemsBox
				gemsBox.getChildren().add(gemBox);
			}
		}

		// Display the gemsBox to the left of the development cards
		center.setRight(gemsBox);

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

}
