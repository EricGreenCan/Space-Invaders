package application;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Icon extends Rectangle {

    private double xPos;
    private double yPos;
    private String type;

    public Icon(double w, double h, double x, double y, Image img) {
        super(w, h);
        xPos = x;
        yPos = y;
        setLayoutX(xPos);
        setLayoutY(yPos);
        setFill(new ImagePattern(img));
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