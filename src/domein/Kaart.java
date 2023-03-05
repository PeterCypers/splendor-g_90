package domein;

public abstract class Kaart {

	private int prestigePunten;
	private int witteKost;
	private int rodeKost;
	private int blauweKost;
	private int groeneKost;
	private int zwarteKost;

	public int getPrestigePunten() {
		return this.prestigePunten;
	}

	public void setPrestigePunten(int prestigePunten) {
		this.prestigePunten = prestigePunten;
	}

	public int getWitteKost() {
		return this.witteKost;
	}

	public void setWitteKost(int witteKost) {
		this.witteKost = witteKost;
	}

	public int getRodeKost() {
		return this.rodeKost;
	}

	public void setRodeKost(int rodeKost) {
		this.rodeKost = rodeKost;
	}

	public int getBlauweKost() {
		return this.blauweKost;
	}

	public void setBlauweKost(int blauweKost) {
		this.blauweKost = blauweKost;
	}

	public int getGroeneKost() {
		return this.groeneKost;
	}

	public void setGroeneKost(int groeneKost) {
		this.groeneKost = groeneKost;
	}

	public int getZwarteKost() {
		return this.zwarteKost;
	}

	public void setZwarteKost(int zwarteKost) {
		this.zwarteKost = zwarteKost;
	}

}