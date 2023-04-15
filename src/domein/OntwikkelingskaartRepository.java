package domein;

import java.util.Collections;
import java.util.List;

import persistentie.OntwikkelingskaartMapper;

public class OntwikkelingskaartRepository {

	private OntwikkelingskaartMapper mapper;
	private List<Ontwikkelingskaart> niveau1;
	private List<Ontwikkelingskaart> niveau2;
	private List<Ontwikkelingskaart> niveau3;

	public OntwikkelingskaartRepository() {
		this.mapper = new OntwikkelingskaartMapper();

		// haal ontwikkelingskaarten op per niveau
		this.niveau1 = mapper.geefN1Kaarten();
		this.niveau2 = mapper.geefN2Kaarten();
		this.niveau3 = mapper.geefN3Kaarten();

		// shuffelt de verschillende niveaus van ontwikkelingskaarten
		Collections.shuffle(niveau1);
		Collections.shuffle(niveau2);
		Collections.shuffle(niveau3);
	}

	// N1
	public List<Ontwikkelingskaart> geefN1Kaarten() {
		return this.niveau1;
	}

	// N2
	public List<Ontwikkelingskaart> geefN2Kaarten() {
		return this.niveau2;
	}

	// N3
	public List<Ontwikkelingskaart> geefN3Kaarten() {
		return this.niveau3;
	}
}