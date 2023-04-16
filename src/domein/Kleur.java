package domein;

public enum Kleur {
	WIT(1), ROOD(2), BLAUW(3), GROEN(4), ZWART(5);

	private final int KLEUR;

	Kleur(int kleur) {
		this.KLEUR = kleur;
	}

	public int getKleur() {
		return this.KLEUR;
	}

	public static Kleur valueOf(int kleur) {
		for (Kleur k : Kleur.values()) {
			if (k.getKleur() == kleur) {
				return k;
			}
		}
		throw new IllegalArgumentException("Invalid keuze: " + kleur);
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
