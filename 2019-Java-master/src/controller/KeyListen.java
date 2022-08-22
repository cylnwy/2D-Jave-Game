package controller;

import java.awt.event.*;

import model.soundEffect;
import view.*;

public class KeyListen extends Thread implements KeyListener {
    private gameControl gc;
    private myPanel mp;
    public int counter = 0;
    private soundEffect sound = new soundEffect();
    private boolean isWalkingSoundPlaying = false;
    private boolean isHitWall = false;

    public KeyListen(myPanel mp) {
        this.mp = mp;
        gc = new gameControl(mp);
        Thread key = new Thread(gc);
        key.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(mp.getFM().getIsStoped()){
            gc.def = false;
            gc.up = false;
            gc.dn = false;
            gc.rt = false;
            gc.rt = false;
            gc.lf = false;
            gc.shot = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            mp.getFM().openDialog();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mp.getFM().openEsc();
        }

        switch (e.getKeyCode()) {
        case KeyEvent.VK_D:
            gc.def = true;
            break;
        case KeyEvent.VK_UP:
            gc.up = true;
            break;
        case KeyEvent.VK_DOWN:
            gc.dn = true;
            break;
        case KeyEvent.VK_RIGHT:
            gc.rt = true;
            break;
        case KeyEvent.VK_LEFT:
            gc.lf = true;
            break;
        case KeyEvent.VK_SPACE:
            gc.shot = true;
            break;
        case KeyEvent.VK_S:
            mp.player.switchBulletType();
            break;
        case KeyEvent.VK_B:
            mp.player.switchItem();
            break;
        case KeyEvent.VK_F:
            mp.player.useItem();
            break;
        case KeyEvent.VK_M:
            mp.getFM().TutorialPage();
            break;
        case KeyEvent.VK_PAGE_DOWN:
            mp.gs.cheatMode();
        }

        // play walking sound only while moving command is made
        if ((gc.up || gc.dn || gc.rt || gc.lf) == true) {
            if (!isWalkingSoundPlaying && !isHitWall) {
                sound.playWalkingSound();
                mp.getFM().setWalkingSound(sound);
                isWalkingSoundPlaying = true;
                mp.getFM().setWalkSoundFlag(true);
            }
            if (mp.player.getIsHitaWall() && isWalkingSoundPlaying) {
                sound.Stopwalking();
                sound.playCollideSound();
                isWalkingSoundPlaying = false;
                mp.getFM().setWalkSoundFlag(false);
                isHitWall = true;
            }
             if(!mp.player.getIsHitaWall()){
                isHitWall = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_D:
            gc.def = false;
            break;
        case KeyEvent.VK_UP:
            gc.up = false;
            break;
        case KeyEvent.VK_DOWN:
            gc.dn = false;
            break;
        case KeyEvent.VK_RIGHT:
            gc.rt = false;
            break;
        case KeyEvent.VK_LEFT:
            gc.lf = false;
            break;
        case KeyEvent.VK_SPACE:
            gc.shot = false;
            break;
        }
        if ((gc.up || gc.dn || gc.rt || gc.lf) != true) {
            if (isWalkingSoundPlaying) {
                sound.Stopwalking();
                isWalkingSoundPlaying = false;
                mp.getFM().setWalkSoundFlag(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}