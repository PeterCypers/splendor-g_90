package domein;

import java.util.Comparator;

public class SpelerComparator implements Comparator<Speler>{

	@Override
	public int compare(Speler o1, Speler o2) {
		int result = Integer.compare(o1.getGeboortejaar(), o2.getGeboortejaar()) * -1;
		int result2 = Integer.compare(o1.getGebruikersnaam().length(),o2.getGebruikersnaam().length()) * -1;
		return result != 0 ? result : result2 != 0 ? result2 : o1.getGebruikersnaam().compareTo(o2.getGebruikersnaam()) * -1;
	}

}
