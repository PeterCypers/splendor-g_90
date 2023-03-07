package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spel;

class SpelTest {
	
	Spel s, s2, s3;
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MID_AANTAL_SPELERS = 3;
	public static final int MAX_AANTAL_SPELERS = 4;
 	
	@Test
	void maakSpel_geldigeAantalSpelers_maaktSpelMetJuisteAttribuutWaarden () {
		s = new Spel(MIN_AANTAL_SPELERS); //4 3
		s2 = new Spel(MID_AANTAL_SPELERS); // 5 4
		s3 = new Spel(MAX_AANTAL_SPELERS); // 7 5
		
		//spel min-aantal:
		assertEquals(4, s.getAantalSteentjes());
		assertEquals(3, s.getAantalEdelen());
		//spel mid-aantal:
		assertEquals(5, s2.getAantalSteentjes());
		assertEquals(4, s2.getAantalEdelen());
		//spel max-aantal:
		assertEquals(7, s3.getAantalSteentjes());
		assertEquals(5, s3.getAantalEdelen());
	}
	@ParameterizedTest
	@ValueSource(ints = {-1, MIN_AANTAL_SPELERS -1, MAX_AANTAL_SPELERS + 1, 10_000})
	void maakSpel_ongeldigeAantalSpelers_werptException(int ongeldigeAantalSpelers) {
		assertThrows(IllegalArgumentException.class, () -> new Spel(ongeldigeAantalSpelers));
	}

}
