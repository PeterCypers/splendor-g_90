package dto;

import java.util.HashMap;
import java.util.List;

import domein.Kleur;

public record SpelerDTO(char type, String gebruikersnaam, int geboortejaar, int aantalPrestigepunten,
		boolean aanDeBeurt, List<SpelVoorwerpDTO> ontwikkelingskaartenInHand, List<SpelVoorwerpDTO> edelenInHand,
		HashMap<Kleur, Integer> edelsteenfichesInHand) {

	public SpelerDTO(String gebruikersnaam, int geboortejaar, int aantalPrestigepunten, boolean aanDeBeurt,
			List<SpelVoorwerpDTO> ontwikkelingskaartenInHand, List<SpelVoorwerpDTO> edelenInHand,
			HashMap<Kleur, Integer> edelsteenfichesInHand) {

		this('S', gebruikersnaam, geboortejaar, aantalPrestigepunten, aanDeBeurt, ontwikkelingskaartenInHand,
				edelenInHand, edelsteenfichesInHand);

	}
}