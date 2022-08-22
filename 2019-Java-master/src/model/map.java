package model;

import java.awt.image.BufferedImage;

import view.myPanel;

public class map {

    private int pix;
    private int red;
    private int green;
    private int blue;
    public int mapWidth = 1100;
    public int mapHeight = 700;
    public int[][] location = new int[mapWidth][mapHeight];
    int num = 0;

    /*
     * Potagonist: 1, Grey rat: 2, Mad Plant: 3, Bomb Cake: 4, Walking Cake: 5,
     * Cthulhu: 6 Wall: 11, Door: 14 Amor Poison: 21, Magic Book: 22, Charging
     * Crystal: 23, Homemade Cakes: 24 Free Space: 0 Dirction: up 0, right 1,down 2,
     * left 3
     */

    //  read the initial location data from a special graph
    public void initialMap(BufferedImage map, myPanel mp) {
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                pix = map.getRGB(i, j);
                red = (pix >> 16) & 0xff;
                green = (pix >> 8) & 0xff;
                blue = (pix) & 0xff;
                if (red == 0 && green == 0 && blue == 0) {
                    this.location[i][j] = 11;
                } else if (red == 255 && green == 0 && blue == 0) {
                    this.location[i][j] = 14;
                } else if (red == 255 && green == 255 && blue == 0) {
                    mp.player.setX(i);
                    mp.player.setY(j);
                    // mp.player.setX(500);
                    // mp.player.setY(200);
                } else if (red == 148 && green == 160 && blue == 171) {
                    character enemy = new character(2, i, j, 3, mp);
                    mp.addEnemy(enemy);
                    Thread thread = new Thread(enemy);
                    enemy.setThread(thread);
                    thread.start();
                } else if (red == 0 && green == 255 && blue == 41) {
                    character enemy = new character(3, i, j, 3, mp);
                    mp.addEnemy(enemy);
                    Thread thread = new Thread(enemy);
                    enemy.setThread(thread);
                    thread.start();
                } else if (red == 0 && green == 255 && blue == 30) {
                    character enemy = new character(4, i, j, 3, mp);
                    mp.addEnemy(enemy);
                    Thread thread = new Thread(enemy);
                    enemy.setThread(thread);
                    thread.start();
                } else if (red == 255 && green == 202 && blue == 0) {
                    character enemy = new character(5, i, j, 3, mp);
                    mp.addEnemy(enemy);
                    Thread thread = new Thread(enemy);
                    enemy.setThread(thread);
                    thread.start();
                } else if (red == 4 && green == 0 && blue == 255) {
                    character enemy = new character(6, i, j, 3, mp);
                    mp.addEnemy(enemy);
                    Thread thread = new Thread(enemy);
                    enemy.setThread(thread);
                    thread.start();
                } else {
                    this.location[i][j] = 0;
                }
            }
        }

    }
}