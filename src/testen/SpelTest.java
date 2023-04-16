package testen;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spel;
import domein.Speler;

class SpelTest {
//	
//	Spel s, s2, s3;
//	public static final int MIN_AANTAL_SPELERS = 2;
//	public static final int MID_AANTAL_SPELERS = 3;
//	public static final int MAX_AANTAL_SPELERS = 4;
// 	
//	@Test
//	void maakSpel_geldigeAantalSpelers_maaktSpelMetJuisteAttribuutWaarden () {
//		s = new Spel(MIN_AANTAL_SPELERS); //4 3
//		s2 = new Spel(MID_AANTAL_SPELERS); // 5 4
//		s3 = new Spel(MAX_AANTAL_SPELERS); // 7 5
//		
//		//spel min-aantal:
//		assertEquals(4, s.getAantalSteentjes());
//		assertEquals(3, s.getAantalEdelen());
//		//spel mid-aantal:
//		assertEquals(5, s2.getAantalSteentjes());
//		assertEquals(4, s2.getAantalEdelen());
//		//spel max-aantal:
//		assertEquals(7, s3.getAantalSteentjes());
//		assertEquals(5, s3.getAantalEdelen());
//	}
//	@ParameterizedTest
//	@ValueSource(ints = {-1, MIN_AANTAL_SPELERS -1, MAX_AANTAL_SPELERS + 1, 10_000})
//	void maakSpel_ongeldigeAantalSpelers_werptException(int ongeldigeAantalSpelers) {
//		assertThrows(IllegalArgumentException.class, () -> new Spel(ongeldigeAantalSpelers));
//	}
	Speler spelerA = new Speler("sA", 2002);
	Speler spelerB = new Speler("sB", 2000);
	Speler spelerC = new Speler("sC", 1999);
	Speler spelerD = new Speler("sD", 1998);

//	@BeforeEach
//	public void setUp() {
//		
//	}

//	@Test
//	public void maakSpel_lijstGeldigeSpelerAantal_maaktNieuwSpelAan() {
//		List<Speler> spelers = new ArrayList<Speler>();
//		spelers.add(spelerA);
//		spelers.add(spelerB);
//		Spel spel = new Spel(spelers);
//		assertEquals(2, spel.getAantalSpelers());
//	}

	// TODO hieronder een idee van mogelijkse test

//	@Test
//	public void maakSpel_lijstOngeldigSpelerAantal_maaktSpelNiet() {
//		List<Speler> spelers = new ArrayList<Speler>();
//		spelers.add(spelerA);
//		Spel spel = new Spel(spelers);
//		assertThrows(IllegalArgumentException.class, () -> "De lijst met spelers moet volledig zijn");
//	}

}