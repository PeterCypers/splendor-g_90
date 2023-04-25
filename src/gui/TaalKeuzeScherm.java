package gui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


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
		splendorLabel.setFont(Font.font("Verdana",20));
		splendorLabel.setAlignment(Pos.CENTER);

		//knop NL
		Button btnNederlands = new Button("NL");
		btnNederlands.setFont(Font.font("Verdana",16));
		btnNederlands.setPrefSize(100, 50);
		btnNederlands.setOnAction(e->{
			//TODO programma in nederlands
		});

		//knop EN
		Button btnEngels = new Button("EN");
		btnEngels.setFont(Font.font("Verdana",16));
		btnEngels.setPrefSize(100, 50);
		btnEngels.setOnAction(e->{
			//TODO programma in engels
		});


		//knop FR
		Button btnFrans = new Button("FR");
		btnFrans.setFont(Font.font("Verdana",16));
		btnFrans.setPrefSize(100, 50);
		btnFrans.setOnAction(e->{
			//TODO programma in frans
		});


		// toevoegen aan grid
		this.add(splendorLabel, 1, 0, 3, 1);
		this.add(btnNederlands, 0, 1);
		this.add(btnEngels, 1, 1);
		this.add(btnFrans, 2, 1);

	}

}
