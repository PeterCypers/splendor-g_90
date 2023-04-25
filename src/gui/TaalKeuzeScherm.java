package gui;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class TaalKeuzeScherm extends GridPane {

	public TaalKeuzeScherm() {
		buildGui();
	}

	private void buildGui() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));

		Label splendorLabel = new Label("SPLENDOR");
		splendorLabel.setFont(Font.font("Helvetica",FontWeight.EXTRA_BOLD,20));
		splendorLabel.setAlignment(Pos.CENTER);

		//knop NL
		Button btnNederlands = new Button("NL");
		btnNederlands.setFont(Font.font("Helvetica",16));
		btnNederlands.setPrefSize(100, 50);
		btnNederlands.setOnAction(this::buttonPushed);

		//knop EN
		Button btnEngels = new Button("EN");
		btnEngels.setFont(Font.font("Helvetica",16));
		btnEngels.setPrefSize(100, 50);
		btnEngels.setOnAction(this::buttonPushed);


		//knop FR
		Button btnFrans = new Button("FR");
		btnFrans.setFont(Font.font("Helvetica",16));
		btnFrans.setPrefSize(100, 50);
		btnFrans.setOnAction(this::buttonPushed);


		// toevoegen aan grid
		this.add(splendorLabel, 1, 0, 3, 1);
		this.add(btnNederlands, 0, 1);
		this.add(btnEngels, 1, 1);
		this.add(btnFrans, 2, 1);



	}
	private void buttonPushed(ActionEvent event) {	        
		
		SpelersToevoegenScherm spelersToevoegen = new SpelersToevoegenScherm();
		Scene scene = new Scene(spelersToevoegen, 500, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

}
