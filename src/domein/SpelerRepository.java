package domein;

import java.util.*;

import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;
	private List<Speler> spelers; // wordt direct ge-returned in geefspelers

	public SpelerRepository() {
		mapper = new SpelerMapper();
		voegSpelersToe();
	}

	public List<Speler> getSpelers() {
		return this.spelers;
	}
	private void voegSpelersToe() {
		this.spelers = mapper.geefSpelers();
	}
}
