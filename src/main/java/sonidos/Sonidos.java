
package sonidos;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public interface Sonidos {
    
    static Media song = new Media(new File("sounds/background.mp3").toURI().toString());
    static MediaPlayer musicPlayer = new MediaPlayer(song);
    
    public static void playBackgroundMusic() {

        musicPlayer.setVolume(0.0);
        
        musicPlayer.setOnEndOfMedia(() -> {
            musicPlayer.seek(Duration.ZERO);
            musicPlayer.play();
        });

        musicPlayer.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(musicPlayer.volumeProperty(), 0.07)));
        timeline.play();
        
    }
    
    public static void pauseBackgroundMusic() {
        musicPlayer.pause();
    }
    
    public static void resumeBackgroundMusic() {
        musicPlayer.play();
    }
    
    private static void playSound(String archivo, double volume) {
        
        Media sound = new Media(new File("sounds/" + archivo).toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        
        player.setVolume(volume);
        player.play();
        
    }
    
    public static void playIASound() {
        playSound("ia.wav", 0.07);
    }
    
    public static void playHumanSound() {
        playSound("human.wav", 0.14);
    }
    
    public static void playClockSound() {
        playSound("clock.wav", 0.50);
    }
    
    public static void playTrophySound() {
        playSound("trophy.wav", 0.25);
    }
    
    public static void playLightSound() {
        playSound("light.wav", 0.30);
    }
    
    public static void playSlideSound() {
        playSound("slide.wav", 0.75);
    }
    
    public static void playSquareSound() {
        playSound("square.mp3", 0.75);
    }
    
    public static void playEndgameSound() {
        playSound("endgame.wav", 0.15);
    }
    
    public static void playWinSound() {
        playSound("win.wav", 0.15);
    }
    
}
