package cui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import domein.DomeinController;
import domein.Kleur;
import domein.SoortKeuze;
import domein.Spel;
import domein.Speler;
import dto.SpelVoorwerpDTO;
import resources.Taal;

public class SplendorApplicatie {

	private DomeinController dc;
	Scanner input = new Scanner(System.in);

	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void startSpel() {
		int keuze = -1;

		taalKeuze();

		// [TEST] connectie db:
		System.out.printf("%s", dc.toonAlleSpelers());

		while (dc.geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS || keuze != 2) {
			if (dc.geefAantalSpelers() < Spel.MIN_AANTAL_SPELERS && keuze == 2) {
				System.out.printf("%s%n%n", Taal.getString("playerCountErrorMsg"));
			}

			do {
				keuze = keuzeMenu();
				if (keuze < 1 || keuze > 7)
					System.out.printf("%s%n%n", Taal.getString("numberChoiceRangeSevenErrorMsg"));
			} while (keuze < 1 || keuze > 7);

			if (keuze == 1) {
				System.out.println(voegSpelerToe());
			}

			// [TEST] tijdelijke keuzes
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
			dc.startNieuwSpel();

			System.out.print(spelGestartFeedback()); 
		}

		int beurten = dc.geefAantalSpelers();
		int ronde = 1;

		while (!dc.isEindeSpel()) {
			// print de ASCII art "ronde" elke keer er een nieuwe ronde is
			if (beurten == dc.geefAantalSpelers()) {
				System.out.println(rondeAsciiArt(Taal.getResource().getLocale().getLanguage()));
			}

			System.out.printf("*****%s %d*****", Taal.getString("splendorApplicatieRoundCountMsg"), ronde);

			beurten--;

			// toont speler situatie
			System.out.print(dc.toonSpelersSituatie());

			// toont spel situatie
			toonSpelSituatie();

			// start een beurt
			speelBeurt();

			// Winnaar wordt hier bepaalt en getoond, maar ook het tellen van de ronden
			if (beurten == 0) {
				beurten = dc.geefAantalSpelers();
				ronde++;

				List<Speler> winnaars = dc.bepaalWinnaar();

				if (winnaars.size() > 0) {
					System.out.println(winnaarAsciiArt(Taal.getResource().getLocale().getLanguage()));
					
					System.out.printf("%s:%n", winnaars.size() == 1 ? Taal.getString("winnerSingle") : Taal.getString("winnerPlural"));
					for (Speler speler : winnaars) {
						System.out.printf("%s %s%n", Taal.getString("player"), speler.getGebruikersnaam());
					}
				}

			}
		}

	}

	private String voegSpelerToe() {
		if (dc.geefAantalSpelers() == Spel.MAX_AANTAL_SPELERS)
			return String.format("%s", Taal.getString("maxPlayerErrorMsg"));
		else {
			String naam = null;
			int geboorteJaar;
			boolean loop = true;

			do {
				try {
					System.out.printf("%s: ", Taal.getString("chooseName"));
					naam = input.next();

					input.nextLine(); // buffer leegmaken

					System.out.printf("%s: ", Taal.getString("chooseBirthyear"));
					geboorteJaar = input.nextInt();

					dc.voegSpelerToe(naam, geboorteJaar);

					loop = false;
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.printf("%s%n%n", Taal.getString("inputMisMatchWholeNumberErrorMsg"));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage()); //TODO check all possible feedback strings, remove error origin klass
					System.out.println();
				}

			} while (loop);
		}

