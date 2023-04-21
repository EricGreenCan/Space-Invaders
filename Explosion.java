package application;

import java.io.File;
import java.nio.file.Paths;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Explosion extends ImageView {

    private int currentFrame = 0;

    private Image[] explosionImages = {
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_1.png"),
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_2.png"),
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_3.png"),
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_4.png"),
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_5.png"),
            new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/xplosion_6.png")
    };

    private Timeline timeline;

    public Explosion(double ix , double iy) {
        super();
        this.setViewport(new Rectangle2D(0, 0, 64, 64));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            currentFrame++;
            if(currentFrame <= 5) {
            	this.setImage(explosionImages[currentFrame]);
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        startAnimation(ix, iy+10);
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
        Media sound = new Media("file:///" + Paths.get("").toAbsolutePath().toString().replace("\\", "/") + "/Sounds/explosion.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
    }

    public void startAnimation(double x, double y) {
        this.setX(x);
        this.setY(y);
        timeline.play();
    }
    
    public boolean animationDone() {
    	return currentFrame > 5;
    }

    public void stopAnimation() {
        timeline.stop();
    }
}