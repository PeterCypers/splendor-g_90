package cui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import domein.DomeinController;
import domein.Edelsteenfiche;
import domein.Kleur;
import domein.SoortKeuze;
import domein.Spel;
import domein.Speler;
import dto.SpelVoorwerpDTO;

public class SplendorApplicatie {

	private DomeinController dc;
	Scanner input = new Scanner(System.in);

	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	// zet testen in commentaar voor het eigenlijke normaal verloop
	public void startSpel() {
		int keuze = -1;
		// [TEST] connectie db:
		System.out.printf("%s", dc.toonAlleSpelers());

		while (dc.geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS || keuze != 2) {
			if (dc.geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS && keuze == 2) {
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

		// [TEST] lijst van actieve spelers:
		// System.out.printf("aantal deelnemers: %d%n%s",dc.geefAantalSpelers(),
		// dc.toonAangemeldeSpelers());

		dc.startNieuwSpel(); // volgorde belangrijk
		System.out.print(spelGestartFeedback()); // volgorde belangrijk

		toonSpelerSituatie();

		while (!dc.isEindeSpel()) {
			toonSpelSituatie();
			speelBeurt();
			dc.volgendeSpeler();
		}

	}

	private String voegSpelerToe() {
		if (dc.geefAantalSpelers() == Spel.MAX_AANTAL_SPELERS)
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
					System.out.println("Het ingevoerde geboortejaar moet een geheel getal zijn\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println();
				}

			} while (loop);
		}

		return "Je hebt een speler toegevoegd!\n";
	}

