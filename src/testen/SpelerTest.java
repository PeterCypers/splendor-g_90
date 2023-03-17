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
	static final LocalDate leeftijdOk = LocalDate.of(LocalDate.now().getYear() - 8, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
	static final LocalDate leeftijdNietOk = LocalDate.of(LocalDate.now().getYear() - 5, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
	//methode gevonden om dagen bij te tellen of af te trekken
	static final LocalDate leeftijdNetTeJong = LocalDate.of(LocalDate.now().getYear()-6, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()).plusDays(10);
	//preciese test(10 dagen verschil)
	static final LocalDate leeftijdNetOkMetTienDagen = LocalDate.of(LocalDate.now().getYear()-6, 
			LocalDate.now().getDayOfMonth()<=10 && LocalDate.now().getMonthValue()==1? 12
					:LocalDate.now().getDayOfMonth()<=10? LocalDate.now().getMonthValue()-1
							:LocalDate.now().getMonthValue(),
							LocalDate.now().getDayOfMonth()<=10?LocalDate.now().getDayOfMonth():
								LocalDate.now().getDayOfMonth()-10);
	//minder preciese test(1 maand verschil)
	static final LocalDate leeftijdNetOkMetEenMaand = LocalDate.of(LocalDate.now().getYear()-6,
			LocalDate.now().getMonthValue()==1? 12 : LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());

	@Test
	void maakSpeler_geldigeParam_MaaktSpeler() {
		Speler s = new Speler(spelerNaamOk1, leeftijdOk);
		Speler s2 = new Speler(spelerNaamOk2, leeftijdOk);
		Speler s3 = new Speler(spelerNaamOk3, leeftijdOk);
		Speler s4 = new Speler(spelerNaamOk4, leeftijdOk);
		Speler s5 = new Speler(spelerNaamOk4, leeftijdNetOkMetTienDagen);
		Speler s6 = new Speler(spelerNaamOk4, leeftijdNetOkMetEenMaand);
		
		assertEquals("Speler1", s.getGebruikersNaam());
		assertEquals(8 , s.getLeeftijd());
		
		assertEquals("Speler 01", s2.getGebruikersNaam());
		assertEquals(8 , s2.getLeeftijd());
		
		assertEquals("Speler_naam 03", s3.getGebruikersNaam());
		assertEquals(8 , s3.getLeeftijd());
		
		assertEquals("Vierde_speler 7", s4.getGebruikersNaam());
		assertEquals(8 , s4.getLeeftijd());
		
		assertEquals("Vierde_speler 7", s5.getGebruikersNaam());
		assertEquals(6 , s5.getLeeftijd());
		
		assertEquals("Vierde_speler 7", s6.getGebruikersNaam());
		assertEquals(6 , s6.getLeeftijd());
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
	
	@Test
	void maakSpeler_spelerNetTeJong_WerptException() {
		assertThrows(IllegalArgumentException.class, () -> new Speler(spelerNaamOk1, leeftijdNetTeJong));
	}

}
