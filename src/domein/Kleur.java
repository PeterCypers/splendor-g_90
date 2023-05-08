package domein;

public enum Kleur {
	WIT(0), ROOD(1), BLAUW(2), GROEN(3), ZWART(4);

	private final int KLEUR;

	private Kleur(int kleur) {
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

	public String soort() {
		String[] soort = { "diamant", "robijn", "saffier", "smaragd", "onyx" };

		return soort[this.getKleur()];
	}

	public String kind() {
		String[] kind = { "diamond", "ruby", "sapphire", "emerald", "onyx" };

		return kind[this.getKleur()];
	}

	public String type() {
		String[] type = {"diamant", "rubis", "saphir", "émeraude", "onyx"};
		
		return type[this.getKleur()];
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
