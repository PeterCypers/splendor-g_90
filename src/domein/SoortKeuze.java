package domein;

public enum SoortKeuze {
	NEEM_DRIE(1), NEEM_TWEE(2), KOOP_KAART(3), PAS_BEURT(4);

	private final int KEUZE;

	private SoortKeuze(int keuze) {
		this.KEUZE = keuze;
	}

	public int getKeuze() {
		return this.KEUZE;
	}

	public static SoortKeuze valueOf(int keuze) {
		for (SoortKeuze sk : SoortKeuze.values()) {
			if (sk.getKeuze() == keuze) {
				return sk;
			}
		}
		throw new IllegalArgumentException("Invalid keuze: " + keuze);
	}
}
