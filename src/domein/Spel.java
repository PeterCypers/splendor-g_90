package domein;

import java.util.ArrayList;
import java.util.List;

public class Spel {
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;
	private final List<Speler> aangemeldeSpelers;
	private Speler spelerAanBeurt;
	private final List<Ontwikkelingskaart> n1;
	private final List<Ontwikkelingskaart> n2;
	private final List<Ontwikkelingskaart> n3;
	// nieuw 7-4-2023
	private Speler winnaar;
	private boolean eindeSpel = false;
	private final List<Edele> edelen;
	private FicheStapel[] ficheStapels; // final mogelijk? waarschijnlijk niet /* volgorde per index:
										// WIT,ROOD,BLAUW,GROEN,ZWART;*/
	private Ontwikkelingskaart[] niveau1Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau2Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau3Zichtbaar = { null, null, null, null };

	public Spel(List<Speler> aangemeldeSpelers, List<List<Ontwikkelingskaart>> ontwikkelingsKaarten, List<Edele> edelen,
			FicheStapel[] ficheStapels) {
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
		if (aangemeldeSpelers.size() < MIN_AANTAL_SPELERS || aangemeldeSpelers.size() > MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException(
					String.format("Een spel moet Min [2], Max [4] spelers bevatten " + "aantal spelers in de lijst: %d",
							aangemeldeSpelers.size()));
	}

	private void controleerOntwikkelingsKaartLijsten(List<List<Ontwikkelingskaart>> ontwikkelingsKaarten) {
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
	}

	private void controleerEdelenLijst(List<Edele> edelen) {
		if (edelen == null)
			throw new IllegalArgumentException(foutBoodschap("Edelen lijst is null"));
		if (edelen.size() != this.getAantalSpelers() + 1)
			throw new IllegalArgumentException(foutBoodschap("Verkeerd aantal edelen in de lijst edelen"));
	}

	private void controleerFicheStapels(FicheStapel[] ficheStapels) {
		if (ficheStapels == null)
			throw new IllegalArgumentException(foutBoodschap("Fichestapel is null"));
		if (ficheStapels.length != 5)
			throw new IllegalArgumentException(foutBoodschap("Aantal fichestapels is niet exact 5"));
		int aantalFichesPerStapel;
		switch (aangemeldeSpelers.size()) {
		case 2 -> aantalFichesPerStapel = 4;
		case 3 -> aantalFichesPerStapel = 5;
		case 4 -> aantalFichesPerStapel = 7;
		default -> throw new IllegalArgumentException(
				String.format("Fout in %s, Unexpected value: ", this.getClass()) + aangemeldeSpelers.size());
		}

		for (int i = 0; i < ficheStapels.length; i++) {
			if (ficheStapels[i].getFiches() == null || ficheStapels[i].getFiches().size() == 0) {
				throw new IllegalArgumentException(foutBoodschap("Een of meerdere fichestapels zijn leeg"));
			}
			if (ficheStapels[i].getFiches().size() != aantalFichesPerStapel)
				throw new IllegalArgumentException(foutBoodschap("Aantal fiches per stapel is niet juist"));
			ficheStapels[i].getFiches().forEach((fiche) -> {
				if (fiche == null)
					throw new IllegalArgumentException(foutBoodschap("Een of meerdere fiches is null"));
			});
		}
	}

	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	public boolean isEindeSpel() {
		return this.eindeSpel;
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
					"Speler mag deze kaart niet kopen.\nU heeft niet genoeg edelsteenfiches om deze ontwikkelingskaart te kopen.");
		}

		// Verwijder fiches uit voorraad speler
		// Terug toevoegen aan fichestapel
		verwijderFichesUitVoorraadVanSpelerAanBeurtEnVoegTerugAanStapelToe();

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
		List<Ontwikkelingskaart> ontwikkelingsKaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		List<Edelsteenfiche> edelSteenFichesInHand = spelerAanBeurt.getEdelsteenfichesInHand();

		for (Ontwikkelingskaart ontwk : ontwikkelingsKaartenInHand) {
			switch (ontwk.getKleurBonus()) {
			case WIT -> somAantalPerKleurInBezit[0]++;
			case ROOD -> somAantalPerKleurInBezit[1]++;
			case BLAUW -> somAantalPerKleurInBezit[2]++;
			case GROEN -> somAantalPerKleurInBezit[3]++;
			case ZWART -> somAantalPerKleurInBezit[4]++;
			}
		}

