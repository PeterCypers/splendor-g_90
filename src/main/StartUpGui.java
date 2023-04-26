package main;

import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		TaalKeuzeScherm root = new TaalKeuzeScherm();
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Taalkeuze");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
