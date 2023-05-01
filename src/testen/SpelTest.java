package testen; //commit extra message

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Edele;
import domein.EdeleRepository;
import domein.EdelsteenficheRepository;
import domein.Kleur;
import domein.Ontwikkelingskaart;
import domein.OntwikkelingskaartRepository;
import domein.Spel;
import domein.SpelVoorwerp;
import domein.Speler;

class SpelTest {
	
//	Spel spelMetGeldigeParameters; //spel in spe
	Spel spelMethodeTester; //spel aangemaakt met juiste parameters voor 4 spelers
	Speler s, s2, s3, s4, s5; //s is jongste speler
	OntwikkelingskaartRepository okRepo;
	EdeleRepository edeleRepo;
	EdelsteenficheRepository ficheRepo;
	
	List<Speler> spelersLijstMetTeWeinigSpelers_1, spelersLijstMetMinAantalSpelers_2, spelersLijstMetMidAantalSpelers_3,
	             spelersLijstMetMaxAantalSpelers_4, spelersLijstMetTeveelSpelers_5;
	List<Edele> edelenVoor2Spelers, edelenVoor3Spelers, edelenVoor4Spelers, lijstMetTeWeinigEdelen, lijstMetTeVeelEdelen;
	List<List<Ontwikkelingskaart>> ontwikkelingsKaarten_goedeLijsten, ontwikkelingsKaarten_metNullLijst, ontwikkelingsKaarten_eenNullKaartInEenLijst,
	ontwikkelingsKaarten_metEenLijstOntbrekend, ontwikkelingsKaarten_metDuplicateLijst;
	
	HashMap<Kleur, Integer> ficheStapel_2_spelers, ficheStapel_3_spelers, ficheStapel_4_spelers;
	
	
	
	/* domeinregels: UC1 -> DR_SPEL_STARTER(1ste speler = jongste speler) | DR_SPEL_ AANTAL_SPELERS(2-4) | DR_SPEL_NIEUW(grootte van de lijsten/fields)
	 *               UC2 -> DR_SPEL_SITUATIE | DR_SPEL_EINDE |  DR_SPEL_WINNAAR
	 *               
	 *  note: fields van Spel hangen voor een groot deel af van het aantal deelnemende spelers -> grootte van de lijsten
	 */
	
