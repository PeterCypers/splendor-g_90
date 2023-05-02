package gui;

import java.io.File;

import domein.DomeinController;
import domein.Ontwikkelingskaart;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
		Ontwikkelingskaart[] niveau1Zichtbaar = dc.getNiveau1Zichtbaar();
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

		private ImageView backgroundImageView;
		private ImageView colorBonusImageView;
		private Text[] costTexts;

		public DevelopmentCardNode(Ontwikkelingskaart kaart) {
			// Load background image
			File backgroundOntwikkelingskaartFotoFile = new File(kaart.getFotoOntwikkelingskaart());
			Image backgroundImage = new Image(backgroundOntwikkelingskaartFotoFile.toURI().toString());
			backgroundImageView = new ImageView(backgroundImage);

			backgroundImageView.setFitWidth(128);
			backgroundImageView.setFitHeight(256);

			// Load color bonus image
			File kleurBonusFotoFile = new File("src/resources/img/costs/circle_diamond.png");
			Image colorBonusImage = new Image(kleurBonusFotoFile.toURI().toString());
			colorBonusImageView = new ImageView(colorBonusImage);

			colorBonusImageView.setFitWidth(40);
			colorBonusImageView.setFitHeight(40);
			StackPane.setAlignment(colorBonusImageView, Pos.BOTTOM_LEFT);

			// Load cost texts
			costTexts = new Text[kaart.getKosten().length];
			for (int i = 0; i < costTexts.length; i++) {
				String cost = Integer.toString(kaart.getKosten()[i]);
				costTexts[i] = new Text(cost);
				costTexts[i].setFont(Font.font("Arial", FontWeight.BOLD, 18));
				// costTexts[i].setFill(Kleur.WHITE);
				// costTexts[i].setStroke(Kleur.ZWART);
				costTexts[i].setStrokeWidth(1);
				StackPane.setAlignment(costTexts[i], Pos.BOTTOM_LEFT);
			}

			// Add child nodes
			getChildren().addAll(backgroundImageView, colorBonusImageView);
			getChildren().addAll(costTexts);
			setPrefSize(backgroundImageView.getFitWidth(), backgroundImageView.getFitHeight());
		}
	}

}
