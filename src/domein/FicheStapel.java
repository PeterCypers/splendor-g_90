package domein;

import java.util.List;

public class FicheStapel {
	
	private int aantalFiches = 0;
	private Kleur kleur;
	private String ficheStapelFoto;
	private List<Edelsteenfiche> fiches;
	
	public FicheStapel(int aantalFiches, Kleur kleur, String foto,  List<Edelsteenfiche> fiches) {
		setAantalFiches(aantalFiches);
		if(fiches == null)
			throw new IllegalArgumentException(String.format("Fout in %s: null obj meegegeven als lijst fiches", this.getClass().getSimpleName()));
		this.aantalFiches = aantalFiches;
		this.kleur = kleur;
		this.ficheStapelFoto = foto;
		this.fiches = fiches;
	}
	
	private void setAantalFiches(int aantalFiches) {
		if(aantalFiches != 4 && aantalFiches != 5 && aantalFiches != 7)
			throw new IllegalArgumentException(String.format("Fout in %s: verkeerde aantal fiches in de stapel", this.getClass().getSimpleName()));
		this.aantalFiches = aantalFiches;
	}
	
	public void voegFicheToe(Edelsteenfiche fiche) {
		if(fiche == null)
			throw new IllegalArgumentException(String.format("Fout in %s: null object fiche", this.getClass().getSimpleName()));
		fiches.add(fiche);
		aantalFiches++;
	}
	
	public Edelsteenfiche neemFiche() {
		if(aantalFiches == 0)
			throw new IllegalArgumentException(String.format("Fout in %s: probeert fiche te nemen, maar stapel is leeg", this.getClass().getSimpleName()));
		aantalFiches--;
		//TODO: fiche uit de lijst halen en teruggeven
		return null;
	}
	
	
	public int getAantalFiches() {
		return aantalFiches;
	}

	public Kleur getKleur() {
		return kleur;
	}

	public String getFicheStapelFoto() {
		return ficheStapelFoto;
	}

	public List<Edelsteenfiche> getFiches() {
		return this.fiches;
	}
}
