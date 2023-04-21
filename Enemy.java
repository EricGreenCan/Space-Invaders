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
	public int e_type;
	public int fire_rate;
	public Image enemyShipImage;
	
	public Enemy(double w, double h, double x, double y, double screen_height, String type) {
		super(w, h);
		HEIGHT = screen_height;
		xPos = x;
		yPos = y;
		enemySpeed = 1.5;
		setLayoutX(xPos);
		setLayoutY(yPos);
        e_type = (int)(Math.random() *(2 - 0 + 1) + 0);
        if(e_type == 0) {
        	enemyShipImage = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/alien1.png");
        	fire_rate = 3;
        }else if (e_type == 1) {
        	enemyShipImage = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/blueealien.png");
        	fire_rate = 9;
        }else if (e_type == 2) {
        	enemyShipImage = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/orangealien.png"); 
        	fire_rate = 3;
        	enemySpeed = 2;
        }
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
