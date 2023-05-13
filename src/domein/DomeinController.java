package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.SpelVoorwerpDTO;
import dto.SpelerDTO;
import resources.Taal;

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
							String.format("%s %s %s.", Taal.getString("duplicatePlayerErrorMsgPart1"), gebruikersNaam,
									Taal.getString("duplicatePlayerErrorMsgPart2")));
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
			throw new IllegalArgumentException(String.format("%s.", Taal.getString("playerDoesntExistErrorMsg")));

		aangemeldeSpelers.add(geselecteerdeSpeler);
	}

	private void controleerAantalSpelers() {
		if (aangemeldeSpelers.size() == Spel.MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException(String.format("%s.", Taal.getString("maxPlayerCountErrorMsg")));
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
		return String.format("%s ---- %s: %d", spel.getSpelerAanBeurt().getGebruikersnaam(),
				Taal.getString("age").toLowerCase(), leeftijdInJaar);
	}

	// TODO player.toString() -> vertaal Speler
	public String toonSpelerAanBeurtSituatie() {
		return String.format("%n*** %s ***%n%s", Taal.getString("playerStatusAfterTurnMsg"),
				this.spel.getSpelerAanBeurt().toString());
	}

	// maakt gebruik van Spel.geefSpelVoorwerpen()
	public List<SpelVoorwerpDTO> toonSpelSituatie() {
		List<SpelVoorwerp> spelvoorwerpen = spel.geefSpelVoorwerpen();
		List<SpelVoorwerpDTO> lijstDTOs = new ArrayList<>();
		SpelVoorwerpDTO dto = null;

		for (SpelVoorwerp vw : spelvoorwerpen) {
			if (vw instanceof Ontwikkelingskaart ok) {
				dto = new SpelVoorwerpDTO(ok.getNiveau(), ok.getPrestigepunten(), ok.getKleurBonus(),
						ok.getFotoOntwikkelingskaart(), ok.getKosten(), ok.toString());
			} else if (vw instanceof Edele e) {
				dto = new SpelVoorwerpDTO(e.getPrestigepunten(), e.getEdeleFoto(), e.getKosten());
			}

			lijstDTOs.add(dto);
		}

		return lijstDTOs;
	}

	/**
	 * 
	 * @return String representatie van de speler situatie en BEURT ASCII
	 */
	public String toonSpelersSituatie() { // BEURT
		String spelerSituatie = "";
		spelerSituatie += beurtAsciiArt(Taal.getResource().getLocale().getLanguage());
		spelerSituatie += String.format(
				"%n************************************ %s: ************************************%n%n",
				Taal.getString("playerSituation"));
		List<Speler> spelerInSpel = this.spel.getAangemeldeSpelers();
		// TODO Speler.toString -> vertaal Speler
		for (Speler s : spelerInSpel) {
			spelerSituatie += String.format("%s%n", s.toString());
		}

		return spelerSituatie;
	}

	// repository [testmethode] , toont alle opgeslagen spelers in de spelerRepo ==
	// alle spelers uit de database opgehaald
	public String toonAlleSpelers() {
		List<Speler> spelers = spelerRepo.getSpelers();
		String alleSpelers = String.format("***DB-%s***%n", Taal.getString("players"));

		for (Speler s : spelers) {
			alleSpelers += String.format("%s: %6s  | %s: %3d %s | %s: %d%n", Taal.getString("username"),
					s.getGebruikersnaam(), Taal.getString("age").toLowerCase(),
					LocalDate.now().getYear() - s.getGeboortejaar(), Taal.getString("dcToonAlleSpelersYear"),
					Taal.getString("birthyear").toLowerCase(), s.getGeboortejaar());
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

		return returnStr.isBlank() ? Taal.getString("dcToonAangemeldeSpelersZeroPlayersMsg") : returnStr;
	}

	public String toonAantalFichesVanSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().toonAantalFiches();
	}

	// TODO spel.toonFiches() -> Spel vertalen
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

	// TODO return List<spelerDTO>
	public List<Speler> bepaalWinnaar() {
		return spel.bepaalWinnaar();
	}

	public void testMaaktWinnaarAan() {
		spel.testMaaktWinnaarAan();

	}
	public void testMaaktEenWinnaarAan() {
		spel.testMaaktEenWinnaarAan();
	}

	/**
	 * SpelVoorwerpDTO constructor (Ontwikkelingskaart): SpelVoorwerpDTO(int niveau,
	 * int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart, int[]
	 * kosten)
	 * 
	 * @return SpelVoorwerpDTO[] length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau1Zichtbaar() {
		Ontwikkelingskaart[] n1Kaarten = spel.getNiveau1Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n1Kaarten.length];

		for (int i = 0; i < n1Kaarten.length; i++) {
			Ontwikkelingskaart o = n1Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten(), o.toString());
		}

		return dtos;
	}

	/**
	 * 
	 * @return SpelVoorwerpDTO[] length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau2Zichtbaar() {
		Ontwikkelingskaart[] n2Kaarten = spel.getNiveau2Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n2Kaarten.length];

		for (int i = 0; i < n2Kaarten.length; i++) {
			Ontwikkelingskaart o = n2Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten(), o.toString());
		}

		return dtos;
	}

	/**
	 * 
	 * @return SpelVoorwerpDTO[] length = 4
	 */
	public SpelVoorwerpDTO[] getNiveau3Zichtbaar() {
		Ontwikkelingskaart[] n3Kaarten = spel.getNiveau3Zichtbaar();
		SpelVoorwerpDTO[] dtos = new SpelVoorwerpDTO[n3Kaarten.length];

		for (int i = 0; i < n3Kaarten.length; i++) {
			Ontwikkelingskaart o = n3Kaarten[i];
			dtos[i] = new SpelVoorwerpDTO(o.getNiveau(), o.getPrestigepunten(), o.getKleurBonus(),
					o.getFotoOntwikkelingskaart(), o.getKosten(), o.toString());
		}

		return dtos;
	}

	/**
	 * SpelVoorwerpDTO constructor(Edele): public SpelVoorwerpDTO(int
	 * prestigepunten, String edeleFoto, int[] kosten)
	 * 
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

	public List<SpelerDTO> getAangemeldeSpelers() {
		List<Speler> aangemeldeSpelers = spel.getAangemeldeSpelers();
		List<SpelerDTO> dtos = new ArrayList<>();

		for (Speler s : aangemeldeSpelers) {
			dtos.add(new SpelerDTO(s.getGebruikersnaam(), s.getGeboortejaar(), s.getPrestigepunten(), s.getAanDeBeurt(),
					s.getOntwikkelingskaartenInHand(), s.getEdelenInHand(), s.getEdelsteenfichesInHand()));
		}

		return dtos;
	}

	/**
	 * 
	 * @param taal expect Taal.getResource().getLocale().getLanguage()
	 * @return Ascii Art of beurt / turn / tour
	 */
	// nieuwe methode 8-05-2023
	private String beurtAsciiArt(String taal) {
		String asciiSign = "";
		if (taal == "nl") {
			asciiSign += "\n" + "  _                     _   \r\n" + " | |__   ___ _   _ _ __| |_ \r\n"
					+ " | '_ \\ / _ \\ | | | '__| __|\r\n" + " | |_) |  __/ |_| | |  | |_ \r\n"
					+ " |_.__/ \\___|\\__,_|_|   \\__|\r\n" + "                            " + "\n";
		}
		if (taal == "en") {
			asciiSign += "\n" + "  _                    \r\n" + " | |_ _   _ _ __ _ __  \r\n"
					+ " | __| | | | '__| '_ \\ \r\n" + " | |_| |_| | |  | | | |\r\n" + "  \\__|\\__,_|_|  |_| |_|\r\n"
					+ "                       " + "\n";
		}
		if (taal == "fr") {
			asciiSign += "\n" + "  _                   \r\n" + " | |_ ___  _   _ _ __ \r\n"
					+ " | __/ _ \\| | | | '__|\r\n" + " | || (_) | |_| | |   \r\n" + "  \\__\\___/ \\__,_|_|   \r\n"
					+ "                      " + "\n";
		}
		return asciiSign;
	}

	public int getRonde() {
		return spel.getRonde();
	}
}