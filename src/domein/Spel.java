package domein;

import java.util.List;

public class Spel {
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;
	private final List<Speler> aangemeldeSpelers;
	private Speler spelerAanBeurt;
	private List<Ontwikkelingskaart> n1;
	private List<Ontwikkelingskaart> n2;
	private List<Ontwikkelingskaart> n3;

	public Spel(List<Speler> aangemeldeSpelers, List<List<Ontwikkelingskaart>> ontwikkelingsKaarten) {
		controleerAantalSpelers(aangemeldeSpelers);
		this.aangemeldeSpelers = aangemeldeSpelers;
		this.bepaalJongsteSpeler(aangemeldeSpelers);
		
		controleerOntwikkelingsKaartLijsten(ontwikkelingsKaarten);
		//setters?
		this.n1 = ontwikkelingsKaarten.get(0);
		this.n2 = ontwikkelingsKaarten.get(1);
		this.n3 = ontwikkelingsKaarten.get(2);
		
		//[test]
		testOntwikkelingsKaartStapels();
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
		if(ontwikkelingsKaarten.size() != 3)
			throw new IllegalArgumentException("Lijst met OntwikkelingsKaarten is niet van lengte 3");
		ontwikkelingsKaarten.forEach((lijst) -> {
			if(lijst == null)
				throw new IllegalArgumentException("Lijst van OntwikkelingsKaarten is null");
			lijst.forEach((kaart) -> {
				if(kaart == null)
					throw new IllegalArgumentException("Kaart in een lijst van OntwikkelingsKaarten is null");
			});
		});
	}

	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	public void volgendeSpeler() {
		// TODO
	}

	public Integer getAantalSpelers() {
		return aangemeldeSpelers.size();
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