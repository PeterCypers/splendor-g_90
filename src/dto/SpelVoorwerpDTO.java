package dto;

import domein.Kleur;
/**
 * volgorder constructor: FicheStapel, Edelsteenfiche, Ontwikkelingskaart, Edele
 * List<Edelsteenfiche> in FicheStapel constructor wordt weggelaten uit de DTO -> geen objecten uit domein naar CUI sturen
 * (gemeenschappelijke waarden, bvb fotos kunnen gewoon dezelfde parameters delen)
 */
public record SpelVoorwerpDTO(char type, int aantalFiches, Kleur kleur, String foto, String soort,
		int niveau, int prestigepunten, int[] kosten) {
	
	//voor FicheStapel int aantalFiches, Kleur kleur, String foto,  List<Edelsteenfiche> fiches type: S (stapel)
	public SpelVoorwerpDTO(int aantalFiches, Kleur kleur, String foto, String soort) {
		this('S', aantalFiches, kleur, foto, soort, 0, 0, null);
	}
	
	//voor Edelsteenfiche String soort, Kleur kleur, String foto type: F (fiche)
	public SpelVoorwerpDTO(String soort, Kleur kleur, String foto) {
		this('F', 0, kleur, foto, soort, 0, 0, null);
	}
	
	//voor Ontwikkelingskaart int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart, int[] kosten type: O (ontw.k.)
	public SpelVoorwerpDTO(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart, int[] kosten) {
		this('O', 0, kleurBonus, fotoOntwikkelingskaart, "", niveau, prestigepunten, kosten);
	}
	
	//voor Edele int prestigePunten, String edeleFoto, int[] kosten type: E (edele)
	public SpelVoorwerpDTO(int prestigePunten, String edeleFoto, int[] kosten) {
		this('E', 0, null, edeleFoto, "", 0, prestigePunten, kosten);
	}

}
