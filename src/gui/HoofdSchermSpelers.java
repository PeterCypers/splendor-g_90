package gui;

import domein.DomeinController;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HoofdSchermSpelers extends BorderPane {
	private final DomeinController dc;

	public HoofdSchermSpelers(DomeinController dc)
	{
		this.dc = dc;
		buildGui();
	}

	private void buildGui()
	{
		Pane top = new Pane();
		top.setPrefHeight(100);
		Label titleLabel = new Label("SPLENDOR");
		
		titleLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 75));
		top.getChildren().add(titleLabel);
		this.setTop(top);

		DetailSpelers ds = new DetailSpelers(dc);
		OverzichtSpelers os = new OverzichtSpelers(dc, ds);
		this.setLeft(os);
		this.setCenter(ds);

	}
}