		for (Edelsteenfiche e : edelSteenFichesInHand) {
			switch (e.getKleur()) {
			case WIT -> somAantalPerKleurInBezit[0]++;
			case ROOD -> somAantalPerKleurInBezit[1]++;
			case BLAUW -> somAantalPerKleurInBezit[2]++;
			case GROEN -> somAantalPerKleurInBezit[3]++;
			case ZWART -> somAantalPerKleurInBezit[4]++;
			}
		}

		return somAantalPerKleurInBezit;
	}

	private void verwijderFichesUitVoorraadVanSpelerAanBeurtEnVoegTerugAanStapelToe() {
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] wegTeNemenEdelsteenfichesUitVoorraad = somAantalPerKleurInBezit();

		for (int i = 0; i < wegTeNemenEdelsteenfichesUitVoorraad.length; i++) {
			for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
				switch (ontwk.getKleurBonus()) {
				case WIT -> wegTeNemenEdelsteenfichesUitVoorraad[0]--;
				case ROOD -> wegTeNemenEdelsteenfichesUitVoorraad[1]--;
				case BLAUW -> wegTeNemenEdelsteenfichesUitVoorraad[2]--;
				case GROEN -> wegTeNemenEdelsteenfichesUitVoorraad[3]--;
				case ZWART -> wegTeNemenEdelsteenfichesUitVoorraad[4]--;
				}
			}

		}

		for (int i = 0; i < wegTeNemenEdelsteenfichesUitVoorraad.length; i++) {
			for (int j = 0; j < wegTeNemenEdelsteenfichesUitVoorraad[i]; j++) {
				Edelsteenfiche ef = new Edelsteenfiche(Kleur.valueOf(i));
				// Terug toevoegen van de fiche aan de stapel
				ficheStapels[i].voegFicheToe(ef);
				// verwijderen van fiches uit de speler zjin voorraad
				spelerAanBeurt.verwijderEdelsteenfiche(ef);
			}
		}
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
	 * @param indexen [0-4, 0-4, 0-4]
	 */
	public void neemDrieFiches(int[] indexen) {
		if (indexen == null)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemDrieFiches", this.getClass()));
		for (int i : indexen) {
			if (i < 0 || i > 4)
				throw new IllegalArgumentException(
						String.format("Fout in %s: bounds error neemDrieFiches", this.getClass()));

			if (ficheStapels[i].getAantalFiches() == 0) {
				throw new RuntimeException("U probeert fiches te nemen van een lege stapel.");
			}
		}

		// verplaats de edelsteenfiches van spel voorraad naar speler voorraad
		for (int i = 0; i < indexen.length; i++) {
			Edelsteenfiche gekozenEdelsteenfiche = ficheStapels[indexen[i]].neemFiche();
			spelerAanBeurt.voegEdelsteenficheToeAanHand(gekozenEdelsteenfiche);
		}

		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	/**
	 * 
	 * @param index 0-4
	 */
	public void neemTweeFiches(int index) {
		if (index < 0 || index > 4)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemTweeFiches", this.getClass()));
		List<Edelsteenfiche> tweeFiches = ficheStapels[index].neemTweeFiches();
		for (Edelsteenfiche ef : tweeFiches) {
			spelerAanBeurt.voegEdelsteenficheToeAanHand(ef);
		}
		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	public int aantalStapelsMeerDanNul() {
		int aantalStapelsMeerDanNul = 0;

		for (FicheStapel stapel : ficheStapels) {
			if (stapel.getAantalFiches() > 0)
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
		for (FicheStapel stapel : ficheStapels) {
			if (stapel.getAantalFiches() >= 4)
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
		for (int i = 0; i < ficheStapels.length; i++) {
			speelbord.add(ficheStapels[i]);
		}
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
		rest[3] = n3.size();
		return rest;
	}

	private String foutBoodschap(String specifiekBericht) {
		return String.format("Fout in %s: %s", this.getClass(), specifiekBericht);
	}

	// [TEST] zijn de n1/n2/n3 stapels goed opgevuld met O-kaarten?
	private void testOntwikkelingskaartStapels() {
		System.out.println("*****Spel test n1/n2/n3 Ontwikkelingskaart stapels zijn goed aangemaakt****");
		System.out.println(n1);
		System.out.println(n2);
		System.out.println(n3);
		System.out.println("***************************************************************************");
	}

	public void plaatsTerugInStapel(int stapelKeuze) {
		Edelsteenfiche ef = new Edelsteenfiche(Kleur.valueOf(stapelKeuze - 1));
		spelerAanBeurt.verwijderEdelsteenfiche(ef);
		ficheStapels[stapelKeuze].voegFicheToe(ef);
	}

}