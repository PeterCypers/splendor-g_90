package domein; //setters verwijdert voor aantalEdelen/aantalSteentjes -> berekend adhv aantalSpelers -> instellein binnen constructor

import java.util.List;

public class Spel {
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;
	private final List<Speler> aangemeldeSpelers;
	private Speler spelerAanBeurt;

	private KaartRepository kaartrepo;

	public Spel(List<Speler> aangemeldeSpelers) {
		controleerAantalSpelers(aangemeldeSpelers);
		this.aangemeldeSpelers = aangemeldeSpelers;
		this.bepaalJongsteSpeler(aangemeldeSpelers);
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

	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	public void volgendeSpeler() {
		// TODO
	}

}