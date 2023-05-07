package main;

import domein.DomeinController;
import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage stage) {
		DomeinController dc = new DomeinController();
		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm(dc);
		Scene scene = new Scene(taalKeuze);
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.setTitle("Taalkeuze");
		stage.show();

//		DomeinController dc = new DomeinController();
//		dc.voegSpelerToe("user1", 2002);
//		dc.voegSpelerToe("user2", 2000);
//		SpeelSpelScherm speelSpelScherm = new SpeelSpelScherm(dc);
//		Scene scene = new Scene(speelSpelScherm);
//		stage.setMaximized(true);
//		stage.setScene(scene);
//		stage.setTitle("Spel");
//		stage.show();

		// WinnaarScherm victory = new WinnaarScherm(dc);
		// Scene scene = new Scene (victory,800,600);
		// primaryStage.setScene(scene);
		// primaryStage.setTitle("victory");
		// primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
