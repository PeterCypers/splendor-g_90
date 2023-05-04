package main;

import java.io.File;

import domein.DomeinController;
import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		DomeinController dc = new DomeinController();

		TaalKeuzeScherm taalKeuze = new TaalKeuzeScherm(dc);
		Scene scene = new Scene(taalKeuze,960,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Taalkeuze");
		primaryStage.show();

		// Set background image
		
	

//		File backgroundFile = new File("src/resources/img/background_misc/splendor_background.png");
//		Image backgroundImage = new Image("src/resources/img/background_misc/splendor_background.png");
//		Background background = new Background(backgroundImage);
//
//
//		taalKeuze.setBackground(background);
		
		



		//		DomeinController dc = new DomeinController();
		//		SpeelSpelScherm speelSpelScherm = new SpeelSpelScherm(dc);
		//		Scene scene = new Scene(speelSpelScherm, 1200, 1000);
		//		primaryStage.setScene(scene);
		//		primaryStage.setTitle("Spel");
		//		primaryStage.show();

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
