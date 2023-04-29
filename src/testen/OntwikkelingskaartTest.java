package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Kleur;
import domein.Ontwikkelingskaart;

class OntwikkelingskaartTest {
	
	Ontwikkelingskaart ok;

	static final int GELDIGE_NIVEAU = 1;
	static final int GELDIGE_PRESTIGE = 3;
	static final Kleur GELDIGE_KLEUR = Kleur.ZWART;
	static final String GELDIGE_FOTO = "src\\img\\cards\\level1\\mine_onyx.jpg";
	static final int[] GELDIGE_KOSTEN_ARRAY = {0, 0, 4, 0, 0};
	
	
	//ongeldige waarden
	static final int[] KOSTEN_ARRAY_TE_KORT = {0, 0, 4, 0};
	static final int[] KOSTEN_ARRAY_TE_LANG = {0, 0, 4, 0, 0, 0};
	
	/**
	 * constructor testen
	 * Ontwikkelingskaart(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart,
			int[] kosten)
	   niveau: [1-3]
	   prestigepunten: [0-5]
	   kleurBonus:(Kleur) WIT||ROOD||BLAUW||GROEN||ZWART
	   fotoOntwikkelingskaart: geldige adres
	   kosten: array lengte 5
	 */
	
	@Test
	void maakOntwikkelingskaart_geldigeParameters_maaktOntwikkelingskaart() {
		ok = new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY);
		int eersteKost = ok.getKosten()[0];
		
		Assertions.assertEquals(1, ok.getNiveau());
		Assertions.assertEquals(3, ok.getPrestigepunten());
		Assertions.assertEquals(Kleur.ZWART, ok.getKleurBonus());
		Assertions.assertEquals("src\\img\\cards\\level1\\mine_onyx.jpg", ok.getFotoOntwikkelingskaart());
		Assertions.assertEquals(0, eersteKost);
	}
	
	@Test
	void maakOntwikkelingskaart_niveauTeKlein_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(0, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakOntwikkelingskaart_niveauTeGroot_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(4, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakOntwikkelingskaart_prestigePuntenTeKlein_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, -1, GELDIGE_KLEUR, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakOntwikkelingskaart_prestigePuntenTeGroot_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, 6, GELDIGE_KLEUR, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakOntwikkelingskaart_kleurBonusNull_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, null, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"   ", "\n \t", "\n", "\t"})
	void maakOntwikkelingskaart_ongeldigeFoto_werptException(String ongeldigeFoto) {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, GELDIGE_KLEUR, ongeldigeFoto, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakOntwikkelingskaart_kostenArrayTeKort_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, KOSTEN_ARRAY_TE_KORT));
	}
	
	@Test
	void maakOntwikkelingskaart_kostenArrayTeLang_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, KOSTEN_ARRAY_TE_LANG));
	}
	
	@Test
	void maakOntwikkelingskaart_kostenArrayNull_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, ()
				-> new Ontwikkelingskaart(GELDIGE_NIVEAU, GELDIGE_PRESTIGE, GELDIGE_KLEUR, GELDIGE_FOTO, null));
	}

}
