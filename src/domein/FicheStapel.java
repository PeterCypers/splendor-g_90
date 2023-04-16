//package domein;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FicheStapel implements SpelVoorwerp {
//
//	private int aantalFiches = 0;
//	private String soort;
//	private Kleur kleur;
//	private String ficheStapelFoto;
//	private List<Edelsteenfiche> fiches;
//
//	public FicheStapel(int aantalFiches, Kleur kleur, List<Edelsteenfiche> fiches) {
//		this.soort = kleur.soort();
//		setAantalFiches(aantalFiches);
//		if (fiches == null)
//			throw new IllegalArgumentException(
//					String.format("Fout in %s: null obj meegegeven als lijst fiches", this.getClass().getSimpleName()));
//		this.aantalFiches = aantalFiches;
//		this.kleur = kleur;
//		this.ficheStapelFoto = kleur.soort();
//		this.fiches = fiches;
//	}
//
//	public void voegFicheToe(Kleur kleur2) {
//		if (kleur2 == null)
//			throw new IllegalArgumentException(
//					String.format("Fout in %s: null object fiche", this.getClass().getSimpleName()));
//		fiches.add(kleur2);
//		aantalFiches++;
//	}
//
//	public Edelsteenfiche neemFiche() {
//		if (aantalFiches == 0)
//			throw new IllegalArgumentException(String.format("Fout in %s: probeert fiche te nemen, maar stapel is leeg",
//					this.getClass().getSimpleName()));
//		aantalFiches--;
//		return fiches.remove(fiches.size() - 1); // bovenste fiche
//	}
//
//	public List<Edelsteenfiche> neemTweeFiches() {
//		if (aantalFiches < 4)
//			throw new IllegalArgumentException(
//					String.format("Fout in %s: probeert 2 fiches te nemen, maar stapel is kleiner dan 4",
//							this.getClass().getSimpleName()));
//		List<Edelsteenfiche> efReturn = new ArrayList<>();
//		efReturn.add(neemFiche());
//		efReturn.add(neemFiche());
//		return efReturn;
//	}
//
//	public String getSoort() {
//		return soort;
//	}
//
//	public int getAantalFiches() {
//		return aantalFiches;
//	}
//
//	private void setAantalFiches(int aantalFiches) {
//		if (aantalFiches != 4 && aantalFiches != 5 && aantalFiches != 7)
//			throw new IllegalArgumentException(
//					String.format("Fout in %s: verkeerde aantal fiches in de stapel", this.getClass().getSimpleName()));
//		this.aantalFiches = aantalFiches;
//	}
//
//	public Kleur getKleur() {
//		return kleur;
//	}
//
//	public String getFicheStapelFoto() {
//		return ficheStapelFoto;
//	}
//
//	public List<Edelsteenfiche> getFiches() {
//		return this.fiches;
//	}
//}
