module splendorModule {
	exports persistentie;
	exports cui;
	exports main;
	exports domein;
	exports testen;
	exports dto;

	requires java.sql;
  
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.controls;
  
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
  
    //exports main to javafx.graphics; //auto-generated exports main -> duplicate
	exports main to javafx.graphics;
  
	//scenebuilder:
	//requires javafx.fxml;
	//opens gui to javafx.fxml;
}