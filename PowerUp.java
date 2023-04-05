package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PowerUp extends Rectangle {

    private double xPos;
    private double yPos;
    private String type;

    public PowerUp(double w, double h, double x, double y, Color clr, String powerup_type, Image img) {
        super(w, h, clr);
        xPos = x;
        yPos = y;
        setLayoutX(xPos);
        setLayoutY(yPos);
        type = powerup_type;
        setFill(new ImagePattern(img));

        DropShadow dropShadow = new DropShadow(10, Color.WHITE);
        dropShadow.setSpread(0.5);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(dropShadow.radiusProperty(), 11)),
                new KeyFrame(Duration.seconds(1), new KeyValue(dropShadow.colorProperty(), Color.rgb(255, 255, 255, 0.5))),
                new KeyFrame(Duration.seconds(2), new KeyValue(dropShadow.radiusProperty(), 13)),
                new KeyFrame(Duration.seconds(3), new KeyValue(dropShadow.colorProperty(), Color.rgb(255, 255, 255, 1))),
                new KeyFrame(Duration.seconds(4), new KeyValue(dropShadow.radiusProperty(), 11)),
                new KeyFrame(Duration.seconds(5), new KeyValue(dropShadow.colorProperty(), Color.rgb(255, 255, 255, 0.5)))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setEffect(dropShadow);
    }

    public String getType() {
        return type;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void update() {
        setLayoutX(xPos);
        setLayoutY(yPos);
    }
}