	//setup:
	@BeforeEach
	void setup() {
		String speler_1_naam = "speler1", speler_2_naam = "speler2", speler_3_naam = "speler3", speler_4_naam = "speler4", speler_5_naam = "speler5";
		int geboorteJaarSpeler1 = 2010, geboorteJaarSpeler2 = 2000, geboorteJaarSpeler3 = 2005, geboorteJaarSpeler4 = 2006, geboorteJaarSpeler5 = 2001;
		s = new Speler(speler_1_naam, geboorteJaarSpeler1);
		s2 = new Speler(speler_2_naam, geboorteJaarSpeler2);
		s3 = new Speler(speler_3_naam, geboorteJaarSpeler3);
		s4 = new Speler(speler_4_naam, geboorteJaarSpeler4);
		s5 = new Speler(speler_5_naam, geboorteJaarSpeler5); //extra speler test constructor spel
		Speler[] vijfSpelers = {s, s2, s3, s4, s5};
		okRepo = new OntwikkelingskaartRepository();
		edeleRepo = new EdeleRepository();
		
		//speler lijsten opvullen:
		spelersLijstMetTeWeinigSpelers_1 = new ArrayList<>();
		spelersLijstMetMinAantalSpelers_2 = new ArrayList<>();
		spelersLijstMetMidAantalSpelers_3 = new ArrayList<>();
		spelersLijstMetMaxAantalSpelers_4 = new ArrayList<>();
		spelersLijstMetTeveelSpelers_5 = new ArrayList<>();
		for (int i = 1; i <= vijfSpelers.length; i++) {
			if(i <= 1) spelersLijstMetTeWeinigSpelers_1.add(vijfSpelers[i-1]);
			if(i <= 2) spelersLijstMetMinAantalSpelers_2.add(vijfSpelers[i-1]);
			if(i <= 3) spelersLijstMetMidAantalSpelers_3.add(vijfSpelers[i-1]);
			if(i <= 4) spelersLijstMetMaxAantalSpelers_4.add(vijfSpelers[i-1]);
			if(i <= 5) spelersLijstMetTeveelSpelers_5.add(vijfSpelers[i-1]);
		}
		
		//goede lijst opvullen met de 3 kaartniveaus:
		ontwikkelingsKaarten_goedeLijsten = new ArrayList<>();
		ontwikkelingsKaarten_goedeLijsten.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_goedeLijsten.add(okRepo.geefN2Kaarten());
		ontwikkelingsKaarten_goedeLijsten.add(okRepo.geefN3Kaarten());
		//met null lijst:
		ontwikkelingsKaarten_metNullLijst = new ArrayList<>();
		ontwikkelingsKaarten_metNullLijst.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_metNullLijst.add(null);
		ontwikkelingsKaarten_metNullLijst.add(okRepo.geefN3Kaarten());
		//met 1 lijst ontbrekend:
		ontwikkelingsKaarten_metEenLijstOntbrekend = new ArrayList<>();
		ontwikkelingsKaarten_metEenLijstOntbrekend.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_metEenLijstOntbrekend.add(okRepo.geefN2Kaarten());
		//met duplicate lijst:
		ontwikkelingsKaarten_metDuplicateLijst = new ArrayList<>();
		ontwikkelingsKaarten_metDuplicateLijst.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_metDuplicateLijst.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_metDuplicateLijst.add(okRepo.geefN2Kaarten());
		
		//met 1 null kaart in een lijst: (hiervoor moet repo opnieuw gemaakt worden om de lijst N3 kaart reference te veranderen
		//anders zal N3 voor alle goeie lijsten ook gemuteerd worden en een null kaart bevatten
		okRepo = new OntwikkelingskaartRepository();
		ontwikkelingsKaarten_eenNullKaartInEenLijst = new ArrayList<>();
		ontwikkelingsKaarten_eenNullKaartInEenLijst.add(okRepo.geefN1Kaarten());
		ontwikkelingsKaarten_eenNullKaartInEenLijst.add(okRepo.geefN2Kaarten());
		List<Ontwikkelingskaart> n3MetNullKaart = okRepo.geefN3Kaarten();
		n3MetNullKaart.set(0, null);
		ontwikkelingsKaarten_eenNullKaartInEenLijst.add(n3MetNullKaart);
		
		
		//edele lijsten:
		edelenVoor2Spelers = edeleRepo.geefEdelen(2);
		edeleRepo = new EdeleRepository();
		edelenVoor3Spelers = edeleRepo.geefEdelen(3);
		edeleRepo = new EdeleRepository();
		edelenVoor4Spelers = edeleRepo.geefEdelen(4);
		edeleRepo = new EdeleRepository();
		lijstMetTeWeinigEdelen = edeleRepo.geefEdelen(1);
		edeleRepo = new EdeleRepository();
		lijstMetTeVeelEdelen = edeleRepo.geefEdelen(4);
		int[] kosten = {4, 4, 4, 4, 4};
		lijstMetTeVeelEdelen.add(new Edele(3, "dame6", kosten));
		//fichestapels:
		ficheRepo = new EdelsteenficheRepository(2);
		ficheStapel_2_spelers = ficheRepo.geefEdelsteenficheStapels();
		ficheRepo = new EdelsteenficheRepository(3);
		ficheStapel_3_spelers = ficheRepo.geefEdelsteenficheStapels();
		ficheRepo = new EdelsteenficheRepository(4);
		ficheStapel_4_spelers = ficheRepo.geefEdelsteenficheStapels();
		
		spelMethodeTester = new Spel(spelersLijstMetMaxAantalSpelers_4, ontwikkelingsKaarten_goedeLijsten, edelenVoor4Spelers,
				ficheStapel_4_spelers);
	}
	/**
	   << Spel constructor verwachte parameters:>>
	   List<Speler> aangemeldeSpelers, List<List<Ontwikkelingskaart>> ontwikkelingsKaarten, List<Edele> edelen,
	   HashMap<Kleur, Integer> ficheStapels
	   
	   grootte van de lijst test met mediaan van lijst grootte: 3 spelers, een lijst teklein is grootte bedoeld voor 2 spelers / lijst tegroot
	   een lijstgrootte voor 4 spelers
	 */
	//constructor tests:
	//ongeldige aangemeldeSpelers parameter:
	@Test
	void maakSpel_teWeinigSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetTeWeinigSpelers_1, ontwikkelingsKaarten_goedeLijsten,edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	
	@Test
	void maakSpel_teVeelSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetTeveelSpelers_5, ontwikkelingsKaarten_goedeLijsten,edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	@Test
	void maakSpel_spelers_null_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(null, ontwikkelingsKaarten_goedeLijsten, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	//ongeldige ontwikkelingsKaarten parameter:
	@Test
	void maakSpel_ontwikkelingsKaarten_metNullLijst_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_metNullLijst, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	@Test
	void maakSpel_ontwikkelingsKaarten_eenNullKaartInEenLijst_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_eenNullKaartInEenLijst, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	
	@Test
	void maakSpel_ontwikkelingsKaarten_metEenLijstOntbrekend_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_metEenLijstOntbrekend, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	
	@Test
	void maakSpel_ontwikkelingsKaarten_metDuplicateLijst_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_metDuplicateLijst, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	@Test
	void maakSpel_ontwikkelingsKaarten_null_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, null, edelenVoor2Spelers, ficheStapel_2_spelers));
	}
	//ongeldige edelen parameter:
	@Test
	void maakSpel_lijstMetTeWeinigEdelen_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_goedeLijsten, lijstMetTeWeinigEdelen, ficheStapel_2_spelers));
	}
	@Test
	void maakSpel_lijstMetTeVeelEdelen_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_goedeLijsten, lijstMetTeVeelEdelen, ficheStapel_2_spelers));
	}
	@Test
	void maakSpel_edelen_null_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_goedeLijsten, null, ficheStapel_2_spelers));
	}
	/**
	 * List<Edele> voor 2 spelers ipv 3
	 */
	@Test
	void maakSpel_lijstMetTeWeinigEdelenVoorDrieSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMidAantalSpelers_3, ontwikkelingsKaarten_goedeLijsten, edelenVoor2Spelers, ficheStapel_3_spelers));
	}
	/**
	 * List<Edele> voor 4 spelers ipv 3
	 */
	@Test
	void maakSpel_lijstMetTeVeelEdelenVoorDrieSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMidAantalSpelers_3, ontwikkelingsKaarten_goedeLijsten, edelenVoor4Spelers, ficheStapel_3_spelers));
	}
	//ongeldige ficheStapels parameter:
	@Test
	void maakSpel_ficheStapels_null_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMidAantalSpelers_3, ontwikkelingsKaarten_goedeLijsten, edelenVoor3Spelers, null));
	}
	/**
	 * HashMap<Kleur, Integer> ficheStapels voor 4 spelers ipv 3
	 */
	@Test
	void maakSpel_stapelTeGrootVoorSpelMetDrieSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMidAantalSpelers_3, ontwikkelingsKaarten_goedeLijsten, edelenVoor3Spelers, ficheStapel_4_spelers));
	}
	/**
	 * HashMap<Kleur, Integer> ficheStapels voor 2 spelers ipv 3
	 */
	@Test
	void maakSpel_stapelTekleinVoorSpelMetDrieSpelers_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(spelersLijstMetMidAantalSpelers_3, ontwikkelingsKaarten_goedeLijsten, edelenVoor3Spelers, ficheStapel_2_spelers));
	}
	//alle geldige parameters: maakt object(controleer op jongste speler -> "speler1" is in deze klasse de jongste)
	@Test
	void maakSpel_geldigeParameters_maaktSpel() {
		Spel spelMetGeldigeParameters = new Spel(spelersLijstMetMinAantalSpelers_2, ontwikkelingsKaarten_goedeLijsten, edelenVoor2Spelers, ficheStapel_2_spelers);
		int somAantalZichtbareKaarten = 0;
		Assertions.assertEquals("speler1" , spelMetGeldigeParameters.getSpelerAanBeurt().getGebruikersnaam());
		Assertions.assertFalse(spelMetGeldigeParameters.isEindeSpel());
		Assertions.assertTrue(spelMetGeldigeParameters.getSpelerAanBeurt().isAanDeBeurt());
		Assertions.assertTrue(spelMetGeldigeParameters.getSpelerAanBeurt().isStartSpeler());
//		Assertions.assertEquals("[36, 26, 16]", Arrays.toString(spelMetGeldigeParameters.aantalKaartenResterend()));
		Assertions.assertEquals(36, spelMetGeldigeParameters.aantalKaartenResterend()[0]);
		Assertions.assertEquals(26, spelMetGeldigeParameters.aantalKaartenResterend()[1]);
		Assertions.assertEquals(16, spelMetGeldigeParameters.aantalKaartenResterend()[2]);
		//juist aantal zichtbare kaarten: 4 + 4 + 4 = 12
		List<SpelVoorwerp> spelvoorwerpen = spelMetGeldigeParameters.geefSpelVoorwerpen();
		for (SpelVoorwerp sv : spelvoorwerpen)
			if(sv instanceof Ontwikkelingskaart) somAantalZichtbareKaarten++;
		Assertions.assertEquals(12, somAantalZichtbareKaarten);
	}
	
	//method tests:
	/**
	 * method: Spel.volgendeSpeler()
	 * spel met 4 spelers, volgende speler cycled juist door de spelers
	 * spelerslijst: [s, s2, s3, s4]
	 * spelers.getNaam: ["speler1", "speler2", "speler3", "speler4"]
	 */
	@Test
	void testVolgendeSpeler_eenKeerVolgendeSpeler_spelerAanBeurtIsJuisteSpeler() {
		//start bij speler s, volgende spelerAanBeurt == s2
		spelMethodeTester.volgendeSpeler();
		List<Speler> spelers = spelMethodeTester.getAangemeldeSpelers();
		Speler speler1 = spelers.get(0);
		Speler speler2 = spelers.get(1);
		Speler speler3 = spelers.get(2);
		Speler speler4 = spelers.get(3);
		Assertions.assertEquals("speler2", spelMethodeTester.getSpelerAanBeurt().getGebruikersnaam());
		
		Assertions.assertFalse(speler1.isAanDeBeurt());
		Assertions.assertTrue(speler2.isAanDeBeurt()); //speler2 is aan de beurt
		Assertions.assertFalse(speler3.isAanDeBeurt());
		Assertions.assertFalse(speler4.isAanDeBeurt());
	}
	@Test
	void testVolgendeSpeler_tweeKeerVolgendeSpeler_spelerAanBeurtIsJuisteSpeler() {
		//start bij speler s, tweeKeerVolgendeSpeler, spelerAanBeurt == s3
		for (int i = 0; i < 2; i++) {
			spelMethodeTester.volgendeSpeler();
		}
		List<Speler> spelers = spelMethodeTester.getAangemeldeSpelers();
		Speler speler1 = spelers.get(0);
		Speler speler2 = spelers.get(1);
		Speler speler3 = spelers.get(2);
		Speler speler4 = spelers.get(3);
		Assertions.assertEquals("speler3", spelMethodeTester.getSpelerAanBeurt().getGebruikersnaam());
		
		Assertions.assertFalse(speler1.isAanDeBeurt());
		Assertions.assertFalse(speler2.isAanDeBeurt()); 
		Assertions.assertTrue(speler3.isAanDeBeurt()); //speler3 is aan de beurt
		Assertions.assertFalse(speler4.isAanDeBeurt());
	}
	@Test
	void testVolgendeSpeler_drieKeerVolgendeSpeler_spelerAanBeurtIsJuisteSpeler() {
		//start bij speler s, drieKeerVolgendeSpeler, spelerAanBeurt == s4
		for (int i = 0; i < 3; i++) {
			spelMethodeTester.volgendeSpeler();
		}
		List<Speler> spelers = spelMethodeTester.getAangemeldeSpelers();
		Speler speler1 = spelers.get(0);
		Speler speler2 = spelers.get(1);
		Speler speler3 = spelers.get(2);
		Speler speler4 = spelers.get(3);
		Assertions.assertEquals("speler4", spelMethodeTester.getSpelerAanBeurt().getGebruikersnaam());
		
		Assertions.assertFalse(speler1.isAanDeBeurt());
		Assertions.assertFalse(speler2.isAanDeBeurt()); 
		Assertions.assertFalse(speler3.isAanDeBeurt());
		Assertions.assertTrue(speler4.isAanDeBeurt()); //speler4 is aan de beurt
	}
	@Test
	void testVolgendeSpeler_vierKeerVolgendeSpeler_spelerAanBeurtIsJuisteSpeler() {
		//start bij speler s, vierKeerVolgendeSpeler, spelerAanBeurt == s1
		for (int i = 0; i < 4; i++) {
			spelMethodeTester.volgendeSpeler();
		}
		List<Speler> spelers = spelMethodeTester.getAangemeldeSpelers();
		Speler speler1 = spelers.get(0);
		Speler speler2 = spelers.get(1);
		Speler speler3 = spelers.get(2);
		Speler speler4 = spelers.get(3);
		Assertions.assertEquals("speler1", spelMethodeTester.getSpelerAanBeurt().getGebruikersnaam());
		
		Assertions.assertTrue(speler1.isAanDeBeurt()); //speler1 is aan de beurt
		Assertions.assertFalse(speler2.isAanDeBeurt()); 
		Assertions.assertFalse(speler3.isAanDeBeurt());
		Assertions.assertFalse(speler4.isAanDeBeurt());
	}
	@Test
	void testVolgendeSpeler_vijfKeerVolgendeSpeler_spelerAanBeurtIsJuisteSpeler() {
		//start bij speler s, vijfKeerVolgendeSpeler, spelerAanBeurt == s2
		for (int i = 0; i < 5; i++) {
			spelMethodeTester.volgendeSpeler();
		}
		List<Speler> spelers = spelMethodeTester.getAangemeldeSpelers();
		Speler speler1 = spelers.get(0);
		Speler speler2 = spelers.get(1);
		Speler speler3 = spelers.get(2);
		Speler speler4 = spelers.get(3);
		Assertions.assertEquals("speler2", spelMethodeTester.getSpelerAanBeurt().getGebruikersnaam());
		
		Assertions.assertFalse(speler1.isAanDeBeurt());
		Assertions.assertTrue(speler2.isAanDeBeurt()); //speler2 is aan de beurt
		Assertions.assertFalse(speler3.isAanDeBeurt());
		Assertions.assertFalse(speler4.isAanDeBeurt());
	}
	/**
	 * method: Spel.kiesOntwikkelingskaart(int niveau, int positie)
	 * niveau: [1-3]
	 * positie: [1-4]
	 * spelerAanBeurt krijgt de gekozen kaart, de prestige die bij die kaart hoort wordt bij de spelers prestige
	 * geteld, de gekozen kaart komt in de hand van de speler terecht, de plaats waar eerst die kaart was, daar komt nu
	 * een nieuwe kaart
	 */
	@ParameterizedTest
	@ValueSource(ints = {-100, 0, 4, 50})
	void testkiesOntwikkelingskaart_ongeldigNiveau_werptException(int ongeldigNiveau) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> this.spelMethodeTester.kiesOntwikkelingskaart(ongeldigNiveau, 1));
	}
	@ParameterizedTest
	@ValueSource(ints = {-100, 0, 5, 50})
	void testkiesOntwikkelingskaart_ongeldigPositie_werptException(int ongeldigPositie) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> this.spelMethodeTester.kiesOntwikkelingskaart(1, ongeldigPositie));
	}
	 /*de random genomen kaarten maakt voor een moeilijk te testen methode...*/
	@Test
	void testkiesOntwikkelingskaart_geldigeParametersNiveau1_spelerNeemtKaartNiveau1() {
		//arrange
		Ontwikkelingskaart ok1 = spelMethodeTester.getNiveau1Zichtbaar()[0];
		Ontwikkelingskaart ok2 = spelMethodeTester.getNiveau1Zichtbaar()[1];
		Ontwikkelingskaart ok3 = spelMethodeTester.getNiveau1Zichtbaar()[2]; // niveau1, positie 3
		Ontwikkelingskaart ok4 = spelMethodeTester.getNiveau1Zichtbaar()[3];
		
		int handSpeler = spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size();
		int prestigeSpeler = spelMethodeTester.getSpelerAanBeurt().getPrestigepunten();
		int kaartPrestige = ok3.getPrestigepunten();
		//speler moet genoeg fiches hebben om de kaart te nemen -> we bekijken de kosten en geven spelerAanBeurt de nodige fiches
		//om de kaart te kunnen kopen
		for (int i = 0; i < ok3.getKosten().length; i++) {
			if(ok3.getKosten()[i] != 0) {
				for (int j = 0; j < ok3.getKosten()[i]; j++) {
					spelMethodeTester.getSpelerAanBeurt().voegEdelsteenficheToeAanHand(Kleur.values()[i]);
				}
			}
		}
		//act
		spelMethodeTester.kiesOntwikkelingskaart(1, 3); //  niveau 1, positie 3
		//assert (kaart in hand, 1ste kaart in hand, positie 0)
		//assert speler heeft de kaart van positie 3 nu in de hand:
		Assertions.assertEquals(ok3, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().get(0));
		//assert lijst kaarten in speler hand is nu grootte +1 
		Assertions.assertEquals(handSpeler + 1, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size());
		//assert speler prestige is nu vermeerdert met de prestigewaarde van de genomen kaart
		Assertions.assertEquals(kaartPrestige, spelMethodeTester.getSpelerAanBeurt().getPrestigepunten());
		//assert kaart op positie 3 moet nu vervangen zijn met een andere kaart
		Assertions.assertNotEquals(ok3, spelMethodeTester.getNiveau1Zichtbaar()[2]);
	}
	@Test
	void testkiesOntwikkelingskaart_geldigeParametersNiveau2_spelerNeemtKaartNiveau2() {
		//arrange
		Ontwikkelingskaart ok1 = spelMethodeTester.getNiveau2Zichtbaar()[0]; // niveau 2, positie 1
		Ontwikkelingskaart ok2 = spelMethodeTester.getNiveau2Zichtbaar()[1];
		Ontwikkelingskaart ok3 = spelMethodeTester.getNiveau2Zichtbaar()[2]; 
		Ontwikkelingskaart ok4 = spelMethodeTester.getNiveau2Zichtbaar()[3];
		
		int handSpeler = spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size();
		int prestigeSpeler = spelMethodeTester.getSpelerAanBeurt().getPrestigepunten();
		int kaartPrestige = ok1.getPrestigepunten();
		//speler moet genoeg fiches hebben om de kaart te nemen -> we bekijken de kosten en geven spelerAanBeurt de nodige fiches
		//om de kaart te kunnen kopen
		for (int i = 0; i < ok1.getKosten().length; i++) {
			if(ok1.getKosten()[i] != 0) {
				for (int j = 0; j < ok1.getKosten()[i]; j++) {
					spelMethodeTester.getSpelerAanBeurt().voegEdelsteenficheToeAanHand(Kleur.values()[i]);
				}
			}
		}
		//act
		spelMethodeTester.kiesOntwikkelingskaart(2, 1); //  niveau 2, positie 1
		//assert (kaart in hand, 1ste kaart in hand, positie 0)
		//assert speler heeft de kaart van positie 1 nu in de hand:
		Assertions.assertEquals(ok1, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().get(0));
		//assert lijst kaarten in speler hand is nu grootte +1 
		Assertions.assertEquals(handSpeler + 1, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size());
		//assert speler prestige is nu vermeerdert met de prestigewaarde van de genomen kaart
		Assertions.assertEquals(kaartPrestige, spelMethodeTester.getSpelerAanBeurt().getPrestigepunten());
		//assert kaart op positie 1 moet nu vervangen zijn met een andere kaart
		Assertions.assertNotEquals(ok1, spelMethodeTester.getNiveau2Zichtbaar()[0]);
	}
	@Test
	void testkiesOntwikkelingskaart_geldigeParametersNiveau3_spelerNeemtKaartNiveau3() {
		//arrange
		Ontwikkelingskaart ok1 = spelMethodeTester.getNiveau3Zichtbaar()[0];
		Ontwikkelingskaart ok2 = spelMethodeTester.getNiveau3Zichtbaar()[1];
		Ontwikkelingskaart ok3 = spelMethodeTester.getNiveau3Zichtbaar()[2]; 
		Ontwikkelingskaart ok4 = spelMethodeTester.getNiveau3Zichtbaar()[3]; // niveau 3, positie 4
		
		int handSpeler = spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size();
		int prestigeSpeler = spelMethodeTester.getSpelerAanBeurt().getPrestigepunten();
		int kaartPrestige = ok4.getPrestigepunten();
		//speler moet genoeg fiches hebben om de kaart te nemen -> we bekijken de kosten en geven spelerAanBeurt de nodige fiches
		//om de kaart te kunnen kopen
		for (int i = 0; i < ok4.getKosten().length; i++) {
			if(ok4.getKosten()[i] != 0) {
				for (int j = 0; j < ok4.getKosten()[i]; j++) {
					spelMethodeTester.getSpelerAanBeurt().voegEdelsteenficheToeAanHand(Kleur.values()[i]);
				}
			}
		}
		//act
		spelMethodeTester.kiesOntwikkelingskaart(3, 4); //  niveau 3, positie 4
		//assert (kaart in hand, 1ste kaart in hand, positie 0)
		//assert speler heeft de kaart van positie 4 nu in de hand:
		Assertions.assertEquals(ok4, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().get(0));
		//assert lijst kaarten in speler hand is nu grootte +1 
		Assertions.assertEquals(handSpeler + 1, spelMethodeTester.getSpelerAanBeurt().getOntwikkelingskaartenInHand().size());
		//assert speler prestige is nu vermeerdert met de prestigewaarde van de genomen kaart
		Assertions.assertEquals(kaartPrestige, spelMethodeTester.getSpelerAanBeurt().getPrestigepunten());
		//assert kaart op positie 4 moet nu vervangen zijn met een andere kaart
		Assertions.assertNotEquals(ok4, spelMethodeTester.getNiveau3Zichtbaar()[0]);
	}
	/**
	 *  method: Spel.kanKaartKopen(Ontwikkelingskaart gekozenOntwikkelingskaart)
	 *  1) een nieuw spel, speler aan beurt kan geen Ontwikkelingskaart kopen
	 *  2) een spel in progress, speler met net te weinig fiches kan geen Ontwikkelingskaart kopen
	 *  3) een spel in progress, speler met net genoeg fiches kan wel een Ontwikkelingskaart kopen
	 */
	@Test
	void testKanKaartKopen_spelerNieuwSpelMetTeWeinigFiches_returnFalse() {
		//arrange
		Ontwikkelingskaart o = new Ontwikkelingskaart(1, 0, Kleur.WIT, "src\\img\\cards\\level1\\mine_diamond.jpg", new int[] {0, 0, 4, 0, 0});
		//act & assert
		Assertions.assertFalse(spelMethodeTester.kanKaartKopen(o));
	}
	@Test
	void testKanKaartKopen_spelerNetTeWeinigFiches_returnFalse() {
		//arrange
		Ontwikkelingskaart o = new Ontwikkelingskaart(1, 0, Kleur.WIT, "src\\img\\cards\\level1\\mine_diamond.jpg", new int[] {0, 0, 4, 0, 0});
		Speler s = spelMethodeTester.getSpelerAanBeurt();
		//speler heeft 4 fiches nodig, maar heeft 3 fiches
		for (int i = 0; i < 3; i++) s.voegEdelsteenficheToeAanHand(Kleur.BLAUW);
		//act & assert
		Assertions.assertFalse(spelMethodeTester.kanKaartKopen(o));
	}
	@Test
	void testKanKaartKopen_ontwikkelingsKaartNull_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.kanKaartKopen(null));
	}
	@Test
	void testKanKaartKopen_spelerNetGenoegFiches_returnTrue() {
		//arrange een witte ontwikkelingskaart die 4 blauwe fiches kost om te kopen
		Ontwikkelingskaart o = new Ontwikkelingskaart(1, 0, Kleur.WIT, "src\\img\\cards\\level1\\mine_diamond.jpg", new int[] {0, 0, 4, 0, 0});
		Speler s = spelMethodeTester.getSpelerAanBeurt();
		//speler heeft 4 fiches nodig, speler heeft net 4 fiches
		for (int i = 0; i < 4; i++) s.voegEdelsteenficheToeAanHand(Kleur.BLAUW);
		//act & assert
		Assertions.assertTrue(spelMethodeTester.kanKaartKopen(o));
	}
	@Test
	void testKanKaartKopen_spelerNetTeWeinigFichesMaarMetOntwikkelingsKaartKleurBonusKortingWelGenoegOmTeKopen_returnTrue () {
		//arrange
		//een witte ontwikkelingskaart die de speler wilt kopen:
		Ontwikkelingskaart wilSpelerKopen = new Ontwikkelingskaart(1, 0, Kleur.WIT, "src\\img\\cards\\level1\\mine_diamond.jpg", new int[] {0, 0, 4, 0, 0});
		//een blauwe ontwikkelingskaart in bezig van de speler:
		Ontwikkelingskaart bezitSpeler = new Ontwikkelingskaart(1, 0, Kleur.BLAUW, "src\\img\\cards\\level1\\mine_sapphire.jpg", new int[] {0, 4, 0, 0, 0});
		Speler s = spelMethodeTester.getSpelerAanBeurt();
		s.voegOntwikkelingskaartToeAanHand(bezitSpeler);
		//speler heeft 4 fiches nodig, maar heeft 3 fiches + kleurbonus van zijn ontwikkelingskaart in bezit
		for (int i = 0; i < 3; i++) s.voegEdelsteenficheToeAanHand(Kleur.BLAUW);
		//act & assert
		Assertions.assertTrue(spelMethodeTester.kanKaartKopen(wilSpelerKopen));
	}
	/**
	 * method: Spel.somAantalPerKleurInBezit() -> return int[]
	 * test op juiste return waardes
	 */
	@Test
	void testSomAantalPerKleurInBezit_spelerBezitOntwikkelingsKaartenEnFiches_returnsJuisteWaarden() {
		//arrange: speler bezit 10 fiches: [3, 2, 0, 2, 3] + 1 witte en 1 rode Ontwikkelingskaart: [1, 1, 0, 0, 0]
		//som kleuren in bezit expected value = [3, 2, 0, 2, 3] + [1, 1, 0, 0, 0] = [4, 3, 0, 2, 3]
		Speler s = spelMethodeTester.getSpelerAanBeurt();
		//1 witte kaart
		Ontwikkelingskaart witteKaart = new Ontwikkelingskaart(1, 0, Kleur.WIT, "src\\img\\cards\\level1\\mine_diamond.jpg", new int[] {0, 0, 4, 0, 0});
		//1 rode kaart
		Ontwikkelingskaart rodeKaart = new Ontwikkelingskaart(1, 0, Kleur.ROOD, "src\\img\\cards\\level1\\mine_ruby.jpg", new int[] {4, 0, 0, 0, 0});
		//act
		for (int i = 0; i < 3; i++) s.voegEdelsteenficheToeAanHand(Kleur.WIT);
		for (int i = 0; i < 2; i++) s.voegEdelsteenficheToeAanHand(Kleur.ROOD);
		// 0 blauwe
		for (int i = 0; i < 2; i++) s.voegEdelsteenficheToeAanHand(Kleur.GROEN);
		for (int i = 0; i < 3; i++) s.voegEdelsteenficheToeAanHand(Kleur.ZWART);
		
		s.voegOntwikkelingskaartToeAanHand(witteKaart);
		s.voegOntwikkelingskaartToeAanHand(rodeKaart);
		
		int[] correctValues = {4, 3, 0, 2, 3};
		int[] somPerKleurInBezit = spelMethodeTester.somAantalPerKleurInBezit();
		//assert
		for (int i = 0; i < correctValues.length; i++) {
			Assertions.assertEquals(correctValues[i], somPerKleurInBezit[i]);
		}
	}
	/**
	 * Spel.totaalAantalfiches() -> int
	 * spel met 4 spelers heeft [7, 7, 7, 7, 7] fiches = 35
	 * TODO: geef speler een aantal fiches en roep deze methode opnieuw aan
	 */
	@Test
	void testTotaalAantalfiches_nieuwSpel_returnJuisteWaarde() {
		Assertions.assertEquals(35, spelMethodeTester.totaalAantalfiches());
	}
	/**
	 * Spel.vulKaartenBij() -> zie spel constructor tests
	 */
	
	/**
	 * Spel.neemDrieFiches(Kleur[] kleuren)
	 * stapels worden met de juiste aantal fiches verminderd, speler bezit de genomen fiches
	 * je mag niet 2x van dezelfde fiche stapen een fiche nemen
	 * je kan geen fiche nemen van een stapel die geen fiches bevat
	 * als speler een ongeldige stapel kiest verminderen de stapels niet, een spel met 4 spelers heeft 35 fiches
	 */
	@Test
	void testNeemDrieFiches_eenStapelBevat0Fiches_werptException() {
		//arrange
		Kleur[] legeStapels = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		Kleur[] teNemenKleuren = {Kleur.WIT, Kleur.GROEN, Kleur.ZWART}; // 1 lege stapel
		//7x een fiche van deze 3 stapels nemen, dan bevatten deze stapels 0 fiches (spel behoud nog 14 fiches (35-(3x7))
		for (int i = 0; i < 7; i++) spelMethodeTester.neemDrieFiches(legeStapels);
		//act & assert
		Assertions.assertThrows(RuntimeException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren));
		Assertions.assertEquals(14, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_tweeStapelsBevatten0Fiches_werptException() {
		//arrange
		Kleur[] legeStapels = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		Kleur[] teNemenKleuren = {Kleur.WIT, Kleur.ROOD, Kleur.ZWART}; // 2 lege stapels
		//7x een fiche van deze 3 stapels nemen, dan bevatten deze stapels 0 fiches (spel behoud nog 14 fiches (35-(3x7))
		for (int i = 0; i < 7; i++) spelMethodeTester.neemDrieFiches(legeStapels);
		//act & assert
		Assertions.assertThrows(RuntimeException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren));
		Assertions.assertEquals(14, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_drieStapelsBevatten0Fiches_werptException() {
		//arrange
		Kleur[] legeStapels = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		Kleur[] teNemenKleuren = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW}; // 3 lege stapels
		//7x een fiche van deze 3 stapels nemen, dan bevatten deze stapels 0 fiches (spel behoud nog 14 fiches (35-(3x7))
		for (int i = 0; i < 7; i++) spelMethodeTester.neemDrieFiches(legeStapels);
		//act & assert
		Assertions.assertThrows(RuntimeException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren));
		Assertions.assertEquals(14, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_tweeMaalDezelfdeStapelEenFicheProberenNemen_werptException() {
		//arrange
		Kleur[] teNemenKleuren = {Kleur.WIT, Kleur.WIT, Kleur.BLAUW};
		Kleur[] teNemenKleuren2 = {Kleur.WIT, Kleur.ROOD, Kleur.ROOD};
		//act & assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren2));
		Assertions.assertEquals(35, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_drieMaalDezelfdeStapelEenFicheProberenNemen_werptException() {
		//arrange
		Kleur[] teNemenKleuren = {Kleur.ZWART, Kleur.ZWART, Kleur.ZWART};
		Kleur[] teNemenKleuren2 = {Kleur.WIT, Kleur.WIT, Kleur.WIT};
		//act & assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(teNemenKleuren2));
		Assertions.assertEquals(35, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_nullParameter_werptException() {
		//arrange
		Kleur[] nullPos1 = {null, Kleur.ROOD, Kleur.BLAUW};
		Kleur[] nullPos2 = {Kleur.WIT, null, Kleur.BLAUW};
		Kleur[] nullPos3 = {Kleur.WIT, Kleur.ROOD, null};
		Kleur[] nullList = null;
		//act & assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(nullPos1));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(nullPos2));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(nullPos3));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemDrieFiches(nullList));
		Assertions.assertEquals(35, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemDrieFiches_geldigeGekozenStapels_spelerAanBeurtKrijgtFiches() {
		//arrange (spel heeft 35 fiches in de fiche stapels, na het nemen van deze 3 -> nog 32)
		Kleur[] teNemenKleuren = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		//act & assert
		spelMethodeTester.neemDrieFiches(teNemenKleuren);
		//totaalAantalfiches expect 32 / somaantalperkleurinbezit expect [1, 1, 1, 0, 0]
		int aantalFichesResterendInSpelFicheStapels = spelMethodeTester.totaalAantalfiches(); // 32 
		int[] fichesPerKleurInBezitSpelerAandeBeurt = spelMethodeTester.somAantalPerKleurInBezit(); // [1, 1, 1, 0, 0]
		Assertions.assertEquals(32, aantalFichesResterendInSpelFicheStapels);
		Assertions.assertEquals(1, fichesPerKleurInBezitSpelerAandeBeurt[0]);
		Assertions.assertEquals(1, fichesPerKleurInBezitSpelerAandeBeurt[1]);
		Assertions.assertEquals(1, fichesPerKleurInBezitSpelerAandeBeurt[2]);
		Assertions.assertEquals(0, fichesPerKleurInBezitSpelerAandeBeurt[3]);
		Assertions.assertEquals(0, fichesPerKleurInBezitSpelerAandeBeurt[4]);
	}
	/**
	 * Spel.neemTweeFiches(Kleur kleur)
	 * stapels worden met de juiste aantal fiches verminderd, speler bezit de genomen fiches
	 * je kan geen fiche nemen van een stapel die geen fiches bevat
	 * ja kan geen neem2fiches doen op een stapel met minder dan 4 fiches
	 * als speler een ongeldige stapel kiest verminderen de stapels niet, een spel met 4 spelers heeft 35 fiches
	 */
	@Test
	void testNeemTweeFiches_nullParameter_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(null));
		Assertions.assertEquals(35, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemTweeFiches_neemTweeFichesVanLegeStapel_werptException() {
		//arrange (een spel met 3 lege stapels heeft 35 - (7x3) = 14 fiches
		Kleur[] legeStapels = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		for (int i = 0; i < 7; i++) spelMethodeTester.neemDrieFiches(legeStapels);
		//act & assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.WIT));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.ROOD));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.BLAUW));
		Assertions.assertEquals(14, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemTweeFiches_stapelMinderDanVierFiches_werptException() {
		//arrange spel met [3, 3, 3, 7, 7] = 23 fiches
		Kleur[] stapelsMetMinderDanVierFiches = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		for (int i = 0; i < 4; i++) spelMethodeTester.neemDrieFiches(stapelsMetMinderDanVierFiches); //stapels van 7 - 4 = 3
		//act & assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.WIT));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.ROOD));
		Assertions.assertThrows(IllegalArgumentException.class, () -> spelMethodeTester.neemTweeFiches(Kleur.BLAUW));
		Assertions.assertEquals(23, spelMethodeTester.totaalAantalfiches());
	}
	@Test
	void testNeemTweeFiches_stapelMetNetGenoegFichesGekozen_spelerKrijgtFiches() {
		//speler kan niet meer dan 10 fiches hebben, neemt in totaal 9 fiches
		//arrange spel met [4, 6, 6, 7, 7] = 30 fiches, speler neemt 2WIT/2ROOD = 30 - 2 - 2 = 26
		Kleur[] stapelsMetZesFiches = {Kleur.WIT, Kleur.ROOD, Kleur.BLAUW};
		spelMethodeTester.neemDrieFiches(stapelsMetZesFiches); //stapels van 7 - 1 = 6 [6, 6, 6, 7, 7]
		spelMethodeTester.neemTweeFiches(Kleur.WIT); // stapel van 6 - 2 = 4 [4, 6, 6, 7, 7]
		//act & assert
		spelMethodeTester.neemTweeFiches(Kleur.WIT);
		spelMethodeTester.neemTweeFiches(Kleur.ROOD); //speler heeft nu 9fiches in totaal genomen
		int aantalFichesResterendInSpelFicheStapels = spelMethodeTester.totaalAantalfiches(); // 26
		int[] fichesPerKleurInBezitSpelerAandeBeurt = spelMethodeTester.somAantalPerKleurInBezit(); // [5, 3, 1, 0, 0]
		Assertions.assertEquals(26, aantalFichesResterendInSpelFicheStapels);
		Assertions.assertEquals(5, fichesPerKleurInBezitSpelerAandeBeurt[0]);
		Assertions.assertEquals(3, fichesPerKleurInBezitSpelerAandeBeurt[1]);
		Assertions.assertEquals(1, fichesPerKleurInBezitSpelerAandeBeurt[2]);
		Assertions.assertEquals(0, fichesPerKleurInBezitSpelerAandeBeurt[3]);
		Assertions.assertEquals(0, fichesPerKleurInBezitSpelerAandeBeurt[4]);
		
	}
	//TODO : Spel.aantalStapelsMeerDanNul() / Spel.bestaatStapelMeerDan4() / Spel.plaatsTerugInStapel(int stapelKeuze)
	//Spel.toonFiches() / Spel.krijgEdele() / Spel.bepaalWinnaar()
}