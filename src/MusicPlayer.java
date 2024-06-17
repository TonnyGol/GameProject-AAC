import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer{
    private final int MIN_VOLUME = 0;
    private final int MAX_VOLUME = 1;

    private Clip backGroundMusicClip;
    private Clip gunFireClip;
    private Clip runOnSandClip;

    private FloatControl BackgroundMusicVolumeControl;
    private FloatControl gunFireVolumeControl;
    private FloatControl runOnSandVolumeControl;

    public MusicPlayer(){
        try {
            File MusicSoundFile = new File("resources\\AudioFiles\\menuSound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(MusicSoundFile);

            File gunFireSoundFile = new File("resources\\AudioFiles\\gunFire.wav");
            AudioInputStream audioInFire = AudioSystem.getAudioInputStream(gunFireSoundFile);

            File runOnSandFile = new File("resources\\AudioFiles\\Run.wav");
            AudioInputStream audioInRun = AudioSystem.getAudioInputStream(runOnSandFile);

            backGroundMusicClip = AudioSystem.getClip();
            gunFireClip = AudioSystem.getClip();
            runOnSandClip = AudioSystem.getClip();

            backGroundMusicClip.open(audioIn);
            gunFireClip.open(audioInFire);
            runOnSandClip.open(audioInRun);

            BackgroundMusicVolumeControl = (FloatControl) backGroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gunFireVolumeControl = (FloatControl) gunFireClip.getControl(FloatControl.Type.MASTER_GAIN);
            runOnSandVolumeControl = (FloatControl) runOnSandClip.getControl(FloatControl.Type.MASTER_GAIN);

            backGroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the backGroundMusicClip continuously.
            backGroundMusicClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ignored) {
        }
    }

    public void setVolumeBackgroundMusic(float volume) {
        if(volume >= MIN_VOLUME || volume <= MAX_VOLUME){
            if (BackgroundMusicVolumeControl != null) {
                float min = BackgroundMusicVolumeControl.getMinimum();
                float max = BackgroundMusicVolumeControl.getMaximum();
                float newVolume = min + (max - min) * volume;
                BackgroundMusicVolumeControl.setValue(newVolume);
            }
        }
    }
    public void setVolumeSoundFx(float volume){
        if (volume >= MIN_VOLUME || volume <= MAX_VOLUME){
            if (gunFireVolumeControl != null && runOnSandVolumeControl != null) {
                gunFireVolumeControl.setValue(gunFireVolumeControl.getMinimum()
                        + (gunFireVolumeControl.getMaximum() - gunFireVolumeControl.getMinimum()) * volume);
                runOnSandVolumeControl.setValue(runOnSandVolumeControl.getMinimum()
                        + (runOnSandVolumeControl.getMaximum() - runOnSandVolumeControl.getMinimum()) * volume);

            }
        }
    }
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

    public Clip getGunFireClip() {
        return gunFireClip;
    }
}

