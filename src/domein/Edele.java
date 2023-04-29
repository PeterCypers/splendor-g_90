package domein;

public class Edele implements SpelVoorwerp{
	public static final int EDELEN_PRESTIGE = 3;
	private int prestigepunten;
	private String edeleFoto;
	private int[] kosten;
	
	public Edele(int prestigePunten, String edeleFoto, int[] kosten) {
		setPrestigePunten(prestigePunten);
		setEdeleFoto(edeleFoto);
		setKosten(kosten);
	}
	
	public int getPrestigepunten() {
		return prestigepunten;
	}

	private void setPrestigePunten(int prestigePunten) {
		if(prestigePunten != EDELEN_PRESTIGE)
			throw new IllegalArgumentException(String.format("Alle edelen moeten %d prestigepunten hebben", EDELEN_PRESTIGE));
		this.prestigepunten = prestigePunten;
	}

	public String getEdeleFoto() {
		return this.edeleFoto;
	}

	private void setEdeleFoto(String edeleFoto) {
		if(edeleFoto == null || edeleFoto.isBlank())
			throw new IllegalArgumentException("Edele foto is null of is leeg");
		this.edeleFoto = edeleFoto;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		if(kosten == null)
			throw new IllegalArgumentException("Kosten is null");
		if(kosten.length != 5)
			throw new IllegalArgumentException("Lengte van de kosten-array is niet exact 5");
		this.kosten = kosten;
	}

	
}