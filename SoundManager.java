package application;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer mediaPlayer;

    public SoundManager() {
        Media sound = new Media(Paths.get("click2.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void playSound() {
        mediaPlayer.play();
    }

    public void stopSound() {
        mediaPlayer.stop();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }
}