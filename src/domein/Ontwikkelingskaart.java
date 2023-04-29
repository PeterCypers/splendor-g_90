package domein;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Ontwikkelingskaart implements SpelVoorwerp {

	private int niveau;
	private int prestigepunten;
	private Kleur kleurBonus;
	private String fotoOntwikkelingskaart;
	private int[] kosten = new int[5];

	public Ontwikkelingskaart(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart,
			int[] kosten) {
		setNiveau(niveau);
		setPrestigepunten(prestigepunten);
		setKleurBonus(kleurBonus);
		setFotoOntwikkelingskaart(fotoOntwikkelingskaart);
		setKosten(kosten);
	}

	public int getNiveau() {
		return this.niveau;
	}

	private void setNiveau(int niveau) {
		if(niveau < 1 || niveau > 3)
			throw new IllegalArgumentException("Niveau Ontwikkelingskaart is niet in range [1-3]");
		this.niveau = niveau;
	}

	public Kleur getKleurBonus() {
		return this.kleurBonus;
	}

	private void setKleurBonus(Kleur kleurBonus) {
		if(kleurBonus == null)
			throw new IllegalArgumentException("Kleurbonus is null");
		this.kleurBonus = kleurBonus;
	}

	public String getFotoOntwikkelingskaart() {
		return this.fotoOntwikkelingskaart;
	}

	private void setFotoOntwikkelingskaart(String fotoOntwikkelingskaart) {
		if(fotoOntwikkelingskaart == null || fotoOntwikkelingskaart.isBlank())
			throw new IllegalArgumentException("Foto is null of is niet ingevuld");
		this.fotoOntwikkelingskaart = fotoOntwikkelingskaart;
	}

	public int getPrestigepunten() {
		return prestigepunten;
	}

	private void setPrestigepunten(int prestigepunten) {
		if(prestigepunten < 0 || prestigepunten > 5)
			throw new IllegalArgumentException("Prestigepunten Ontwikkelingskaart zijn niet in range [0-5]");
		this.prestigepunten = prestigepunten;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		if(kosten == null)
			throw new IllegalArgumentException("Kosten-array is null");
		if(kosten.length != 5)
			throw new IllegalArgumentException("Lengte van de kosten-array is niet exact 5");
		this.kosten = kosten;
	}

	@Override
	public String toString() {
		String kostenString = "";
		for (int i = 0; i < Kleur.values().length; i++) {
			kostenString += String.format("%s: %d%n", Kleur.values()[i].toString(), kosten[i]);
		}
		String ontwikkelingsKaartAlsString = String.format("%s niveau %s, kleur %s en prestige %d%nKost:%n%s",
				getClass().getSimpleName(), getNiveau(), getKleurBonus().toString(), this.getPrestigepunten(),
				kostenString);

		return ontwikkelingsKaartAlsString;
	}

}