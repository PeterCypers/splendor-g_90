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
		this.spelerRepo = new SpelerRepository();
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
	
	//moet nog van naam veranderen: voegSpelerToe
	public void registreerSpeler(String gebruikersNaam, int geboorteJaar) {
		controleerAantalSpelers();
		boolean inDatabase = false;
		Speler geselecteerdeSpeler = null;
		List<Speler> databaseSpelers = spelerRepo.geefSpelers();
		for (Speler speler : databaseSpelers) {
			if(speler.getGebruikersNaam().equals(gebruikersNaam) && speler.getGeboorteJaar() == geboorteJaar) {
				inDatabase = true;
				geselecteerdeSpeler = speler;
			}
		}
		
		if(!inDatabase)
			throw new IllegalArgumentException("Speler bestaat niet.");
		spelers.add(geselecteerdeSpeler);
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