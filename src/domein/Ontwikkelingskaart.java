package domein;

import java.util.Arrays;

import resources.Taal;

public class Ontwikkelingskaart implements SpelVoorwerp {

	private int niveau;
	private int prestigepunten;
	private Kleur kleurBonus;
	private String fotoOntwikkelingskaart;
	private int[] kosten = new int[5];

	public Ontwikkelingskaart(int niveau, int prestigepunten, Kleur kleurBonus, String fotoOntwikkelingskaart,
			int[] kosten) {
		setNiveau(niveau);
		setPrestigepunten(prestigepunten);
		setKleurBonus(kleurBonus);
		setFotoOntwikkelingskaart(fotoOntwikkelingskaart);
		setKosten(kosten);
	}

	public int getNiveau() {
		return this.niveau;
	}

	private void setNiveau(int niveau) {
		if (niveau < 1 || niveau > 3)
			throw new IllegalArgumentException(Taal.getString("ontwikkelingskaartSetNiveauExceptionMsg"));
		this.niveau = niveau;
	}

	public Kleur getKleurBonus() {
		return this.kleurBonus;
	}

	private void setKleurBonus(Kleur kleurBonus) {
		if (kleurBonus == null)
			throw new IllegalArgumentException(Taal.getString("ontwikkelingskaartSetKleurBonusExceptionMsg"));
		this.kleurBonus = kleurBonus;
	}

	public String getFotoOntwikkelingskaart() {
		return this.fotoOntwikkelingskaart;
	}

	private void setFotoOntwikkelingskaart(String fotoOntwikkelingskaart) {
		if (fotoOntwikkelingskaart == null || fotoOntwikkelingskaart.isBlank())
			throw new IllegalArgumentException(
					Taal.getString("ontwikkelingskaartSetFotoOntwikkelingskaartExceptionMsg"));
		this.fotoOntwikkelingskaart = fotoOntwikkelingskaart;
	}

	public int getPrestigepunten() {
		return prestigepunten;
	}

	private void setPrestigepunten(int prestigepunten) {
		if (prestigepunten < 0 || prestigepunten > 5)
			throw new IllegalArgumentException(Taal.getString("ontwikkelingskaartSetPrestigepuntenExceptionMsg"));
		this.prestigepunten = prestigepunten;
	}

	public int[] getKosten() {
		return kosten;
	}

	private void setKosten(int[] kosten) {
		if (kosten == null)
			throw new IllegalArgumentException(Taal.getString("setKostenNullExceptionMsg"));
		if (kosten.length != 5)
			throw new IllegalArgumentException(Taal.getString("setKostenArrayLengthExceptionMsg"));
		this.kosten = kosten;
	}

	@Override
	public String toString() {

		String kostenString = "";
		// format Kleur: kost
		// Kleur: kost
//		for (int i = 0; i < Kleur.values().length; i++) {
//			kostenString += String.format("%s: %d%n",Taal.getString(Kleur.values()[i].toString()), kosten[i]);
//		}
		kostenString = Arrays.toString(this.kosten);
		String ontwikkelingsKaartAlsString = String.format("%s - %s %d, %s %-5s %s %s %d%n%s: %s",
				Taal.getString("developmentCard"), Taal.getString("level").toLowerCase(), getNiveau(),
				Taal.getString("colour").toLowerCase(), Taal.getString(getKleurBonus().toString()).toLowerCase(),
				Taal.getString("and").toLowerCase(), Taal.getString("prestige").toLowerCase(), this.getPrestigepunten(),
				Taal.getString("cost"), kostenString);

		return ontwikkelingsKaartAlsString;
	}

}