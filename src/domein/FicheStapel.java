package domein;

import java.util.ArrayList;
import java.util.List;

public class FicheStapel implements SpelVoorwerp{
	
	private int aantalFiches = 0;
	private String soort;
	private Kleur kleur;
	private String ficheStapelFoto;
	private List<Edelsteenfiche> fiches;
	
	public FicheStapel(String soort, int aantalFiches, Kleur kleur, String foto,  List<Edelsteenfiche> fiches) {
		this.soort = soort;
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
	//afgewerkt 11-4-2023
	public Edelsteenfiche neemFiche() {
		if(aantalFiches == 0)
			throw new IllegalArgumentException(String.format("Fout in %s: probeert fiche te nemen, maar stapel is leeg", this.getClass().getSimpleName()));
		aantalFiches--;
		return fiches.remove(fiches.size()-1); //bovenste fiche
	}
	//nieuw 11-4-2023
	public List<Edelsteenfiche> neemTweeFiches(){
		if(aantalFiches < 4)
			throw new IllegalArgumentException(String.format("Fout in %s: probeert 2 fiches te nemen, maar stapel is kleiner dan 4", this.getClass().getSimpleName()));
		List<Edelsteenfiche> efReturn = new ArrayList<>();
		efReturn.add(neemFiche());
		efReturn.add(neemFiche());
		return efReturn;
	}
	
	public String getSoort() {
		return soort;
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
