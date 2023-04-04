package application;

import javafx.scene.paint.Color;

import java.nio.file.Paths;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {
	
	public double xPos;
	public double yPos;
	private double HEIGHT;
	public double enemySpeed;
	@SuppressWarnings("exports")
	public Image enemyShipImage = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/alien1.png");
	
	public Enemy(double w, double h, double x, double y, double screen_height, String type) {
		super(w, h);
		HEIGHT = screen_height;
		xPos = x;
		yPos = y;
		enemySpeed = 1.5;
		setLayoutX(xPos);
		setLayoutY(yPos);
		setFill(new ImagePattern(enemyShipImage));
		DropShadow dropShadow = new DropShadow(10, Color.rgb(0, 0, 0, 0.4));
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setRadius(15);
        dropShadow.setSpread(0.15);
        setEffect(dropShadow);
	}
	
	public void update() {
	    yPos += enemySpeed;
	    setLayoutX(xPos);
	    setLayoutY(yPos);  
	    if (yPos > HEIGHT) {
	        yPos = -getHeight();
	        setLayoutY(yPos);
	    }
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public void render() {
		//TODO
	}
}
