package gui;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resources.Taal;

public class TaalKeuzeScherm extends StackPane {
	private final DomeinController dc;

	public TaalKeuzeScherm(DomeinController dc) {
		this.dc=dc;
		buildGui();

	}

	private void buildGui() {

		this.setAlignment(Pos.CENTER);
		//		this.setSpacing(10);
		//		this.setPadding(new Insets(20));
		Label lblSplendor = new Label("SPLENDOR");
		lblSplendor.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 100));
		lblSplendor.setAlignment(Pos.CENTER);

		File backgroundFile = new File("src/resources/img/background_misc/splendor.jpg");
		Image backgroundImage = new Image(backgroundFile.toURI().toString());
		ImageView backgroundImageView = new ImageView(backgroundImage);

		// Create a region and set its background
		Region backgroundRegion = new Region();
		backgroundRegion.setBackground(new Background(new BackgroundImage(backgroundImage,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(1.0, 1.0, true, true, false, false))));

		// Add the backgroundRegion and the backgroundImageView to a StackPane
		StackPane stackPane = new StackPane(backgroundRegion, backgroundImageView);

		//TODO: repeat uitzetten
		//TODO: knoppen beetje lager

		// knop NL
		Button btnNederlands = new Button("NL");
		btnNederlands.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
		btnNederlands.setPrefSize(100, 50);
		btnNederlands.setOnAction(this::drukOpTaalKnop);
		btnNederlands.setTooltip(new Tooltip("Nederlands"));

		// knop EN
		Button btnEngels = new Button("EN");
		btnEngels.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
		btnEngels.setPrefSize(100, 50);
		btnEngels.setOnAction(this::drukOpTaalKnop);
		btnNederlands.setTooltip(new Tooltip("English"));

		// knop FR
		Button btnFrans = new Button("FR");
		btnFrans.setFont(Font.font("Helvetica",FontWeight.BOLD, 16));
		btnFrans.setPrefSize(100, 50);
		btnFrans.setOnAction(this::drukOpTaalKnop);
		btnNederlands.setTooltip(new Tooltip("Francais"));

		// wrap knoppen in HBox
		HBox btnBox = new HBox(btnNederlands, btnEngels, btnFrans);
		btnBox.setPadding(new Insets(50, 0, 0, 0));
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(10);

		// toevoegen aan VBox
		this.getChildren().addAll(stackPane, btnBox);

	}

	private void drukOpTaalKnop(ActionEvent event) {
		Button button = (Button) event.getTarget();
		String language = button.getText();
		String country = "";

		switch (language) {
		case "NL", "FR", "DE" -> {
			country = "BE";
		}
		case "EN" -> {
			country = "UK";
		}
		}

		Locale l = new Locale(language.toLowerCase(), country);
		ResourceBundle r = ResourceBundle.getBundle("resources/resource", l);
		Taal.setResource(r);



		HoofdSchermSpelers hoofdScherm = new HoofdSchermSpelers(dc);
		Scene scene = new Scene(hoofdScherm,800,600);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setTitle("Spelers selecteren");
		stage.setScene(scene);
		stage.show();
	}
}
