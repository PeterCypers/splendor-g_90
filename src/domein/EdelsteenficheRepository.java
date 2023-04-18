package domein;

import java.util.HashMap;

import persistentie.EdelsteenficheMapper;

public class EdelsteenficheRepository {

	private EdelsteenficheMapper mapper;
	private HashMap<Kleur, Integer> ficheStapels;

	// aantalFiches afhankelijk van aantalSpelers
	public EdelsteenficheRepository(int aantalSpelers) {
		this.mapper = new EdelsteenficheMapper(aantalSpelers);
		// haalt fiches op
		ficheStapels = mapper.geefAlleEdelsteenficheStapels();
	}

	// lijst wordt perfect ingesteld in mapper klasse, voor zover dit op deze manier
	// mag (zie dc.startNieuwSpel())
	public HashMap<Kleur, Integer> geefEdelsteenficheStapels() {
		return this.ficheStapels;
	}

}
