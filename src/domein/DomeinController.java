package domein;

import java.time.LocalDate;
import java.util.*;

public class DomeinController {
	
	private Spel spel;
	private final List<Speler> aangemeldeSpelers;
	
	// moet een getter voor zijn
	private final SpelerRepository spelerRepo;
	private final OntwikkelingskaartRepository kaartRepo; //nieuw
	private EdelsteenficheRepository edelsteenRepo;
	private final EdeleRepository edeleRepo;
	
	public DomeinController() {
		this.aangemeldeSpelers = new ArrayList<>();
		this.spelerRepo = new SpelerRepository();
		this.kaartRepo = new OntwikkelingskaartRepository();
		this.edeleRepo = new EdeleRepository();
	}


	public void startNieuwSpel() {
		//mag dit: object initiatie in methode start, om de mapper-lijst consctructie te vergemakkelijken?
		this.edelsteenRepo = new EdelsteenficheRepository(aangemeldeSpelers.size());
		List<List<Ontwikkelingskaart>> alleNiveausOntwikkelingsKaartLijst = haalOntwikkelingskaartenUitRepo();
		//TODO constructor spel moet edelen en edelstenen ontvangen en bijhouden
		List<Edele> edelen = this.haalEdelenUitRepo(geefAantalSpelers());
		FicheStapel[] ficheStapels = this.haalEdelsteenficheStapelsUitRepo();
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
	
	//nieuwe methode 6-4-2023
	private List<Edele> haalEdelenUitRepo(int aantalSpelers) {
		//test:
		testPrintLijstMetEdelen(edeleRepo.geefEdelen(aantalSpelers));
		//einde test
		return edeleRepo.geefEdelen(aantalSpelers);
	}
	
	//nieuwe methode 6-4-2023
	private FicheStapel[] haalEdelsteenficheStapelsUitRepo() {
		//test:
		testPrintLijstMetEdelsteenFiches(edelsteenRepo.geefEdelsteenficheStapels());
		testPrintStapelsEdelsteenFiches(edelsteenRepo.geefEdelsteenficheStapels());
		//einde test
		return edelsteenRepo.geefEdelsteenficheStapels();
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
	//nieuwe methode 6-4-2023
	// [testmethode] om te zien of de edelen-lijst goed opgevuld is
	private void testPrintLijstMetEdelen(List<Edele> edelen) {
		System.out.println();
		System.out.println("*****DC test LijstMetEdelen goed opgevuld met EdelenKaarten****************");
		System.out.printf("Aantal Spelers: %d%nGrootte vd lijst: %d%n", this.geefAantalSpelers(), edelen.size());
		System.out.println(edelen);
		for (Edele e : edelen) {
			System.out.printf("prestige: %d%nfoto: %s%nkosten: %s%n", e.getPrestigePunten(), e.getEdeleFoto(), Arrays.toString(e.getKosten()));
		}
		System.out.println("***************************************************************************");
	}
	
	//nieuwe methode 7-4-2023
	private void testPrintStapelsEdelsteenFiches(FicheStapel[] alleFicheStapels) {
		//adres, kleur, aantalfiches op attribuut + op lengte van lijst, naam van foto (%d(i), %s,%s,%d,%d,%s)
		System.out.println(); 
		System.out.println("*****DC test op de 5 FicheStapels******************************************");
		for(int i = 0; i < alleFicheStapels.length; i++) {
		System.out.printf("-------------%n%s %d: %s%nKleur: %s%n"
				+ "AantalFiches: %d, lijst-lengte: %d%n"
				+ "Foto: %s%n", alleFicheStapels[i].getClass().getSimpleName(), i+1,  alleFicheStapels[i],
				alleFicheStapels[i].getKleur().name(),
				alleFicheStapels[i].getAantalFiches(), alleFicheStapels[i].getFiches().size(),
				alleFicheStapels[i].getFicheStapelFoto());
		}
		System.out.println("***************************************************************************");
	}
	
	//nieuwe methode 6-4-2023
	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	private void testPrintLijstMetEdelsteenFiches(FicheStapel[] ficheStapels) {
		/*WIT,ROOD,BLAUW,GROEN,ZWART;*/
		int wit=0, rood=0, blauw=0, groen=0, zwart=0;
		//waarom werkt deze lus niet?
//		fiches.forEach(fiche -> {
//			switch (fiche.getKleur().name()) {
//			case "WIT" -> wit++;
//			}
//		});
		for (int i = 0; i < ficheStapels.length; i++) {
			List<Edelsteenfiche> fiches =  ficheStapels[i].getFiches();
			for (Edelsteenfiche f : fiches) {
				switch (f.getKleur().name()) {
				case "WIT" -> wit++;
				case "ROOD" -> rood++;
				case "BLAUW" -> blauw++;
				case "GROEN" -> groen++;
				case "ZWART" -> zwart++;
				}
			}
		}

		System.out.println();
		System.out.println("*****DC test LijstMetFiches goed opgevuld met Fiches***********************");
		System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n", this.geefAantalSpelers(), ficheStapels[0].getFiches().size());
		for (int i = 0; i < ficheStapels.length; i++) {
			System.out.println(ficheStapels[i].getFiches());
		}
		System.out.println("Aantal fiches per kleur:");
		System.out.printf("Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches: %d%nZwarte fiches: %d%n",
				wit,rood,blauw,groen,zwart);
		System.out.println("***************************************************************************");
	}
	


}