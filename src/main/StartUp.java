package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import cui.SplendorApplicatie;
import domein.DomeinController;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartUp extends Application {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int keuze = 0;
		boolean errorExists = true;

		do {
			try {
				do {
					System.out.print("Gui?(1) of Cui?(2): ");
					keuze = input.nextInt();
				} while (keuze < 1 || keuze > 2);
				errorExists = false;
			} catch (InputMismatchException e) {
				input.next();
				System.out.println("Je keuze moet een geheel getal zijn.");
			}
		} while (errorExists);

		// CUI opstarten
		if (keuze == 2) {
			new SplendorApplicatie(new DomeinController()).startSpel();
			System.exit(0);
		}
		// GUI opstarten
		if (keuze == 1) {
			try {
				launchGui(args);
			} catch (Error e) {
				System.exit(1);
			}
		}
	}

	private static void launchGui(String[] args) {
		Application.launch(StartUpGui.class, args);
	}

	private static void launchCui(String[] args) {
		Application.launch(StartUpCui.class, args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// Leave this method empty
	}

}
