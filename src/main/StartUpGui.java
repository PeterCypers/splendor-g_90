package main;

import gui.SpelersToevoegenScherm;
import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {

//		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm();
		SpelersToevoegenScherm spelersToevoegen = new SpelersToevoegenScherm();
//		Scene scene = new Scene(taalKeuze, 500, 300);
		Scene scene = new Scene(spelersToevoegen, 500, 300);
		primaryStage.setScene(scene);
//		primaryStage.setTitle("Taalkeuze");
		primaryStage.setTitle("Spelers");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
