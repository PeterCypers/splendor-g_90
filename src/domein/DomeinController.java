package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dto.SpelVoorwerpDTO;

public class DomeinController implements SpelVoorwerp {

	private Spel spel;
	private final List<Speler> aangemeldeSpelers;

	// moet een getter voor zijn
	private final SpelerRepository spelerRepo;
	private final OntwikkelingskaartRepository kaartRepo; // nieuw
	private EdelsteenficheRepository edelsteenRepo;
	private final EdeleRepository edeleRepo;

	public DomeinController() {
		this.aangemeldeSpelers = new ArrayList<>();
		this.spelerRepo = new SpelerRepository();
		this.kaartRepo = new OntwikkelingskaartRepository();
		this.edeleRepo = new EdeleRepository();
	}

	/**
	 * initializatie edelsteenRepo nadat aantalspelers gekend is om aanmaken Fiches
	 * te vergemakkelijken repo+mapper krijgen in de constructor aantal spelers mee
	 */

	public void startNieuwSpel() {
		this.edelsteenRepo = new EdelsteenficheRepository(aangemeldeSpelers.size());
		List<List<Ontwikkelingskaart>> alleNiveausOntwikkelingsKaartLijst = haalOntwikkelingskaartenUitRepo();
		List<Edele> edelen = this.haalEdelenUitRepo(geefAantalSpelers());
		HashMap<Kleur, Integer> ficheStapels = this.haalEdelsteenficheStapelsUitRepo();
		this.spel = new Spel(aangemeldeSpelers, alleNiveausOntwikkelingsKaartLijst, edelen, ficheStapels);
	}

	public boolean isEindeSpel() {
		return this.spel.isEindeSpel();
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
		// controle gebruiker wil dezelfde speler opnieuw toevoegen uit
		// database/repository:
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

	public boolean spelerIsAanBeurt() {
		return this.spel.getSpelerAanBeurt().isAanDeBeurt();
	}

	public void volgendeSpeler() {
		this.spel.volgendeSpeler();
	}

	public void pasBeurt() {
		// TODO controleer of methode kan leiden tot verkeerde object status
		spel.getSpelerAanBeurt().setAanDeBeurt(false);
	}

	private List<List<Ontwikkelingskaart>> haalOntwikkelingskaartenUitRepo() {
		List<List<Ontwikkelingskaart>> alleKaartenPerNiveau = new ArrayList<>();
		alleKaartenPerNiveau.add(kaartRepo.geefN1Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN2Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN3Kaarten());

		// test:
		// testPrintLijstMetO_Kaarten(alleKaartenPerNiveau);
		return alleKaartenPerNiveau;
	}

	private List<Edele> haalEdelenUitRepo(int aantalSpelers) {
		// test:
		testPrintLijstMetEdelen(edeleRepo.geefEdelen(aantalSpelers));
		// einde test
		return edeleRepo.geefEdelen(aantalSpelers);
	}

	private HashMap<Kleur, Integer> haalEdelsteenficheStapelsUitRepo() {
		// test:
		// testPrintLijstMetEdelsteenFiches(edelsteenRepo.geefEdelsteenficheStapels());
		testPrintStapelsEdelsteenFiches(edelsteenRepo.geefEdelsteenficheStapels());
		// einde test
		return edelsteenRepo.geefEdelsteenficheStapels();
	}

	public String toonSpelerAanBeurtVerkort() {
		int leeftijdInJaar = LocalDate.now().getYear() - spel.getSpelerAanBeurt().getGeboortejaar();
		return String.format("%s ---- leeftijd: %d", spel.getSpelerAanBeurt().getGebruikersnaam(), leeftijdInJaar);
	}

	public String toonSpelerAanBeurtSituatie() {
		return "*** Speler status na beurt ***%n" + this.spel.getSpelerAanBeurt().toString();
	}

	// maakt gebruik van Spel.geefSpelVoorwerpen()
	public List<SpelVoorwerpDTO> toonSpelSituatie() {
		List<SpelVoorwerp> spelvoorwerpen = spel.geefSpelVoorwerpen();
		List<SpelVoorwerpDTO> lijstDTOs = new ArrayList<>();
		SpelVoorwerpDTO dto = null;

		for (SpelVoorwerp vw : spelvoorwerpen) {
			if (vw instanceof Ontwikkelingskaart ok) {
				dto = new SpelVoorwerpDTO(ok.getNiveau(), ok.getPrestigepunten(), ok.getKleurBonus(),
						ok.getFotoOntwikkelingskaart(), ok.getKosten());
			} else if (vw instanceof Edele e) {
				dto = new SpelVoorwerpDTO(e.getPrestigePunten(), e.getEdeleFoto(), e.getKosten());
			}
			lijstDTOs.add(dto);
		}
		return lijstDTOs;
	}

	public String toonSpelersSituatie() {
		String spelerSituatie = "";
		List<Speler> spelerInSpel = this.spel.getAangemeldeSpelers();
		for (Speler s : spelerInSpel) {
			spelerSituatie += String.format("%s%n", s.toString());
		}
		return spelerSituatie;
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
		alleSpelers += String.format("*******************************************************%n");
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

	public String toonSpelerAanBeurtZijnFiches() {
		return spel.getSpelerAanBeurt().toonAantalFiches();
	}

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	public String toonAantalFichesVanSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().toonAantalFiches();
	}

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	public String toonSpelFiches() {
		return this.spel.toonFiches();
	}

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	public int totaalAantalFichesVanSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().totaalAantalFiches();
	}

