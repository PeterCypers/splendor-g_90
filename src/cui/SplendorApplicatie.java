package cui;

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
	
	//zet testen in komentaar voor het eigenlijke normaal verloop
	public void startSpel() {
		int keuze = -1;
		//[TEST] connectie db:
		System.out.printf("%s",dc.toonAlleSpelers());
		
		while(geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS || keuze != 2) {
			if(geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS && keuze == 2) {
				System.out.println("Je hebt nog niet genoeg spelers gekozen om een spel te starten.\n");
			}
		do {
			keuze = keuzeMenu();
			if (keuze != 1 && keuze != 2)
				System.out.println("Kies 1 of 2");
		} while (keuze != 1 && keuze != 2);

		if (keuze == 1) {
			System.out.println(voegSpelerToe());
		}
		
		}
		//[TEST] lijst van actieve spelers: 
		System.out.printf("aantal deelnemers: %d%n%s",geefAantalSpelers(), dc.toonAangemeldeSpelers());
		
		dc.startNieuwSpel(); //volgorde belangrijk
		System.out.print(spelGestartFeedback()); //volgorde belangrijk
		
		
	}
	
	private String voegSpelerToe() {
		if (this.geefAantalSpelers() == Spel.MAX_AANTAL_SPELERS)
			return "Maximum aantal spelers bereikt";
		else {
			String naam = null;
			int geboorteJaar;
			boolean loop = true;
			do {
				try {
					System.out.print("Kies een naam: ");
					naam = input.next();
					input.nextLine(); // buffer leegmaken
					System.out.print("Geef geboortejaar in: ");
					geboorteJaar = input.nextInt();

					dc.voegSpelerToe(naam, geboorteJaar);
					loop = false;
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.println("De ingevoerde geboortejaar moet een geheel getal zijn\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println();
				}

			} while (loop);
		}
		return "Je hebt een speler toegevoegd!\n";
	}
	
	private int geefAantalSpelers() {
		return dc.geefAantalSpelers();
	}
	
	//deze methode is waarschijnlijk overbodig want je kan de dc aanspreken om exact dit te doen
	private String geefSpelerAanBeurt() {
		return dc.geefSpelerAanBeurt();
	}
	
	private int keuzeMenu() {
		int keuze = -1;
		boolean loop = true;
		do {
			try {
			System.out.println("Maak een keuze: \n"
					+ "1. Speler Toevoegen \n"
					+ "2. Spel starten");
			System.out.print("keuze: ");
			keuze = input.nextInt();
			loop = false;
			}
			catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
		}while(loop);

		return keuze;
	}
	
	private void spelOpstarten() {
		//TODO ? (zie this.startSpel())
	}
	
	//TODO verplaats verantwoordelijkheid voor feedback geven naar spel app -> dc -> spel
	private String spelGestartFeedback () {
		return String.format("\n*****Een nieuw spel is gestart*****\nDe jongste speler mag beginnen%n"
				+ "Speler aan beurt: %s", geefSpelerAanBeurt());
	}



}