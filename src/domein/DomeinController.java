package domein;

import java.time.LocalDate;
import java.util.*;

public class DomeinController {

	private Spel spel;
	private final List<Speler> aangemeldeSpelers;
	// private Speler spelerAanBeurt; // spel moet speler aan beurt bijhouden en er
	// moet een getter voor zijn
	private SpelerRepository spelerRepo;

	public DomeinController() {
		this.aangemeldeSpelers = new ArrayList<>();
		this.spelerRepo = new SpelerRepository();
	}

	// aangepast jongstespeler nu op leeftijd niet geboortejaar bepaald
	public void startNieuwSpel() {
		this.spel = new Spel(aangemeldeSpelers);
	}

	/**
	 * gebruik hulpmethode controleerAantalSpelers
	 * 
	 * @param gebruikersNaam
	 * @param geboorteJaar
	 */
	public void voegSpelerToe(String gebruikersNaam, int geboorteJaar) {
		// TODO zie TODO2
		// controle max aantal spelers bereikt?:
		controleerAantalSpelers();
		// controle speler is al toegevoegd?:
		if (aangemeldeSpelers.size() > 0) {
			for (Speler speler : aangemeldeSpelers) {
				if (speler.getGebruikersnaam().equals(gebruikersNaam))
					throw new IllegalArgumentException(
							String.format("Speler met de naam %s is al aan het spel toegevoegd.", gebruikersNaam));
			}
		}
		// controle speler die gebruiker wil toevoegen zit in de database/repository?:
		List<Speler> spelersInRepository = spelerRepo.getSpelers();
		boolean spelerBestaat = false;
		Speler geselecteerdeSpeler = null;
		for (Speler speler : spelersInRepository) {
			// TODO controleer Speler klasse
			if (speler.getGebruikersnaam().equals(gebruikersNaam) && speler.getGeboortejaar() == geboorteJaar) {
				spelerBestaat = true;
				geselecteerdeSpeler = speler;
			}
		}
		if (!spelerBestaat)
			throw new IllegalArgumentException("Speler bestaat niet.");
		aangemeldeSpelers.add(geselecteerdeSpeler);
	}

	// overbodige methode?
	private void controleerAantalSpelers() {
		if (aangemeldeSpelers.size() == Spel.MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException("Maximum aantal spelers bereikt. Kies om een spel te starten.");
	}

	// nieuwe methode toegevoegd voor controle input/spelertoevoegen in applicatie
	// laag
	public int geefAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	// verandert, gebruikt nu de getter in Spel om de speler-aan-beurt op te halen
	public String geefSpelerAanBeurt() {
		return String.format("%s", spel.getSpelerAanBeurt().toString());
	}

	// repository [testmethode] , toont alle opgeslagen spelers in de spelerRepo ==
	// alle spelers uit de database opgehaald
	// TODO controleer Speler klasse
	public String toonAlleSpelers() {
		List<Speler> spelers = spelerRepo.getSpelers();
		String alleSpelers = String.format("***DB-Spelers***%n");
		for (Speler s : spelers) {
			alleSpelers += String.format("Naam: %6s  | leeftijd: %3d jaar | geboortejaar: %d%n", s.getGebruikersnaam(),
					LocalDate.now().getYear() - s.getGeboortejaar(), s.getGeboortejaar());
		}
		alleSpelers += String.format("****************%n");
		return alleSpelers;
	}

	// [testmethode] om de lijst van deelnemende spelers aan het spel te controleren
	public String toonAangemeldeSpelers() {
		String returnStr = "";
		if (aangemeldeSpelers.size() > 0) {
			for (Speler speler : aangemeldeSpelers) {
				returnStr += speler.toString() + "\n"; // als dit niet werkt -> string.format
			}
		}

		return returnStr.isBlank() ? "Er zijn nog geen deelnemers" : returnStr;
	}

}