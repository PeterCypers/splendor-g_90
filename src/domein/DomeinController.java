package domein;

import java.time.LocalDate;
import java.util.*;

public class DomeinController {
	
	private Spel spel;
	private final List<Speler> aangemeldeSpelers;
	
	// moet een getter voor zijn
	private final SpelerRepository spelerRepo;
	private final KaartRepository kaartRepo; //nieuw
	//TODO
	//private final EdelsteenficheRepository edelsteenRepo;
	//private final EdeleRepository edeleRepo;
	
	public DomeinController() {
		this.aangemeldeSpelers = new ArrayList<>();
		this.spelerRepo = new SpelerRepository();
		this.kaartRepo = new KaartRepository();
	}


	public void startNieuwSpel() {
		List<List<Ontwikkelingskaart>> alleNiveausOntwikkelingsKaartLijst = haalOntwikkelingskaartenUitRepo();
		this.spel = new Spel(aangemeldeSpelers, alleNiveausOntwikkelingsKaartLijst);
	}

	/**
	 * gebruik hulpmethode controleerAantalSpelers
	 * 
	 * @param gebruikersNaam
	 * @param geboorteJaar
	 */
	public void voegSpelerToe(String gebruikersNaam, int geboorteJaar) {
		// controle max aantal spelers bereikt:
		controleerAantalSpelers();
		// controle of speler al is toegevoegd:
		if (aangemeldeSpelers.size() > 0) {
			for (Speler speler : aangemeldeSpelers) {
				if (speler.getGebruikersnaam().equals(gebruikersNaam))
					throw new IllegalArgumentException(
							String.format("Speler met de naam %s is al aan het spel toegevoegd.", gebruikersNaam));
			}
		}
		// controle gebruiker wil dezelfde speler opnieuw toevoegen uit database/repository:
		List<Speler> spelersInRepository = spelerRepo.getSpelers();
		boolean spelerBestaat = false;
		Speler geselecteerdeSpeler = null;
		for (Speler speler : spelersInRepository) {

			if (speler.getGebruikersnaam().equals(gebruikersNaam) && speler.getGeboortejaar() == geboorteJaar) {
				spelerBestaat = true;
				geselecteerdeSpeler = speler;
			}
		}
		if (!spelerBestaat)
			throw new IllegalArgumentException("Speler bestaat niet.");
		aangemeldeSpelers.add(geselecteerdeSpeler);
	}

	private void controleerAantalSpelers() {
		if (aangemeldeSpelers.size() == Spel.MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException("Maximum aantal spelers bereikt. Kies om een spel te starten.");
	}

	public int geefAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	public String geefSpelerAanBeurt() {
		return String.format("%s", spel.getSpelerAanBeurt().toString());
	}
	
	private List<List<Ontwikkelingskaart>> haalOntwikkelingskaartenUitRepo() {
		List<List<Ontwikkelingskaart>> alleKaartenPerNiveau = new ArrayList<>();
		alleKaartenPerNiveau.add(kaartRepo.geefN1Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN2Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN3Kaarten());
		
		//test:
		testPrintLijstMetO_Kaarten(alleKaartenPerNiveau);
		return alleKaartenPerNiveau;
	}

	// repository [testmethode] , toont alle opgeslagen spelers in de spelerRepo ==
	// alle spelers uit de database opgehaald
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
	
	// [testmethode] om te zien of de n1/n2/n3 lijst-lijst goed opgevuld is
	private void testPrintLijstMetO_Kaarten (List<List<Ontwikkelingskaart>> listlist) {
		System.out.println();
		System.out.println("*****DC test alle kaartstapels goed opgevuld met ontwikkelings kaarten*****");
		for (List<Ontwikkelingskaart> list : listlist) {
			System.out.println(list);
		}
		System.out.println("***************************************************************************");
	}

}