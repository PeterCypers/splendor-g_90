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
	
	static final String spelerNaamOk1 = "Speler1";
	static final String spelerNaamOk2 = "Speler 01";
	static final String spelerNaamOk3 = "Speler_naam 03";
	static final String spelerNaamOk4 = "Vierde_speler 7";
	static final String startNietMetLetter = "8Speler 4";
	static final int leeftijdOk = LocalDate.now().getYear() - 8;
	static final int leeftijdNietOk = LocalDate.now().getYear() - 5;
	static final int leeftijdNetOk = LocalDate.now().getYear() - 6;

	@Test
	void maakSpeler_geldigeParam_MaaktSpeler() {
		Speler s = new Speler(spelerNaamOk1, leeftijdOk);
		Speler s2 = new Speler(spelerNaamOk2, leeftijdOk);
		Speler s3 = new Speler(spelerNaamOk3, leeftijdOk);
		Speler s4 = new Speler(spelerNaamOk4, leeftijdNetOk);
		
		assertEquals("Speler1", s.getGebruikersNaam());
		assertEquals(leeftijdOk, s.getGeboorteJaar());
		
		assertEquals("Speler 01", s2.getGebruikersNaam());
		assertEquals(leeftijdOk, s2.getGeboorteJaar());
		
		assertEquals("Speler_naam 03", s3.getGebruikersNaam());
		assertEquals(leeftijdOk, s3.getGeboorteJaar());
		
		assertEquals("Vierde_speler 7", s4.getGebruikersNaam());
		assertEquals(leeftijdNetOk, s4.getGeboorteJaar());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"", "   ", " \t   \n", startNietMetLetter})
	void maakSpeler_ongeldigeGebruikersNaam_WerptException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Speler(ongeldigeNaam, leeftijdOk));
	}
	
	@Test
	void maakSpeler_spelerTeJong_WerptException() {
		assertThrows(IllegalArgumentException.class, () -> new Speler(spelerNaamOk1, leeftijdNietOk));
	}

}
