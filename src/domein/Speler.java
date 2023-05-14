package domein;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import resources.Taal;

public class Speler {
	private static final int MAX_EDELSTEENFICHES_IN_VOORRAAD = 10;

	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalPrestigepunten;
	private boolean aanDeBeurt;
	private boolean startSpeler;
	private List<Ontwikkelingskaart> ontwikkelingskaartenInHand;
	private List<Edele> edelenInHand;
	private HashMap<Kleur, Integer> edelsteenfichesInHand;

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		// defaultwaarden initializeren + lege lijsten aanmaken
		aantalPrestigepunten = 0;
		aanDeBeurt = false;
		startSpeler = false;
		ontwikkelingskaartenInHand = new ArrayList<>();
		edelenInHand = new ArrayList<>();
		edelsteenfichesInHand = new HashMap<Kleur, Integer>();
	}

	public static int getMaxEdelsteenfichesInVoorraad() {
		return MAX_EDELSTEENFICHES_IN_VOORRAAD;
	}

	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.isBlank()) {
			throw new IllegalArgumentException(Taal.getString("spelerSetGebruikersnaamNullOrEmptyExceptionMsg"));
		}

		String geldigeTekens = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZéèö0123456789 _";

		String alphabet = geldigeTekens.substring(0, geldigeTekens.indexOf("0"));
		boolean bevatOngeldigeTeken = false;

		for (int i = 0; i < gebruikersnaam.length(); i++) {
			if (geldigeTekens.indexOf(gebruikersnaam.charAt(i)) == -1)
				bevatOngeldigeTeken = true;
		}

		// naam controle met regex
		if (!gebruikersnaam.matches("[a-zA-Z][a-zA-Z\\d\\s_]*")) {
			throw new IllegalArgumentException("Naam moet geldige tekens bevatten (letters, spaties, underscore) \n"
					+ "en moet beginnen met een letter ");
		}

		// naam mag geen ongeldige tekens bevatten:
		if (bevatOngeldigeTeken) {
			throw new IllegalArgumentException(Taal.getString("spelerSetGebruikersnaamInvalidCharacterExceptionMsg"));
		}

		// naam moet starten met een letter:
		if (alphabet.indexOf(gebruikersnaam.charAt(0)) == -1) {
			throw new IllegalArgumentException(
					Taal.getString("spelerSetGebruikersnaamNotStartsWithLetterExceptionMsg"));
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return this.geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		if (geboortejaar > 2016) {
			throw new IllegalArgumentException(Taal.getString("spelerSetGeboortejaarUserAgeExceptionMsg"));
		}
		this.geboortejaar = geboortejaar;
	}

	public int getPrestigepunten() {
		return aantalPrestigepunten;
	}

	public boolean isAanDeBeurt() {
		return aanDeBeurt;
	}

	public final void setAanDeBeurt(boolean nieuweWaarde) {
		aanDeBeurt = nieuweWaarde;
	}

	public boolean getStartSpeler() {
		return startSpeler;
	}

	// zal 1x gebruikt worden door Spel om de jongstespeler als startspeler in te
	// stellen
	public final void setStartSpeler() {
		startSpeler = true;
	}

	public List<Ontwikkelingskaart> getOntwikkelingskaartenInHand() {
		return ontwikkelingskaartenInHand;
	}

	public List<Edele> getEdelenInHand() {
		return edelenInHand;
	}

	public HashMap<Kleur, Integer> getEdelsteenfichesInHand() {
		return edelsteenfichesInHand;
	}

	// voeg toe aan hand methodes
	public void voegOntwikkelingskaartToeAanHand(Ontwikkelingskaart ok) {
		if (ok == null)
			throw new IllegalArgumentException(String.format("%s %s: %s", Taal.getString("errorIn"), this.getClass(),
					Taal.getString("developmentCardNullExceptionMsg")));
		ontwikkelingskaartenInHand.add(ok);
	}

	public void voegEdelsteenficheToeAanHand(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException(String.format("%s %s: %s", Taal.getString("errorIn"), this.getClass(),
					Taal.getString("gemTokenNullExceptionMsg")));

		Integer currentValue = edelsteenfichesInHand.get(kleur);
		if (currentValue != null) {
			edelsteenfichesInHand.put(kleur, currentValue + 1);
		} else {
			edelsteenfichesInHand.put(kleur, 1);
		}
	}

	public void voegEdeleToeAanHand(Edele e) {
		if (e == null)
			throw new IllegalArgumentException(String.format("%s %s: %s", Taal.getString("errorIn"), this.getClass(),
					Taal.getString("nobleNullExceptionMsg")));
		this.edelenInHand.add(e);
	}

	public void voegPuntenToe(int prestigepunten) {
		this.aantalPrestigepunten += prestigepunten;
	}

	public void verwijderEdelsteenfiche(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException(String.format("%s %s: %s", Taal.getString("errorIn"), this.getClass(),
					Taal.getString("gemTokenNullExceptionMsg")));

		int currentValue = edelsteenfichesInHand.get(kleur);
		if (currentValue - 1 > 0) {
			edelsteenfichesInHand.put(kleur, currentValue - 1);
		} else {
			edelsteenfichesInHand.remove(kleur);
		}
	}

	public boolean buitenVoorraad() {
		// controleer of speler meer dan MAX_EDELSTEENFICHES_IN_VOORRAAD (aantal: 10)
		// heeft
		int totaalAantalEdelsteenfiches = this.totaalAantalFiches();
		return totaalAantalEdelsteenfiches > MAX_EDELSTEENFICHES_IN_VOORRAAD;

	}

	public String toonAantalFiches() {
		String representatieFiches = "";

		if (edelsteenfichesInHand.size() > 0) {
			for (Kleur kleur : Kleur.values()) {
				Integer aantalFiches = edelsteenfichesInHand.get(kleur);
				representatieFiches += String.format("%-6s %d%n", Taal.getString(kleur.toString()) + ":",
						aantalFiches != null ? aantalFiches : 0);
			}
		} else {
			representatieFiches += String.format("%s%n", Taal.getString("spelerToonAantalFichesNoGemTokensMsg"));
		}

		return representatieFiches;
	}

	public int totaalAantalFiches() {
		int sum = 0;
		for (int value : edelsteenfichesInHand.values()) {
			sum += value;
		}
		return sum;
	}

	@Override
	public String toString() {
		int leeftijdInJaar = LocalDate.now().getYear() - this.geboortejaar;

		// O-kaarten
		String ontwikkelingskaartenInBezit = "";

		if (ontwikkelingskaartenInHand.size() > 0) {
			for (Ontwikkelingskaart ok : ontwikkelingskaartenInHand) {
				ontwikkelingskaartenInBezit += String.format("%s%n", ok.toString());
			}
		} else {
			ontwikkelingskaartenInBezit = Taal.getString("spelerNoDevelopmentCardsInPossessionMsg") + "\n";
		}

		// Fiches /*WIT,ROOD,BLAUW,GROEN,ZWART;*/
		String edelSteenFichesInBezit = toonAantalFiches();

		// Edelen
		String edelenInBezit = "";
		int edelPrestigeTotaal = 0;

		if (edelenInHand.size() > 0) {
			for (Edele e : edelenInHand) {
				edelPrestigeTotaal += e.getPrestigepunten();
			}
		}

		edelenInBezit += String.format("%s %d %s %d%n", Taal.getString("spelerNoblesInPossessionMsg1"),
				edelenInHand.size(), Taal.getString("spelerNoblesInPossessionMsg2"), edelPrestigeTotaal);

		return String.format("%s: %s --- %s: %d%n" // 4
				+ "%s: %d %n" // 2
				+ "%s %n" // 1
				+ "%s %s %n" // 2
				+ "%s %s: %n" // 2
				+ "%s" // 1
				+ "%s %s: %n" // 2
				+ "%s" // 1
				+ "%s %s: %n" // 2
				+ "%s" // 1
				+ "%n", Taal.getString("player"), gebruikersnaam, Taal.getString("age").toLowerCase(), leeftijdInJaar,
				Taal.getString("prestigePoints"), aantalPrestigepunten,
				isAanDeBeurt() ? Taal.getString("turnYes") : Taal.getString("turnNo"),
				getStartSpeler() ? Taal.getString("is") : Taal.getString("isNot"),
				Taal.getString("startingPlayer").toLowerCase(), Taal.getString("developmentCards"),
				Taal.getString("inPossession").toLowerCase(), ontwikkelingskaartenInBezit, Taal.getString("gemTokens"),
				Taal.getString("inPossession").toLowerCase(), edelSteenFichesInBezit, Taal.getString("nobles"),
				Taal.getString("inPossession").toLowerCase(), edelenInBezit);

	}

}