package ui;

import java.time.LocalDate;

import domein.*;

public class SplendorApplicatie {

	private DomeinController dc;

	public void startSpel() {
		//test spelers toevoegen:
		String spelerNaamOk1 = "Speler1";
		String spelerNaamOk2 = "Speler 01";
		String spelerNaamOk3 = "Speler_naam 03";
		String empty = "";
		String startNietMetLetter = "8Speler 4";
		int leeftijdOk = LocalDate.now().getYear() - 8;
		int leeftijdNietOk = LocalDate.now().getYear() - 3;
		dc.registreerSpeler(startNietMetLetter, leeftijdOk);

	}

	/**
	 * 
	 * @param dc
	 */
	public SplendorApplicatie(DomeinController dc) {
		this.dc = dc;
	}

}