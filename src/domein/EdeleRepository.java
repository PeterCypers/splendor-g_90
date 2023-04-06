package domein;

import java.util.List;

import persistentie.EdeleMapper;

public class EdeleRepository {
	
	private EdeleMapper mapper;
	private List<Edele> edeleStapel;

	public EdeleRepository(int aantalEdelen) {
		//TODO methode haaledelen op die ook aantalEdelen meekrijgt, haalt edelen uit mapper, shuffled de edelen 
		//stelt de vult de edelestapel met aantalEdelen aantal edelen uit de opgehaalde lijst uit mapper
	}
}
