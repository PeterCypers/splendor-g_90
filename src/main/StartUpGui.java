package main;

import gui.TaalKeuzeScherm;
import gui.SpelersToevoegenScherm;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		TaalKeuzeScherm taalkeuze = new TaalKeuzeScherm();
		SpelersToevoegenScherm spelersToevoegen = new SpelersToevoegenScherm();
//		Scene scene = new Scene(taalkeuze, 500, 300);
		Scene scene = new Scene(spelersToevoegen, 500, 300);
		primaryStage.setScene(scene);

//		primaryStage.setTitle("Taal Keuze");
		primaryStage.setTitle("Spelers Toevoegen");
		primaryStage.show();
	}




	public static void main(String[] args) {
		launch(args);
	}

}
