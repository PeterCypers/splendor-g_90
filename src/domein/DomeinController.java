package domein;

import java.time.LocalDate;
import java.util.*;

public class DomeinController {

	private Spel spel;
	private List<Speler> spelers;
	private Speler spelerAanBeurt; //nieuw attribuut toegevoegd
	private SpelerRepository spelerRepo;
	
	public DomeinController() {
		this.spelers = new ArrayList<>();
	}

	//aangepast jongstespeler nu op leeftijd niet geboortejaar bepaald
	public void startNieuwSpel() {
		this.spel = new Spel(spelers.size());
		int huidigJaar = LocalDate.now().getYear();
		int jongste = Integer.MAX_VALUE;
		Speler jongsteSpeler = null;
		for (Speler speler : spelers) {
			if((huidigJaar - speler.getGeboorteJaar()) < jongste) {
				jongsteSpeler = speler;
				jongste = huidigJaar - speler.getGeboorteJaar();
			}
		}
		spelerAanBeurt = jongsteSpeler;
	}

	/**
	 * gebruik hulpmethode controleerAantalSpelers
	 * en gebruik:
	 * LocalDate.now().getYear(); 
	 * om de leeftijd te controleren
	 * @param gebruikersNaam
	 * @param geboorteJaar
	 */
	public void registreerSpeler(String gebruikersNaam, int geboorteJaar) {
		controleerAantalSpelers();
		spelers.add(new Speler(gebruikersNaam, geboorteJaar));
	}

	/**
	 * word gebruikt door registreer
	 */
	private void controleerAantalSpelers() {
		if(spelers.size() == Spel.MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException("Maximum aantal spelers bereikt. Kies om een spel te starten.");
	}
	
	//nieuwe methode toegevoegd voor controle input/spelertoevoegen in applicatie laag
	public int geefAantalSpelers() {
		return spelers.size();
	}
	
	public String geefSpelerAanBeurt() {
		return String.format("%s", spelerAanBeurt.toString());
	}

}