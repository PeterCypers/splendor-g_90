package persistentie;

import java.util.ArrayList;
import java.util.List;

import domein.Kleur;
import domein.Ontwikkelingskaart;

public class OntwikkelingskaartMapper {
	
	public List<Ontwikkelingskaart> geefN1Kaarten(){
		Kleur kleur;
		List<Ontwikkelingskaart> niveau1 = new ArrayList<>();
		String[] niveau1Fotos = {"src\\img\\cards\\level1\\mine_diamond.jpg",
								 "src\\img\\cards\\level1\\mine_ruby.jpg",
								 "src\\img\\cards\\level1\\mine_sapphire.jpg",
								 "src\\img\\cards\\level1\\mine_emerald.jpg",
								 "src\\img\\cards\\level1\\mine_onyx.jpg"};
		
	/* WIT,ROOD,BLAUW,GROEN,ZWART */
	/* DIAMOND, RUBY, SAPPHIRE, EMERALD, ONYX */
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
		kleur = Kleur.WIT;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, niveau1Fotos[0], witteKaartKosten[i]));
		kleur = Kleur.ROOD;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, niveau1Fotos[1], rodeKaartKosten[i]));
		kleur = Kleur.BLAUW;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, niveau1Fotos[2], blauweKaartKosten[i]));
		kleur = Kleur.GROEN;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, niveau1Fotos[3], groeneKaartKosten[i]));
		kleur = Kleur.ZWART;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, niveau1Fotos[4], zwarteKaartKosten[i]));
	}
	
	
	return niveau1;
	}
	
	public List<Ontwikkelingskaart> geefN2Kaarten() {
		Kleur kleur;
		List<Ontwikkelingskaart> niveau2 = new ArrayList<>();
		
		// niveau2
		/* WIT,ROOD,BLAUW,GROEN,ZWART */
		/* DIAMOND, RUBY, SAPPHIRE, EMERALD, ONYX */
		int[] prestigeLijst = {1, 1, 2, 2, 2, 3};
		String[] patroon1 = {"1", "1", "2", "1", "2", "2"}; //zie excel file
		String[] patroon2 = {"1", "1", "2", "2", "1", "2"}; //zie excel file
		
		/* WIT : DIAMOND  (patroon 1)*/
		int[][] witteKaartKosten = {{ 0, 2, 0, 3, 2 },
				                    { 2, 3, 3, 0, 0 },
				                    { 0, 5, 0, 0, 0 },
				                    { 0, 4, 0, 1, 2 },
				                    { 0, 5, 0, 0, 3 },
				                    { 6, 0, 0, 0, 0 }};
		String witteKaart1 = "src\\img\\cards\\level2\\caravan_diamond.jpg";
		String witteKaart2 = "src\\img\\cards\\level2\\lapidary_diamond.jpg";
		String[] witteKaartFotos = {witteKaart1, witteKaart1, witteKaart2, witteKaart1, witteKaart2, witteKaart2};
		
		/* ROOD : RUBY  (patroon 1) */ 
		int[][] rodeKaartKosten = {{ 2, 2, 0, 0, 3 },
                                   { 0, 2, 3, 0, 3 },
                                   { 0, 0, 0, 0, 5 },
                                   { 1, 0, 4, 2, 0 },
                                   { 3, 0, 0, 0, 5 },
                                   { 0, 6, 0, 0, 0 }};
		String rodeKaart1 = "src\\img\\cards\\level2\\felucca_ruby.jpg";
		String rodeKaart2 = "src\\img\\cards\\level2\\lapidary_ruby.jpg";
		String[] rodeKaartFotos = {rodeKaart1, rodeKaart1, rodeKaart2, rodeKaart1, rodeKaart2, rodeKaart2};
		
		/* BLAUW : SAPPHIRE (patroon 2) */
		int[][] blauweKaartKosten = {{ 0, 3, 2, 2, 0 },
                                     { 0, 0, 2, 3, 3 },
                                     { 0, 0, 5, 0, 0 },
                                     { 2, 1, 0, 0, 4 },
                                     { 5, 0, 3, 0, 0 },
                                     { 0, 0, 6, 0, 0 }};
		String blauweKaart1 = "src\\img\\cards\\level2\\caravan_sapphire.jpg";
		String blauweKaart2 = "src\\img\\cards\\level2\\lapidary_sapphire.jpg";
		String[] blauweKaartFotos = {blauweKaart1, blauweKaart1, blauweKaart2, blauweKaart2, blauweKaart1, blauweKaart2};
		
		/* GROEN : EMERALD (patroon 1) */
		int[][] groeneKaartKosten = {{ 2, 0, 3, 0, 2 },
                                     { 3, 3, 0, 2, 0 },
                                     { 0, 0, 0, 5, 0 },
                                     { 4, 0, 2, 0, 1 },
                                     { 0, 0, 5, 3, 0 },
                                     { 0, 0, 0, 6, 0 }};
		String groeneKaart1 = "src\\img\\cards\\level2\\trader_emerald.jpg";
		String groeneKaart2 = "src\\img\\cards\\level2\\carrack_emerald.jpg";
		String[] groeneKaartFotos = {groeneKaart1, groeneKaart1, groeneKaart2, groeneKaart1, groeneKaart2, groeneKaart2};
		
		/* ZWART : ONYX (patroon 1) */
		int[][] zwarteKaartKosten = {{ 3, 0, 2, 2, 0 },
                                     { 3, 0, 0, 3, 2 },
                                     { 5, 0, 0, 0, 0 },
                                     { 0, 2, 1, 4, 0 },
                                     { 0, 3, 0, 5, 0 },
                                     { 0, 0, 0, 0, 6 }};
		String zwarteKaart1 = "src\\img\\cards\\level2\\caravan_onyx.jpg";
		String zwarteKaart2 = "src\\img\\cards\\level2\\lapidary_onyx.jpg";
		String[] zwarteKaartFotos = {zwarteKaart1, zwarteKaart1, zwarteKaart2, zwarteKaart1, zwarteKaart2, zwarteKaart2};
		
		for (int i = 0; i < witteKaartKosten.length; i++) {
			int prestigePunten = prestigeLijst[i];

			kleur = Kleur.WIT;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, witteKaartFotos[i], witteKaartKosten[i]));
			kleur = Kleur.ROOD;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, rodeKaartFotos[i], rodeKaartKosten[i]));
			kleur = Kleur.BLAUW;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, blauweKaartFotos[i], blauweKaartKosten[i]));
			kleur = Kleur.GROEN;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, groeneKaartFotos[i], groeneKaartKosten[i]));
			kleur = Kleur.ZWART;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, zwarteKaartFotos[i], zwarteKaartKosten[i]));
			
		}
		return niveau2;
	}
	
	public List<Ontwikkelingskaart> geefN3Kaarten() {
		Kleur kleur;
		List<Ontwikkelingskaart> niveau3 = new ArrayList<>();
		// niveau3
		/*WIT,ROOD,BLAUW,GROEN,ZWART*/
		/*DIAMOND, RUBY, SAPPHIRE, EMERALD, ONYX*/
		int[] prestigeLijst = {3, 4, 4, 5};
		String[] fotoLijstPatroonA = {"1", "1", "2", "2"};
		String[] fotoLijstPatroonB = {"1", "2", "2", "1"};
		
		/* WIT : DIAMOND  (patroon B)*/
		int[][] witteKaartKosten = {{ 0, 5, 3, 3, 3 },
                                    { 0, 0, 0, 0, 7 },
                                    { 3, 3, 0, 0, 6 },
                                    { 3, 0, 0, 0, 7 }};
		String witteKaart1 = "src\\img\\cards\\level3\\building_diamond.jpg";
		String witteKaart2 = "src\\img\\cards\\level3\\shop_diamond.jpg";
		String[] witteKaartFotos = {witteKaart1, witteKaart2, witteKaart2, witteKaart1};

		/* ROOD : RUBY  (patroon A) */
		int[][] rodeKaartKosten = {{ 3, 0, 5, 3, 3 },
                                   { 0, 0, 0, 7, 0 },
                                   { 0, 3, 3, 6, 0 },
                                   { 0, 3, 0, 7, 0 }};
		String rodeKaart1 = "src\\img\\cards\\level3\\statue_ruby.jpg";
		String rodeKaart2 = "src\\img\\cards\\level3\\building_ruby.jpg";
		String[] rodeKaartFotos = {rodeKaart1, rodeKaart1, rodeKaart2, rodeKaart2};

		/* BLAUW : SAPPHIRE (patroon A) */
		int[][] blauweKaartKosten = {{ 3, 3, 0, 3, 5 },
                                     { 7, 0, 0, 0, 0 },
                                     { 6, 0, 3, 0, 3 },
                                     { 7, 0, 3, 0, 0 }};
		String blauweKaart1 = "src\\img\\cards\\level3\\canal_sapphire.jpg";
		String blauweKaart2 = "src\\img\\cards\\level3\\shop_sapphire.jpg";
		String[] blauweKaartFotos = {blauweKaart1, blauweKaart1, blauweKaart2, blauweKaart2};

		/* GROEN : EMERALD (patroon B) */
		int[][] groeneKaartKosten = {{ 5, 3, 3, 0, 3 },
                                     { 0, 0, 7, 0, 0 },
                                     { 3, 0, 6, 3, 0 },
                                     { 0, 0, 7, 3, 0 }};
		String groeneKaart1 = "src\\img\\cards\\level3\\building_emerald.jpg";
		String groeneKaart2 = "src\\img\\cards\\level3\\bridge_emerald.jpg";
		String[] groeneKaartFotos = {groeneKaart1, groeneKaart2, groeneKaart2, groeneKaart1};

		/* ZWART : ONYX (patroon B) */
		int[][] zwarteKaartKosten = {{ 3, 3, 3, 5, 0 },
                                     { 0, 7, 0, 0, 0 },
                                     { 0, 6, 0, 3, 3 },
                                     { 0, 7, 0, 0, 3 }};
		String zwarteKaart1 = "src\\img\\cards\\level3\\alley_onyx.jpg";
		String zwarteKaart2 = "src\\img\\cards\\level3\\street_onyx.jpg";
		String[] zwarteKaartFotos = {zwarteKaart1, zwarteKaart2, zwarteKaart2, zwarteKaart1};
		
		
		for (int i = 0; i < witteKaartKosten.length; i++) {
			int prestigePunten = prestigeLijst[i];
			
			kleur = Kleur.WIT;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, witteKaartFotos[i], witteKaartKosten[i]));
			kleur = Kleur.ROOD;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, rodeKaartFotos[i], rodeKaartKosten[i]));
			kleur = Kleur.BLAUW;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, blauweKaartFotos[i], blauweKaartKosten[i]));
			kleur = Kleur.GROEN;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, groeneKaartFotos[i], groeneKaartKosten[i]));
			kleur = Kleur.ZWART;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, zwarteKaartFotos[i], zwarteKaartKosten[i]));
			
		}

		return niveau3;
	}

}
