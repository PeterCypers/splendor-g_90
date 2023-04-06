package persistentie;

import java.util.ArrayList;
import java.util.List;

import domein.Edele;

public class EdeleMapper {
	
	private final static int EDELENPRESTIGE = 3;
	
	//10 kaarten, hardcoded, volgorde van extra_info overzicht alle edelen
	public List<Edele> geefAlleEdelen(){
		/* kosten volgorde:
		 * WIT,ROOD,BLAUW,GROEN,ZWART;
		 * Edele(int pp, String foto, int[] kosten)*/
		
		List<Edele> edelen = new ArrayList<>();
		String[] fotos = {"dame1", "heer1", "dame2", "dame3", "heer2", "dame4", "heer3", "heer4", "heer5", "dame5"};
		int[][] kosten = {{4, 0, 0, 0, 4},
		                  {3, 3, 0, 0, 3},
		                  {3, 0, 3, 0, 3},
		                  {3, 0, 3, 3, 0},
		                  {0, 3, 0, 3, 3},
		                  {0, 3, 3, 3, 0},
		                  {0, 0, 4, 4, 0},
		                  {0, 4, 0, 0, 4},
		                  {4, 0, 4, 0, 0},
		                  {0, 4, 0, 4, 0}};
		
		//alle edelen aanmaken + toevoegen aan de lijst
		for (int i = 0; i < kosten.length; i++) {
			edelen.add(new Edele(EDELENPRESTIGE, fotos[i], kosten[i]));
		}
		return edelen;
	}

}
