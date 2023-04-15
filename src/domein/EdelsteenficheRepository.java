package domein;

import java.util.List;

import persistentie.EdelsteenficheMapper;

public class EdelsteenficheRepository {

	private EdelsteenficheMapper mapper;
	private FicheStapel[] ficheStapels;

	// aantalFiches afhankelijk van aantalSpelers
	public EdelsteenficheRepository(int aantalSpelers) {
		this.mapper = new EdelsteenficheMapper(aantalSpelers);
		// haalt fiches op
		ficheStapels = mapper.geefAlleEdelsteenficheStapels();
	}

	// lijst wordt perfect ingesteld in mapper klasse, voor zover dit op deze manier
	// mag (zie dc.startNieuwSpel())
	public FicheStapel[] geefEdelsteenficheStapels() {
		return this.ficheStapels;
	}

}