		return String.format("%s%n%n", Taal.getString("playerAddedFeedbackMsg"));
	}

	private int keuzeMenu() {
		int keuze = -1;
		boolean loop = true;

		do {
			try {
				System.out.printf("%s:%n", Taal.getString("choose"));

				if (dc.geefAantalSpelers() != Spel.MAX_AANTAL_SPELERS) {
					System.out.printf("1. %s%n", Taal.getString("addPlayer"));
				}

				if (dc.geefAantalSpelers() >= Spel.MIN_AANTAL_SPELERS) {
					System.out.printf("2. %s%n", Taal.getString("playGame"));
				}

//				System.out.println("\nTijdelijke keuzes (om andere dingen sneller te bereiken en te testen):\n"
//						+ "3. Spel starten met 2 juiste spelers\n" + "4. Spel starten met 3 juiste spelers\n"
//						+ "5. Spel starten met 4 juiste spelers\n"
//						+ "6. Zal het spel starten met 2 spelers en veel edelsteenfiches toekennen\n"
//						+ "7. Zal het spel starten met 2 spelers die al een aantal prestigepunten hebben om te winnen "
//						+ "(15 + random waarde van 1 tot 3)\n");
				System.out.printf("%n%s:%n3. %s 2 %s%n4. %s 3 %s%n5. %s 4 %s%n6. %s%n7. %s%n%n",
						Taal.getString("temporaryChoices"), Taal.getString("startGameWith"),
						Taal.getString("correctPlayers"), Taal.getString("startGameWith"),
						Taal.getString("correctPlayers"), Taal.getString("startGameWith"),
						Taal.getString("correctPlayers"), Taal.getString("choiceSix"), Taal.getString("choiceSeven"));
				System.out.printf("%s: ", Taal.getString("choice"));
				keuze = input.nextInt();
				loop = false;
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.printf("%s%n%n", Taal.getString("inputMisMatchWholeNumberErrorMsg"));
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
				+ "\n" + Taal.getString("splendorApplicatieSpelGestartFeedbackMsgPart1") + "\n\n" 
				+ Taal.getString("splendorApplicatieSpelGestartFeedbackMsgPart2") + "\n\n");
	}

	private void toonSpelSituatie() {
		List<SpelVoorwerpDTO> dtos = dc.toonSpelSituatie();

		System.out.println(Taal.getString("splendorApplicatieToonSpelSituatieGameSituationMsg") + "\n");
		System.out.println(Taal.getString("splendorApplicatieToonSpelSituatieAvailableNoblesMsg"));

		for (SpelVoorwerpDTO dto : dtos) {
			if (dto.type() == 'E') {
				System.out.printf("%s: %s --- %s: %d%n"
						+ "%s: %s%n", Taal.getString("noble"), dto.foto(), Taal.getString("prestigePoints"), dto.prestigepunten(),
						Taal.getString("cost"), Arrays.toString(dto.kosten()));
			}
		}

		System.out.println("\n" + Taal.getString("splendorApplicatieToonSpelSituatieAvailableDevelopmentCardsMsg")); // foto, niveau, kleur, prestige, \n kosten

		for (int i = 1; i < 4; i++) {
			int niveau = (i == 1) ? 1 : (i == 2) ? 2 : 3;
			for (SpelVoorwerpDTO dto : dtos) {

				if (dto.type() == 'O' && dto.niveau() == niveau) {
//					System.out.printf("Ontwikkelingskaart Niv.%d, Foto#%s, Kleur: %s, prestige: %d%nKosten: %s%n",
//							dto.niveau(), dto.foto(), dto.kleur().toString(), dto.prestigepunten(),
//							Arrays.toString(dto.kosten()));
					System.out.println(dto.repr());
				}
			}
		}

		System.out.println("\n" + Taal.getString("splendorApplicatieToonSpelSituatieAvailableGemTokensMsg"));
		System.out.println(dc.toonSpelFiches());

		System.out.println();
	}

	private void speelBeurt() {
		SoortKeuze keuze = null;
		System.out.println("************************************ "+ Taal.getString("splendorApplicatieSpeelBeurtPlayerTurnMsg") +" ************************************");
		
		System.out.printf("%s %s%n%n", Taal.getString("splendorApplicatieSpeelBeurtPlayerTurnMsg"), dc.toonSpelerAanBeurtVerkort());

		/*
		 * zolang speler aan de beurt is => toon de verschillende opties => en laat hem
		 * een optie kiezen
		 */
		while (dc.spelerIsAanBeurt()) {
			System.out.printf("%s%n%s%n%s%n%s%n%s%n%s ",
					Taal.getString("splendorApplicatieSpeelBeurtMakeAChoiceMsg"),
					Taal.getString("splendorApplicatieSpeelBeurtChoice1Msg"),
					Taal.getString("splendorApplicatieSpeelBeurtChoice2Msg"),
					Taal.getString("splendorApplicatieSpeelBeurtChoice3Msg"),
					Taal.getString("splendorApplicatieSpeelBeurtChoice4Msg"),
					Taal.getString("splendorApplicatieSpeelBeurtChooseMsg"));

			try {
				int keuzeGetal = input.nextInt();
				keuze = SoortKeuze.valueOf(keuzeGetal);
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(Taal.getString("splendorApplicatieSpeelBeurtInputMismatchExceptionMsg") + "\n");
			} catch (IllegalArgumentException e) { //TODO check all possible feedback strings, remove error origin klass
				System.out.println(e.getMessage());
			}

			try {
				switch (keuze) {
				case NEEM_DRIE -> neemDrieVerschillendeFiches();
				case NEEM_TWEE -> neemTweeDezelfdeFiches();
				case KOOP_KAART -> koopOntwikkelingskaart();
				case PAS_BEURT -> dc.pasBeurt();
				}
			} catch (RuntimeException e) { //TODO check all possible feedback strings, remove error origin klass
				System.out.println(e.getMessage());
			}

			dc.volgendeSpeler();
		}

	}
	//TODO
	private void koopOntwikkelingskaart() {
		int niveau = 0;
		int positie = 0;

		do {
			System.out.printf("%n%s ", Taal.getString("splendorApplicatieKoopOntwikkelingskaartChooseLevelMsg"));

			try {
				niveau = input.nextInt();
				if (niveau < 1 || niveau > 3)
					throw new IllegalArgumentException(Taal.getString("splendorApplicatieKoopOntwikkelingskaartChooseLevelBoundsExceptionMsg"));
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.printf("%s%n%n", Taal.getString("splendorApplicatieKoopOntwikkelingskaartChooseLevelInputMismatchMsg"));
			} catch (IllegalArgumentException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(e.getMessage());
			}
		} while (niveau < 1 || niveau > 3);

		do {
			System.out.printf("%s ", Taal.getString("splendorApplicatieKoopOntwikkelingskaartChoosePositionMsg"));

			try {
				positie = input.nextInt();
				if (positie < 1 || positie > 4)
					throw new IllegalArgumentException(Taal.getString("splendorApplicatieKoopOntwikkelingskaartChoosePositionBoundsExceptionMsg"));
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.printf("%s%n%n", Taal.getString("splendorApplicatieKoopOntwikkelingskaartChoosePositionInputMismatchMsg"));
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
			System.out.print(Taal.getString("splendorApplicatieKoopOntwikkelingskaartGetNobleWentWrongMsg"));
		}
	}

	/**
	 * geef de optie aan de gebruiker om maximaal 3 stenen te nemen
	 */
	private void neemDrieVerschillendeFiches() {
		int aantalFichesDieGenomenMogenWorden = Math.min(3, dc.geefAantalStapelsMeerDanNul());

		if (aantalFichesDieGenomenMogenWorden == 0) {
			throw new RuntimeException(String.format("%n%s%n", Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesZeroTokensExceptionMsg")));
		}
		if (dc.totaalAantalFichesVanSpelerAanBeurt() == 10) {
			throw new RuntimeException(String.format("%n%s%n", Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesPlayerTenTokensExceptionMsg")));
		}
		/*
		 * Een set heeft altijd unieke waarden => daardoor worden er 3 verschillende
		 * edelsteenfiches verwacht die elk uit een verschillende stapel komen
		 */
		Set<Integer> keuzeSet = new HashSet<Integer>();

		System.out.printf("%n%s %d %s%n", Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesChoose1"), aantalFichesDieGenomenMogenWorden,
				Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesChoose2"));
		//TODO check works
		//System.out.println("---check---");
		for (Kleur k : Kleur.values()) {
			System.out.printf("%-6s %d%n", Taal.getString(k.toString()) + ":", k.getKleur() + 1);
		}

		do {
			System.out.printf("%s %d: ", Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesToken"), keuzeSet.size() + 1);

			try {
				int keuze = input.nextInt();

				// kijk
				if (keuze < 1 || keuze > 5) {
					throw new IllegalArgumentException(Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesBoundsExceptionMsg"));
				}
				if (keuzeSet.contains(keuze)) {
					System.out.printf("%s ", Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesStackChoiceMsg"));
					for (Integer i : keuzeSet) {
						System.out.print(i + " ");
					}
					System.out.println("\n");

					throw new IllegalArgumentException(Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesDuplicateColourExceptionMsg"));
				}

				keuzeSet.add(keuze);
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(Taal.getString("splendorApplicatieSpeelBeurtInputMismatchExceptionMsg") + "\n");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage()); //TODO check
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
			throw new RuntimeException(String.format("%n%s%n", Taal.getString("splendorApplicatieNeemTweeDezelfdeFichesNoStackOfFourExceptionMsg")));
		}

		try {
			int keuze = 0;

			System.out.printf(String.format("%n%s%n", Taal.getString("splendorApplicatieNeemTweeDezelfdeFichesChooseStackMsg")));

			for (Kleur k : Kleur.values()) {
				System.out.printf("%-6s %d%n", Taal.getString(k.toString()) + ":", k.getKleur() + 1);
			}

			do {
				System.out.print(Taal.getString("splendorApplicatieSpeelBeurtChooseMsg") + " ");

				try {
					keuze = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine(); // buffer leegmaken
					System.out.println(Taal.getString("splendorApplicatieSpeelBeurtInputMismatchExceptionMsg") + "\n");
				}

				if (keuze < 1 || keuze > 5)
					System.out.println(Taal.getString("splendorApplicatieNeemDrieVerschillendeFichesBoundsExceptionMsg"));
			} while (keuze < 1 || keuze > 5);

			Kleur kleur = Kleur.valueOf(keuze - 1);

			dc.neemTweeFiches(kleur);

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage()); //TODO check
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

		System.out.printf("%s %d %s%n", Taal.getString("splendorApplicatieGeefFichesTerugTooManyTokensMsgPart1"),
				Speler.getMaxEdelsteenfichesInVoorraad(),
				Taal.getString("splendorApplicatieGeefFichesTerugTooManyTokensMsgPart2"));

		// toon speler aan de beurt zijn fiches na elke verwijdering
		System.out.println(dc.toonAantalFichesVanSpelerAanBeurt());

		System.out.printf("%s %d %s%n", Taal.getString("splendorApplicatieGeefFichesTerugGiveBackMsgPart1"),
				aantalTerugTePlaatsen, Taal.getString("splendorApplicatieGeefFichesTerugGiveBackMsgPart2"));
		//TODO uitlijnen check of het werkt
		//System.out.println("----check----");
		for (Kleur k : Kleur.values()) {
			System.out.printf("%-6s %d%n", Taal.getString(k.toString()) + ":", k.getKleur() + 1);
		}

		System.out.println("");

		for (int i = 0; i < aantalTerugTePlaatsen; i++) {
			boolean isTerugGelegd = true;

			while (isTerugGelegd) {

				try {
					System.out.printf("%s ", Taal.getString("splendorApplicatieGeefFichesTerugOwnStackChoiceToReturnMsg"));

					int stapelKeuze = input.nextInt();

					dc.plaatsTerugInStapel(stapelKeuze - 1);

					isTerugGelegd = false;
				} catch (RuntimeException e) {
					System.out.printf("%n%s%n%n", Taal.getString("splendorApplicatieGeefFichesTerugEmptyOwnStackExceptionMsg"));
					isTerugGelegd = true;
				}

			}
		}

	}

	/**
	 *
	 * @param taal expect Taal.getResource().getLocale().getLanguage()
	 * @return Ascii Art of Ronde / Round / Partie
	 */
	private String rondeAsciiArt(String taal) {
		String asciiSign = "";
		if(taal == "nl") {
			asciiSign += "                      _      \r\n"
					+ "  _ __ ___  _ __   __| | ___ \r\n"
					+ " | '__/ _ \\| '_ \\ / _` |/ _ \\\r\n" 
					+ " | | | (_) | | | | (_| |  __/\r\n"
					+ " |_|  \\___/|_| |_|\\__,_|\\___|\r\n" 
					+ "                             ";
		}
		if(taal == "en") {
			asciiSign += "                            _ \r\n" 
					+ "  _ __ ___  _   _ _ __   __| |\r\n"
					+ " | '__/ _ \\| | | | '_ \\ / _` |\r\n" 
					+ " | | | (_) | |_| | | | | (_| |\r\n"
					+ " |_|  \\___/ \\__,_|_| |_|\\__,_|\r\n" 
					+ "                              ";
		}
		if(taal == "fr") {
			asciiSign += "                   _   _      \r\n" 
					+ "  _ __   __ _ _ __| |_(_) ___ \r\n"
					+ " | '_ \\ / _` | '__| __| |/ _ \\\r\n" 
					+ " | |_) | (_| | |  | |_| |  __/\r\n"
					+ " | .__/ \\__,_|_|   \\__|_|\\___|\r\n" 
					+ " |_|                          \n";
		}
		return asciiSign;
	}
	/**
	 * @param taal expect Taal.getResource().getLocale().getLanguage()
	 * @return Ascii Art of Winnaar / Winner / Gagnant
	 */
	private String winnaarAsciiArt(String taal) {
		String asciiSign = "";
		if(taal == "nl") {
			asciiSign += "           _                              \r\n"
					+ " __      _(_)_ __  _ __   __ _  __ _ _ __ \r\n"
		            + " \\ \\ /\\ / / | '_ \\| '_ \\ / _` |/ _` | '__|\r\n"
					+ "  \\ V  V /| | | | | | | | (_| | (_| | |   \r\n"
					+ "   \\_/\\_/ |_|_| |_|_| |_|\\__,_|\\__,_|_|   \r\n"
					+ "                                          \n";
		}
		if(taal == "en") {
			asciiSign += "           _                       \r\n"
					+ " __      _(_)_ __  _ __   ___ _ __ \r\n"
					+ " \\ \\ /\\ / / | '_ \\| '_ \\ / _ \\ '__|\r\n"
					+ "  \\ V  V /| | | | | | | |  __/ |   \r\n"
					+ "   \\_/\\_/ |_|_| |_|_| |_|\\___|_|   \r\n"
					+ "                                   \n";
		}
		if(taal == "fr") {
			asciiSign += "                                      _   \r\n"
					+ "   __ _  __ _  __ _ _ __   __ _ _ __ | |_ \r\n"
					+ "  / _` |/ _` |/ _` | '_ \\ / _` | '_ \\| __|\r\n"
					+ " | (_| | (_| | (_| | | | | (_| | | | | |_ \r\n"
					+ "  \\__, |\\__,_|\\__, |_| |_|\\__,_|_| |_|\\__|\r\n"
					+ "  |___/       |___/                       \n\n";
		}
		return asciiSign;
	}
	
	/**
	 * user System set language: Locale.getDefault().getCountry()
	 * ResourceBundle set language: Taal.getResource().getLocale().getLanguage()
	 */
	private void taalKeuze() {
		int taalKeuze = -1;
		String country = "BE";
		String language = "nl";
		System.out.println("Choose Language:\n1. NL\n2. EN\n3. FR");
		do {
			try {
				taalKeuze = input.nextInt();
				if (taalKeuze < 1 || taalKeuze > 3)
					throw new IllegalArgumentException("Choose: 1, 2 or 3");
			} catch (InputMismatchException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println("Input must be a whole number");
			} catch (IllegalArgumentException e) {
				input.nextLine(); // buffer leegmaken
				System.out.println(e.getMessage());
			}
		} while (taalKeuze < 1 || taalKeuze > 3);

		switch (taalKeuze) {
		case 1 -> {
			country = "BE";
			language = "nl";
		}
		case 2 -> {
			country = "UK";
			language = "en";
		}
		case 3 -> {
			country = "BE";
			language = "fr";
		}
		}
		Locale l = new Locale(language, country);
		ResourceBundle r = ResourceBundle.getBundle("resources/resource", l);
		Taal.setResource(r);
	}
}