package domein;

/**
 * om een spel aan te maken moet je enkel weten hoeveel spelers er zullen deelnemen, je kan in de constructor dan het aantal steentjes en edelen instellen
 */
public class Spel {

	private SpeelBord tafel;
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;
	private int aantalSpelers;
	private int aantalSteentjes;
	private int aantalEdelen;

	public int getAantalSpelers() {
		return this.aantalSpelers;
	}

	public void setAantalSpelers(int aantalSpelers) {
		this.aantalSpelers = aantalSpelers;
	}

	public int getAantalSteentjes() {
		return this.aantalSteentjes;
	}

	public void setAantalSteentjes(int aantalSteentjes) {
		this.aantalSteentjes = aantalSteentjes;
	}

	public int getAantalEdelen() {
		return this.aantalEdelen;
	}

	public void setAantalEdelen(int aantalEdelen) {
		this.aantalEdelen = aantalEdelen;
	}

	/**
	 * 
	 * @param aantalSpelers
	 */
	public Spel(int aantalSpelers) {
		// TODO - implement Spel.Spel
		throw new UnsupportedOperationException();
	}

}