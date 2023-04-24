package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import main.StartUpGui;

public class TaalKeuzeScherm extends GridPane {

	private Button btnNederlands;
	private Button btnEngels;
	private Button btnFrans;

	public TaalKeuzeScherm() {
		buildGui();
	}

	private void buildGui() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(20));

		Label lblTaalKeuze = new Label("Kies je taal:");
		Font verdanaFont = Font.font("Verdana", 16);
		lblTaalKeuze.setFont(verdanaFont);

		Button btnNederlands = new Button("Nederlands");
		btnNederlands.setPrefSize(200, 50);
	
		btnNederlands.setFont(verdanaFont);

		Button btnEngels = new Button("Engels");
		btnEngels.setPrefSize(200, 50);
	

		btnEngels.setFont(verdanaFont);

		Button btnFrans = new Button("Frans");
		btnFrans.setPrefSize(200, 50);
		
		btnFrans.setFont(verdanaFont);

		this.add(lblTaalKeuze, 0, 0, 3, 1);
		this.add(btnNederlands, 0, 1);
		this.add(btnEngels, 1, 1);
		this.add(btnFrans, 2, 1);
	}
	public void setOnTaalGekozen(EventHandler<ActionEvent> handler) {
		btnNederlands.setOnAction(handler);
		btnEngels.setOnAction(handler);
		btnFrans.setOnAction(handler);
	}

}
