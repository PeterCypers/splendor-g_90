package domein; //setters verwijdert voor aantalEdelen/aantalSteentjes -> berekend adhv aantalSpelers -> instellein binnen constructor

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
	
	/**
	 * 
	 * @param aantalSpelers
	 */
	public Spel(int aantalSpelers) {
		setAantalSpelers(aantalSpelers);

		//TODO bereken aantal edelstenen/edelen adhv aantalSpelers
	}

	public int getAantalSpelers() {
		return this.aantalSpelers;
	}

	private void setAantalSpelers(int aantalSpelers) {
		if(aantalSpelers < MIN_AANTAL_SPELERS || aantalSpelers > MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException("Het aantal spelers die deelnemen aan het spel ligt niet tussen [2-4]");
		this.aantalSpelers = aantalSpelers;
	}

	public int getAantalSteentjes() {
		return this.aantalSteentjes;
	}

	public int getAantalEdelen() {
		return this.aantalEdelen;
	}



}