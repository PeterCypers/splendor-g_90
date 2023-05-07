package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.SpelVoorwerpDTO;

public class DomeinController {
	private Spel spel;
	private final List<Speler> aangemeldeSpelers;

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
		spel.getSpelerAanBeurt().setAanDeBeurt(false);
	}

	private List<List<Ontwikkelingskaart>> haalOntwikkelingskaartenUitRepo() {
		List<List<Ontwikkelingskaart>> alleKaartenPerNiveau = new ArrayList<>();
		alleKaartenPerNiveau.add(kaartRepo.geefN1Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN2Kaarten());
		alleKaartenPerNiveau.add(kaartRepo.geefN3Kaarten());

		// [TEST]
		// testPrintLijstMetO_Kaarten(alleKaartenPerNiveau);

		return alleKaartenPerNiveau;
	}

	private List<Edele> haalEdelenUitRepo(int aantalSpelers) {
		return edeleRepo.geefEdelen(aantalSpelers);
	}

	private HashMap<Kleur, Integer> haalEdelsteenficheStapelsUitRepo() {
		return edelsteenRepo.geefEdelsteenficheStapels();
	}

	public String toonSpelerAanBeurtVerkort() {
		int leeftijdInJaar = LocalDate.now().getYear() - spel.getSpelerAanBeurt().getGeboortejaar();
		return String.format("%s ---- leeftijd: %d", spel.getSpelerAanBeurt().getGebruikersnaam(), leeftijdInJaar);
	}

	public String toonSpelerAanBeurtSituatie() {
		return "\n" + "*** Speler status na beurt ***" + "\n" + this.spel.getSpelerAanBeurt().toString();
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
				dto = new SpelVoorwerpDTO(e.getPrestigepunten(), e.getEdeleFoto(), e.getKosten());
			}

			lijstDTOs.add(dto);
		}

		return lijstDTOs;
	}

	public String toonSpelersSituatie() {
		String spelerSituatie = "";
		spelerSituatie += "\n" + "  _                     _   \r\n" + " | |__   ___ _   _ _ __| |_ \r\n"
				+ " | '_ \\ / _ \\ | | | '__| __|\r\n" + " | |_) |  __/ |_| | |  | |_ \r\n"
				+ " |_.__/ \\___|\\__,_|_|   \\__|\r\n" + "                            " + "\n";
		spelerSituatie += "\n************************************ Speler situatie: ************************************\n\n";
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

	public String toonAantalFichesVanSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().toonAantalFiches();
	}

	public String toonSpelFiches() {
		return this.spel.toonFiches();
	}

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

	public void krijgEdele() {
		spel.krijgEdele();
	}

	// [TEST]
	public void testGeeftVeelEdelsteenfichesAanSpelers() {
		spel.testGeeftVeelEdelsteenfichesAanSpelers();

	}

	public List<Speler> bepaalWinnaar() {
		return spel.bepaalWinnaar();
	}

	public void testMaaktWinnaarAan() {
		spel.testMaaktWinnaarAan();

	}
	/**
	 * SpelVoorwerpDTO constructor (Ontwikkelingskaart):
	 * SpelVoorwerpDTO(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart,
			int[] kosten)
	 * @return SpelVoorwerpDTO[] length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau1Zichtbaar() {
		Ontwikkelingskaart[] n1Kaarten = spel.getNiveau1Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n1Kaarten.length];
		for (int i = 0; i < n1Kaarten.length; i++) {
			Ontwikkelingskaart o = n1Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten());
		}
		return dtos;
	}
	/**
	 * 
	 * @return SpelVoorwerpDTO[]  length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau2Zichtbaar() {
		Ontwikkelingskaart[] n2Kaarten = spel.getNiveau2Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n2Kaarten.length];
		for (int i = 0; i < n2Kaarten.length; i++) {
			Ontwikkelingskaart o = n2Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten());
		}
		return dtos;
	}
	/**
	 * 
	 * @return SpelVoorwerpDTO[]  length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau3Zichtbaar() {
		Ontwikkelingskaart[] n3Kaarten = spel.getNiveau3Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n3Kaarten.length];
		for (int i = 0; i < n3Kaarten.length; i++) {
			Ontwikkelingskaart o = n3Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten());
		}
		return dtos;
	}
	/**
	 * SpelVoorwerpDTO constructor(Edele): 
	 * public SpelVoorwerpDTO(int prestigepunten, String edeleFoto, int[] kosten) 
	 * @return List<SpelVoorwerpDTO>
	 */
	public List<SpelVoorwerpDTO> getEdelen() {
		List<Edele> edelen = spel.getEdelen();
		List<SpelVoorwerpDTO> dtos = new ArrayList<>();
		for (Edele e : edelen) {
			dtos.add(new SpelVoorwerpDTO(e.getPrestigepunten(), e.getEdeleFoto(), e.getKosten()));
		}
		return dtos;
	}

	public HashMap<Kleur, Integer> getFicheStapels() {
		return spel.getFicheStapels();
	}
}