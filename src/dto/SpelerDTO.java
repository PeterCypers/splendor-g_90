package dto;

public record SpelerDTO(char type, String gebruikersnaam, int geboortejaar) {

	public SpelerDTO(String gebruikersnaam, int geboortejaar) {
		this('S', gebruikersnaam, geboortejaar);
	}

}
