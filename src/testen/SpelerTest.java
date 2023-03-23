package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

class SpelerTest {

	Speler s, s2, s3, s4;

	static final String GELDIGE_NAAM = "speler1";
	static final String GELDIGE_NAAM_2 = "Speler_Naam 2";
	static final int GELDIGE_LEEFTIJD = 2000;
	static final int ONGELDIGE_LEEFTIJD = 2022;

	@Test
	void maakSpeler_geldigeParam_MaaktSpeler() {
		Speler s = new Speler(GELDIGE_NAAM, GELDIGE_LEEFTIJD);
		Speler s2 = new Speler(GELDIGE_NAAM_2, GELDIGE_LEEFTIJD);

		assertEquals("speler1", s.getGebruikersnaam());
		assertEquals("Speler_Naam 2", s2.getGebruikersnaam());
	}

	@ParameterizedTest
	@ValueSource(strings = { "speler3", "S123456", "S 1234_", "s___ 123" })
	void maakSpeler_geldigeGebruikersnamen_maaktDeSpelersAan(String geldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(geldigeNaam, GELDIGE_LEEFTIJD));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "", "   ", " \t   \n" })
	void maakSpeler_legeOfSpatiesBevatendeOngeldigeGebruikersnaam_werptException(String legeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(legeNaam, GELDIGE_LEEFTIJD));
	}

	@ParameterizedTest
	@ValueSource(strings = { "8SpelerNaam", "s%%%", "abcdefg[][^§(((" })
	void maakSpeler_OngeldigeGebruikersnaam_werptException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(ongeldigeNaam, GELDIGE_LEEFTIJD));
	}

	@Test
	void maakSpeler_spelerTeJong_werptException() {
		assertThrows(IllegalArgumentException.class, () -> new Speler(GELDIGE_NAAM, ONGELDIGE_LEEFTIJD));
	}

	@Test
	void maakSpeler_spelerNetTeJong_werptException() {
		LocalDate now = LocalDate.now();
		int geboortejaarTeJongeSpeler = now.getYear() - 5;
		assertThrows(IllegalArgumentException.class, () -> new Speler(GELDIGE_NAAM, geboortejaarTeJongeSpeler));
	}
	
	@Test
	void maakSpeler_spelerNetOudGenoeg_maaktDeSpelerAan() {
		LocalDate now = LocalDate.now();
		int geboortejaarZesJarige = now.getYear() - 6;
		assertThrows(IllegalArgumentException.class, () -> new Speler(GELDIGE_NAAM, geboortejaarZesJarige));
	}

}
