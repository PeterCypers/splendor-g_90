package gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.StartUpGui;
import resources.Taal;

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
	    this.setTop(lblTitle);
	    BorderPane.setAlignment(lblTitle, Pos.CENTER);
	    
	    Button btnKeerTerug = new Button(Taal.getString("goBack"));
	    btnKeerTerug.setOnAction(this::drukKeerTerug);
	    this.setTop(btnKeerTerug);
	    BorderPane.setAlignment(lblTitle, Pos.TOP_LEFT);
	    
	    
	    DetailSpelers ds = new DetailSpelers(dc);
	    OverzichtSpelers os = new OverzichtSpelers(dc, ds);
	    

	    HBox centerContainer = new HBox(20, os, ds);
	    centerContainer.setAlignment(Pos.CENTER);
	    ds.setAlignment(Pos.TOP_CENTER);
	    os.setAlignment(Pos.TOP_CENTER);
	    this.setCenter(centerContainer);
	    
	    
	}
	private void drukKeerTerug(ActionEvent event) {

		
		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm(new DomeinController());
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(taalKeuze, stage.getWidth(), stage.getHeight());
		stage.setScene(scene);
	}
}
