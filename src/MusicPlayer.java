import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer extends Thread {
    public static boolean isCharacterShooting;

    private Clip backGroundMusicClip;
    public static Clip gunFireClip;
    private FloatControl BackgroundMusicVolumeControl;
    private FloatControl gunFireVolumeControl;

    public MusicPlayer(){
        try {
            this.isCharacterShooting = false;
            // Open an audio input stream.
            File MusicSoundFile = new File("resources\\AudioFiles\\menuSound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(MusicSoundFile);

            File gunFireSoundFile = new File("resources\\AudioFiles\\gunFire.wav");
            AudioInputStream audioInFire = AudioSystem.getAudioInputStream(gunFireSoundFile);

            // Get a sound backGroundMusicClip resource.
            backGroundMusicClip = AudioSystem.getClip();
            gunFireClip = AudioSystem.getClip();

            // Open audio backGroundMusicClip and load samples from the audio input stream.
            backGroundMusicClip.open(audioIn);
            gunFireClip.open(audioInFire);

            // Get the volume control from the backGroundMusicClip.
            BackgroundMusicVolumeControl = (FloatControl) backGroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gunFireVolumeControl = (FloatControl) gunFireClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Start playing the music.
            backGroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the backGroundMusicClip continuously.
            backGroundMusicClip.start();
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
        if (backGroundMusicClip != null) {
            backGroundMusicClip.stop();
            backGroundMusicClip.close();
        }
    }
    // Method to set the volume (volume should be between 0.0 and 1.0)
    public void setVolume(float volume) {
        if (BackgroundMusicVolumeControl != null) {
            float min = BackgroundMusicVolumeControl.getMinimum();
            float max = BackgroundMusicVolumeControl.getMaximum();
            float newVolume = min + (max - min) * volume;
            BackgroundMusicVolumeControl.setValue(newVolume);
        }
    }
    // Method to get the current volume (returns a value between 0.0 and 1.0)
    public float getVolume() {
        if (BackgroundMusicVolumeControl != null) {
            float min = BackgroundMusicVolumeControl.getMinimum();
            float max = BackgroundMusicVolumeControl.getMaximum();
            float currentVolume = (BackgroundMusicVolumeControl.getValue() - min) / (max - min);
            return currentVolume;
        }
        return 0.1f; // Default volume if volume control is unavailable
    }


}