	/**
	 * @param niveau   : 1-3
	 * @param positie: 1-4
	 */
	public void kiesOntwikkelingskaart(int niveau, int positie) {
		spel.kiesOntwikkelingskaart(niveau, positie);
	}

	public void neemDrieFiches(Kleur[] kleuren) {
		spel.neemDrieFiches(kleuren);
	}

	public void neemTweeFiches(Kleur kleur) {
		spel.neemTweeFiches(kleur);
	}

	public boolean bestaatStapelMeerDan4() {
		return spel.bestaatStapelMeerDan4();
	}

	public int geefAantalStapelsMeerDanNul() {
		return spel.aantalStapelsMeerDanNul();
	}

	public boolean buitenVoorraad() {
		return spel.getSpelerAanBeurt().buitenVoorraad();
	}

	public void plaatsTerugInStapel(int stapelKeuze) {
		spel.plaatsTerugInStapel(stapelKeuze);
	}

	public int geefAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
	// private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer>
	// ficheStapels) {
	// /* WIT,ROOD,BLAUW,GROEN,ZWART; */
	// int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
	// // waarom werkt deze lus niet?
	// // fiches.forEach(fiche -> {
	// // switch (fiche.getKleur().name()) {
	// // case "WIT" -> wit++;
	// // }
	// // });
	// for (int i = 0; i < ficheStapels.size(); i++) {
	// List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
	// for (Edelsteenfiche f : fiches) {
	// switch (f.getKleur().name()) {
	// case "WIT" -> wit++;
	// case "ROOD" -> rood++;
	// case "BLAUW" -> blauw++;
	// case "GROEN" -> groen++;
	// case "ZWART" -> zwart++;
	// }
	// }
	// }
	//
	// System.out.println();
	// System.out.println("*****DC test LijstMetFiches goed opgevuld met
	// Fiches***********************");
	// System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n",
	// this.geefAantalSpelers(),
	// ficheStapels[0].getFiches().size());
	// for (int i = 0; i < ficheStapels.length; i++) {
	// System.out.println(ficheStapels[i].getFiches());
	// }
	// System.out.println("Aantal fiches per kleur:");
	// System.out.printf(
	// "Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches:
	// %d%nZwarte fiches: %d%n", wit,
	// rood, blauw, groen, zwart);
	// System.out.println("***************************************************************************");
	// }

