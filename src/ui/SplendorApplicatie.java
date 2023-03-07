package ui;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import domein.*;

public class SplendorApplicatie {

	private DomeinController dc;
	Scanner input = new Scanner(System.in);
	/**
	 * 
	 * @param dc
	 */
	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void startSpel() {
		int keuze;
		
		while(geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS) {
		do {
			keuze = keuzeMenu();
			if (keuze != 1 && keuze != 2)
				System.out.println("Kies 1 of 2");
		} while (keuze != 1 && keuze != 2);

		if (keuze == 1) {
			voegSpelerToe();
		}
		
		}
	}
	
	private void voegSpelerToe() {
		//TODO try catch block
		String naam;
		System.out.print("Kies een naam:");
		naam = input.nextLine();
		
		input.nextLine(); //buffer leegmaken
		
	}
	
	private int geefAantalSpelers() {
		return dc.geefAantalSpelers();
	}
	
	private String geefSpelerAanBeurt() {
		//TODO getter voor speler aan beurt in dc
		return null;
	}
	
	private int keuzeMenu() {
		int keuze = -1;
		boolean loop = true;
		do {
			try {
			System.out.println("Maak een keuze: \n"
					+ "1. Nieuwe speler registreren \n"
					+ "2. Spel starten");
			System.out.print("keuze: ");
			keuze = input.nextInt();
			loop = false;
			}
			catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn");
			}
		}while(loop);

		return keuze;
	}



}