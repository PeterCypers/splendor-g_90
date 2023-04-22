package main;

import gui.TaalKeuzeScherm;
//import gui.WelkomScherm;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		TaalKeuzeScherm taalkeuze = new TaalKeuzeScherm();
		
		Scene scene = new Scene(taalkeuze, 250, 50);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Taal Keuze");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
