package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Speler {

	private String gebruikersnaam;
	private int geboortejaar;
	//nieuw 8-4-2023
	private int aantalPrestigePunten;
	private boolean aanDeBeurt;
	private boolean startSpeler;
	private List<Ontwikkelingskaart> ontwikkelingsKaartenInHand;
	private List<Edele> edelenInHand;
	private List<Edelsteenfiche> edelSteenFichesInHand;

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		//defaultwaarden initializeren + lege lijsten aanmaken
		aantalPrestigePunten = 0;
		aanDeBeurt = false;
		startSpeler = false;
		ontwikkelingsKaartenInHand = new ArrayList<>();
		edelenInHand = new ArrayList<>();
		edelSteenFichesInHand = new ArrayList<>();
	}

	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}
	
	//nieuw 8-4-2023
	public boolean isAanDeBeurt() {
		return aanDeBeurt;
	}
	public final void setAanDeBeurt(boolean nieuweWaarde) {
		aanDeBeurt = nieuweWaarde;
	}
	public boolean isStartSpeler() {
		return startSpeler;
	}
	//zal 1x gebruikt worden door Spel om de jongstespeler als startspeler in te stellen
	public final void setStartSpeler() {
		startSpeler = true;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.isBlank()) {
			throw new IllegalArgumentException("Gebruikersnaam moet ingevuld zijn.");
		}

		String geldigeTekens = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZéèö0123456789 _";
		String alphabet = geldigeTekens.substring(0, geldigeTekens.indexOf("0"));
		boolean bevatOngeldigeTeken = false;

		for (int i = 0; i < gebruikersnaam.length(); i++) {
			if (geldigeTekens.indexOf(gebruikersnaam.charAt(i)) == -1)
				bevatOngeldigeTeken = true;
		}
		// naam mag geen ongeldige tekens bevatten:
		if (bevatOngeldigeTeken) {
			throw new IllegalArgumentException(
					"Gebruikersnaam mag enkel letters en cijfers en spaties en underscores('_') bevatten.");
		}

		// naam moet starten met een letter:
		if (alphabet.indexOf(gebruikersnaam.charAt(0)) == -1) {
			throw new IllegalArgumentException("GebruikersNaam moet starten met een letter.");
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return this.geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		if (geboortejaar > 2016) {
			throw new IllegalArgumentException("Je moet minstens 6 jaar oud zijn om dit spel te spelen.");
		}
		this.geboortejaar = geboortejaar;
	}
	
	public int getPrestigePunten() {
		return aantalPrestigePunten;
	}
	
	public void updatePrestigePunten() {
		
	}

	@Override
	public String toString() {
		int leeftijdInJaar = LocalDate.now().getYear() - this.geboortejaar;
		//O-kaarten
		String ontwikkelingskaartenInBezit = "";
		if(ontwikkelingsKaartenInHand.size() > 0) {
			for (Ontwikkelingskaart ok : ontwikkelingsKaartenInHand) {
				ontwikkelingskaartenInBezit += String.format("%s%n", ok.toString());
			}
		}else {
			ontwikkelingskaartenInBezit = "Je hebt momenteel geen ontwikkelingskaarten in je bezit\n";
		}
		//Fiches /*WIT,ROOD,BLAUW,GROEN,ZWART;*/
		int wit=0, rood=0, blauw=0, groen=0, zwart=0;
		String edelSteenFichesInBezit = "";
		if(edelSteenFichesInHand.size() > 0) {
			for (Edelsteenfiche ef : edelSteenFichesInHand) {
				String kleur = ef.getKleur().toString();
				switch (kleur) {
				case "wit" -> wit++;
				case "rood" -> rood++;
				case "blauw" -> blauw++;
				case "groen" -> groen++;
				case "zwart" -> zwart++;
				}
			}
		}
		edelSteenFichesInBezit += String.format("Witte: %d%n"
				+ "Rode: %d%n"
				+ "Blauwe: %d%n"
				+ "Groene: %d%n"
				+ "Zwarte: %d%n", wit, rood, blauw, groen, zwart);
		
		//Edelen
		String edelenInBezit = "";
		int edelPrestigeTotaal = 0;
		if(edelenInHand.size() > 0) {
			for(Edele e : edelenInHand) {
				edelPrestigeTotaal += e.getPrestigePunten();
			}
		}
		edelenInBezit += String.format("Je hebt %d edelen in bezit met een totale prestigewaarde van %d%n", edelenInHand.size(), edelPrestigeTotaal);

		return String.format("%s: %s --- leeftijd: %d%n"
				+ "Prestigepunten: %d is %saan de beurt%n"
				+ "%s de start speler%n"
				+ "Ontwikkelingskaarten in bezit:%n"
				+ "%s"
				+ "Edelsteenfiches in bezit:%n"
				+ "%s"
				+ "Edelen in bezit:%n"
				+ "%s",
				getClass().getSimpleName(), gebruikersnaam, leeftijdInJaar, aantalPrestigePunten, isAanDeBeurt()? "": "niet ",
						isStartSpeler()? "Is":"Is niet", ontwikkelingskaartenInBezit, edelSteenFichesInBezit, edelenInBezit);
	}

}