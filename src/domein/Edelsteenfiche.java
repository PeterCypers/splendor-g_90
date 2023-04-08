package domein;

public class Edelsteenfiche implements SpelVoorwerp{

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
	private String ficheFoto;

	public Edelsteenfiche(String soort, Kleur kleur, String foto) {
		setSoort(soort);
		setKleur(kleur);
		setFicheFoto(foto);
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
	
	public String getFicheFoto() {
		return this.ficheFoto;
	}
	
	private void setFicheFoto(String foto) {
		this.ficheFoto = foto;
	}

}