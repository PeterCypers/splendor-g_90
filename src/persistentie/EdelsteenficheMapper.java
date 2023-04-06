package persistentie;

import java.util.ArrayList;
import java.util.List;

import domein.Edelsteenfiche;
import domein.Kleur;

public class EdelsteenficheMapper {
	
	private int aantalFiches;
	/**
	 * 2 spelers = 4 fiches
	 * 3 spelers = 5 fiches
	 * 4 spelers = 7 fiches
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
		if(aantalFiches == 0)
			throw new IllegalArgumentException(String.format("fout in %s: Aantal fiches moet 4, 5 of 7 zijn", this.getClass().getSimpleName()));
		this.aantalFiches = aantalFiches;
	}
	
	/**
	 * @return een lijst van grootte this.aantalFiches van elke kleur
	 */
	public List<Edelsteenfiche> geefAlleEdelsteenfiches() {

		List<Edelsteenfiche> fiches = new ArrayList<>();
		 /* WIT(diamant),ROOD(robijn),BLAUW(saffier),GROEN(smaragd),ZWART(onyx);*/
		String[] soort = {"diamant", "robijn", "saffier", "smaragd", "onyx"};
		
		for (Kleur k : Kleur.values()) {
			for (int i = 0; i < aantalFiches; i++) {
				fiches.add(new Edelsteenfiche(soort[k.ordinal()], k, soort[k.ordinal()]));
			}
		}
		return fiches;
	}

}
