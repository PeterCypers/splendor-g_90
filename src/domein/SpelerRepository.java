package domein;

import java.util.*;

import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;
	private List<Speler> spelers;

	public SpelerRepository() {
		mapper = new SpelerMapper();
		slaSpelersOp();
	}

	public List<Speler> getSpelers() {
		return this.spelers;
	}

	// naam verandert naar slaSpelersOp
	private void slaSpelersOp() {
		this.spelers = mapper.geefSpelers();
	}
}
