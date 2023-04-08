package cui;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import domein.*;
import dto.SpelVoorwerpDTO;

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
		//System.out.printf("aantal deelnemers: %d%n%s",geefAantalSpelers(), dc.toonAangemeldeSpelers());
		
		dc.startNieuwSpel(); //volgorde belangrijk
		System.out.print(spelGestartFeedback()); //volgorde belangrijk
		
		toonSpelerSituatie();
		
		while(!dc.isEindeSpel()) {
			toonSpelSituatie();
			speelBeurt();
		}
		
		
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
		return String.format("\n*****Een nieuw spel is gestart*****\nDe jongste speler mag beginnen%n");
	}
	
	//nieuw 8-4-2023
	private void toonSpelerSituatie() {
		System.out.print(dc.toonSpelerSituatie());
	}
	
	//nieuw 8-4-2023
	private void toonSpelSituatie() {
		List<SpelVoorwerpDTO> dtos = dc.toonSpelSituatie();
		
		System.out.println("*****Spel Situatie:*****\n");
		System.out.println("Beschikbare Edelen:");
		for (SpelVoorwerpDTO dto : dtos) {
			if(dto.type() == 'E') {
				System.out.printf("Edele: %s, prestige: %d%nKost: %s%n", dto.foto(), dto.prestigepunten(), Arrays.toString(dto.kosten()));
			}
		}
		System.out.println("\nBeschikbare Ontwikkelings kaarten:"); //foto, niveau, kleur, prestige, \n kosten
		for (int i = 0; i < 3; i++) {
			for (SpelVoorwerpDTO dto : dtos) {
				if(dto.type() == 'O' && i == 0) {
					if(dto.niveau() == 3) {
						System.out.printf("Ontwikkelingskaart Niv.%d, Foto#%s, Kleur: %s, prestige: %d%nKosten: %s%n",
								dto.niveau(), dto.foto(), dto.kleur().toString(), dto.prestigepunten(), Arrays.toString(dto.kosten()));
					}
				} else if(dto.type() == 'O' && i == 1) {
					if(dto.niveau() == 2) {
						System.out.printf("Ontwikkelingskaart Niv.%d, Foto#%s, Kleur: %s, prestige: %d%nKosten: %s%n",
								dto.niveau(), dto.foto(), dto.kleur().toString(), dto.prestigepunten(), Arrays.toString(dto.kosten()));
					}
				} else if(dto.type() == 'O' && i == 2) {
					if(dto.niveau() == 3) {
						System.out.printf("Ontwikkelingskaart Niv.%d, Foto#%s, Kleur: %s, prestige: %d%nKosten: %s%n",
								dto.niveau(), dto.foto(), dto.kleur().toString(), dto.prestigepunten(), Arrays.toString(dto.kosten()));
					}
				}
			}
		}
		System.out.println("\nBeschikbare fiches per stapel:"); //foto\n, kleur\n, resterende fiches
		for (SpelVoorwerpDTO dto : dtos) {
			if(dto.type() == 'S') {
				System.out.printf("FicheStapel: %s%nKleur: %s%nResterende Fiches: %d%n", dto.foto(), dto.kleur().toString(), dto.aantalFiches());
			}
		}
		System.out.println();
	}
	
	private void speelBeurt() {
		//TODO moet verder afgewerkt worden
		int keuze = 0;
		System.out.printf("Speler aan beurt is: %s%n%n", dc.geefSpelerAanBeurtVerkort());
		
		System.out.print("Maak een keuze:\n1. Bekijk je status\n2. Neem Fiches\n3. Koop een Ontwikkelings kaart\nKeuze: ");
		do {
			try {
				keuze = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
			if(keuze < 1 || keuze > 3) System.out.println("Gelieve een geldige waarde in te voeren:");
		}while(keuze < 1 || keuze > 3);
	}

}