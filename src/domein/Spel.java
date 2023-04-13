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
	private FicheStapel[] ficheStapels; // final mogelijk? /*volgorde per index: WIT,ROOD,BLAUW,GROEN,ZWART;*/
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

		// [test]
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
	// nieuw 11-4-2023
	public void kiesOntwikkelingskaart(int niveau, int positie) {
		if (positie < 1 || positie > 4)
			throw new IllegalArgumentException(foutBoodschap("Positie moet in range: [1-4] zijn"));
		if (niveau < 1 || niveau > 3)
			throw new IllegalArgumentException(foutBoodschap("Niveau moet in range: [2-3] zijn"));
		Ontwikkelingskaart ok = null;
		switch (niveau) {
		case 1 -> {
			ok = niveau1Zichtbaar[positie - 1];
			if (ok == null)
				throw new IllegalArgumentException("Gekozen ontwikkelingskaart is null");
			niveau1Zichtbaar[positie - 1] = null;
		}
		case 2 -> {
			ok = niveau2Zichtbaar[positie - 1];
			if (ok == null)
				throw new IllegalArgumentException("Gekozen ontwikkelingskaart is null");
			niveau2Zichtbaar[positie - 1] = null;
		}
		case 3 -> {
			ok = niveau3Zichtbaar[positie - 1];
			if (ok == null)
				throw new IllegalArgumentException("Gekozen ontwikkelingskaart is null");
			niveau3Zichtbaar[positie - 1] = null;
		}
		}
		this.spelerAanBeurt.voegOntwikkelingsKaartToeAanHand(ok);
		vulKaartenBij(); // kaarten telkens bijvullen nadat 1 wordt genomen

		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
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
	 * 
	 * @param indexen [0-4, 0-4, 0-4]
	 */
	// nieuw 11-4-2023
	public void neemDrieFiches(int[] indexen) {
		if (indexen == null)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemDrieFiches", this.getClass()));
		for (int i : indexen) {
			if (i < 0 || i > 4)
				throw new IllegalArgumentException(
						String.format("Fout in %s: bounds error neemDrieFiches", this.getClass()));
		}
		;
		if (indexen.length != 3)
			throw new IllegalArgumentException(
					String.format("Fout in %s: lengte indexen param neemDrieFiches", this.getClass()));
		if (indexen[0] == indexen[1] || indexen[0] == indexen[2] || indexen[1] == indexen[2])
			throw new IllegalArgumentException(String.format(
					"Fout in %s: Probeert 2x dezelfde kleur fiche te nemen in neemDrieFiches", this.getClass()));

		int index1 = indexen[0];
		int index2 = indexen[1];
		int index3 = indexen[2];

		spelerAanBeurt.voegEdelsteenFicheToeAanHand(ficheStapels[index1].neemFiche());
		spelerAanBeurt.voegEdelsteenFicheToeAanHand(ficheStapels[index2].neemFiche());
		spelerAanBeurt.voegEdelsteenFicheToeAanHand(ficheStapels[index3].neemFiche());

		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	/**
	 * 
	 * @param index 0-4
	 */
	// nieuw 11-4-2023
	public void neemTweeFiches(int index) {
		if (index < 0 || index > 4)
			throw new IllegalArgumentException(
					String.format("Fout in %s: nullobject passed in neemTweeFiches", this.getClass()));
		List<Edelsteenfiche> tweeFiches = ficheStapels[index].neemTweeFiches();
		for (Edelsteenfiche ef : tweeFiches) {
			spelerAanBeurt.voegEdelsteenFicheToeAanHand(ef);
		}
		// nadat alles goed uitgevoerd is, zal deze speler hun beurt voorbij zijn
		spelerAanBeurt.setAanDeBeurt(false);
	}

	public Integer getAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	public List<Speler> getAangemeldeSpelers() {
		return aangemeldeSpelers;
	}

	// nieuwe methode 7-4-2023
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

	// [testmethode] zijn de n1/n2/n3 stapels goed opgevuld met O-kaarten?
	private void testOntwikkelingsKaartStapels() {
		System.out.println("*****Spel test n1/n2/n3 Ontwikkelingskaart stapels zijn goed aangemaakt****");
		System.out.println(n1);
		System.out.println(n2);
		System.out.println(n3);
		System.out.println("***************************************************************************");
	}

}