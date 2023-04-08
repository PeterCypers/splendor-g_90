package domein;

public enum Kleur {
	WIT,
	ROOD,
	BLAUW,
	GROEN,
	ZWART;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
