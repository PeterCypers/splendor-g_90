package main;

import cui.SplendorApplicatie;
import domein.DomeinController;

public class StartUpCui {

	public static void main(String[] args) {
		new SplendorApplicatie(new DomeinController()).startSpel();
	}

}