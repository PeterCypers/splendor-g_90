package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SpeelSpelScherm extends BorderPane {
	private final DomeinController dc;

	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
		buildGui();
	}
	private void buildGui() {

		//center
		Label lblCenter = new Label("CENTER");
		this.setCenter(new Pane(lblCenter));

		//top
		Label lblTop = new Label("TOP");
		this.setTop(new Pane(lblTop));
		BorderPane.setAlignment(lblTop, Pos.CENTER);

		//bot
		Label lblBot = new Label("BOT");
		this.setBottom(new Pane(lblBot));
		BorderPane.setAlignment(lblBot, Pos.CENTER);

		//left
		Label lblLeft = new Label("LEFT");
		this.setLeft(new Pane(lblLeft));
		BorderPane.setAlignment(lblLeft, Pos.CENTER);

		//right
		Label lblRight = new Label("RIGHT");
		this.setRight(new Pane(lblRight));
		BorderPane.setAlignment(lblRight, Pos.CENTER);

	}

}
