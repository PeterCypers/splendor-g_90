package gui;

import java.io.File;

import domein.DomeinController;
import domein.Kleur;
import domein.Ontwikkelingskaart;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
			// Load background image for development card
			File backgroundFile = new File(ontwikkelingskaart.getFotoOntwikkelingskaart());
			Image backgroundImage = new Image(backgroundFile.toURI().toString());
			ImageView backgroundImageView = new ImageView(backgroundImage);
			backgroundImageView.setFitWidth(128);
			backgroundImageView.setFitHeight(256);

			// Load color bonus image
			File colorBonusFile = new File(
					String.format("src/resources/img/gems/%s.png", ontwikkelingskaart.getKleurBonus().kind()));
			Image colorBonusImage = new Image(colorBonusFile.toURI().toString());
			ImageView colorBonusImageView = new ImageView(colorBonusImage);
			colorBonusImageView.setFitWidth(40);
			colorBonusImageView.setFitHeight(40);
			StackPane.setAlignment(colorBonusImageView, Pos.TOP_RIGHT);

			// Adds the prestigepoints to the development card
			Text prestigePointsText = new Text(Integer.toString(ontwikkelingskaart.getPrestigepunten()));
			prestigePointsText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
			prestigePointsText.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 1;");
			StackPane.setAlignment(prestigePointsText, Pos.TOP_LEFT);

			// Adds a white background for the prestigepoints and the colorBonus
			Rectangle colorBonusBackground = new Rectangle(128, 40);
			colorBonusBackground.setFill(Color.WHITE);
			colorBonusBackground.setOpacity(0.75);
			StackPane.setAlignment(colorBonusBackground, Pos.TOP_CENTER);
//			StackPane.setMargin(colorBonusBackground, new Insets(2, 2, 0, 4));

			// Adds the costs with the costs images behind it at the bottom
//			HBox costBox = new HBox();
//			costBox.setAlignment(Pos.BOTTOM_CENTER);
//			for (int i = 0; i < ontwikkelingskaart.getKosten().length; i++) {
//				Kleur kleur = Kleur.valueOf(i);
//				File costFile = new File(String.format("src/resources/img/costs/circle_%s.png", kleur.kind()));
//				Image costImage = new Image(costFile.toURI().toString());
//				ImageView costImageView = new ImageView(costImage);
//				costImageView.setFitWidth(25);
//				costImageView.setFitHeight(25);
//
//				String cost = Integer.toString(ontwikkelingskaart.getKosten()[i]);
//				Text costText = new Text(cost);
//				costText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
//				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
//				costText.setStrokeWidth(1);
//
//				StackPane costStackPane = new StackPane(costImageView, costText);
//				costStackPane.setAlignment(Pos.BOTTOM_CENTER);
//				costStackPane.setPrefSize(30, 30);
//
//				costBox.getChildren().add(costStackPane);
//			}
			// ---------------------------------------------------------------------------------------------
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
				costImageView.setFitWidth(25);
				costImageView.setFitHeight(25);

				String cost = Integer.toString(ontwikkelingskaart.getKosten()[i]);
				Text costText = new Text(cost);
				costText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.BOTTOM_CENTER);
				costStackPane.setPrefSize(30, 30);

				if (i < 3) {
					firstThreeCostsBox.getChildren().add(costStackPane);
				} else {
					remainingTwoCostsBox.getChildren().add(costStackPane);
				}
			}

			costBox.getChildren().addAll(firstThreeCostsBox, remainingTwoCostsBox);

			// ---------------------------------------------------------------------------------------------
			VBox costBox2 = new VBox();
			costBox2.setAlignment(Pos.BOTTOM_CENTER);
			HBox bottomRow = new HBox();
			bottomRow.setAlignment(Pos.CENTER);
			HBox topRow = new HBox();
			topRow.setAlignment(Pos.CENTER);

			for (int i = 0; i < ontwikkelingskaart.getKosten().length; i++) {
				Kleur kleur = Kleur.valueOf(i);
				File costFile = new File(String.format("src/resources/img/costs/circle_%s.png", kleur.kind()));
				Image costImage = new Image(costFile.toURI().toString());
				ImageView costImageView = new ImageView(costImage);
				costImageView.setFitWidth(25);
				costImageView.setFitHeight(25);

				String cost = Integer.toString(ontwikkelingskaart.getKosten()[i]);
				Text costText = new Text(cost);
				costText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				costText.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
				costText.setStrokeWidth(1);

				StackPane costStackPane = new StackPane(costImageView, costText);
				costStackPane.setAlignment(Pos.BOTTOM_CENTER);
				costStackPane.setPrefSize(30, 30);

				if (i < 2) {
					topRow.getChildren().add(costStackPane);
				} else {
					bottomRow.getChildren().add(costStackPane);
				}
			}

			costBox2.getChildren().addAll(topRow, bottomRow);

			// -------------------------------------------------------------------------------------------------

			// Add child nodes
			getChildren().addAll(backgroundImageView, colorBonusBackground, prestigePointsText, costBox,
					colorBonusImageView);

			// Add child nodes
			setPrefSize(backgroundImageView.getFitWidth(), backgroundImageView.getFitHeight());
		}
	}
}
