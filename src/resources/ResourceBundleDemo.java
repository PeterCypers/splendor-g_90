package resources;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleDemo {
	public static void main(String[] args) {
		Locale enUK = new Locale("en", "UK");
		Locale nlBE = new Locale("nl", "BE");
		Locale frBE = new Locale("fr", "BE");
		Locale deBE = new Locale("de", "BE");

		ResourceBundle resourceBundle_en_UK = ResourceBundle.getBundle("resource_en_UK.properties", enUK);
		ResourceBundle resourceBundle_nl_BE = ResourceBundle.getBundle("resource_nl_BE.properties", nlBE);
		ResourceBundle resourceBundle_fr_BE = ResourceBundle.getBundle("resource_fr_BE.properties", frBE);
		ResourceBundle resourceBundle_de_BE = ResourceBundle.getBundle("resource_de_BE.properties", deBE);

		String edelsteenfiches = resourceBundle_en_UK.getString("edelsteenfiches");
		System.out.println(edelsteenfiches);

		String message = resourceBundle_en_UK.getString("message");
		message = MessageFormat.format(message, "ABC", "DEF");
		System.out.println(message);
		// edelseenfiches=gems
		// message={0}test{2}
	}
}
