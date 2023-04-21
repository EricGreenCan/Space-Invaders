module Game {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires javafx.media;
	opens application to javafx.graphics, javafx.fxml;
}
