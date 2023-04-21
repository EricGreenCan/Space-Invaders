package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyListener {

    private Scene scene;
    private boolean isPaused;
    public ObservableSet<KeyCode> downKeys;
    
    public KeyListener(Scene scene) {
        this.scene = scene;
        this.isPaused = false;
        downKeys = FXCollections.observableSet();
    }	

    public void start() {
 
	    scene.setOnKeyPressed(evt->{
            downKeys.add(evt.getCode());
        });

        scene.setOnKeyReleased(evt->{
            downKeys.remove(evt.getCode());
        });

        @SuppressWarnings("incomplete-switch")
		Timeline timer = new Timeline(new KeyFrame(
            javafx.util.Duration.millis(16), ae -> {
                downKeys.stream().parallel().forEach(kc -> {
                    Platform.runLater(() -> {
                        switch(kc){
                            case ESCAPE: setPauseTrue();
                                break;
                           
                        }
                    });
                });
            }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
   
    }
    
    public boolean isKeyPressed(String KEY) {
        KeyCode keyCode = KeyCode.valueOf(KEY);
        return downKeys.contains(keyCode);
    }
    
    public void setPauseTrue() {
        isPaused = true;
    }
    
    public void setPauseFalse() {
    	isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }
}