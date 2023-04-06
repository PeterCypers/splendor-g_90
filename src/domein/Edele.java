package domein;

public class Edele {
	private int prestigePunten;
	private String edeleFoto;
	private int[] kosten;
	
	public Edele(int prestigePunten, String edeleFoto, int[] kosten) {
		setPrestigePunten(prestigePunten);
		setEdeleFoto(edeleFoto);
		setKosten(kosten);
	}
	
	public int getPrestigePunten() {
		return prestigePunten;
	}

	private void setPrestigePunten(int prestigePunten) {
		this.prestigePunten = prestigePunten;
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