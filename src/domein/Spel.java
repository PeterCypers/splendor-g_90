package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import resources.Taal;

public class Spel {
	public static final int MIN_AANTAL_SPELERS = 2;
	public static final int MAX_AANTAL_SPELERS = 4;

	private final List<Speler> aangemeldeSpelers;
	private Speler spelerAanBeurt;

	private final List<Ontwikkelingskaart> n1;
	private final List<Ontwikkelingskaart> n2;
	private final List<Ontwikkelingskaart> n3;

	private boolean eindeSpel = false;

	private final List<Edele> edelen;

	private HashMap<Kleur, Integer> ficheStapels;

	private Ontwikkelingskaart[] niveau1Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau2Zichtbaar = { null, null, null, null };
	private Ontwikkelingskaart[] niveau3Zichtbaar = { null, null, null, null };

	private int ronde = 0;

	/**
	 * Class constructor. <br>
	 * - aantal deelnemende spelers wordt gecontroleerd volgens DR_SPEL_
	 * AANTAL_SPELERS<br>
	 * - de jongste <code>Speler</code> mag beginnen, bij meerdere jongste spelers
	 * zie methode <code>bepaalStartSpeler()</code><br>
	 * - -> de startSpeler wordt bepaald<br>
	 * - -> de startSpeler wordt nu de eerste <code>spelerAanbeurt</code><br>
	 * - alle lijsten worden gecontroleerd - <code>n1</code>, <code>n2</code>,
	 * <code>n3</code> -> lijsten worden ingesteld op de uitgepakte
	 * <code>ontwikkelingsKaarten</code><br>
	 * - de zichtbare lijsten van kaarten worden bijgevuld
	 * 
	 * @param aangemeldeSpelers    lijst van deelnemende <code>Speler</code>s
	 * @param ontwikkelingsKaarten de drie geshuffelde lijsten van
	 *                             <code>Ontwikkelingskaart</code> van niveau 1, 2
	 *                             en 3
	 * @param edelen               geshuffelde <code>List</code> van
	 *                             <code>Edele</code>
	 * @param ficheStapels         een <code>HashMap</code> die de aantal fiches per
	 *                             kleur bijhoudt<br>
	 * 
	 * @throws IllegalArgumentException bij het controlleren van
	 *                                  <code>controleerAantalSpelers()</code><br>
	 *                                  <code>controleerOntwikkelingsKaartLijsten()</code>,
	 *                                  <code>controleerEdelenLijst()</code><br>
	 *                                  en <code>controleerFicheStapels()</code>
	 */

	public Spel(List<Speler> aangemeldeSpelers, List<List<Ontwikkelingskaart>> ontwikkelingsKaarten, List<Edele> edelen,
			HashMap<Kleur, Integer> ficheStapels) {
		controleerAantalSpelers(aangemeldeSpelers);
		this.aangemeldeSpelers = aangemeldeSpelers;

		this.bepaalStartSpeler(aangemeldeSpelers);
		this.spelerAanBeurt.setStartSpeler();
		this.spelerAanBeurt.setAanDeBeurt(true);

		verhoogRonde();

		controleerOntwikkelingsKaartLijsten(ontwikkelingsKaarten);

		this.n1 = ontwikkelingsKaarten.get(0);
		this.n2 = ontwikkelingsKaarten.get(1);
		this.n3 = ontwikkelingsKaarten.get(2);

		controleerEdelenLijst(edelen);
		this.edelen = edelen;

		controleerFicheStapels(ficheStapels);
		this.ficheStapels = ficheStapels;

		vulKaartenBij();

		// [TEST]
		// testOntwikkelingsKaartStapels();
	}

	public int getRonde() {
		return ronde;
	}

	private void verhoogRonde() {
		this.ronde += 1;
	}

	/**
	 * DR_SPEL_STARTER + DR_RONDE_BEURT<br>
	 * bepaalt start speler en zet deze vooraan in de lijst van aangemelde spelers
	 * 
	 * @param aangemeldeSpelers de <code>List<Speler></code> van deelnemende
	 *                          spelers<br>
	 *                          - uit de lijst wordt de <code>Speler</code> met de
	 *                          recentste geboortedatum de eerste
	 *                          <code>spelerAanBeurt</code><br>
	 *                          - bij gelijke geboortedatum wordt de
	 *                          <code>Speler</code> met de langste naam gekozen<br>
	 *                          - bij gelijke 'geboortedatum' en 'naam lengte' wordt
	 *                          [Z-A] omgekeerd alfabetisch gekozen
	 */
	private void bepaalStartSpeler(List<Speler> aangemeldeSpelers) {
		List<Speler> copySorting = new ArrayList<>();
		for (Speler s : aangemeldeSpelers)
			copySorting.add(s);
		Collections.sort(copySorting, new SpelerComparator());
		spelerAanBeurt = copySorting.get(0);
		int indexStartSpeler = aangemeldeSpelers.indexOf(spelerAanBeurt);
		aangemeldeSpelers.add(0, aangemeldeSpelers.remove(indexStartSpeler));
	}

