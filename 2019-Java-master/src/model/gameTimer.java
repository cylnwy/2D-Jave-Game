package model;

import javax.swing.*;

import view.myPanel;

import java.awt.event.*;

public class gameTimer {
    int second0 = 0;
    int second1 = 0;
    int minute0 = 0;
    int minute1 = 0;
    Timer timer;
    boolean flage = true;
    myPanel mp;
    ActionListener AL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (flage) {
                second0++;
                if (second0 == 10) {
                    second1++;
                    second0 = 0;
                }
                if (second1 == 6) {
                    second1 = 0;
                    minute0++;
                }
                if (minute0 == 10) {
                    minute0 = 0;
                    minute1++;
                }
                if (minute1 == 6) {
                    minute1 = 0;
                }
            }
        }
    };

    public gameTimer(myPanel mp) {
        this.mp = mp;
        timer = new Timer(1000, AL);
        timer.start();
    }

    public int getSecond0(){
        return second0;
    }

    public int getSecond1(){
        return second1;
    }

    public int getMinute0(){
        return minute0;
    }

    public int getMinute1(){
        return minute1;
    }

    public void timerPause() {
        flage = false;
    }

    public void timerResume() {
        flage = true;
    }
}