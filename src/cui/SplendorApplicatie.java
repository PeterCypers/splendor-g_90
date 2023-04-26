package cui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import domein.DomeinController;
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
				if (keuze < 1 || keuze > 7)
					System.out.println("Kies 1 of 2");
			} while (keuze < 1 || keuze > 7);

			if (keuze == 1) {
				System.out.println(voegSpelerToe());
			}

			switch (keuze) {
			case 3 -> {
				dc.voegSpelerToe("user1", 2002);
				dc.voegSpelerToe("user2", 2000);
				keuze = 2;
			}
			case 4 -> {
				dc.voegSpelerToe("user1", 2002);
				dc.voegSpelerToe("user2", 2000);
				dc.voegSpelerToe("user3", 2001);
				keuze = 2;
			}
			case 5 -> {
				dc.voegSpelerToe("user4", 1999);
				dc.voegSpelerToe("user2", 2000);
				dc.voegSpelerToe("user3", 2001);
				dc.voegSpelerToe("user1", 2002);
				keuze = 2;
			}
			case 6 -> {
				dc.voegSpelerToe("user1", 2002);
				dc.voegSpelerToe("user2", 2000);
				dc.startNieuwSpel();
				dc.testGeeftVeelEdelsteenfichesAanSpelers();
				keuze = 2;
			}
			case 7 -> {
				dc.voegSpelerToe("user1", 2002);
				dc.voegSpelerToe("user2", 2000);
				dc.startNieuwSpel();
				dc.testMaaktWinnaarAan();
				keuze = 2;
			}
			}
		}

		// [TEST] lijst van actieve spelers:
		// System.out.printf("aantal deelnemers: %d%n%s",dc.geefAantalSpelers(),
		// dc.toonAangemeldeSpelers());

		if (keuze == 2) {
			// print de ASCII art "testen" af
			System.out.println("\n\r\n" + "  _____         _             \r\n" + " |_   _|__  ___| |_ ___ _ __  \r\n"
					+ "   | |/ _ \\/ __| __/ _ \\ '_ \\ \r\n" + "   | |  __/\\__ \\ ||  __/ | | |\r\n"
					+ "   |_|\\___||___/\\__\\___|_| |_|\r\n" + "                              ");

			dc.startNieuwSpel();

			System.out.print(spelGestartFeedback());

		}

		int beurten = dc.geefAantalSpelers();
		int ronde = 1;

		while (!dc.isEindeSpel()) {
			// print de ASCII art "ronde" elke keer er een nieuwe ronde is
			if (beurten == dc.geefAantalSpelers()) {
				System.out.println("                      _      \r\n" + "  _ __ ___  _ __   __| | ___ \r\n"
						+ " | '__/ _ \\| '_ \\ / _` |/ _ \\\r\n" + " | | | (_) | | | | (_| |  __/\r\n"
						+ " |_|  \\___/|_| |_|\\__,_|\\___|\r\n" + "                             ");
			}

			System.out.printf("*****Het is nu ronde %d*****", ronde);

			beurten--;

			// toont speler situatie
			System.out.print(dc.toonSpelersSituatie());

			// toont spel situatie
			toonSpelSituatie();

			// start een beurt
			speelBeurt();

			// bepaalt volgende speler
			dc.volgendeSpeler();

			// Winnaar wordt hier bepaalt en getoond, maar ook het tellen van de ronden
			if (beurten == 0) {
				beurten = dc.geefAantalSpelers();
				ronde++;

				List<Speler> winnaars = dc.bepaalWinnaar();

				if (winnaars.size() > 0) {
					System.out.println("           _                              \r\n"
							+ " __      _(_)_ __  _ __   __ _  __ _ _ __ \r\n"
							+ " \\ \\ /\\ / / | '_ \\| '_ \\ / _` |/ _` | '__|\r\n"
							+ "  \\ V  V /| | | | | | | | (_| | (_| | |   \r\n"
							+ "   \\_/\\_/ |_|_| |_|_| |_|\\__,_|\\__,_|_|   \r\n"
							+ "                                          ");
					System.out.printf("DE WINNAAR%s:%n", winnaars.size() == 1 ? " IS" : "S ZIJN");
					for (Speler speler : winnaars) {
						System.out.printf("Speler %s%n", speler.getGebruikersnaam());
					}
				}

			}
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
				System.out.println("Maak een keuze:");

				if (dc.geefAantalSpelers() != Spel.MAX_AANTAL_SPELERS) {
					System.out.println("1. Speler toevoegen");
				}

				if (dc.geefAantalSpelers() >= Spel.MIN_AANTAL_SPELERS) {
					System.out.println("2. Spel starten");
				}

				System.out.println("Tijdelijke keuzes (om andere dingen sneller te bereiken en te testen):\n"
						+ "3. Spel starten met 2 juiste spelers\n" + "4. Spel starten met 3 juiste spelers\n"
						+ "5. Spel starten met 4 juiste spelers\n"
						+ "6. Zal het spel starten met 2 spelers en veel edelsteenfiches toekennen\n"
						+ "7. Zal het spel starten met 2 spelers die al een aantal prestigepunten hebben om te winnen "
						+ "(15 + random waarde van 1 tot 3)\n");
				System.out.print("Keuze: ");
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

	private void toonSpelSituatie() {
		List<SpelVoorwerpDTO> dtos = dc.toonSpelSituatie();

		System.out
				.println("************************************ Spel Situatie: ************************************ \n");
		System.out.println("Beschikbare edelen:");

		for (SpelVoorwerpDTO dto : dtos) {
			if (dto.type() == 'E') {
				System.out.printf("Edele: %s --- Prestigepunten: %d%nKost: %s%n", dto.foto(), dto.prestigepunten(),
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

		System.out.println("\nBeschikbare fiches per stapel:");
		System.out.println(dc.toonSpelFiches());

		System.out.println();
	}

	private void speelBeurt() {
		SoortKeuze keuze = null;
		System.out.println(
				"************************************ Speler Aan Beurt: ************************************ ");
		System.out.printf("Speler aan beurt is: %s%n%n", dc.toonSpelerAanBeurtVerkort());

		/*
		 * zolang speler aan de beurt is => toon de verschillende opties => en laat hem
		 * een optie kiezen
		 */
		while (dc.spelerIsAanBeurt()) {
			System.out.print("Maak een keuze:\n" + "1. Neem 3 verschillende fiches\n" + "2. Neem 2 dezelfde fiches\n"
					+ "3. Koop een ontwikkelingskaart\n" + "4. Pas uw beurt\nKeuze: ");

			try {
				int keuzeGetal = input.nextInt();
				keuze = SoortKeuze.valueOf(keuzeGetal);
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

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

	}

	private void koopOntwikkelingskaart() {
		int niveau = 0;
		int positie = 0;

		do {
			System.out.print("\nKies niveau van kaart [1-3]: ");

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

		try {
			dc.krijgEdele();
		} catch (RuntimeException e) {
			System.out.printf("Er is iets fout gelopen in de krijgEdele methode");
		}
	}

	/**
	 * geef de optie aan de gebruiker om maximaal 3 stenen te nemen
	 */
	private void neemDrieVerschillendeFiches() {
		int aantalFichesDieGenomenMogenWorden = Math.min(3, dc.geefAantalStapelsMeerDanNul());

		if (aantalFichesDieGenomenMogenWorden == 0) {
			throw new RuntimeException("\nDeze actie kan niet gedaan worden omdat alle stapels leeg zijn.\n");
		}
		if (dc.totaalAantalFichesVanSpelerAanBeurt() == 10) {
			throw new RuntimeException("\nSpeler heeft reeds 10 edelsteenfiches in voorraad.\n");
		}
		/*
		 * Een set heeft altijd unieke waarden => daardoor worden er 3 verschillende
		 * edelsteenfiches verwacht die elk uit een verschillende stapel komen
		 */
		Set<Integer> keuzeSet = new HashSet<Integer>();

		System.out.printf(
				"%nKies %d stapels om een fiche van te nemen, kies een getal die hoort bij je gekozen stapel.%n",
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
					System.out.print("U heeft al gekozen voor volgende stapelnummers: ");
					for (Integer i : keuzeSet) {
						System.out.print(i + " ");
					}
					System.out.println("\n");

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

		/*
		 * Set<Integer> keuzeSet = new HashSet<>(Arrays.asList(1, 2, 3)); Kleur[]
		 * kleurKeuze = keuzeSet.stream().map(i -> Kleur.valueOf("KLEUR_" +
		 * i)).toArray(Kleur[]::new);
		 * 
		 */
		Kleur[] kleurKeuze = new Kleur[keuzeSet.size()];
		int index = 0;
		for (Integer keuze : keuzeSet) {
			kleurKeuze[index] = Kleur.valueOf(keuze - 1);
			index++;
		}

		dc.neemDrieFiches(kleurKeuze);

		if (dc.buitenVoorraad()) {
			geefFichesTerug();
		}
	}

	private void neemTweeDezelfdeFiches() {

		if (!dc.bestaatStapelMeerDan4()) {
			throw new RuntimeException(
					"\nDeze actie kan niet gedaan worden omdat er geen stapels zijn die 4 of meer fiches hebben\n");
		}

		try {
			int keuze = 0;

			System.out.printf(
					"\nKies een stapel om 2 dezelfde fiches van te nemen, kies een getal die hoort bij je gekozen stapel.%n");

			for (Kleur k : Kleur.values()) {
				System.out.printf("%s %d%n", k, k.getKleur() + 1);
			}

			do {
				System.out.print("Keuze: ");

				try {
					keuze = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.println("Je keuze moet een geheel getal zijn\n");
				}

				if (keuze < 1 || keuze > 5)
					System.out.println("Kies een stapel van [1-5]");
			} while (keuze < 1 || keuze > 5);

			Kleur kleur = Kleur.valueOf(keuze - 1);

			dc.neemTweeFiches(kleur);

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		if (dc.buitenVoorraad()) {
			geefFichesTerug();
		}
	}

	private void geefFichesTerug() {
		// toon overzicht van edelsteenfiches in speler zijn voorraad
		System.out.println(dc.toonAantalFichesVanSpelerAanBeurt());

		// vraag speler om edelsteenfiches terug te leggen naar spel voorraad
		int aantalTerugTePlaatsen = dc.totaalAantalFichesVanSpelerAanBeurt() - 10;

		System.out.printf(
				"U heeft volgende edelsteenfiches in bezit (maar dit zijn er meer dan %d toegestane voorraad)%n",
				Speler.getMaxEdelsteenfichesInVoorraad());

		// toon speler aan de beurt zijn fiches na elke verwijdering
		System.out.println(dc.toonAantalFichesVanSpelerAanBeurt());

		System.out.printf("Geef %d fiches terug aan de spel stapels, kies een getal die hoort bij je gekozen stapel.%n",
				aantalTerugTePlaatsen);

		for (Kleur k : Kleur.values()) {
			System.out.printf("%s %d%n", k, k.getKleur() + 1);
		}

		System.out.println("");

		for (int i = 0; i < aantalTerugTePlaatsen; i++) {
			boolean isTerugGelegd = true;

			while (isTerugGelegd) {

				try {
					System.out.printf("Plaats fiche terug uit eigen stapel (met nummer): ");

					int stapelKeuze = input.nextInt();

					dc.plaatsTerugInStapel(stapelKeuze - 1);

					isTerugGelegd = false;
				} catch (RuntimeException e) {
					System.out.println("\nU probeert fiches terug te plaatsen van een lege stapel.\n");
					isTerugGelegd = true;
				}

			}
		}

	}
}