	private int keuzeMenu() {
		int keuze = -1;
		boolean loop = true;

		do {
			try {
				System.out.println("Maak een keuze: \n" + "1. Speler toevoegen \n" + "2. Spel starten");
				System.out.print("keuze: ");
				keuze = input.nextInt();
				loop = false;
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
		} while (loop);

		return keuze;
	}

	private String spelGestartFeedback() {
		return String.format("\n\r\n" + "  ____        _                _            \r\n"
				+ " / ___| _ __ | | ___ _ __   __| | ___  _ __ \r\n"
				+ " \\___ \\| '_ \\| |/ _ \\ '_ \\ / _` |/ _ \\| '__|\r\n"
				+ "  ___) | |_) | |  __/ | | | (_| | (_) | |   \r\n"
				+ " |____/| .__/|_|\\___|_| |_|\\__,_|\\___/|_|   \r\n"
				+ "       |_|                                  \r\n"
				+ "\n*****Een nieuw spel is gestart*****\n\nDe jongste speler mag beginnen\n\n");
	}

	// nieuw 8-4-2023
	private void toonSpelerSituatie() {
		System.out.print(dc.toonSpelerSituatie());
	}

	// nieuw 8-4-2023
	private void toonSpelSituatie() {
		List<SpelVoorwerpDTO> dtos = dc.toonSpelSituatie();

		System.out.println("************Spel Situatie:************\n");
		System.out.println("Beschikbare edelen:");

		for (SpelVoorwerpDTO dto : dtos) {
			if (dto.type() == 'E') {
				System.out.printf("Edele: %s, prestige: %d%nKost: %s%n", dto.foto(), dto.prestigepunten(),
						Arrays.toString(dto.kosten()));
			}
		}

		System.out.println("\nBeschikbare ontwikkelingskaarten:"); // foto, niveau, kleur, prestige, \n kosten

		for (int i = 1; i < 4; i++) {
			int niveau = (i == 1) ? 1 : (i == 2) ? 2 : 3;
			for (SpelVoorwerpDTO dto : dtos) {

				if (dto.type() == 'O' && dto.niveau() == niveau) {
					System.out.printf("Ontwikkelingskaart Niv.%d, Foto#%s, Kleur: %s, prestige: %d%nKosten: %s%n",
							dto.niveau(), dto.foto(), dto.kleur().toString(), dto.prestigepunten(),
							Arrays.toString(dto.kosten()));
				}
			}
		}

		System.out.println("\nBeschikbare fiches per stapel:"); // foto\n, kleur\n, resterende fiches

		for (SpelVoorwerpDTO dto : dtos) {
			if (dto.type() == 'S') {
				System.out.printf("FicheStapel: %s%nKleur: %s%nResterende Fiches: %d%n", dto.foto(),
						dto.kleur().toString(), dto.aantalFiches());
			}
		}

		System.out.println();
	}

	// afgewerkt 11-4-2023
	private void speelBeurt() {
		SoortKeuze keuze = null;

		System.out.printf("Speler aan beurt is: %s%n%n", dc.toonSpelerAanBeurtVerkort());

		/*
		 * zolang speler aan de beurt is => toon de verschillende opties => en laat hem
		 * een optie kiezen
		 */
		while (dc.spelerIsAanBeurt()) {
			System.out.print("Maak een keuze:\n" + "1. Neem 3 verschillende fiches\n" + "2. Neem 2 dezelfde fiches\n"
					+ "3. Koop een ontwikkelingskaart\n" + "4. Pas u beurt\nKeuze: ");

			// while (keuze == null) {
			try {
				int keuzeGetal = input.nextInt();
				keuze = SoortKeuze.valueOf(keuzeGetal);
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			// }
			try {
				switch (keuze) {
				case NEEM_DRIE -> neemDrieVerschillendeFiches();
				case NEEM_TWEE -> neemTweeDezelfdeFiches();
				case KOOP_KAART -> koopOntwikkelingskaart();
				case PAS_BEURT -> dc.pasBeurt();
				}
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		}

		/*
		 * TODO: add context
		 */
		int keuze2 = 0;
		System.out.print("Wil je nog je speler status bekijken?\n" + "1. Bekijk status en beëindig beurt\n"
				+ "2. Beëindig beurt\n" + "keuze: ");
		do {
			try {
				keuze2 = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
			if (keuze2 < 1 || keuze2 > 2)
				System.out.println("Gelieve optie 1 of 2 te kiezen");
		} while (keuze2 < 1 || keuze2 > 2);

		if (keuze2 == 1)
			System.out.print(dc.toonSpelerAanBeurtSituatie());

	}

	private void koopOntwikkelingskaart() {
		int niveau = 0;
		int positie = 0;

		do {
			System.out.print("Kies niveau van kaart [1-3]: ");

			try {
				niveau = input.nextInt();
				if (niveau < 1 || niveau > 3)
					throw new IllegalArgumentException("Gelieve een niveau van [1-3] te kiezen");
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn tussen [1-3]\n");
			} catch (IllegalArgumentException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(e.getMessage());
			}
		} while (niveau < 1 || niveau > 3);

		do {
			System.out.print("Kies de positie van je kaart [1-4]: ");

			try {
				positie = input.nextInt();
				if (positie < 1 || positie > 4)
					throw new IllegalArgumentException("Gelieve een positie van [1-4] te kiezen");
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn tussen [1-4]\n");
			} catch (IllegalArgumentException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(e.getMessage());
			}
		} while (positie < 1 || positie > 4);

		try {
			dc.kiesOntwikkelingskaart(niveau, positie);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	private void neemDrieVerschillendeFiches() {
		int aantalFichesDieGenomenMogenWorden = Math.min(3, dc.geefAantalStapelsMeerDanNul());

		if (aantalFichesDieGenomenMogenWorden == 0) {
			throw new RuntimeException("Deze actie kan niet gedaan worden omdat alle stapels leeg zijn");
		}

		/*
		 * Een set heeft altijd unieke waarden =>
		 * 
		 */
		Set<Integer> keuzeSet = new HashSet<Integer>();

		System.out.printf(
				"Kies %d stapels om een fiche van te nemen, kies een getal die hoort bij je gekozen stapel.%n",
				aantalFichesDieGenomenMogenWorden);
		for (Kleur k : Kleur.values()) {
			System.out.printf("%s %d%n", k, k.getKleur() + 1);
		}

		do {
			System.out.printf("Fiche %d: ", keuzeSet.size() + 1);

			try {
				int keuze = input.nextInt();

				// kijk
				if (keuze < 1 || keuze > 5) {
					throw new IllegalArgumentException("Kies een stapel van [1-5]");
				}
				if (keuzeSet.contains(keuze)) {
					// TODO: toon de keuzes van de speler die al gekozen zijn
					throw new IllegalArgumentException(
							String.format("U probeert 2 edelsteenfiches van dezelfde kleur te nemen."));
				}

				keuzeSet.add(keuze);
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (aantalFichesDieGenomenMogenWorden != keuzeSet.size());

		/*
		 * maakt een array die de unieke keuzes ontvangt van keuzeSet (speler gekozen
		 * stapels) vermindert elke keuze met 1 door user input
		 */
		int[] ficheKeuze = keuzeSet.stream().mapToInt(i -> i - 1).toArray();

		dc.neemDrieFiches(ficheKeuze);

		if (dc.buitenVoorraad()) {
			geefFichesTerug();
		}
	}

	private void geefFichesTerug() {
		// toon overzicht van edelsteenfiches in speler zijn voorraad
		ArrayList<Edelsteenfiche> edelsteenfichesInHand = dc.geefSpelerAanBeurtZijnFiches();

		int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;

		for (Edelsteenfiche ef : edelsteenfichesInHand) {
			Kleur kleur = ef.getKleur();
			switch (kleur) {
			case WIT -> wit++;
			case ROOD -> rood++;
			case BLAUW -> blauw++;
			case GROEN -> groen++;
			case ZWART -> zwart++;
			}
		}

		System.out.printf("Witte: %d%n" + "Rode: %d%n" + "Blauwe: %d%n" + "Groene: %d%n" + "Zwarte: %d%n", wit, rood,
				blauw, groen, zwart);

		// vraag speler om edelsteenfiches terug te leggen naar spel voorraad
		int aantalTerugTePlaatsen = dc.geefSpelerAanBeurtZijnFiches().size() - 10;

		System.out.printf(
				"U heeft volgende edelsteenfiches in bezit (maar dit zijn er meer dan %d toegestane voorraad)",
				Speler.getMaxEdelsteenfichesInVoorraad());

		dc.toonSpelerAanBeurtSituatie();

		System.out.printf("Geef %d fiches terug aan de spel stapels, kies een getal die hoort bij je gekozen stapel.%n",
				aantalTerugTePlaatsen);

		for (Kleur k : Kleur.values()) {
			System.out.printf("%s %d%n", k, k.getKleur() + 1);
		}

		for (int i = 0; i < aantalTerugTePlaatsen; i++) {
			System.out.printf("Plaats fiche terug uit eigen stapel (met nummer):");
			int stapelKeuze = input.nextInt();

			dc.plaatsTerugInStapel(stapelKeuze);
		}

	}

	private void neemTweeDezelfdeFiches() {

		if (!dc.bestaatStapelMeerDan4()) {
			throw new RuntimeException(
					"Deze actie kan niet gedaan worden omdat er geen stapels zijn die 4 of meer fiches hebben");
		}

		try {
			int keuze = 0;

			System.out.printf(
					"Kies een stapel om 2 dezelfde fiches van te nemen, kies een getal die hoort bij je gekozen stapel.%n");
			for (Kleur k : Kleur.values()) {
				System.out.printf("%s %d%n", k, k.getKleur() + 1);
			}

			do {
				System.out.print("keuze: ");

				try {
					keuze = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.println("Je keuze moet een geheel getal zijn\n");
				}

				if (keuze < 1 || keuze > 5)
					System.out.println("Kies een stapel van [1-5]");
			} while (keuze < 1 || keuze > 5);

			int index = keuze - 1;
			dc.neemTweeFiches(index);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		if (dc.buitenVoorraad()) {
			geefFichesTerug();
		}
	}
}