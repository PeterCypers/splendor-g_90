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

	static final String spelerNaamOk1 = "speler1";
	static final String spelerNaamOk2 = "Speler_Naam 2";
	static final int GELDIGE_LEEFTIJD = 2000;
	static final int ONGELDIGE_LEEFTIJD = 2022;

	//TODO eventueel in parameterized test van maken met geldige namen in de plaats
	@Test
	void maakSpeler_geldigeParam_MaaktSpeler() {
		Speler s = new Speler(spelerNaamOk1, GELDIGE_LEEFTIJD);
		Speler s2 = new Speler(spelerNaamOk2, GELDIGE_LEEFTIJD);

		assertEquals("speler1", s.getGebruikersnaam());
		assertEquals("Speler_Naam 2", s2.getGebruikersnaam());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "", "   ", " \t   \n" })
	void maakSpeler_legeOfSpatiesBevatendeOngeldigeGebruikersnaam_WerptException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(ongeldigeNaam, GELDIGE_LEEFTIJD));
	}
	//TODO deze kan opgesplist worden in met speciale tekens en met started met een cijfer
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "8SpelerNaam", "s%%%", "abcdefg[][^§(((" })
	void maakSpeler_OngeldigeGebruikersnaam_WerptException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(ongeldigeNaam, GELDIGE_LEEFTIJD));
	}

	@Test
	void maakSpeler_spelerTeJong_WerptException() {
		assertThrows(IllegalArgumentException.class, () -> new Speler(spelerNaamOk1, ONGELDIGE_LEEFTIJD));
	}

	//TODO huidig jaar oproepen en dan -6 doen
	@Test
	void maakSpeler_spelerNetTeJong_WerptException() {
		assertThrows(IllegalArgumentException.class, () -> new Speler(spelerNaamOk1, 2018));
	}

}
