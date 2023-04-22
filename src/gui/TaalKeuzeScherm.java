package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TaalKeuzeScherm extends HBox {
	
	public TaalKeuzeScherm() {
		
		this.setPadding(new Insets(10));
		this.setSpacing(15);
//		this.setMaxSize(10, 10);
		this.setMaxHeight(10);
		
		//List<String> keuzes = new ArrayList<>(Arrays.asList("Nederlands", "Engels"));
		Label lblTaalKeuze = new Label("Kies Je Taal:");
		ChoiceBox<String> cb = new ChoiceBox<>();
		cb.getItems().addAll("Nederlands", "Engels");
		
		Button btnOk = new Button("OK");
		
		this.getChildren().addAll(lblTaalKeuze, cb, btnOk);
	}

}
