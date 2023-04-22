module splendorModule {

	requires javafx.base;
	requires javafx.graphics;
	requires javafx.controls;
	
	requires java.sql;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;

	exports main to javafx.graphics;
}