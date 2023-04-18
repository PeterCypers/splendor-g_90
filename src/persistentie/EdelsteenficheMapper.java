package persistentie;

import java.util.HashMap;

import domein.Kleur;

public class EdelsteenficheMapper {

	private int aantalFiches;

	/**
	 * 2 spelers = 4 fiches 3 spelers = 5 fiches 4 spelers = 7 fiches
	 * 
	 * @param aantalSpelers -> stel aantalFiches in adhv aantalspelers
	 */
	public EdelsteenficheMapper(int aantalSpelers) {
		switch (aantalSpelers) {
		case 2 -> setAantalFiches(4);
		case 3 -> setAantalFiches(5);
		case 4 -> setAantalFiches(7);
		default -> setAantalFiches(0);
		}
	}

	private void setAantalFiches(int aantalFiches) {
		if (aantalFiches == 0)
			throw new IllegalArgumentException(
					String.format("fout in %s: Aantal fiches moet 4, 5 of 7 zijn", this.getClass().getSimpleName()));
		this.aantalFiches = aantalFiches;
	}

	/**
	 * @return 5 fichestapels van grootte this.aantalFiches van elke kleur
	 */
	public HashMap<Kleur, Integer> geefAlleEdelsteenficheStapels() {
		/* WIT(diamant),ROOD(robijn),BLAUW(saffier),GROEN(smaragd),ZWART(onyx); */
		HashMap<Kleur, Integer> ficheStapels = new HashMap<>();

		for (Kleur k : Kleur.values()) {
			ficheStapels.put(k, aantalFiches);
		}

		return ficheStapels;
	}

}
