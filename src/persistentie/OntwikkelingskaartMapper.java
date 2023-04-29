package persistentie;

import java.util.ArrayList;
import java.util.List;

import domein.Kleur;
import domein.Ontwikkelingskaart;

public class OntwikkelingskaartMapper {
	
	//fotoOntwikkelingskaart TODO - hoe gaan die fotos ingevuld worden?
	private static final String PLACEHOLDER = "999";
	public List<Ontwikkelingskaart> geefN1Kaarten(){
		Kleur kleur;
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
		kleur = Kleur.WIT;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, PLACEHOLDER, witteKaartKosten[i]));
		kleur = Kleur.ROOD;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, PLACEHOLDER, rodeKaartKosten[i]));
		kleur = Kleur.BLAUW;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, PLACEHOLDER, blauweKaartKosten[i]));
		kleur = Kleur.GROEN;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, PLACEHOLDER, groeneKaartKosten[i]));
		kleur = Kleur.ZWART;
		niveau1.add(new Ontwikkelingskaart(1, prestigepunten, kleur, PLACEHOLDER, zwarteKaartKosten[i]));
	}
	
	
	return niveau1;
	}
	
	public List<Ontwikkelingskaart> geefN2Kaarten() {
		Kleur kleur;
		List<Ontwikkelingskaart> niveau2 = new ArrayList<>();
		
		// niveau2
		/* WIT,ROOD,BLAUW,GROEN,ZWART; */
		int[] prestigeLijst = {1, 1, 2, 2, 2, 3};
		String[] fotoLijst = {"1", "1", "2", "1", "2", "2"};
		String[] fotoBuitenPatroon = {PLACEHOLDER, PLACEHOLDER, PLACEHOLDER, "2", "1", PLACEHOLDER}; //zie excel file
		
		
		int[][] witteKaartKosten = {{ 0, 2, 0, 3, 2 },
				                    { 2, 3, 3, 0, 0 },
				                    { 0, 5, 0, 0, 0 },
				                    { 0, 4, 0, 1, 2 },
				                    { 0, 5, 0, 0, 3 },
				                    { 6, 0, 0, 0, 0 }};
		int[][] rodeKaartKosten = {{ 2, 2, 0, 0, 3 },
                                   { 0, 2, 3, 0, 3 },
                                   { 0, 0, 0, 0, 5 },
                                   { 1, 0, 4, 2, 0 },
                                   { 3, 0, 0, 0, 5 },
                                   { 0, 6, 0, 0, 0 }};
		int[][] blauweKaartKosten = {{ 0, 3, 2, 2, 0 },
                                     { 0, 0, 2, 3, 3 },
                                     { 0, 0, 5, 0, 0 },
                                     { 2, 1, 0, 0, 4 },
                                     { 5, 0, 3, 0, 0 },
                                     { 0, 0, 6, 0, 0 }};
		int[][] groeneKaartKosten = {{ 2, 0, 3, 0, 2 },
                                     { 3, 3, 0, 2, 0 },
                                     { 0, 0, 0, 5, 0 },
                                     { 4, 0, 2, 0, 1 },
                                     { 0, 0, 5, 3, 0 },
                                     { 0, 0, 0, 6, 0 }};
		int[][] zwarteKaartKosten = {{ 3, 0, 2, 2, 0 },
                                     { 3, 0, 0, 3, 2 },
                                     { 5, 0, 0, 0, 0 },
                                     { 0, 2, 1, 4, 0 },
                                     { 0, 3, 0, 5, 0 },
                                     { 0, 0, 0, 0, 6 }};
		
		for (int i = 0; i < witteKaartKosten.length; i++) {
			int prestigePunten = prestigeLijst[i];
			String foto = fotoLijst[i];
			String exceptionFoto = fotoBuitenPatroon[i];
			kleur = Kleur.WIT;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, foto, witteKaartKosten[i]));
			kleur = Kleur.ROOD;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, foto, rodeKaartKosten[i]));
			//extra conditie voor blauw: foto buiten patroon
			kleur = Kleur.BLAUW;
			boolean exception = (i==3 || i==4);
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, exception? exceptionFoto : foto, blauweKaartKosten[i]));
			kleur = Kleur.GROEN;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, foto, groeneKaartKosten[i]));
			kleur = Kleur.ZWART;
			niveau2.add(new Ontwikkelingskaart(2, prestigePunten, kleur, foto, zwarteKaartKosten[i]));
			
		}
		return niveau2;
	}
	
	public List<Ontwikkelingskaart> geefN3Kaarten() {
		Kleur kleur;
		List<Ontwikkelingskaart> niveau3 = new ArrayList<>();
		// niveau3
		/*WIT,ROOD,BLAUW,GROEN,ZWART;*/
		int[] prestigeLijst = {3, 4, 4, 5};
		String[] fotoLijstPatroonA = {"1", "1", "2", "2"};
		String[] fotoLijstPatroonB = {"1", "2", "2", "1"};
		
		//FP B
		int[][] witteKaartKosten = {{ 0, 5, 3, 3, 3 },
                                    { 0, 0, 0, 0, 7 },
                                    { 3, 3, 0, 0, 6 },
                                    { 3, 0, 0, 0, 7 }};
		//FP A
		int[][] rodeKaartKosten = {{ 3, 0, 5, 3, 3 },
                                   { 0, 0, 0, 7, 0 },
                                   { 0, 3, 3, 6, 0 },
                                   { 0, 3, 0, 7, 0 }};
		//FP A
		int[][] blauweKaartKosten = {{ 3, 3, 0, 3, 5 },
                                     { 7, 0, 0, 0, 0 },
                                     { 6, 0, 3, 0, 3 },
                                     { 7, 0, 3, 0, 0 }};
		//FP B
		int[][] groeneKaartKosten = {{ 5, 3, 3, 0, 3 },
                                     { 0, 0, 7, 0, 0 },
                                     { 3, 0, 6, 3, 0 },
                                     { 0, 0, 7, 3, 0 }};
		//FP B
		int[][] zwarteKaartKosten = {{ 3, 3, 3, 5, 0 },
                                     { 0, 7, 0, 0, 0 },
                                     { 0, 6, 0, 3, 3 },
                                     { 0, 7, 0, 0, 3 }};
		//Twee lussen -> foto-patroon A//B
		//patroon A (ROOD, BLAUW):
		for (int i = 0; i < witteKaartKosten.length; i++) {
			String foto = fotoLijstPatroonA[i];
			int prestigePunten = prestigeLijst[i];
			kleur = Kleur.ROOD;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, foto, rodeKaartKosten[i]));
			kleur = Kleur.BLAUW;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, foto, blauweKaartKosten[i]));
			
		}
		//patroon B (WIT, GROEN, ZWART):
		for (int i = 0; i < witteKaartKosten.length; i++) {
			String foto = fotoLijstPatroonB[i];
			int prestigePunten = prestigeLijst[i];
			kleur = Kleur.WIT;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, foto, witteKaartKosten[i]));
			kleur = Kleur.GROEN;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, foto, groeneKaartKosten[i]));
			kleur = Kleur.ZWART;
			niveau3.add(new Ontwikkelingskaart(3, prestigePunten, kleur, foto, zwarteKaartKosten[i]));
		}
		return niveau3;
	}

}
