package resources;

import java.util.ResourceBundle;

public class Taal {
	private static ResourceBundle resource;

	public static ResourceBundle getResource() {
		return resource;
	}

	public static void setResource(ResourceBundle resource) {
		Taal.resource = resource;
	}

	public static String getString(String naam) {
		return resource.getString(naam);
	}
}
