import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer{
    private Clip backGroundMusicClip;
    private Clip gunFireClip;
    private Clip runOnSandClip;

    private FloatControl BackgroundMusicVolumeControl;
    private FloatControl gunFireVolumeControl;
    private FloatControl runOnSandVolumeControl;

    public MusicPlayer(){
        try {
            // Open an audio input stream.
            File MusicSoundFile = new File("resources\\AudioFiles\\menuSound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(MusicSoundFile);

            File gunFireSoundFile = new File("resources\\AudioFiles\\gunFire.wav");
            AudioInputStream audioInFire = AudioSystem.getAudioInputStream(gunFireSoundFile);

            File runOnSandFile = new File("resources\\AudioFiles\\Run.wav");
            AudioInputStream audioInRun = AudioSystem.getAudioInputStream(runOnSandFile);

            // Get a sound backGroundMusicClip resource.
            backGroundMusicClip = AudioSystem.getClip();
            gunFireClip = AudioSystem.getClip();
            runOnSandClip = AudioSystem.getClip();

            // Open audio backGroundMusicClip and load samples from the audio input stream.
            backGroundMusicClip.open(audioIn);
            gunFireClip.open(audioInFire);
            runOnSandClip.open(audioInRun);

            // Get the volume control from the backGroundMusicClip.
            BackgroundMusicVolumeControl = (FloatControl) backGroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gunFireVolumeControl = (FloatControl) gunFireClip.getControl(FloatControl.Type.MASTER_GAIN);
            runOnSandVolumeControl = (FloatControl) runOnSandClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Start playing the music.
            backGroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the backGroundMusicClip continuously.
            backGroundMusicClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ignored) {
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
    public void setVolumeBackgroundMusic(float volume) {
        if(volume >= 0 || volume <= 1){
            if (BackgroundMusicVolumeControl != null) {
                float min = BackgroundMusicVolumeControl.getMinimum();
                float max = BackgroundMusicVolumeControl.getMaximum();
                float newVolume = min + (max - min) * volume;
                BackgroundMusicVolumeControl.setValue(newVolume);
            }
        }
    }
    public void setVolumeSoundFx(float volume){
        if (volume >= 0 || volume <= 1){
            if (gunFireVolumeControl != null && runOnSandVolumeControl != null) {
                gunFireVolumeControl.setValue(gunFireVolumeControl.getMinimum()
                        + (gunFireVolumeControl.getMaximum() - gunFireVolumeControl.getMinimum()) * volume);
                runOnSandVolumeControl.setValue(runOnSandVolumeControl.getMinimum()
                        + (runOnSandVolumeControl.getMaximum() - runOnSandVolumeControl.getMinimum()) * volume);

            }
        }
    }
    // Method to get the current volume (returns a value between 0.0 and 1.0)
    public float getVolumeBackgroundMusic() {
        if (BackgroundMusicVolumeControl != null) {
            float min = BackgroundMusicVolumeControl.getMinimum();
            float max = BackgroundMusicVolumeControl.getMaximum();
            return (BackgroundMusicVolumeControl.getValue() - min) / (max - min);
        }
        return 0.1f; // Default volume if volume control is unavailable
    }
    public float getVolumeSoundFx(){
        if (gunFireVolumeControl != null && runOnSandVolumeControl != null) {
            float min = gunFireVolumeControl.getMinimum();
            float max = gunFireVolumeControl.getMaximum();
            return (gunFireVolumeControl.getValue() - min) / (max - min);
        }
        return 0.1f; // Default volume if volume control is unavailable
    }

    public Clip getRunOnSandClip() {
        return runOnSandClip;
    }

    public void setRunOnSandClip(Clip runOnSandClip) {
        this.runOnSandClip = runOnSandClip;
    }

    public Clip getGunFireClip() {
        return gunFireClip;
    }

    public void setGunFireClip(Clip gunFireClip) {
        this.gunFireClip = gunFireClip;
    }

}

