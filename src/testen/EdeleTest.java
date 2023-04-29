package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Edele;

public class EdeleTest {
	
	Edele e;
	static final int GELDIGE_EDELEN_PRESTIGE = 3;
	static final int[] GELDIGE_KOSTEN_ARRAY = {3, 3, 0, 0, 3};
	static final String GELDIGE_FOTO = "src\\img\\nobles\\noble01.png";
	
	//ongeldige waarden
	static final int[] KOSTEN_ARRAY_TE_KORT = {3, 3, 0, 0};
	static final int[] KOSTEN_ARRAY_TE_LANG = {3, 3, 0, 0, 3, 3};
	
	/**
	 * constructor tests
	 * Edele(int prestigePunten, String edeleFoto, int[] kosten)
	 */
	@Test
	void maakEdele_geldigeParameters_maaktEdele() {
		e = new Edele(GELDIGE_EDELEN_PRESTIGE, GELDIGE_FOTO, GELDIGE_KOSTEN_ARRAY);
		int kostenOpIndex0 = e.getKosten()[0]; // 3
		
		Assertions.assertEquals(3, e.getPrestigepunten());
		Assertions.assertEquals("src\\img\\nobles\\noble01.png", e.getEdeleFoto());
		Assertions.assertEquals(3, kostenOpIndex0);
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"   ", "\n \t", "\n", "\t"})
	void maakEdele_ongeldigeEdeleFoto_werptException (String ongeldigeFoto) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edele(GELDIGE_EDELEN_PRESTIGE, ongeldigeFoto, GELDIGE_KOSTEN_ARRAY));
	}
	
	@Test
	void maakEdele_kostenNull_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edele(GELDIGE_EDELEN_PRESTIGE, GELDIGE_FOTO, null));
	}
	
	@Test
	void maakEdele_kostenArrayTeKort_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edele(GELDIGE_EDELEN_PRESTIGE, GELDIGE_FOTO, KOSTEN_ARRAY_TE_KORT));
	}
	
	@Test
	void maakEdele_kostenArrayTeLang_werptException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Edele(GELDIGE_EDELEN_PRESTIGE, GELDIGE_FOTO, KOSTEN_ARRAY_TE_LANG));
	}

}
