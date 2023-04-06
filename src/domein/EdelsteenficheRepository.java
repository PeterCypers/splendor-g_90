package domein;

import java.util.List;

import persistentie.EdelsteenficheMapper;

public class EdelsteenficheRepository {
	
	private EdelsteenficheMapper mapper;
	private List<Edelsteenfiche> edelsteenficheStapel;
	
	//aantalFiches afhankelijk van aantalSpelers
	public EdelsteenficheRepository(int aantalSpelers) {
		this.mapper = new EdelsteenficheMapper(aantalSpelers);
		haalFichesOp();
	}

	private void haalFichesOp() {
		edelsteenficheStapel = mapper.geefAlleEdelsteenfiches();
	}
	
	//lijst wordt perfect ingesteld in mapper klasse, voor zover dit op deze manier mag (zie dc.startNieuwSpel())
	public List<Edelsteenfiche> geefEdelsteenfiches() {
		return this.edelsteenficheStapel;
	}

}
