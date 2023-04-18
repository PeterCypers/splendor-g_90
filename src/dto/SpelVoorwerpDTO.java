package dto;

import domein.Kleur;

/**
 * volgorder constructor: FicheStapel, Edelsteenfiche, Ontwikkelingskaart, Edele
 * List<Edelsteenfiche> in FicheStapel constructor wordt weggelaten uit de DTO
 * -> geen objecten uit domein naar CUI sturen (gemeenschappelijke waarden, bvb
 * fotos kunnen gewoon dezelfde parameters delen)
 */
public record SpelVoorwerpDTO(char type, int prestigepunten, String foto, int[] kosten, int niveau, Kleur kleur) {

	// voor Ontwikkelingskaart int niveau, int prestigepunten, Kleur kleurBonus,
	// String fotoOntwikkelingskaart, int[] kosten type: O (ontw.k.)
	public SpelVoorwerpDTO(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart,
			int[] kosten) {
		this('O', prestigepunten, fotoOntwikkelingskaart, kosten, niveau, kleurBonus);
	}

	// voor Edele int prestigePunten, String edeleFoto, int[] kosten type: E (edele)
	public SpelVoorwerpDTO(int prestigepunten, String edeleFoto, int[] kosten) {
		this('E', prestigepunten, edeleFoto, kosten, 0, null);
	}
}
