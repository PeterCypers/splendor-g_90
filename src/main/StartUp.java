package main;

import domein.DomeinController;
import ui.SplendorApplicatie;

public class StartUp {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SplendorApplicatie(new DomeinController()).startSpel();
	}

}