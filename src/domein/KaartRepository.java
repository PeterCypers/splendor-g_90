package domein;

import java.util.List;

import persistentie.KaartMapper;

public class KaartRepository {
	
	private KaartMapper mapper;
	private List<Ontwikkelingskaart> niveau1;
	//TODO niveau2
	//TODO niveau3
	
	public KaartRepository() {
		this.mapper = new KaartMapper();
		haalN1KaartenOp();
	}
	
	public List<Ontwikkelingskaart> geefN1Kaarten(){
		return this.niveau1;
	}
	
	private void haalN1KaartenOp() {
		this.niveau1 = mapper.geefN1Kaarten();
	}
	
	//TODO add methodes:
	/*
	 * private void haalN2KaartenOp()
	 * 
	 * private void haalN3KaartenOp()
	 */
}