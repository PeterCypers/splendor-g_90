package domein;

public class Speler {

	private String gebruikersnaam;
	private int geboortejaar;

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
	}

	public String getGebruikersnaam() {
		return this.gebruikersnaam;
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

	@Override
	public String toString() {
		int leeftijdInJaar = 2023 - this.geboortejaar;

		return String.format("%s: %s --- leeftijd: %d", getClass().getSimpleName(), gebruikersnaam, leeftijdInJaar);
	}

}