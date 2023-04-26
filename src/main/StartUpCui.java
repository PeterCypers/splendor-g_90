package main;

import cui.SplendorApplicatie;
import domein.DomeinController;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartUpCui extends Application {

	public static void main(String[] args) {
		new SplendorApplicatie(new DomeinController()).startSpel();
	}

	@Override
	public void start(Stage primaryStage) {
		// Leave this method empty
	}

}