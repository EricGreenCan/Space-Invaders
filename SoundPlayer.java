package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private Map<String, MediaPlayer> players;
    //public MediaPlayer mediaPlayer;

    public SoundPlayer() {
        players = new HashMap<>();
    }

    public void play(String fileName) {
        try {
        	MediaPlayer mediaPlayer = new MediaPlayer(new Media("file:///" + Paths.get("").toAbsolutePath().toString().replace("\\", "/") + fileName));
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
            });
            players.put(fileName, mediaPlayer);
        	mediaPlayer.play();
        }catch(Exception e) {
        	
        }
    }

    public void stop(String fileName) {
        MediaPlayer mediaPlayer = players.get(fileName);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void stopAll() {
        for (MediaPlayer mediaPlayer : players.values()) {
            mediaPlayer.stop();
        }
    }

}