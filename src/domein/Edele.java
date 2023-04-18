package domein;

public class Edele implements SpelVoorwerp{
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
		this.prestigepunten = prestigePunten;
	}

	public String getEdeleFoto() {
		return this.edeleFoto;
	}

	private void setEdeleFoto(String edeleFoto) {
		this.edeleFoto = edeleFoto;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		this.kosten = kosten;
	}

	
}