package domein;

import java.util.Objects;

import resources.Taal;

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
		    throw new IllegalArgumentException(String.format("%s %d %s", 
				    Taal.getString("edeleSetPrestigePuntenExceptionMsgPart1"), EDELEN_PRESTIGE,
				    Taal.getString("edeleSetPrestigePuntenExceptionMsgPart1")));
		this.prestigepunten = prestigePunten;
	}

	public String getEdeleFoto() {
		return this.edeleFoto;
	}

	private void setEdeleFoto(String edeleFoto) {
		if(edeleFoto == null || edeleFoto.isBlank())
			throw new IllegalArgumentException(Taal.getString("edeleSetEdeleFotoExceptionMsg"));
		this.edeleFoto = edeleFoto;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		if(kosten == null)
			throw new IllegalArgumentException(Taal.getString("setKostenNullExceptionMsg"));
		if(kosten.length != 5)
			throw new IllegalArgumentException(Taal.getString("setKostenArrayLengthExceptionMsg"));
		this.kosten = kosten;
	}

	@Override
	public int hashCode() {
		return Objects.hash(edeleFoto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edele other = (Edele) obj;
		return Objects.equals(edeleFoto, other.edeleFoto);
	}
	
	
	
}