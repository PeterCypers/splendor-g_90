package domein;

import java.time.LocalDate;
import java.time.Period;

public class Speler {

	private String gebruikersNaam;
	private LocalDate geboorteDatum; //van int naar datum verandert
	private int geboorteJaar;
	
	/**
	 * 
	 * @param gebruikersNaam
	 * @param geboorteJaar
	 */
	public Speler(String gebruikersNaam, LocalDate geboorteDatum) {
		setGebruikersNaam(gebruikersNaam);
		setGeboorteDatumEnJaar(geboorteDatum);
	}

	public String getGebruikersNaam() {
		return this.gebruikersNaam;
	}

	private void setGebruikersNaam(String gebruikersNaam) {
		//naam moet ingevuld zijn
		if(gebruikersNaam == null || gebruikersNaam.isBlank())
			throw new IllegalArgumentException("GebruikersNaam moet ingevuld zijn.");
		
		String geldigeTekens = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZéèö0123456789 _";
		String alphabet = geldigeTekens.substring(0, geldigeTekens.indexOf("0"));
		boolean bevatOngeldigeTeken = false;
		
		for (int i = 0; i < gebruikersNaam.length(); i++) {
			if (geldigeTekens.indexOf(gebruikersNaam.charAt(i)) == -1) bevatOngeldigeTeken = true;
		}
		//naam mag geen ongeldige tekens bevatten
		if(bevatOngeldigeTeken)
			throw new IllegalArgumentException("GebruikersNaam mag enkel letters en cijfers en spaties en underscores('_') bevatten.");
		//naam moet starten met een letter
		if(alphabet.indexOf(gebruikersNaam.charAt(0)) == -1)
			throw new IllegalArgumentException("GebruikersNaam moet starten met een letter.");
		this.gebruikersNaam = gebruikersNaam;
	}

	public LocalDate getGeboorteDatum() {
		return this.geboorteDatum;
	}
	
	private void setGeboorteJaar(int geboorteJaar) {
		this.geboorteJaar = geboorteJaar;
	}
	
	public int getGeboorteJaar() {
		return this.geboorteJaar;
	}

	private void setGeboorteDatumEnJaar(LocalDate geboorteDatum) {
		LocalDate huidigeTijd = LocalDate.now();
		
		//de tijd tussen de geboorte dag/maand/jaar en vandaag
		Period period = Period.between(geboorteDatum, huidigeTijd);
		if(period.getYears() < 6)
			throw new IllegalArgumentException("Je moet minstens 6 jaar oud zijn om dit spel te spelen.");
		int leeftijdInJaar = period.getYears();
		this.geboorteDatum = geboorteDatum;
		setGeboorteJaar(leeftijdInJaar);
	}
	
	//toString toegevoegd
	@Override
	public String toString() {
		int leeftijd = LocalDate.now().getYear() - geboorteJaar;
		
		return String.format("%s: %s - leeftijd: %d", getClass().getSimpleName(), gebruikersNaam, leeftijd);
	}



}