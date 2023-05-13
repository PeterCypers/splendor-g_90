package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;

public abstract class StartUp extends Application {

	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			int keuze = 0;
			boolean errorExists = true;

			do {
				try {
					do {
						System.out.print("Gui?(1) or Cui?(2): ");
						keuze = input.nextInt();
					} while (keuze < 1 || keuze > 2);
					errorExists = false;
				} catch (InputMismatchException e) {
					input.next();
					System.out.println("Your entered number must be a valid number.");
				}
			} while (errorExists);

			// CUI opstarten
			if (keuze == 2) {
				try {
					launchCui(args);
					// new SplendorApplicatie(new DomeinController()).startSpel();
					System.exit(0);
				} catch (Error e) {
					System.exit(1);
				}
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
	}

	private static void launchGui(String[] args) {
		Application.launch(StartUpGui.class, args);
	}

	private static void launchCui(String[] args) {
		Application.launch(StartUpCui.class, args);
	}
}
