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
		this.niveau = niveau;
	}

	public Kleur getKleurBonus() {
		return this.kleurBonus;
	}

	private void setKleurBonus(Kleur kleurBonus) {
		this.kleurBonus = kleurBonus;
	}

	public String getFotoOntwikkelingskaart() {
		return this.fotoOntwikkelingskaart;
	}

	private void setFotoOntwikkelingskaart(String fotoOntwikkelingskaart) {
		this.fotoOntwikkelingskaart = fotoOntwikkelingskaart;
	}

	public int getPrestigepunten() {
		return prestigepunten;
	}

	private void setPrestigepunten(int prestigepunten) {
		this.prestigepunten = prestigepunten;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		this.kosten = kosten;
	}

	public Map<Kleur, Integer> getKostenMap() {
		// TODO
		return null;
	}

	public List<Kleur> getKleuren() {
		// TODO 
		return null;
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