	/**
	 * DR_SPEL_ AANTAL_SPELERS: aantal spelers [2-4]
	 * 
	 * @param aangemeldeSpelers de <code>List<Speler></code> van deelnemende
	 *                          spelers<br>
	 * 
	 * @throws IllegalArgumentException als de lijst <code>null</code> is, of als
	 *                                  aantal spelers niet klopt
	 */
	private void controleerAantalSpelers(List<Speler> aangemeldeSpelers) {
		if (aangemeldeSpelers == null)
			throw new IllegalArgumentException(Taal.getString("spelControleerAantalSpelersNullExceptionMsg"));
		if (aangemeldeSpelers.size() < MIN_AANTAL_SPELERS || aangemeldeSpelers.size() > MAX_AANTAL_SPELERS)
			throw new IllegalArgumentException(
					String.format("%s: %d", Taal.getString("spelControleerAantalSpelersInvalidPlayerCountExceptionMsg"),
							aangemeldeSpelers.size()));
	}

	/**
	 * controleert of de <code>Spel</code> <code>Constructor</code> een geldige
	 * lijst<br>
	 * van lijsten van ontwikkelingskaarten ontvangt<br>
	 * 
	 * @param ontwikkelingsKaarten de <code>List</code> van
	 *                             <code>Ontwikkelingskaart</code> lijsten van
	 *                             niveau1, niveau2 en niveau3
	 * 
	 * @throws IllegalArgumentException als de parameter null is, als één van de
	 *                                  lijsten in de lijst in parameter null zijn
	 *                                  <br>
	 *                                  of als een <code>Ontwikkelingskaart</code>
	 *                                  null is, of als de aantal lijsten != 3, of
	 *                                  als er een duplicate lijst is
	 */
	private void controleerOntwikkelingsKaartLijsten(List<List<Ontwikkelingskaart>> ontwikkelingsKaarten) {
		if (ontwikkelingsKaarten == null)
			throw new IllegalArgumentException(
					(Taal.getString("spelControleerOntwikkelingsKaartLijstenOuterListNullExceptionMsg")));
		if (ontwikkelingsKaarten.size() != 3)
			throw new IllegalArgumentException(
					(Taal.getString("spelControleerOntwikkelingsKaartLijstenSize3ExceptionMsg")));
		ontwikkelingsKaarten.forEach((lijst) -> {
			if (lijst == null)
				throw new IllegalArgumentException(
						(Taal.getString("spelControleerOntwikkelingsKaartLijstenInnerListNullExceptionMsg")));
			lijst.forEach((kaart) -> {
				if (kaart == null)
					throw new IllegalArgumentException(
							(Taal.getString("spelControleerOntwikkelingsKaartLijstenCardNullExceptionMsg")));
			});
		});
		// check op duplicate lijsten -> N1/N2/N3 - lists hebben een unieke size()
		if (ontwikkelingsKaarten.get(0).size() == ontwikkelingsKaarten.get(1).size()
				|| ontwikkelingsKaarten.get(0).size() == ontwikkelingsKaarten.get(2).size()
				|| ontwikkelingsKaarten.get(1).size() == ontwikkelingsKaarten.get(2).size())
			throw new IllegalArgumentException(
					(Taal.getString("spelControleerOntwikkelingsKaartLijstenDuplicateListExceptionMsg")));
	}

	/**
	 * DR_SPEL_NIEUW aantal edelen voor een spel = aantal spelers + 1
	 * 
	 * @param edelen de <code>List</code> van <code>Edelen</code>
	 * 
	 * @throws IllegalArgumentException als parameter = null of aantal edelen niet
	 *                                  klopt
	 */
	private void controleerEdelenLijst(List<Edele> edelen) {
		if (edelen == null)
			throw new IllegalArgumentException((Taal.getString("spelControleerEdelenLijstNullExceptionMsg")));
		if (edelen.size() != this.getAantalSpelers() + 1)
			throw new IllegalArgumentException((Taal.getString("spelControleerEdelenLijstSizeExceptionMsg")));
	}

	/**
	 * DR_SPEL_NIEUW aantal fiches per stapel: <br>
	 * --> spel met 2 spelers heeft 4 fiches per stapel <br>
	 * --> spel met 3 spelers heeft 5 fiches per stapel <br>
	 * --> spel met 4 spelers heeft 7 fiches per stapel <br>
	 * 
	 * @param ficheStapels de <code>HashMap</code> die per <code>Kleur</code> het
	 *                     <code>aantal fiches</code> bij houd
	 * 
	 * @throws IllegalArgumentException als de <code>HashMap</code> =
	 *                                  <code>null</code><br>
	 *                                  als de size() van ficheStapels != 5 <br>
	 *                                  als de aantal fiches per stapel niet klopt
	 *                                  <br>
	 *                                  of als een stapel = null, of als een stapel
	 *                                  0 fiches heeft
	 */
	private void controleerFicheStapels(HashMap<Kleur, Integer> ficheStapels) {
		int aantalFichesPerStapel;

		if (ficheStapels == null)
			throw new IllegalArgumentException((Taal.getString("spelControleerFicheStapelsStackListNullExceptionMsg")));

		if (ficheStapels.size() != 5)
			throw new IllegalArgumentException((Taal.getString("spelControleerFicheStapelsStackListSizeExceptionMsg")));

		switch (aangemeldeSpelers.size()) {
		case 2 -> aantalFichesPerStapel = 4;
		case 3 -> aantalFichesPerStapel = 5;
		case 4 -> aantalFichesPerStapel = 7;
		default -> throw new IllegalArgumentException(
				String.format("%s %s, Unexpected value: ", Taal.getString("errorIn"), this.getClass())
						+ aangemeldeSpelers.size());
		}

		ficheStapels.forEach((kleur, aantalFiches) -> {
			if (aantalFiches == null || aantalFiches == 0) {
				throw new IllegalArgumentException(
						(Taal.getString("spelControleerFicheStapelsNullOrEmptyStackExceptionMsg")));
			}

			if (aantalFiches != aantalFichesPerStapel) {
				throw new IllegalArgumentException(
						(Taal.getString("spelControleerFicheStapelsInvalidStackSizeExceptionMsg")));
			}
		});
	}

