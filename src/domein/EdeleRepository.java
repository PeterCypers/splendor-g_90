package domein;

import java.util.Collections;
import java.util.List;

import persistentie.EdeleMapper;

public class EdeleRepository {

	private EdeleMapper mapper;
	private List<Edele> edeleStapel;

	public EdeleRepository() {
		this.mapper = new EdeleMapper();
		// haalt edelen op
		edeleStapel = mapper.geefAlleEdelen();
		Collections.shuffle(edeleStapel);
	}

	/**
	 * (1)aantalEdelen afhankelijk van aantalSpelers, de teveel aan edelen wordt
	 * hier verwijderd uit de lijst en de aangepaste lijst teruggegeven (2)de lijst
	 * in deze repo wordt gemuteerd, als dat niet mag ... creëer hier een nieuwe
	 * lijst(een copy) en vul die op met de juiste aantal edelen en geef die nieuwe
	 * lijst terug (3)parameter: je wilt het aantal spelers die deelnemen kennen(DC)
	 * voor je de edelen ophaalt uit deze repository geef de spelerlijst.length mee
	 * in DC om deze op te halen
	 */

	public List<Edele> geefEdelen(int aantalSpelers) {
		int aantalEdelen = aantalSpelers + 1;
		// laatste element in de lijst verwijderen tot de lijst.size == aantalEdelen
		while (edeleStapel.size() > aantalEdelen) {
			edeleStapel.remove(edeleStapel.size() - 1);
		}
		return edeleStapel;
	}
}
