package persistentie;

import java.util.ArrayList;
import java.util.List;

import domein.Kleur;
import domein.Ontwikkelingskaart;

public class KaartMapper {
	
	//fotoOntwikkelingskaart (foto's hebben we nog niet, voorlopig null meegeven in constructor O-Kaart)

	public List<Ontwikkelingskaart> geefN1Kaarten(){
		String kleur;
		List<Ontwikkelingskaart> niveau1 = new ArrayList<>();
	
	/*WIT,ROOD,BLAUW,GROEN,ZWART;*/
	//niveau 1: index[0][] krijgt telkens 1 prestigepunt
	int[][] witteKaartKosten = {{0, 0, 4, 0, 0},
								{0, 0, 0, 3, 0},
								{0, 1, 0, 2, 0},
								{2, 0, 0, 2, 0},
								{0, 3, 0, 1, 1},
								{2, 1, 2, 0, 0},
								{1, 0, 1, 1, 1},
								{1, 1, 2, 1, 0}};
	int[][] rodeKaartKosten = {{4, 0, 0, 0, 0},
                               {3, 0, 0, 0, 0},
                               {0, 0, 2, 1, 0},
                               {2, 2, 0, 0, 0},
                               {1, 1, 0, 0, 3},
                               {2, 0, 0, 1, 2},
                               {1, 0, 1, 1, 1},
                               {2, 0, 1, 1, 1}};
	int[][] blauweKaartKosten = {{0, 4, 0, 0, 0},
	                             {0, 0, 0, 0, 3},
	                             {1, 0, 0, 0, 2},
	                             {0, 0, 0, 2, 2},
	                             {0, 1, 1, 3, 0},
	                             {1, 2, 0, 2, 0},
	                             {1, 1, 0, 1, 1},
	                             {1, 2, 0, 1, 1}};
	int[][] groeneKaartKosten = {{0, 0, 0, 0, 4},
			                     {0, 3, 0, 0, 0},
			                     {2, 0, 1, 0, 0},
			                     {0, 2, 2, 0, 0},
			                     {1, 0, 3, 1, 0},
			                     {0, 2, 1, 0, 2},
			                     {1, 1, 1, 0, 1},
			                     {1, 1, 1, 0, 2}};
	int[][] zwarteKaartKosten = {{0, 0, 4, 0, 0},
			                     {0, 0, 0, 3, 0},
			                     {0, 1, 0, 2, 0},
			                     {2, 0, 0, 2, 0},
			                     {0, 3, 0, 1, 1},
			                     {2, 1, 2, 0, 0},
			                     {1, 0, 1, 1, 1},
			                     {1, 1, 2, 1, 0}};
	
	//kan korter geschreven worden als je een manier kent om multidimensional arrays in een arraylist op te slaan
	//hier worden ze wel redelijk door elkaar in de lijst opgeslagen
	for (int i = 0; i < witteKaartKosten.length; i++) {
		int prestigepunten = i==0? 1 : 0;
		kleur = Kleur.WIT.name().toLowerCase();
		niveau1.add(new Ontwikkelingskaart(prestigepunten, kleur, null, witteKaartKosten[i]));
		kleur = Kleur.ROOD.name().toLowerCase();
		niveau1.add(new Ontwikkelingskaart(prestigepunten, kleur, null, rodeKaartKosten[i]));
		kleur = Kleur.BLAUW.name().toLowerCase();
		niveau1.add(new Ontwikkelingskaart(prestigepunten, kleur, null, blauweKaartKosten[i]));
		kleur = Kleur.GROEN.name().toLowerCase();
		niveau1.add(new Ontwikkelingskaart(prestigepunten, kleur, null, groeneKaartKosten[i]));
		kleur = Kleur.ZWART.name().toLowerCase();
		niveau1.add(new Ontwikkelingskaart(prestigepunten, kleur, null, zwarteKaartKosten[i]));
	}
	
	
	return niveau1;
	}

}
