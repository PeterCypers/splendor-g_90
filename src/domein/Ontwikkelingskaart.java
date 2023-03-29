package domein;

public class Ontwikkelingskaart {

	private int prestigepunten;
	private Kleur kleurBonus;
	private String fotoOntwikkelingskaart;
	private int[] kosten = new int[5];

	public Ontwikkelingskaart(int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart, int[] kosten) {
		setPrestigepunten(prestigepunten);
		setKleurBonus(kleurBonus);
		setFotoOntwikkelingskaart(fotoOntwikkelingskaart);
		setKosten(kosten);
	}

	public Kleur getKleurBonus() {
		return this.kleurBonus;
	}

	private void setKleurBonus(Kleur kleurBonus) {
		this.kleurBonus = kleurBonus;
	}

	public String getFotoOntwikkelingskaart() {
		return this.fotoOntwikkelingskaart;
	}

	private void setFotoOntwikkelingskaart(String fotoOntwikkelingskaart) {
		this.fotoOntwikkelingskaart = fotoOntwikkelingskaart;
	}

	public int getPrestigepunten() {
		return prestigepunten;
	}

	private void setPrestigepunten(int prestigepunten) {
		this.prestigepunten = prestigepunten;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		this.kosten = kosten;
	}

}