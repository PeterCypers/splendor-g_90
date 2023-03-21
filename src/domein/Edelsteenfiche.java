package domein;

public class Edelsteenfiche {

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

	public Edelsteenfiche(String soort, String kleur) {
		setSoort(soort);
		setKleur(kleur);
	}
	
	/* ---- Setters & Getters ---- */
	public String getSoort() {
		return this.soort;
	}

	private void setSoort(String soort) {
		this.soort = soort;
	}

	public String getKleur() {
		return this.kleur;
	}

	private void setKleur(String kleur) {
		this.kleur = kleur;
	}

	

}