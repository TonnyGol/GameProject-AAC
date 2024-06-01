import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer extends Thread {
    private Clip clip;
    private FloatControl volumeControl;

    public MusicPlayer(){
        try {
            // Open an audio input stream.
            File soundFile = new File("resources\\AudioFiles\\menuSound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource.
            clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);

            // Get the volume control from the clip.
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Start playing the music.
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the clip continuously.
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    // Method to stop the music
    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
    // Method to set the volume (volume should be between 0.0 and 1.0)
    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newVolume = min + (max - min) * volume;
            volumeControl.setValue(newVolume);
        }
    }
    // Method to get the current volume (returns a value between 0.0 and 1.0)
    public float getVolume() {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float currentVolume = (volumeControl.getValue() - min) / (max - min);
            return currentVolume;
        }
        return 0.1f; // Default volume if volume control is unavailable
    }


}

