package dto;

import java.util.List;
import java.util.Map;

import domein.Edele;
import domein.Kleur;
import domein.Ontwikkelingskaart;

public record SpelerDTO(char type, String gebruikersnaam, int geboortejaar, int aantalPrestigepunten,
		boolean aanDeBeurt, boolean startSpeler, List<Ontwikkelingskaart> ontwikkelingskaartenInHand,
		List<Edele> edelenInHand, Map<Kleur, Integer> edelsteenfichesInHand) {

	public SpelerDTO(String gebruikersnaam, int geboortejaar, int aantalPrestigepunten, boolean aanDeBeurt,
			boolean startSpeler, List<Ontwikkelingskaart> ontwikkelingskaartenInHand, List<Edele> edelenInHand,
			Map<Kleur, Integer> edelsteenfichesInHand) {

		this('S', gebruikersnaam, geboortejaar, aantalPrestigepunten, aanDeBeurt, startSpeler,
				ontwikkelingskaartenInHand, edelenInHand, edelsteenfichesInHand);

	}

}
