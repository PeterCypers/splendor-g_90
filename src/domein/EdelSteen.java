package domein;

public class EdelSteen {

	/**
	 * afhankelijk van de kleur:
	 * Smaragden (groen)
	 * Diamanten (wit)
	 * Saffieren (blauw)
	 * Onyxen (zwart)
	 * Robijnen (rood)
	 */
	private String soort;
	private String kleur;

	public String getSoort() {
		return this.soort;
	}

	public void setSoort(String soort) {
		this.soort = soort;
	}

	public String getKleur() {
		return this.kleur;
	}

	public void setKleur(String kleur) {
		this.kleur = kleur;
	}

}