	/**
	 * geeft de huidige Speler terug die aan de beurt is
	 * 
	 * @return de <code>Speler</code> aan de beurt
	 */
	public Speler getSpelerAanBeurt() {
		return spelerAanBeurt;
	}

	/**
	 * DR_SPEL_EINDE: Een of meerdere Spelers heeft 15 of meer prestige punten
	 * 
	 * @return <code>eindeSpel</code> -> <code>true</code> als DR_SPEL_EINDE is
	 *         bereikt, anders <code>false</code>
	 */
	public boolean isEindeSpel() {
		return this.eindeSpel;
	}

	/**
	 * geeft de array van zichtbare Ontwikkelingskaarten van Niveau 1
	 * 
	 * @return de <code>array</code> zichtbare Niveau 1 Ontwikkelingskaarten
	 */
	public Ontwikkelingskaart[] getNiveau1Zichtbaar() {
		return this.niveau1Zichtbaar;
	}

	/**
	 * geeft de array van zichtbare Ontwikkelingskaarten van Niveau 2
	 * 
	 * @return <code>array</code> zichtbare Niveau 2 Ontwikkelingskaarten
	 */
	public Ontwikkelingskaart[] getNiveau2Zichtbaar() {
		return this.niveau2Zichtbaar;
	}

	/**
	 * geeft de array van zichtbare Ontwikkelingskaarten van Niveau 3
	 * 
	 * @return <code>array</code> zichtbare Niveau 3 Ontwikkelingskaarten
	 */
	public Ontwikkelingskaart[] getNiveau3Zichtbaar() {
		return this.niveau3Zichtbaar;
	}

	/**
	 * geeft de lijst met Edelen terug
	 * 
	 * @return <code>List</code> van Edelen
	 */
	public List<Edele> getEdelen() {
		return this.edelen;
	}

	/**
	 * geeft de EdelsteenFiche stapels in Spel terug als HashMap
	 * 
	 * @return <code>HashMap{@literal<}Kleur, Integer{@literal>}</code> die de fiche
	 *         stapels voorstelt
	 */
	public HashMap<Kleur, Integer> getFicheStapels() {
		return this.ficheStapels;
	}

	/**
	 * 1. -<code>spelerAanBeurt</code>.aanDeBeurt wordt op <code>false</code> gezet
	 * <br>
	 * <br>
	 * 2. -<code>spelerAanBeurt</code> wordt de volgende speler in de lijst<br>
	 * of de 1ste speler in de lijst indien<br>
	 * de <code>spelerAanBeurt</code> de laatste in de lijst was <br>
	 * <br>
	 * 3. -de volgende(nu huidige) <code>spelerAanBeurt</code>.aanDeBeurt wordt op
	 * <code>true</code> gezet
	 */
	public void volgendeSpeler() {
		// potentieel om huidigespeler == aan de beurt te vervangen door nieuwe speler
		// die ook == aan de beurt
		if (spelerAanBeurt.isAanDeBeurt())
			spelerAanBeurt.setAanDeBeurt(false);

		int indexHuidigeSpeler = aangemeldeSpelers.indexOf(spelerAanBeurt);

		// index laatste speler in lijst
		int maxIndex = aangemeldeSpelers.size() - 1;

		// als speler == laatste in de lijst -> volgende speler = 1ste in de lijst
		int indexVolgendeSpeler = indexHuidigeSpeler == maxIndex ? 0 : indexHuidigeSpeler + 1;

		this.spelerAanBeurt = aangemeldeSpelers.get(indexVolgendeSpeler);

		// volgende speler is aandebeurt, wordt ook in die speler bijgehouden,
		// een speler aandebeurt wordt false, na successvolle afronden van
		// spelmethodes(fiches/o-kaarten nemen)
		spelerAanBeurt.setAanDeBeurt(true);

		if (spelerAanBeurt.getStartSpeler())
			verhoogRonde();
	}

