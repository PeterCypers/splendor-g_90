package domein;

public class Ontwikkelingskaart extends Kaart {

	private String kleurBonus;
	private String fotoOntwikkelingskaart;

	public String getKleurBonus() {
		return this.kleurBonus;
	}

	private void setKleurBonus(String kleurBonus) {
		this.kleurBonus = kleurBonus;
	}

	public String getFotoOntwikkelingskaart() {
		return this.fotoOntwikkelingskaart;
	}

	private void setFotoOntwikkelingskaart(String fotoOntwikkelingskaart) {
		this.fotoOntwikkelingskaart = fotoOntwikkelingskaart;
	}

}