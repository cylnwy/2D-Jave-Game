package model;

import java.awt.*;
import javax.swing.JPanel;

import view.LoF;  

public class drawPanel extends JPanel {  
    private static final long serialVersionUID = 1109023645161037661L;
    private Image image = null;
    private LoF window;
    private int[] time = null;
    private int score = -1;

    public drawPanel(Image image) {  
        this.image = image;  
    }  

    public drawPanel(Image image, LoF window, int[] time, int score) {  
        this.image = image;
        this.window = window;
        this.time = time;
        this.score = score;
    }  

    protected void paintComponent(Graphics g) {  
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        if (score != -1) {
            drawResult(g);
            drawTime(g);
        }
    }

    public void drawResult(Graphics g) {
        int[] digits = new int[4];

        digits[0] = score / 1000;
        digits[1] = score % 1000 / 100;
        digits[2] = score % 100 / 10;
        digits[3] = score % 10;

        for (int i = 0; i < digits.length; i++) {

            Image imageNum;

            switch (digits[i]) {
            case 1:
                imageNum = window.imageRes.one;
                break;
            case 2:
                imageNum = window.imageRes.two;
                break;
            case 3:
                imageNum = window.imageRes.three;
                break;
            case 4:
                imageNum = window.imageRes.four;
                break;
            case 5:
                imageNum = window.imageRes.five;
                break;
            case 6:
                imageNum = window.imageRes.six;
                break;
            case 7:
                imageNum = window.imageRes.seven;
                break;
            case 8:
                imageNum = window.imageRes.eight;
                break;
            case 9:
                imageNum = window.imageRes.nine;
                break;
            default:
                imageNum = window.imageRes.zero;
                break;
            }

            switch(i) {
            case 0:
                g.drawImage(imageNum, 328, 533, 19, 32, this);
                break;
            case 1:
                g.drawImage(imageNum, 347, 533, 19, 32, this);
                break;
            case 2:
                g.drawImage(imageNum, 366, 533, 19, 32, this);
                break;
            case 3:
                g.drawImage(imageNum, 385, 533, 19, 32, this);
                break;
            }
        }
    }

    public void drawTime(Graphics g) {
        g.drawImage(window.imageRes.clock, 704, 516, 100, 70, this);
        Image imageNum;

        for (int i = 0; i < 4; i++) {
            int number = 0;
            switch (i) {
            case 0:
                number = time[0];
                break;
            case 1:
                number = time[1];
                break;
            case 2:
                number = time[2];
                break;
            case 3:
                number = time[3];
                break;
            }

            switch (number) {
                default:
                    imageNum = window.imageRes.zero;
                    break;
                case 1:
                    imageNum = window.imageRes.one;
                    break;
                case 2:
                    imageNum = window.imageRes.two;
                    break;
                case 3:
                    imageNum = window.imageRes.three;
                    break;
                case 4:
                    imageNum = window.imageRes.four;
                    break;
                case 5:
                    imageNum = window.imageRes.five;
                    break;
                case 6:
                    imageNum = window.imageRes.six;
                    break;
                case 7:
                    imageNum = window.imageRes.seven;
                    break;
                case 8:
                    imageNum = window.imageRes.eight;
                    break;
                case 9:
                    imageNum = window.imageRes.nine;
                    break;
                }
            
            switch (i) {
            case 0:
                g.drawImage(imageNum, 709, 533, 19, 32, this);
                break;
            case 1:
                g.drawImage(imageNum, 728, 533, 19, 32, this);
                break;
            case 2:
                g.drawImage(imageNum, 756, 533, 19, 32, this);
                break;
            case 3:
                g.drawImage(imageNum, 775, 533, 19, 32, this);
                break;
            }
        }
    }
}