	/**
	 * de <code>Speler</code> kiest een <code>Ontwikkelingskaart</code> om te
	 * nemen<br>
	 * indien aan de voorwaarden voldaan zijn:<br>
	 * -> speler bezit genoeg edelsteenfiches en/of kleurbonussen<br>
	 * -> gekozen parameters liggen in de verwachte range<br>
	 * <br>
	 * <code>Speler</code> betaalt de nodige edelsteenfiches, die worden weer aan
	 * <code>Spel</code> toegevoegd <br>
	 * <code>Speler</code> krijgt de gekozen <code>Ontwikkelingskaart</code><br>
	 * <code>Speler</code> prestige punten worden verhoogt met de punten van de
	 * gekozen <code>Ontwikkelingskaart</code><br>
	 * <code>array</code> van zichtbare kaarten wordt bijgevuld -> zie methode:
	 * <code>vulKaartenBij()</code>
	 * 
	 * @param niveau  : 1-3 (het niveau van Ontwikkelingskaart)
	 * @param positie : 1-4 (de positie in de array van zichtbare kaarten)(-1 voor
	 *                index) <br>
	 * 
	 * @throws IllegalArgumentException als <code>niveau</code> niet in range[1-3]
	 *                                  ligt<br>
	 *                                  of <code>positie</code> niet in range[1-4]
	 *                                  ligt<br>
	 *                                  of als gekozen
	 *                                  <code>Ontwikkelingskaart</code> =
	 *                                  <code>null</code><br>
	 * @throws RuntimeException         als de speler niet genoeg <br>
	 *                                  edelsteenfiches en/of kleurbonussen
	 *                                  bezit<br>
	 *                                  om de gekozen
	 *                                  <code>Ontwikkelingskaart</code> te kopen
	 */
	public void kiesOntwikkelingskaart(int niveau, int positie) {
		if (positie < 1 || positie > 4)
			throw new IllegalArgumentException(
					(Taal.getString("spelKiesOntwikkelingskaartPositionOutOfBoundsExceptionMsg")));
		if (niveau < 1 || niveau > 3)
			throw new IllegalArgumentException(
					(Taal.getString("spelKiesOntwikkelingskaartLevelOutOfBoundsExceptionMsg")));

		Ontwikkelingskaart gekozenOntwikkelingskaart = null;

		Ontwikkelingskaart[][] niveauZichtbaar = { niveau1Zichtbaar, niveau2Zichtbaar, niveau3Zichtbaar };
		gekozenOntwikkelingskaart = niveauZichtbaar[niveau - 1][positie - 1];

		if (gekozenOntwikkelingskaart == null) {
			throw new IllegalArgumentException((Taal.getString("spelKiesOntwikkelingskaartCardNullExceptionMsg")));
		}

		// Kijken of de speler genoeg fiches en/of ontwikkelingskaarten reeds in hand
		// heeft om deze kaart te kopen
		if (!kanKaartKopen(gekozenOntwikkelingskaart)) {
			throw new RuntimeException((String.format("%n%s.%n",
					Taal.getString("spelKiesOntwikkelingskaartFailCanBuyCheckExceptionMsg"))));
		}

		// Verwijder fiches uit voorraad speler
		// Terug toevoegen aan fichestapel
		verwijderFichesVanSpelerEnVoegToeAanSpel(gekozenOntwikkelingskaart);

		// Verwijder kaart van de zichtbare kaarten
		niveauZichtbaar[niveau - 1][positie - 1] = null;

		// Voeg de kaart toe aan de voorraad van de speler
		spelerAanBeurt.voegOntwikkelingskaartToeAanHand(gekozenOntwikkelingskaart);

		// Voeg de prestigepunten van de ontwikkelingskaart toe aan de speler zijn
		// punten
		spelerAanBeurt.voegPuntenToe(gekozenOntwikkelingskaart.getPrestigepunten());

		// zichtbare kaarten stapel worden bijgevuld
		vulKaartenBij();
	}

	/**
	 * controleert of <code>spelerAanBeurt</code> gekozen
	 * <code>Ontwikkelingskaart</code> kan kopen<br>
	 * volgens DR_BEURT_KOOP_KAART<br>
	 * 
	 * @param gekozenOntwikkelingskaart gekozen kaart uit methode:
	 *                                  <code>kiesOntwikkelingskaart(int niveau, int positie)</code>
	 * @return <code>true</code> als <code>spelerAanBeurt</code> genoeg fiches en/of
	 *         kleurbonussen <br>
	 *         heeft om de gekozen <code>Ontwikkelingskaart</code> te kopen, anders
	 *         <code>false</code>
	 */
	public boolean kanKaartKopen(Ontwikkelingskaart gekozenOntwikkelingskaart) {
		if (gekozenOntwikkelingskaart == null)
			throw new IllegalArgumentException((Taal.getString("spelKanKaartKopenCardNullExceptionMsg")));

		int[] somAantalPerKleurInBezit = somAantalPerKleurInBezit();
		int[] kosten = gekozenOntwikkelingskaart.getKosten();

		boolean kaartKoopbaar = false;

		for (int i = 0; i < kosten.length; i++) {
			if (somAantalPerKleurInBezit[i] >= kosten[i]) {
				kaartKoopbaar = true;
			} else {
				kaartKoopbaar = false;
				break;
			}
		}

		return kaartKoopbaar;
	}

	/**
	 * punten per kleur van <code>spelerAanBeurt</code><br>
	 * (punten: aantal fiches + kleurbonus)
	 * 
	 * @return <code>array</code> van <code>int</code> van lengte 5<br>
	 *         op elke index de som van edelsteenfiches + kleurbonus van
	 *         <code>spelerAanBeurt</code><br>
	 *         <br>
	 *         [witte-punten, rode-punten, blauwe-punten, groene-punten,
	 *         zwarte-punten]
	 */
	public int[] somAantalPerKleurInBezit() {
		int[] somAantalPerKleurInBezit = new int[5];
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		HashMap<Kleur, Integer> edelsteenfichesInHand = spelerAanBeurt.getEdelsteenfichesInHand();

		for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
			somAantalPerKleurInBezit[ontwk.getKleurBonus().getKleur()]++;
		}

		for (Kleur kleur : Kleur.values()) {
			// haal aantal fiches op uit de hashmap
			int aantalFiches = 0;
			Integer aantalFichesUitMap = edelsteenfichesInHand.get(kleur);

			// controleert of de hashmap een null waarde retourneert
			if (aantalFichesUitMap != null) {
				aantalFiches = aantalFichesUitMap.intValue();
			}

			somAantalPerKleurInBezit[kleur.getKleur()] += aantalFiches;
		}