	// [testmethode] om te zien of de n1/n2/n3 lijst-lijst goed opgevuld is
	private void testPrintLijstMetO_Kaarten(List<List<Ontwikkelingskaart>> listlist) {
		System.out.println();
		System.out.println("*****DC test alle kaartstapels goed opgevuld met ontwikkelings kaarten*****");
		for (List<Ontwikkelingskaart> list : listlist) {
			System.out.println(list);
		}
		System.out.println("***************************************************************************");
	}

	// [testmethode] om te zien of de edelen-lijst goed opgevuld is
	private void testPrintLijstMetEdelen(List<Edele> edelen) {
		System.out.println();
		System.out.println("*****DC test LijstMetEdelen goed opgevuld met EdelenKaarten****************");
		System.out.printf("Aantal Spelers: %d%nGrootte vd lijst: %d%n", this.geefAantalSpelers(), edelen.size());
		System.out.println(edelen);
		for (Edele e : edelen) {
			System.out.printf("prestige: %d%nfoto: %s%nkosten: %s%n", e.getPrestigePunten(), e.getEdeleFoto(),
					Arrays.toString(e.getKosten()));
		}
		System.out.println("***************************************************************************");
	}

	// [testmethode]
	private void testPrintStapelsEdelsteenFiches(HashMap<Kleur, Integer> alleFicheStapels) {
		// adres, kleur, aantalfiches op attribuut + op lengte van lijst, naam van foto
		// (%d(i), %s,%s,%d,%d,%s)
		System.out.println();
		System.out.println("*****DC test op de 5 FicheStapels******************************************");
		for (Kleur kleur : Kleur.values()) {
//			System.out.printf(
//					"-------------%n%s %d: %s%nKleur: %s%n" + "AantalFiches: %d, lijst-lengte: %d%n" + "Foto: %s%n",
//					alleFicheStapels[i].getClass().getSimpleName(), i + 1, alleFicheStapels[i],
//					alleFicheStapels[i].getKleur().name(), alleFicheStapels[i].getAantalFiches(),
//					alleFicheStapels[i].getFiches().size(), alleFicheStapels[i].getFicheStapelFoto());
			System.out.printf("Kleur %s: aantal %d%n", kleur, alleFicheStapels.get(kleur));
		}
		System.out.println("***************************************************************************");
	}

	// [testmethode] om te zien of de edelsteenfiches-lijst goed opgevuld is
//	private void testPrintLijstMetEdelsteenFiches(HashMap<Kleur, Integer> ficheStapels) {
//		/* WIT,ROOD,BLAUW,GROEN,ZWART; */
//		int wit = 0, rood = 0, blauw = 0, groen = 0, zwart = 0;
//		// waarom werkt deze lus niet?
//		// fiches.forEach(fiche -> {
//		// switch (fiche.getKleur().name()) {
//		// case "WIT" -> wit++;
//		// }
//		// });
//		for (int i = 0; i < ficheStapels.size(); i++) {
//			List<Edelsteenfiche> fiches = ficheStapels[i].getFiches();
//			for (Edelsteenfiche f : fiches) {
//				switch (f.getKleur().name()) {
//				case "WIT" -> wit++;
//				case "ROOD" -> rood++;
//				case "BLAUW" -> blauw++;
//				case "GROEN" -> groen++;
//				case "ZWART" -> zwart++;
//				}
//			}
//		}
//
//		System.out.println();
//		System.out.println("*****DC test LijstMetFiches goed opgevuld met Fiches***********************");
//		System.out.printf("Aantal Spelers: %d%nGrootte vd 1ste lijst: %d%n", this.geefAantalSpelers(),
//				ficheStapels[0].getFiches().size());
//		for (int i = 0; i < ficheStapels.length; i++) {
//			System.out.println(ficheStapels[i].getFiches());
//		}
//		System.out.println("Aantal fiches per kleur:");
//		System.out.printf(
//				"Witte fiches: %d%nRode fiches: %d%nBlauwe fiches: %d%nGroene fiches: %d%nZwarte fiches: %d%n", wit,
//				rood, blauw, groen, zwart);
//		System.out.println("***************************************************************************");
//	}

}