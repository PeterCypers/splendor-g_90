package domein;

import java.time.LocalDate;
import java.util.*;

public class DomeinController {

	private Spel spel;
	private List<Speler> spelers;
	private Speler spelerAanBeurt; // spel moet speler aan beurt bijhouden en er moet een getter voor zijn
	private SpelerRepository spelerRepo;

	public DomeinController() {
		this.spelers = new ArrayList<>();
		this.spelerRepo = new SpelerRepository();
	}

	// aangepast jongstespeler nu op leeftijd niet geboortejaar bepaald
	public void startNieuwSpel() {
		//TODO spel moet speler aanbeurt initializeren op jongstespeler uit de meegekregen lijst spelers (1 parameter in spelconstructor: de lijst van spelers meegeven)
		//spel moet een methode krijgen bepaal speler aan beurt?
		this.spel = new Spel(spelers.size());
		int huidigJaar = LocalDate.now().getYear();
		int jongste = Integer.MAX_VALUE;
		Speler jongsteSpeler = null;
		for (Speler speler : spelers) {
			if (speler.getLeeftijd() < jongste) {
				jongsteSpeler = speler;
				jongste = speler.getLeeftijd();
			}
		}
		spelerAanBeurt = jongsteSpeler;
	}

	/**
	 * gebruik hulpmethode controleerAantalSpelers en gebruik:
	 * LocalDate.now().getYear(); om de leeftijd te controleren
	 * 
	 * @param gebruikersNaam
	 * @param geboorteJaar
	 */

	// moet nog van naam veranderen: voegSpelerToe User1 + 2000(moet 2001 zijn)
	public void voegSpelerToe(String gebruikersNaam, int geboorteJaar) {
		//controle max aantal spelers bereikt?:
		controleerAantalSpelers();
		//controle speler is al toegevoegd?:
		if(spelers.size() > 0) {
			for (Speler speler : spelers) {
				if(speler.getGebruikersNaam().equals(gebruikersNaam))throw new IllegalArgumentException(String.format("Speler met de naam %s is al aan het spel toegevoegd.", gebruikersNaam));
			}
		}
		//controle speler die gebruiker wil toevoegen zit in de database/repository?:
		List<Speler> spelersInRepository = spelerRepo.getSpelers();
		boolean spelerBestaat = false;
		Speler geselecteerdeSpeler = null;
		for (Speler speler : spelersInRepository) {
			if (speler.getGebruikersNaam().equals(gebruikersNaam) && speler.getGeboorteDatum().getYear() == geboorteJaar) {
				spelerBestaat = true;
				geselecteerdeSpeler = speler;
			}
		}

		if (!spelerBestaat)
			throw new IllegalArgumentException("Speler bestaat niet.");
		spelers.add(geselecteerdeSpeler);
	}

	/**
	 * word gebruikt door registreer
	 */
	private void controleerAantalSpelers() {
		if (spelers.size() == Spel.MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException("Maximum aantal spelers bereikt. Kies om een spel te starten.");
	}

	// nieuwe methode toegevoegd voor controle input/spelertoevoegen in applicatie
	// laag
	public int geefAantalSpelers() {
		return spelers.size();
	}
	
	public String geefSpelerAanBeurt() {
		//TODO zal moeten veranderen, speler aan beurt zal in spel bijgehouden worden
		return String.format("%s", spelerAanBeurt.toString());
	}
	
	//repository [testmethode] , toont alle opgeslagen spelers in de spelerRepo == alle spelers uit de database opgehaald
	public String toonAlleSpelers() {
		List<Speler> spelers = spelerRepo.getSpelers();
		String alleSpelers = String.format("***DB-Spelers***%n");
		for (Speler s : spelers) {
			alleSpelers += String.format("Naam: %s  | leeftijd: %d jaar | geboortejaar: %d%n", s.getGebruikersNaam(), s.getLeeftijd(),
					s.getGeboorteDatum().getYear());
		}
		alleSpelers += String.format("****************%n");
		return alleSpelers;
	}
	//[testmethode] om de lijst van deelnemende spelers aan het spel te controleren
	public String toonAlleDeelnemers() {
		String returnStr = "";
		if(spelers.size() > 0) {
			for (Speler speler : spelers) {
				returnStr += speler.toString() + "\n"; //als dit niet werkt -> string.format
			}
		}

		return returnStr.isBlank()? "Er zijn nog geen deelnemers" : returnStr;
	}

}