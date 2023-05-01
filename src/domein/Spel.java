package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Spel {
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;

	private final List<Speler> aangemeldeSpelers;
	private Speler spelerAanBeurt;

	private final List<Ontwikkelingskaart> n1;
	private final List<Ontwikkelingskaart> n2;
	private final List<Ontwikkelingskaart> n3;

	private boolean eindeSpel = false;

	private final List<Edele> edelen;

	private HashMap<Kleur, Integer> ficheStapels;

	private Ontwikkelingskaart[] niveau1Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau2Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau3Zichtbaar = { null, null, null, null };

	public Spel(List<Speler> aangemeldeSpelers, List<List<Ontwikkelingskaart>> ontwikkelingsKaarten, List<Edele> edelen,
			HashMap<Kleur, Integer> ficheStapels) {
		controleerAantalSpelers(aangemeldeSpelers);
		this.aangemeldeSpelers = aangemeldeSpelers;
		// jongste speler wordt 1ste speler aanbeurt, jongste speler is startspeler en
		// is aan de beurt
		this.bepaalJongsteSpeler(aangemeldeSpelers);
		this.spelerAanBeurt.setStartSpeler();
		this.spelerAanBeurt.setAanDeBeurt(true);

		controleerOntwikkelingsKaartLijsten(ontwikkelingsKaarten);

		this.n1 = ontwikkelingsKaarten.get(0);
		this.n2 = ontwikkelingsKaarten.get(1);
		this.n3 = ontwikkelingsKaarten.get(2);

		controleerEdelenLijst(edelen);
		this.edelen = edelen;

		controleerFicheStapels(ficheStapels);
		this.ficheStapels = ficheStapels;

		vulKaartenBij();

		// [TEST]
		// testOntwikkelingsKaartStapels();
	}

	private void bepaalJongsteSpeler(List<Speler> aangemeldeSpelers) {
		int jongste = Integer.MIN_VALUE;
		Speler jongsteSpeler = null;
		for (Speler speler : aangemeldeSpelers) {
			if (speler.getGeboortejaar() > jongste) {
				jongsteSpeler = speler;
				jongste = speler.getGeboortejaar();
			}
		}
		spelerAanBeurt = jongsteSpeler;
	}

	private void controleerAantalSpelers(List<Speler> aangemeldeSpelers) {
		if (aangemeldeSpelers == null)
			throw new IllegalArgumentException("Lijst met Spelers is null");
		if (aangemeldeSpelers.size() < MIN_AANTAL_SPELERS || aangemeldeSpelers.size() > MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException(
					String.format("Een spel moet Min [2], Max [4] spelers bevatten " + "aantal spelers in de lijst: %d",
							aangemeldeSpelers.size()));
	}

	private void controleerOntwikkelingsKaartLijsten(List<List<Ontwikkelingskaart>> ontwikkelingsKaarten) {
		if (ontwikkelingsKaarten == null) // commit extra msg
			throw new IllegalArgumentException("Lijst met de lijsten van ontwikkelings kaarten per niveau is null");
		if (ontwikkelingsKaarten.size() != 3)
			throw new IllegalArgumentException(foutBoodschap("Lijst met OntwikkelingsKaarten is niet van lengte 3"));
		ontwikkelingsKaarten.forEach((lijst) -> {
			if (lijst == null)
				throw new IllegalArgumentException(foutBoodschap("Lijst van OntwikkelingsKaarten is null"));
			lijst.forEach((kaart) -> {
				if (kaart == null)
					throw new IllegalArgumentException(
							foutBoodschap("Kaart in een lijst van OntwikkelingsKaarten is null"));
			});
		});
		// nieuwe conditie, controler op duplicate lijsten, gecontroleerd op unieke size
		// van kaartlijsten
		if (ontwikkelingsKaarten.get(0).size() == ontwikkelingsKaarten.get(1).size()
				|| ontwikkelingsKaarten.get(0).size() == ontwikkelingsKaarten.get(2).size()
				|| ontwikkelingsKaarten.get(1).size() == ontwikkelingsKaarten.get(2).size())
			throw new IllegalArgumentException("Duplicate lijsten ontwikkelingskaarten");
	}

	private void controleerEdelenLijst(List<Edele> edelen) {
		if (edelen == null)
			throw new IllegalArgumentException(foutBoodschap("Edelen lijst is null"));
		if (edelen.size() != this.getAantalSpelers() + 1)
			throw new IllegalArgumentException(foutBoodschap("Verkeerd aantal edelen in de lijst edelen"));
	}

	private void controleerFicheStapels(HashMap<Kleur, Integer> ficheStapels) {
		int aantalFichesPerStapel;

		if (ficheStapels == null)
			throw new IllegalArgumentException(foutBoodschap("Fichestapel is null"));

		if (ficheStapels.size() != 5)
			throw new IllegalArgumentException(foutBoodschap("Aantal fichestapels is niet exact 5"));

		switch (aangemeldeSpelers.size()) {
		case 2 -> aantalFichesPerStapel = 4;
		case 3 -> aantalFichesPerStapel = 5;
		case 4 -> aantalFichesPerStapel = 7;
		default -> throw new IllegalArgumentException(
				String.format("Fout in %s, Unexpected value: ", this.getClass()) + aangemeldeSpelers.size());
		}

		ficheStapels.forEach((kleur, aantalFiches) -> {
			if (aantalFiches == null || aantalFiches == 0) {
				throw new IllegalArgumentException(foutBoodschap("Een of meerdere fichestapels zijn leeg"));
			}

			if (aantalFiches != aantalFichesPerStapel) {
				throw new IllegalArgumentException(foutBoodschap("Aantal fiches per stapel is niet juist"));
			}
		});
	}

	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	public boolean isEindeSpel() {
		return this.eindeSpel;
	}

	public Ontwikkelingskaart[] getNiveau1Zichtbaar() {
		return this.niveau1Zichtbaar;
	}

	public Ontwikkelingskaart[] getNiveau2Zichtbaar() {
		return this.niveau2Zichtbaar;
	}

	public Ontwikkelingskaart[] getNiveau3Zichtbaar() {
		return this.niveau3Zichtbaar;
	}

	public void volgendeSpeler() {
		// potentieel om huidigespeler == aan de beurt te vervangen door nieuwe speler
		// die ook == aan de beurt
		if (spelerAanBeurt.isAanDeBeurt())
			spelerAanBeurt.setAanDeBeurt(false);

		int indexHuidigeSpeler = aangemeldeSpelers.indexOf(spelerAanBeurt);

		// index laatste speler in lijst
		int maxIndex = aangemeldeSpelers.size() - 1;

		// als speler == laatste in de lijst -> volgende speler = 1ste in de lijst
		int indexVolgendeSpeler = indexHuidigeSpeler == maxIndex ? 0 : indexHuidigeSpeler + 1;

		this.spelerAanBeurt = aangemeldeSpelers.get(indexVolgendeSpeler);

		// volgende speler is aandebeurt, wordt ook in die speler bijgehouden,
		// een speler aandebeurt wordt false, na successvolle afronden van
		// spelmethodes(fiches/o-kaarten nemen)
		spelerAanBeurt.setAanDeBeurt(true);
	}

	/**
	 * @param niveau   : 1-3
	 * @param positie: 1-4 (-1 voor index)
	 */
	public void kiesOntwikkelingskaart(int niveau, int positie) {
		if (positie < 1 || positie > 4)
			throw new IllegalArgumentException(foutBoodschap("Positie moet in range: [1-4] zijn"));
		if (niveau < 1 || niveau > 3)
			throw new IllegalArgumentException(foutBoodschap("Niveau moet in range: [2-3] zijn"));

		Ontwikkelingskaart gekozenOntwikkelingskaart = null;
		Ontwikkelingskaart[][] niveauZichtbaar = { niveau1Zichtbaar, niveau2Zichtbaar, niveau3Zichtbaar };
		gekozenOntwikkelingskaart = niveauZichtbaar[niveau - 1][positie - 1];

		if (gekozenOntwikkelingskaart == null) {
			throw new IllegalArgumentException("Gekozen ontwikkelingskaart is null");
		}

		// Kijken of de speler genoeg fiches en/of ontwikkelingskaarten reeds in hand
		// heeft om deze kaart te kopen
		if (!kanKaartKopen(gekozenOntwikkelingskaart)) {
			throw new RuntimeException(
					"\nSpeler mag deze kaart niet kopen.\nU heeft niet genoeg edelsteenfiches om deze ontwikkelingskaart te kopen.\n");
		}

		// [TEST]
		int[] somAantalKleur = somAantalPerKleurInBezit();
		System.out.println("HIER ---------------------------------------------------------------------------------");
		for (int i : somAantalKleur) {
			System.out.println(i);
		}

		// Verwijder fiches uit voorraad speler
		// Terug toevoegen aan fichestapel
		verwijderFichesVanSpelerEnVoegToeAanSpel(gekozenOntwikkelingskaart);

		// Verwijder kaart van de zichtbare kaarten
		niveauZichtbaar[niveau - 1][positie - 1] = null;

		// Voeg de kaart toe aan de voorraad van de speler
		spelerAanBeurt.voegOntwikkelingskaartToeAanHand(gekozenOntwikkelingskaart);

		// Voeg de prestigepunten van de ontwikkelingskaart toe aan de speler zijn
		// punten
		spelerAanBeurt.voegPuntenToe(gekozenOntwikkelingskaart.getPrestigepunten());

		// zichtbare kaarten stapel worden bijgevuld
		vulKaartenBij();

		// speler zijn beurt wordt stopgezet
		spelerAanBeurt.setAanDeBeurt(false);
	}

	public boolean kanKaartKopen(Ontwikkelingskaart gekozenOntwikkelingskaart) {
		if (gekozenOntwikkelingskaart == null)
			throw new IllegalArgumentException("Ontwikkelingskaart is null in Spel.kanKaartKopen");
		int[] somAantalPerKleurInBezit = somAantalPerKleurInBezit();

		int[] kosten = gekozenOntwikkelingskaart.getKosten();

		boolean kaartKoopbaar = false;

		for (int i = 0; i < kosten.length; i++) {
			if (somAantalPerKleurInBezit[i] >= kosten[i]) {
				kaartKoopbaar = true;
			} else {
				kaartKoopbaar = false;
				break;
			}
		}

		return kaartKoopbaar;
	}

	public int[] somAantalPerKleurInBezit() {
		int[] somAantalPerKleurInBezit = new int[5];
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		HashMap<Kleur, Integer> edelsteenfichesInHand = spelerAanBeurt.getEdelsteenfichesInHand();

		for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
			somAantalPerKleurInBezit[ontwk.getKleurBonus().getKleur()]++;
		}

		for (Kleur kleur : Kleur.values()) {
			// haal aantal fiches op uit de hashmap
			int aantalFiches = 0;
			Integer aantalFichesUitMap = edelsteenfichesInHand.get(kleur);

			// controleert of de hashmap een null waarde retourneert
			if (aantalFichesUitMap != null) {
				aantalFiches = aantalFichesUitMap.intValue();
			}

			somAantalPerKleurInBezit[kleur.getKleur()] += aantalFiches;
		}

		return somAantalPerKleurInBezit;
	}

	private void verwijderFichesVanSpelerEnVoegToeAanSpel(Ontwikkelingskaart gekozenOntwikkelingskaart) {
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] wegTeNemenFichesUitVoorraad = gekozenOntwikkelingskaart.getKosten();

		for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
			wegTeNemenFichesUitVoorraad[ontwk.getKleurBonus().getKleur()]--;
		}

		for (Kleur kleur : Kleur.values()) {
			for (int j = 0; j < wegTeNemenFichesUitVoorraad[kleur.getKleur()]; j++) {
				// Terug toevoegen van de fiche aan de stapel
				voegFicheToe(kleur);
				// verwijderen van fiches uit de speler zjin voorraad
				spelerAanBeurt.verwijderEdelsteenfiche(kleur);
			}
		}
	}

	private void voegFicheToe(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException(String.format("Fout in %s: Edelsteenfiche is null", this.getClass()));

		Integer currentValue = ficheStapels.get(kleur);
		if (currentValue != null) {
			ficheStapels.put(kleur, currentValue + 1);
		} else {
			ficheStapels.put(kleur, 1);
		}
	}

	private void verwijderFiche(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException(String.format("Fout in %s: Kleur is null", this.getClass()));

		int currentValue = ficheStapels.get(kleur);
		if (currentValue - 1 > 0) {
			ficheStapels.put(kleur, currentValue - 1);
		} else {
			ficheStapels.remove(kleur);
		}
	}

	public int totaalAantalfiches() {
		int sum = 0;
		for (int value : ficheStapels.values()) {
			sum += value;
		}
		return sum;
	}

	public void vulKaartenBij() {
		// 1x itereren over 1 van de lijsten, ze zijn allemaal even lang
		for (int i = 0; i < niveau1Zichtbaar.length; i++) {
			// n1.size() - 1 is de laatste kaart, verwijder ze uit trekstapel en zet ze op
			// het bord:
			if (niveau1Zichtbaar[i] == null && n1.size() > 0)
				niveau1Zichtbaar[i] = n1.remove(n1.size() - 1);
			if (niveau2Zichtbaar[i] == null && n2.size() > 0)
				niveau2Zichtbaar[i] = n2.remove(n2.size() - 1);
			if (niveau3Zichtbaar[i] == null && n3.size() > 0)
				niveau3Zichtbaar[i] = n3.remove(n3.size() - 1);
		}
	}

	/**
	 * @param kleuren [0-4, 0-4, 0-4]
	 */
	public void neemDrieFiches(Kleur[] kleuren) {
		if (kleuren == null)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemDrieFiches", this.getClass()));

		if (kleuren[0] == kleuren[1] || kleuren[1] == kleuren[2] || kleuren[0] == kleuren[2]) {
			throw new IllegalArgumentException(String.format(
					"\nU probeert edelsteenfiches te nemen van dezelfde stapel.\nU moet edelsteenfiches nemen van 3 verschillende stapels\n"));
		}

		for (Kleur kleur : kleuren) {
			if (kleur == null)
				throw new IllegalArgumentException(
						String.format("\nU probeert fiches te nemen van een lege stapel. (null)\n"));

			// wanneer een stapel geen fiches bevat
			if (ficheStapels.get(kleur) == null || ficheStapels.get(kleur) <= 0) {
				throw new RuntimeException("\nU probeert fiches te nemen van een lege stapel. (0)\n");
			}
		}

		// verplaats de edelsteenfiches van spel voorraad naar speler voorraad
		for (Kleur kleur : kleuren) {
			verwijderFiche(kleur);
			spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
		}

		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	/**
	 * 
	 * @param index 0-4
	 */
	public void neemTweeFiches(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemTweeFiches", this.getClass()));

		// controleren of de gekozen stapel om van te nemen nog bestaat
		if (ficheStapels.get(kleur) == null) {
			throw new IllegalArgumentException("\nDe stapel die u gekozen heeft is leeg.\n");
		}

		// controleren of het mogelijk is om 2 te verwijderen uit gekozen kleur stapel
		if (ficheStapels.get(kleur) < 4) {
			throw new IllegalArgumentException(
					"\nU mag geen 2 dezelfde fiches nemen uit 1 stapel als deze minder heeft dan 4\n");
		}

		// 2 verwijderen uit stapel van spel
		verwijderFiche(kleur);
		verwijderFiche(kleur);

		// 2 toevoegen aan speler zijn stapel
		spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
		spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);

		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	public int aantalStapelsMeerDanNul() {
		int aantalStapelsMeerDanNul = 0;

		for (Integer aantalFiches : ficheStapels.values()) {
			if (aantalFiches > 0)
				aantalStapelsMeerDanNul++;
		}

		return aantalStapelsMeerDanNul;
	}

	/**
	 * is er een stapel met meer dan 4 fiches
	 * 
	 * @return boolean
	 */
	public boolean bestaatStapelMeerDan4() {
		for (Integer aantalFiches : ficheStapels.values()) {
			if (aantalFiches >= 4)
				return true;
		}

		return false;
	}

	public Integer getAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	public List<Speler> getAangemeldeSpelers() {
		return aangemeldeSpelers;
	}

	public List<SpelVoorwerp> geefSpelVoorwerpen() {
		List<SpelVoorwerp> speelbord = new ArrayList<>();

		for (Edele edele : this.edelen)
			speelbord.add(edele);

		for (int i = 0; i < niveau1Zichtbaar.length; i++) {
			speelbord.add(niveau1Zichtbaar[i]);
			speelbord.add(niveau2Zichtbaar[i]);
			speelbord.add(niveau3Zichtbaar[i]);
		}

		return speelbord;
	}

	public int[] aantalKaartenResterend() {
		int[] rest = new int[3];
		rest[0] = n1.size();
		rest[1] = n2.size();
		rest[2] = n3.size();
		return rest;
	}

	private String foutBoodschap(String specifiekBericht) {
		return String.format("Fout in %s: %s", this.getClass(), specifiekBericht);
	}

	public void plaatsTerugInStapel(int stapelKeuze) {
		// verwijder fiche bij speler
		spelerAanBeurt.verwijderEdelsteenfiche(Kleur.valueOf(stapelKeuze));

		// voeg fiche toe aan bord
		voegFicheToe(Kleur.valueOf(stapelKeuze));
	}

	public String toonFiches() {
		String representatieFiches = "";

		if (ficheStapels.size() > 0) {
			for (Kleur kleur : Kleur.values()) {
				Integer aantalFiches = ficheStapels.get(kleur);
				representatieFiches += String.format("%s: %d%n", kleur, aantalFiches != null ? aantalFiches : 0);
			}
		}

		return representatieFiches.isBlank() ? "Alle fiche stapels zijn leeg" : representatieFiches;
	}

	public void krijgEdele() {
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] aantalOntwikkelingskaartKleurBonus = new int[Kleur.values().length];

		for (Ontwikkelingskaart ontwikkelingskaart : ontwikkelingskaartenInHand) {
			Kleur kleurBonus = ontwikkelingskaart.getKleurBonus();
			aantalOntwikkelingskaartKleurBonus[kleurBonus.ordinal()]++;
		}
		// controle of de speler de edele kan krijgen
		for (Edele edele : edelen) {
			boolean kanEdeleKopen = true;
			int[] kosten = edele.getKosten();

			for (int kleurIndex = 0; kleurIndex < kosten.length; kleurIndex++) {
				if (kosten[kleurIndex] > aantalOntwikkelingskaartKleurBonus[kleurIndex]) {
					kanEdeleKopen = false;
					break;
				}
			}

			if (kanEdeleKopen) {
				// voeg prestigepunten toe van edele aan speler zijn totaal
				spelerAanBeurt.voegPuntenToe(edele.getPrestigepunten());

				// voeg de edele toe als de speler het juist aantal ontwikkelingskaarten heeft
				spelerAanBeurt.voegEdeleToeAanHand(edele);

				// verwijder de edele uit de lijst van spel
				edelen.remove(edele);
				break;
			}
		}
	}

	public List<Speler> bepaalWinnaar() {
		List<Speler> potentieleWinnaars = new ArrayList<>();
		List<Speler> winnaars = new ArrayList<>();

		// Stap 1: Bepaal het hoogste aantal prestigepunten van de potiënle winnaars
		int hoogstePrestigepunten = 0;

		for (Speler speler : aangemeldeSpelers) {
			int prestigepunten = speler.getPrestigepunten();

			if (prestigepunten >= 15) {
				eindeSpel = true;
				potentieleWinnaars.add(speler);
			}

			if (prestigepunten > hoogstePrestigepunten) {
				hoogstePrestigepunten = prestigepunten;
			}
		}

		// Itereer over de winnaars lijst en voeg de spelers die er aan voldoen
		for (Speler speler : potentieleWinnaars) {
			if (speler.getPrestigepunten() == hoogstePrestigepunten) {
				winnaars.add(speler);
			}
		}

		return winnaars;
	}

	// [TEST] zijn de n1/n2/n3 stapels goed opgevuld met O-kaarten?
	private void testOntwikkelingskaartStapels() {
		System.out.println("*****Spel test n1/n2/n3 Ontwikkelingskaart stapels zijn goed aangemaakt****");
		System.out.println(n1);
		System.out.println(n2);
		System.out.println(n3);
		System.out.println("***************************************************************************");
	}

	// [TEST]
	public void testGeeftVeelEdelsteenfichesAanSpelers() {
		for (Kleur kleur : Kleur.values()) {
			for (int i = 0; i < 100; i++)
				spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
		}

	}

	public void testMaaktWinnaarAan() {
		Random random = new Random();

		for (Speler speler : aangemeldeSpelers) {
			int randomWaarde = random.nextInt(3) + 1;
			speler.voegPuntenToe(15 + randomWaarde);
		}
	}

}