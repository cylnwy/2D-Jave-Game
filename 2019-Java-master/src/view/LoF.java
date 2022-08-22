package view;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;

import controller.KeyListen;
import model.*;

public class LoF {
    private JFrame frame;
    private functionalMenu FM;
    private gameTimer timer;
    private soundEffect sound = new soundEffect();
    myPanel mp;
    public imageRes imageRes;

    // Main Function
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoF window = new LoF();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoF() {
        frame = new JFrame();
        frame.setTitle("The Legend of Fakelda");
        frame.setBounds(450, 70, 1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        imageRes = new imageRes();
        FM = new functionalMenu(this);
        dispWelcome();
    }

    // Welcome
    public void dispWelcome() {
        frame.getContentPane().removeAll();
        frame.add(FM.welcome());
        frame.revalidate();
        frame.repaint();
    }

    // Menu
    public void dispMemu() {
        frame.getContentPane().removeAll();
        frame.add(FM.menu());
        frame.revalidate();
        sound.playMenuBGM();
        frame.repaint();
    }

    // High Score
    public void dispScores() {
        File f = new File("./res/scores.txt");
        frame.getContentPane().removeAll();
        frame.add(FM.scores(txtRW.txt2String(f)));
        frame.revalidate();
        frame.repaint();
    }
    
    // --- Run Game ---
    public void runGame(){
        sound.StopMenuBGM();
        sound.playGameBGM();
        FM.setSound(sound);
        mp = new myPanel(this);
        timer = new gameTimer(mp);
        mp.setTimer(timer);
        frame.getContentPane().removeAll();
        Thread t = new Thread(mp);
        mp.setThread(t);
        t.start();
        frame.getContentPane().add(mp);
        frame.addKeyListener(new KeyListen(mp));
        frame.requestFocus();

        addPauseDialog(mp,timer);
        addEscDialog(mp,timer);

        mp.setFocusable(true);
        FM.TutorialPage();
        frame.repaint();
    }

    // Finish Game (ask for name)
    public void finishGame() {
        mp.gs.setMapStage(-1);
        frame.getContentPane().removeAll();
        frame.add(FM.congrats(mp.getTime(), mp.player.score));
        mp.getThread().interrupt();
        frame.revalidate();
        frame.requestFocus();
        // sound.playMenuBGM();
        frame.repaint();
    }

    // Lose
    public void loseGame(){
        frame.getContentPane().removeAll();;
        frame.add(FM.gameOverScreen());
        frame.revalidate();
        frame.repaint();
    }

    // Compare
    public void compareScore() {
        File f = new File("./res/scores.txt");
        frame.getContentPane().removeAll();
        frame.add(FM.rank(txtRW.txt2String(f), mp.getTime(), mp.player.score));
        frame.revalidate();
        frame.repaint();
    }

    // Pause
    public void addPauseDialog(myPanel mp, gameTimer timer){
        FM.creatPause(mp,timer);
    }

    // Escape
    public void addEscDialog(myPanel mp, gameTimer timer){
        FM.creatEsc(mp);
    }

    // Close
    public void closeDialog(){
        FM.closeDialog_pu();
    }

    // Get Functional Menu
    public functionalMenu getFM() {
        return FM;
    }

    // Get Fram
    public JFrame getFrame() {
        return frame;
    }

    public soundEffect getSound() {
        return sound;
    }
}