		return somAantalPerKleurInBezit;
	}

	/**
	 * vermindert de voorraad edelsteenfiches van <code>spelerAanBeurt</code><br>
	 * volgens de <code>kosten</code> van de gekozen
	 * <code>Ontwikkelingskaart</code><br>
	 * en voegt deze toe aan de voorraad edelsteenfiches van <code>Spel</code>
	 * 
	 * @param gekozenOntwikkelingskaart gekozen kaart uit methode:
	 *                                  <code>kiesOntwikkelingskaart(int niveau, int positie)</code><br>
	 */
	private void verwijderFichesVanSpelerEnVoegToeAanSpel(Ontwikkelingskaart gekozenOntwikkelingskaart) {
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] wegTeNemenFichesUitVoorraad = gekozenOntwikkelingskaart.getKosten();

		for (Ontwikkelingskaart ontwk : ontwikkelingskaartenInHand) {
			wegTeNemenFichesUitVoorraad[ontwk.getKleurBonus().getKleur()]--;
		}

		for (Kleur kleur : Kleur.values()) {
			for (int j = 0; j < wegTeNemenFichesUitVoorraad[kleur.getKleur()]; j++) {
				// Terug toevoegen van de fiche aan de stapel
				voegFicheToe(kleur);
				// verwijderen van fiches uit de speler zjin voorraad
				spelerAanBeurt.verwijderEdelsteenfiche(kleur);
			}
		}
	}

	/**
	 * verhoogt de waarde bij de gekozen <code>Kleur</code> in
	 * <code>HashMap{@literal<}Kleur, Integer{@literal>}</code> <code>ficheStapels</code>
	 * van <code>Spel</code>
	 * 
	 * @param kleur gekozen edelsteenfiche(Kleur)<br>
	 * @throws IllegalArgumentException als parameter <code>kleur</code> = null
	 */
	private void voegFicheToe(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException((Taal.getString("spelFicheColourNullExceptionMsg")));

		Integer currentValue = ficheStapels.get(kleur);
		if (currentValue != null) {
			ficheStapels.put(kleur, currentValue + 1);
		} else {
			ficheStapels.put(kleur, 1);
		}
	}

	/**
	 * vermindert de <code>value</code> van de <code>key</code> <code>Kleur</code>
	 * in <br>
	 * <code>HashMap{@literal<}Kleur, Integer{@literal>}</code> <code>ficheStapels</code><br>
	 * van <code>Spel</code> met 1 als die groter is dan 1<br>
	 * anders verwijdert de <code>key</code>-<code>value</code> paar uit de
	 * <code>HashMap</code>
	 * 
	 * @param kleur gekozen edelsteenfiche(Kleur)<br>
	 * @throws IllegalArgumentException als parameter <code>kleur</code> = null
	 */
	private void verwijderFiche(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException((Taal.getString("spelFicheColourNullExceptionMsg")));

		int currentValue = ficheStapels.get(kleur);
		if (currentValue - 1 > 0) {
			ficheStapels.put(kleur, currentValue - 1);
		} else {
			ficheStapels.remove(kleur);
		}
	}

	/**
	 * geeft de som van edelsteenfiches in spel terug
	 * 
	 * @return <code>int</code> totaal aantal fiches in <code>ficheStapels</code>
	 */
	public int totaalAantalfiches() {
		int sum = 0;
		for (int value : ficheStapels.values()) {
			sum += value;
		}
		return sum;
	}

	/**
	 * overloopt de lijsten van zichtbare kaarten per niveau<br>
	 * als de positie null is, neemt een kaart uit de trekstapel en voegt deze <br>
	 * toe op de lege positie:<br>
	 * <br>
	 * 
	 * <code>niveau1Zichtbaar</code> wordt bijgevuld uit de lijst
	 * <code>n1</code><br>
	 * <code>niveau2Zichtbaar</code> wordt bijgevuld uit de lijst
	 * <code>n2</code><br>
	 * <code>niveau3Zichtbaar</code> wordt bijgevuld uit de lijst
	 * <code>n3</code><br>
	 */
	public void vulKaartenBij() {
		// 1x itereren over 1 van de lijsten, ze zijn allemaal even lang
		for (int i = 0; i < niveau1Zichtbaar.length; i++) {
			// n1.size() - 1 is de laatste kaart, verwijder ze uit trekstapel en zet ze op
			// het bord:
			if (niveau1Zichtbaar[i] == null && n1.size() > 0)
				niveau1Zichtbaar[i] = n1.remove(n1.size() - 1);
			if (niveau2Zichtbaar[i] == null && n2.size() > 0)
				niveau2Zichtbaar[i] = n2.remove(n2.size() - 1);
			if (niveau3Zichtbaar[i] == null && n3.size() > 0)
				niveau3Zichtbaar[i] = n3.remove(n3.size() - 1);
		}
	}

	/**
	 * DR_BEURT_ACTIE,<br>
	 * DR_BEURT_AANTAL_FICHES : <code>Speler</code> neemt 3 fiches, elk van een
	 * verschillend <code>Kleur</code><br>
	 * <code>spelerAanBeurt</code> neemt 3 fiches:<br>
	 * -> voegt de 3 fiches toe aan de fiches van <code>spelerAanBeurt</code><br>
	 * -> vermindert de <code>ficheStapels</code> in <code>Spel</code>
	 * 
	 * @param kleuren <code>array</code> van 3 gekozen <code>Kleuren</code> <br>
	 * @throws IllegalArgumentException als parameter <code>kleuren</code> =
	 *                                  null<br>
	 *                                  of als aantal unieke Kleuren != 3<br>
	 *                                  of als een <code>Kleur</code> = null in de
	 *                                  parameter <code>array</code>
	 *                                  <code>kleuren</code><br>
	 *                                  of als een van de <code>values</code> van
	 *                                  <code>Kleuren</code> = 0<br>
	 *                                  <br>
	 * 
	 *                                  (index [0-4, 0-4, 0-4])
	 */
	public void neemDrieFiches(Kleur[] kleuren) {
		if (kleuren == null)
			throw new IllegalArgumentException((Taal.getString("spelNeemDrieFichesEmptyListItemExceptionMsg")));

		Set<Kleur> kleurenSet = new HashSet<>(Arrays.asList(kleuren));
		if (kleurenSet.size() < kleuren.length) {
			throw new IllegalArgumentException(
					String.format("%n%s.%n%s%n", Taal.getString("spelNeemDrieFichesDuplicateStackExceptionMsgPart1"),
							Taal.getString("spelNeemDrieFichesDuplicateStackExceptionMsgPart2")));
		}

		for (Kleur kleur : kleuren) {
			if (kleur == null)
				throw new IllegalArgumentException(
						String.format("%n%s%n", Taal.getString("spelNeemDrieFichesEmptyStackExceptionMsg")));

			// wanneer een stapel geen fiches bevat
			if (ficheStapels.get(kleur) == null || ficheStapels.get(kleur) <= 0) {
				throw new RuntimeException(
						String.format("%n%s%n", Taal.getString("spelNeemDrieFichesEmptyStackExceptionMsg2")));
			}
		}

		// verplaats de edelsteenfiches van spel voorraad naar speler voorraad
		for (Kleur kleur : kleuren) {
			verwijderFiche(kleur);
			spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
		}

	}

	/**
	 * DR_BEURT_ACTIE,<br>
	 * DR_BEURT_AANTAL_FICHES : <code>Speler</code> neemt 2 fiches, beiden moeten
	 * van dezelfde <code>Kleur</code> zijn<br>
	 * <code>spelerAanBeurt</code> neemt 2 fiches:<br>
	 * -> voegt de 2 fiches toe aan de fiches van <code>spelerAanBeurt</code><br>
	 * -> vermindert de <code>ficheStapels</code> in <code>Spel</code>
	 * 
	 * @param kleur de gekozen <code>Kleur</code><br>
	 * @throws IllegalArgumentException als parameter <code>kleur</code> = null<br>
	 *                                  of als <code>ficheStapel</code> van gekozen
	 *                                  <code>Kleur</code> in <code>Spel</code><br>
	 *                                  niet bestaat(een lege stapel)<br>
	 *                                  of als in gekozen stapel het resterende
	 *                                  aantal fiches {@literal<} 4<br>
	 *                                  <br>
	 * 
	 *                                  (index 0-4)
	 */
	public void neemTweeFiches(Kleur kleur) {
		if (kleur == null)
			throw new IllegalArgumentException((Taal.getString("spelFicheColourNullExceptionMsg")));

		// controleren of de gekozen stapel om van te nemen nog bestaat
		if (ficheStapels.get(kleur) == null) {
			throw new IllegalArgumentException(
					String.format("%n%s.%n", Taal.getString("spelNeemTweeFichesEmptyStackExceptionMsg")));
		}

		// controleren of het mogelijk is om 2 te verwijderen uit gekozen kleur stapel
		if (ficheStapels.get(kleur) < 4) {
			throw new IllegalArgumentException(
					String.format("%n%s.%n", Taal.getString("spelNeemTweeFichesLessThanFourExceptionMsg")));
		}

		// 2 verwijderen uit stapel van spel
		verwijderFiche(kleur);
		verwijderFiche(kleur);

		// 2 toevoegen aan speler zijn stapel
		spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
		spelerAanBeurt.voegEdelsteenficheToeAanHand(kleur);
	}

	/**
	 * geeft het aantal stapels terug die meer dan 0 fiches bevatten
	 * 
	 * @return <code>int</code> aantal stapels die meer dan 0 fiches bevatten
	 */
	public int aantalStapelsMeerDanNul() {
		int aantalStapelsMeerDanNul = 0;

		for (Integer aantalFiches : ficheStapels.values()) {
			if (aantalFiches > 0)
				aantalStapelsMeerDanNul++;
		}

		return aantalStapelsMeerDanNul;
	}

	/**
	 * is er een stapel met 4 fiches of meer dan 4 fiches
	 * 
	 * @return <code>boolean </code> <code>true</code> als één of meer stapels <br>
	 *         bestaan met aantalfiches >= 4, anders <code>false</code>
	 * 
	 */
	public boolean stapelVierOfMeerFichesAanwezig() {
		for (Integer aantalFiches : ficheStapels.values()) {
			if (aantalFiches >= 4)
				return true;
		}

		return false;
	}

	/**
	 * geeft het aantal spelers
	 * 
	 * @return <code>int</code> aantal <code>Spelers</code><br>
	 *         in de <code>List</code> van <code>aangemeldeSpelers</code>
	 */
	public int getAantalSpelers() {
		return aangemeldeSpelers.size();
	}

	/**
	 * geeft de lijst van aangemelde spelers
	 * 
	 * @return de <code>List</code> van <code>aangemeldeSpelers</code>
	 */
	public List<Speler> getAangemeldeSpelers() {
		return aangemeldeSpelers;
	}

	/**
	 * geeft een lijst van spelvoorwerpen terug, dit zijn: <code>Edele</code><br>
	 * of <code>Ontwikkelingskaart</code> van <code>Tag Interface</code><br>
	 * <code>Spelvoorwerp</code>
	 * 
	 * @return <code>List</code> van <code>Spelvoorwerpen</code>
	 */
	public List<SpelVoorwerp> geefSpelVoorwerpen() {
		List<SpelVoorwerp> speelbord = new ArrayList<>();

		for (Edele edele : this.edelen)
			speelbord.add(edele);

		for (int i = 0; i < niveau1Zichtbaar.length; i++) {
			speelbord.add(niveau1Zichtbaar[i]);
			speelbord.add(niveau2Zichtbaar[i]);
			speelbord.add(niveau3Zichtbaar[i]);
		}

		return speelbord;
	}

	/**
	 * geeft een array met aantal kaarten resterend in de trekstapels per niveau<br>
	 * - [0]: niveau1<br>
	 * - [1]: niveau2<br>
	 * - [2]: niveau3<br>
	 * 
	 * @return <code>int</code>{@literal []} de <code>array</code> representatie
	 *         <br>
	 *         van het aantal kaarten resterend per niveau
	 */
	public int[] aantalKaartenResterend() {
		int[] rest = new int[3];
		rest[0] = n1.size();
		rest[1] = n2.size();
		rest[2] = n3.size();
		return rest;
	}

	/**
	 * de Speler kiest optie [1, 2, 3, 4, 5] die de gekozen Kleur-stapel<br>
	 * voorstelt van de <code>ficheStapels</code><br>
	 * 
	 * @param stapelKeuze de index(zero based) van de <code>Kleur</code> in
	 *                    <code>Kleur.values()</code><br>
	 *                    verwacht: de Speler keuze van stapel - 1 (mapping
	 *                    speler-keuze naar Kleur-index)
	 */
	public void plaatsTerugInStapel(int stapelKeuze) {
		// verwijder fiche bij speler
		spelerAanBeurt.verwijderEdelsteenfiche(Kleur.valueOf(stapelKeuze));

		// voeg fiche toe aan bord
		voegFicheToe(Kleur.valueOf(stapelKeuze));
	}

	/**
	 * geeft de textuele weergave van de fiche stapels die dan uitgeprint kunnen
	 * worden
	 * 
	 * @return de <code>String</code> representatie van de fiches (voor CUI)
	 */
	public String toonFiches() {
		String representatieFiches = "";

		if (ficheStapels.size() > 0) {
			for (Kleur kleur : Kleur.values()) {
				Integer aantalFiches = ficheStapels.get(kleur);
				representatieFiches += String.format("%-6s %d%n", Taal.getString(kleur.toString()) + ":",
						aantalFiches != null ? aantalFiches : 0);
			}
		}

		return representatieFiches.isBlank() ? Taal.getString("spelToonFichesAllStacksEmptyMsg") : representatieFiches;
	}

	/**
	 * DR_BEURT_SPECIALE_TEGEL: <br>
	 * Als de <code>Speler</code> genoeg kleurbonussen heeft krijgt die automatisch
	 * de <code>Edele</code>
	 */
	public void krijgEdele() {
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] aantalOntwikkelingskaartKleurBonus = new int[Kleur.values().length];

		for (Ontwikkelingskaart ontwikkelingskaart : ontwikkelingskaartenInHand) {
			Kleur kleurBonus = ontwikkelingskaart.getKleurBonus();
			aantalOntwikkelingskaartKleurBonus[kleurBonus.ordinal()]++;
		}

		// controle of de speler de edele kan krijgen
		for (Edele edele : edelen) {
			boolean kanEdeleKopen = true;
			int[] kosten = edele.getKosten();

			for (int kleurIndex = 0; kleurIndex < kosten.length; kleurIndex++) {
				if (kosten[kleurIndex] > aantalOntwikkelingskaartKleurBonus[kleurIndex]) {
					kanEdeleKopen = false;
					break;
				}
			}

			if (kanEdeleKopen) {
				kiesEdele(edele);
				break;
			}
		}
	}

	// TODO javadocs krijgEdeleGUI
	public List<Edele> krijgEdeleGui() {
		List<Edele> verkrijgbareEdelen = new ArrayList<>();
		List<Ontwikkelingskaart> ontwikkelingskaartenInHand = spelerAanBeurt.getOntwikkelingskaartenInHand();
		int[] aantalOntwikkelingskaartKleurBonus = new int[Kleur.values().length];

		for (Ontwikkelingskaart ontwikkelingskaart : ontwikkelingskaartenInHand) {
			Kleur kleurBonus = ontwikkelingskaart.getKleurBonus();
			aantalOntwikkelingskaartKleurBonus[kleurBonus.ordinal()]++;
		}

		// controle of de speler de edele kan krijgen
		for (Edele edele : edelen) {
			boolean kanEdeleKopen = true;
			int[] kosten = edele.getKosten();

			for (int kleurIndex = 0; kleurIndex < kosten.length; kleurIndex++) {
				if (kosten[kleurIndex] > aantalOntwikkelingskaartKleurBonus[kleurIndex]) {
					kanEdeleKopen = false;
					break;
				}
			}

			if (kanEdeleKopen) {
				verkrijgbareEdelen.add(edele);
			}
		}

		return verkrijgbareEdelen;
	}

	// TODO kiesEdeleGUI javadocs
	public void kiesEdele(Edele edele) {
		// voeg prestigepunten toe van edele aan speler zijn totaal
		spelerAanBeurt.voegPuntenToe(edele.getPrestigepunten());

		// voeg de edele toe als de speler het juist aantal ontwikkelingskaarten heeft
		spelerAanBeurt.voegEdeleToeAanHand(edele);

		// verwijder de edele uit de lijst van spel
		edelen.removeIf(e -> e.equals(edele));
	}

	// TODO javadocs updaten
	/**
	 * DR_SPEL_WINNAAR<br>
	 * deze lijst stelt de Winnaars van het spel voor:<br>
	 * de spelers met het hoogste aantal prestige punten(>=15)<br>
	 * bij gelijke prestige: de speler met de laagste aantal<br>
	 * ontwikkelingskaarten<br>
	 * bij gelijke ontwikkelingskaarten: meerdere winnaars
	 * 
	 * @return een <code>List</code> van <code>Spelers</code><br>
	 *         die voldoen aan DR_SPEL_WINNAAR
	 */
	public List<Speler> bepaalWinnaar() {
		List<Speler> potentieleWinnaars = new ArrayList<>();
		List<Speler> winnaars = new ArrayList<>();

		boolean hasWinner = aangemeldeSpelers.stream().anyMatch(speler -> speler.getPrestigepunten() >= 15);

		if (hasWinner) {
			int hoogstePrestigepunten = 0;
			int laagsteOntwKaartenCount = Integer.MAX_VALUE;

			for (Speler speler : aangemeldeSpelers) {
				int prestigepunten = speler.getPrestigepunten();

				if (prestigepunten >= 15) {
					eindeSpel = true;
					potentieleWinnaars.add(speler);

					if (prestigepunten > hoogstePrestigepunten) {
						hoogstePrestigepunten = prestigepunten;
					}

					if (speler.getPrestigepunten() == hoogstePrestigepunten) {
						int aantalOKSpeler = speler.getOntwikkelingskaartenInHand().size();

						if (aantalOKSpeler < laagsteOntwKaartenCount) {
							laagsteOntwKaartenCount = aantalOKSpeler;
						}
					}
				}
			}

			for (Speler speler : potentieleWinnaars) {
				int aantalOKSpeler = speler.getOntwikkelingskaartenInHand().size();
				if (speler.getPrestigepunten() == hoogstePrestigepunten && aantalOKSpeler == laagsteOntwKaartenCount) {
					winnaars.add(speler);
				}
			}

		}
		return winnaars;

	}

	/**
	 * [TEST-METHODE] zijn de n1/n2/n3 stapels goed opgevuld met O-kaarten?
	 */
	private void testOntwikkelingskaartStapels() {
		System.out.println(Taal.getString("spelTestOntwikkelingskaartStapelsCardStacksMsg"));
		System.out.println(n1);
		System.out.println(n2);
		System.out.println(n3);
		System.out.println("***************************************************************************");
	}

	/**
	 * [DEMO-METHODE]<br>
	 * vergemakkelijkt het tonen van verdere spel verlopen:<br>
	 * vooral het kopen van Ontwikkelingskaarten
	 */
	public void testGeeftVeelEdelsteenfichesAanSpelers() {
		for (Speler speler : aangemeldeSpelers) {
			for (Kleur kleur : Kleur.values()) {
				for (int i = 0; i < 100; i++)
					speler.voegEdelsteenficheToeAanHand(kleur);
			}
		}
	}

	// [TEST] optie 7
	public void testGeeftOntwikkelingskaartenAanSpelerAanBeurt() {
		Ontwikkelingskaart ontwikkelingskaart = null;

		for (Speler speler : aangemeldeSpelers) {
			for (Kleur kleur : Kleur.values()) {
				for (int i = 1; i <= 10; i++) {
					if (i <= 2) {
						ontwikkelingskaart = new Ontwikkelingskaart(i, 0, kleur,
								"src/resources/img/background_misc/splendor-icon.png", new int[] { 0, 0, 0, 0, 0 });
						speler.voegOntwikkelingskaartToeAanHand(ontwikkelingskaart);
					}
					speler.voegEdelsteenficheToeAanHand(kleur);
				}
			}
		}
	}

	/**
	 * [DEMO-METHODE]<br>
	 * simulatie van een spel-einde
	 */
	public void testMaaktWinnaarAan() {
		Random random = new Random();

		for (Speler speler : aangemeldeSpelers) {
			int randomWaarde = random.nextInt(3) + 1;
			speler.voegPuntenToe(15 + randomWaarde);
		}
	}

	// [TEST] optie 8
	public void testMaaktEenWinnaarAan() {
		Random rand = new Random();
		for (int i = 0; i < aangemeldeSpelers.size(); i++) {
			int randomWaarde1_15 = rand.nextInt(14) + 1;
			aangemeldeSpelers.get(i).voegPuntenToe(randomWaarde1_15);
		}
		int index = rand.nextInt(4);
		aangemeldeSpelers.get(index).voegPuntenToe(4);

	}

	// [TEST] Optie 9
	public void testGeeftEvenVeelWinnendePrestigepuntenMaarVerschillendAantalOntwikkelinkgskaarten() {
		Ontwikkelingskaart ontwikkelingskaart = null;

		for (Speler speler : aangemeldeSpelers) {
			speler.voegPuntenToe(15);
			if (speler.isAanDeBeurt()) {
				for (Kleur kleur : Kleur.values()) {
					for (int i = 1; i <= 2; i++) {
						ontwikkelingskaart = new Ontwikkelingskaart(i, 0, kleur,
								"src/resources/img/background_misc/splendor-icon.png", new int[] { 0, 0, 0, 0, 0 });
						speler.voegOntwikkelingskaartToeAanHand(ontwikkelingskaart);
					}
				}
			}
		}

	}

}