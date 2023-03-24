package domein;

import java.util.List;

import persistentie.KaartMapper;

public class KaartRepository {
	
	private KaartMapper mapper;
	private List<Ontwikkelingskaart> niveau1;
	private List<Ontwikkelingskaart> niveau2;
	private List<Ontwikkelingskaart> niveau3;
	
	public KaartRepository() {
		this.mapper = new KaartMapper();
		haalN1KaartenOp();
		haalN2KaartenOp();
		haalN3KaartenOp();
	}
	//N1
	public List<Ontwikkelingskaart> geefN1Kaarten(){
		return this.niveau1;
	}
	private void haalN1KaartenOp() {
		this.niveau1 = mapper.geefN1Kaarten();
	}
	//N2
	private void haalN2KaartenOp() {
		this.niveau2 = mapper.geefN2Kaarten();
	}
	public List<Ontwikkelingskaart> geefN2Kaarten(){
		return this.niveau2;
	}
	//N3
	private void haalN3KaartenOp() {
		this.niveau3 = mapper.geefN3Kaarten();
	}
	public List<Ontwikkelingskaart> geefN3Kaarten(){
		return this.niveau3;
	}
}