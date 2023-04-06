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
	private Kleur kleur;
	private String foto;

	public Edelsteenfiche(String soort, Kleur kleur, String foto) {
		setSoort(soort);
		setKleur(kleur);
		setFoto(foto);
	}
	
	/* ---- Setters & Getters ---- */
	public String getSoort() {
		return this.soort;
	}

	private void setSoort(String soort) {
		this.soort = soort;
	}

	public Kleur getKleur() {
		return this.kleur;
	}

	private void setKleur(Kleur kleur) {
		this.kleur = kleur;
	}
	
	public String getFoto() {
		return this.foto;
	}
	
	private void setFoto(String foto) {
		this.foto = foto;
	}

	

}