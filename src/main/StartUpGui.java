package main;

import domein.DomeinController;
import gui.SpeelSpelScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
//		DomeinController dc = new DomeinController();
//		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm(dc);
//		Scene scene = new Scene(taalKeuze, 800, 600);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Taalkeuze");
//		primaryStage.show();

		DomeinController dc = new DomeinController();
		SpeelSpelScherm speelSpelScherm = new SpeelSpelScherm(dc);
		Scene scene = new Scene(speelSpelScherm, 1600, 900);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Spel");
		primaryStage.show();

//		WinnaarScherm victory = new WinnaarScherm(dc);
//		Scene scene = new Scene (victory,800,600);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("victory");
//		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
