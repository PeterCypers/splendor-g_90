package gui;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import resources.Taal;

public class TaalKeuzeScherm extends VBox {
private final DomeinController dc;
	public TaalKeuzeScherm(DomeinController dc) {
		this.dc=dc;
		buildGui();
	}

	private void buildGui() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.setPadding(new Insets(20));
		Label lblSplendor = new Label("SPLENDOR");
		lblSplendor.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 100));
		lblSplendor.setAlignment(Pos.CENTER);

		// knop NL
		Button btnNederlands = new Button("NL");
		btnNederlands.setFont(Font.font("Helvetica", 16));
		btnNederlands.setPrefSize(100, 50);
		btnNederlands.setOnAction(this::buttonPushed);
		btnNederlands.setTooltip(new Tooltip("Nederlands"));

		// knop EN
		Button btnEngels = new Button("EN");
		btnEngels.setFont(Font.font("Helvetica", 16));
		btnEngels.setPrefSize(100, 50);
		btnEngels.setOnAction(this::buttonPushed);
		btnNederlands.setTooltip(new Tooltip("English"));

		// knop FR
		Button btnFrans = new Button("FR");
		btnFrans.setFont(Font.font("Helvetica", 16));
		btnFrans.setPrefSize(100, 50);
		btnFrans.setOnAction(this::buttonPushed);
		btnNederlands.setTooltip(new Tooltip("Francais"));

		// wrap knoppen in HBox
		HBox btnBox = new HBox(btnNederlands, btnEngels, btnFrans);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(10);

		// toevoegen aan VBox
		this.getChildren().addAll(lblSplendor, btnBox);

	}

	private void buttonPushed(ActionEvent event) {
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
		Scene scene = new Scene(hoofdScherm);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setTitle("Spelers selecteren");
		stage.setScene(scene);
		stage.show();
	}
}
