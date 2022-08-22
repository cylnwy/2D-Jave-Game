package model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class soundEffect {
    private Clip MenuBGM;
    private Clip GameBGM;
    private Clip walking;
    private Clip collision;
    private Clip shot;
    private Clip explosion;

    public soundEffect() {

    }

    private Clip playBGM(String path, Clip name, float newValue) {
        try {
            File filePath = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(filePath);
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            try {
                name = (Clip) AudioSystem.getLine(info);
                name.open(audioIn);
                FloatControl gainControl = (FloatControl) name.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(newValue);
                name.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private void playEffect(String path, Clip name) {
        try {
            File filePath = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(filePath);
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            try {
                name = (Clip) AudioSystem.getLine(info);
                name.open(audioIn);
                FloatControl gainControl = (FloatControl) name.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-25.0f);
                name.start();
                name.addLineListener(new LineListener() {
                    public void update(LineEvent event) {
                      if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                      }
                    }
                  });
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playCollideSound(){
        playEffect("./res/SoundEffect/collide.wav", collision);
    }

    public void playExplosion(){
        playEffect("./res/SoundEffect/explosion.wav", explosion);
    }

    public void shotSound() {
        playEffect("./res/SoundEffect/shot.wav", shot);
    }

    public void playWalkingSound() {
       walking = playBGM("./res/SoundEffect/moving_1.wav",walking, -25.0f);
    }

    public void playMenuBGM() {
        MenuBGM = playBGM("./res/SoundEffect/BGM_Menu.wav", MenuBGM, -25.0f);
    }

    public void playGameBGM() {
        GameBGM = playBGM("./res/SoundEffect/BGM_game.wav", GameBGM, -30.0f);
    }

    public void StopMenuBGM() {
        MenuBGM.close();
    }

    public void Stopwalking() {
        walking.close();
    }

    public void StopGameBGM() {
        GameBGM.close();
    }
}