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
		
		/* foto volgorde: zoals op extra_info gegeven pdf
		 * noble04, noble10, noble01, noble02, noble09, noble03, noble06, noble07, noble08, noble05 */
		List<Edele> edelen = new ArrayList<>();
		String[] edelenFotos = {"src\\img\\nobles\\noble04.png",
								"src\\img\\nobles\\noble10.png",
								"src\\img\\nobles\\noble01.png",
								"src\\img\\nobles\\noble02.png",
								"src\\img\\nobles\\noble09.png",
								"src\\img\\nobles\\noble03.png",
								"src\\img\\nobles\\noble06.png",
								"src\\img\\nobles\\noble07.png",
								"src\\img\\nobles\\noble08.png",
								"src\\img\\nobles\\noble05.png"};
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
			edelen.add(new Edele(EDELENPRESTIGE, edelenFotos[i], kosten[i]));
		}
		return edelen;
	}

}
