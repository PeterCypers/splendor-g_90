package gui;

import java.io.File;

import domein.DomeinController;
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
		// center
		Pane center = new Pane();
		Label lblCenter = new Label("CENTER");
		center.getChildren().add(lblCenter);
		this.setCenter(lblCenter);

		// Center pane
		center.setStyle("-fx-background-color: #008080;"); // optional: set background color for testing purposes
		this.setCenter(center);

		// Add development cards
//		DevelopmentCardPane devCardPane = new DevelopmentCardPane();
//		devCardPane.setLayoutX(50); // adjust x-position as needed
//		devCardPane.setLayoutY(50); // adjust y-position as needed
//		center.getChildren().add(devCardPane);
		Ontwikkelingskaart[] niveau1Zichtbaar = dc.getNiveau3Zichtbaar();
		DevelopmentCardNode devCardNode = new DevelopmentCardNode(niveau1Zichtbaar[0]);
		center.getChildren().add(devCardNode);

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

	public class DevelopmentCardNode extends StackPane {
		public DevelopmentCardNode(Ontwikkelingskaart ontwikkelingskaart) {
			final int WIDTH = 128;
			final int HEIGHT = 256;
			final int SIZE = 40;
			final int FONTSIZE = 28;

			// Load background image for development card
			File backgroundFile = new File(ontwikkelingskaart.getFotoOntwikkelingskaart());
			Image backgroundImage = new Image(backgroundFile.toURI().toString());
			ImageView backgroundImageView = new ImageView(backgroundImage);
			backgroundImageView.setFitWidth(WIDTH);
			backgroundImageView.setFitHeight(HEIGHT);

			// Load color bonus image
			File colorBonusFile = new File(
					String.format("src/resources/img/gems/%s.png", ontwikkelingskaart.getKleurBonus().kind()));
			Image colorBonusImage = new Image(colorBonusFile.toURI().toString());
			ImageView colorBonusImageView = new ImageView(colorBonusImage);
			colorBonusImageView.setFitWidth(SIZE);
			colorBonusImageView.setFitHeight(SIZE);
			StackPane.setAlignment(colorBonusImageView, Pos.TOP_RIGHT);

			// Adds the prestigepoints to the development card
			Text prestigePointsText = new Text(Integer.toString(ontwikkelingskaart.getPrestigepunten()));
			prestigePointsText.setFont(Font.font("Arial", FontWeight.BOLD, FONTSIZE));
			prestigePointsText.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 1;");
			StackPane.setAlignment(prestigePointsText, Pos.TOP_LEFT);
			StackPane.setMargin(prestigePointsText, new Insets(4));

			// Adds a white background for the prestigepoints and the colorBonus
			Rectangle colorBonusBackground = new Rectangle(WIDTH, SIZE);
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
				costImageView.setFitWidth(SIZE);
				costImageView.setFitHeight(SIZE);

				String cost = Integer.toString(ontwikkelingskaart.getKosten()[i]);
				Text costText = new Text(cost);
				costText.setFont(Font.font("Arial", FontWeight.BOLD, FONTSIZE / 1.25));
				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.CENTER);
				costStackPane.setPrefSize(SIZE, SIZE);

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
			Rectangle clip = new Rectangle(WIDTH, HEIGHT);
			clip.setArcWidth(20);
			clip.setArcHeight(20);
			setClip(clip);

			// Set the background of the stack pane to white
			setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}
}
