package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import cui.SplendorApplicatie;
import domein.DomeinController;
import gui.TaalKeuzeScherm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		TaalKeuzeScherm root = new TaalKeuzeScherm();
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Taalkeuze");
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int keuze = 0;
		boolean errorExists = true;
		do {
		try {
			do {
				System.out.print("Gui?(1) of Cui?(2): ");
				keuze = input.nextInt();
			}while(keuze < 1 || keuze > 2);
			errorExists = false;
		} catch (InputMismatchException e) {
			input.next();
			System.out.println("Je keuze moet een geheel getal zijn.");
		}
		}while(errorExists);
		
		//CUI opstarten
		if(keuze == 2) {
			new SplendorApplicatie(new DomeinController()).startSpel();
			System.exit(0);
		}
		//GUI opstarten
		if(keuze == 1) {
			try {
			launch(args);
			}catch(Error e) {
				System.exit(1);
			}
		}

	}



}
