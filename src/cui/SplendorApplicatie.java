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

		System.out.println("*****Spel Situatie:*****\n");
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
		int keuze;

		System.out.printf("Speler aan beurt is: %s%n%n", dc.geefSpelerAanBeurtVerkort());

		while (dc.spelerIsAanBeurt()) {
			keuze = 0;
			System.out
					.print("Maak een keuze:\n1. Neem fiches\n2. Koop een ontwikkelingskaart\n3. Pas u beurt\nKeuze: ");
			do {
				try {
					keuze = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.println("Je keuze moet een geheel getal zijn\n");
				}

				if (keuze < 1 || keuze > 4)
					System.out.println("Gelieve een geldige waarde in te voeren:");
			} while (keuze < 1 || keuze > 4);

			switch (keuze) {
			case 1 -> neemFiches();
			case 2 -> koopOntwikkelingskaart();
			case 3 -> dc.pasBeurt();
			}
		}
		keuze = 0;
		System.out.print("Wil je nog je speler status bekijken?\n" + "1. Bekijk status en beëindig beurt\n"
				+ "2. Beëindig beurt\n" + "keuze: ");
		do {
			try {
				keuze = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
			if (keuze < 1 || keuze > 2)
				System.out.println("Gelieve optie 1 of 2 te kiezen");
		} while (keuze < 1 || keuze > 2);

		if (keuze == 1)
			System.out.print(dc.toonSpelerAanBeurtSituatie());

	}

	// nieuw 11-4-2023
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

	// nieuw 11-4-2023
	private void neemFiches() {
		int keuze = 0;
		System.out.print("Kies de fiches die je wilt nemen, 3 verschillende, of 2 dezelfde\n"
				+ "1. Drie verschillende fiches\n" + "2. Twee dezelfde fiches\n");
		do {
			System.out.print("keuze: ");
			try {
				keuze = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Je keuze moet een geheel getal zijn\n");
			}
			if (keuze < 1 || keuze > 2)
				System.out.println("Gelieve optie 1 of 2 te kiezen");
		} while (keuze < 1 || keuze > 2);

		if (keuze == 1)
			drieVerschillendeFiches();
		else if (keuze == 2)
			tweeZelfdeFiches();
	}

	/* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// nieuw 11-4-2023
	private void tweeZelfdeFiches() {
		try {
			int keuze = 0;

			System.out.printf(
					"Kies een stapel om 2 dezelfde fiches van te nemen, kies een getal die hoort bij je gekozen stapel.%n"
							+ "Wit: 1%n" + "Rood: 2%n" + "Blauw: 3%n" + "Groen: 4%n" + "Zwart: 5%n");
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
	}

	// nieuw 11-4-2023
	private void drieVerschillendeFiches() {
		boolean errorExists = true;
		// fout opvangen te weinig fiches in stapel (methode volledig opnieuw opstarten)
		do {
			try {
				int keuze1 = 0, keuze2 = 0, keuze3 = 0;
				System.out.printf(
						"Kies drie stapels om een fiche van te nemen, kies een getal die hoort bij je gekozen stapel.%n"
								+ "Wit: 1%n" + "Rood: 2%n" + "Blauw: 3%n" + "Groen: 4%n" + "Zwart: 5%n");
				// eerste fiche keuze:
				do {
					System.out.print("Eerste Fiche: ");
					try {
						keuze1 = input.nextInt();
					} catch (InputMismatchException e) {
						input.nextLine(); // buffer leegmaken
						System.out.println("Je keuze moet een geheel getal zijn\n");
					}
					if (keuze1 < 1 || keuze1 > 5)
						System.out.println("Kies een stapel van [1-5]");
				} while (keuze1 < 1 || keuze1 > 5);
				// tweede fiche keuze:
				do {
					System.out.print("Tweede Fiche: ");
					try {
						keuze2 = input.nextInt();
					} catch (InputMismatchException e) {
						input.nextLine(); // buffer leegmaken
						System.out.println("Je keuze moet een geheel getal zijn\n");
					}
					if (keuze2 < 1 || keuze2 > 5)
						System.out.println("Kies een stapel van [1-5]");
				} while (keuze2 < 1 || keuze2 > 5);
				// derde fiche keuze:
				do {
					System.out.print("Derde Fiche: ");
					try {
						keuze3 = input.nextInt();
					} catch (InputMismatchException e) {
						input.nextLine(); // buffer leegmaken
						System.out.println("Je keuze moet een geheel getal zijn\n");
					}
					if (keuze3 < 1 || keuze3 > 5)
						System.out.println("Kies een stapel van [1-5]");
				} while (keuze3 < 1 || keuze3 > 5);
				int[] gekozenFiches = { keuze1 - 1, keuze2 - 1, keuze3 - 1 }; // telkens -1: lijstkeuze omzetten naar
																				// indexen
				dc.neemDrieFiches(gekozenFiches);
				errorExists = false;

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} while (errorExists); // methode-wide error lus

	}
}