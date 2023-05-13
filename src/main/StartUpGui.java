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
		stage.setTitle("Choose language");
		stage.show();

		// [TEST] WinnaarScherm
//		 String language = "EN";
//		 String country = "UK";
//		 Locale l = new Locale(language.toLowerCase(), country);
//		 ResourceBundle r = ResourceBundle.getBundle("resources/resource", l);
//		 Taal.setResource(r);
//
//		 dc.voegSpelerToe("user7", 1995);
//		 dc.voegSpelerToe("user8", 2000);
//		 dc.voegSpelerToe("user9", 2002);
//		 dc.voegSpelerToe("user10", 2003);
//		 WinnaarScherm victory = new WinnaarScherm(dc);
//		 Scene scene = new Scene (victory);
//		 stage.setScene(scene);
//		 stage.setTitle(Taal.getString("victory"));
//		 stage.show();
//		 stage.setMaximized(true);

		// [TEST] SpeelSpelScherm
//		String language = "EN";
//		String country = "UK";
//		Locale l = new Locale(language.toLowerCase(), country);
//		ResourceBundle r = ResourceBundle.getBundle("resources/resource", l);
//		Taal.setResource(r);
//		dc.voegSpelerToe("user1", 2002);
//		dc.voegSpelerToe("user2", 2000);
////		dc.voegSpelerToe("user3", 2001);
////		dc.voegSpelerToe("user4", 1999);
//		SpeelSpelScherm speelSpelScherm = new SpeelSpelScherm(dc);
//		Scene scene = new Scene(speelSpelScherm);
//		stage.setMaximized(true);
//		stage.setScene(scene);
//		stage.setTitle(Taal.getString("game"));
//		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
