package main;

import cui.SplendorApplicatie;
import domein.DomeinController;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartUpCui extends Application {

	@Override
	public void start(Stage primaryStage) {
		new SplendorApplicatie(new DomeinController()).startSpel();
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}