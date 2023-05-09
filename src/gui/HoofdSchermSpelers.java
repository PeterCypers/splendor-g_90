package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HoofdSchermSpelers extends BorderPane {
	private final DomeinController dc;
	private Label lblTitle;
	public HoofdSchermSpelers(DomeinController dc)
	{
		this.dc = dc;
		buildGui();
	}

	private void buildGui() {

	    lblTitle = new Label("SPLENDOR");
	    lblTitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 100));
	    lblTitle.setPrefHeight(200);
	    
	    DetailSpelers ds = new DetailSpelers(dc);
	    OverzichtSpelers os = new OverzichtSpelers(dc, ds);
	    

	    HBox centerContainer = new HBox(20, os, ds);
	    centerContainer.setAlignment(Pos.CENTER);
	    ds.setAlignment(Pos.TOP_CENTER);
	    os.setAlignment(Pos.TOP_CENTER);
	    
	    this.setTop(lblTitle);
	    BorderPane.setAlignment(lblTitle, Pos.CENTER);
	    this.setCenter(centerContainer);
	}

}
