package view;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;

import controller.*;
import model.*;

public class myPanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 6789744755912428519L;
    private gameTimer timer;
    private soundEffect sound = new soundEffect();
    private impactDetecter impDet = new impactDetecter(this);
    private Thread thread;
    public LoF window;
    public Image levelMap = null;
    public map Loadedmap = null;
    public character player = null;
    public gameStage gs = null;
    public Vector<character> enemies = new Vector<character>();
    public Vector<Bomb> bs = new Vector<Bomb>();
    public Vector<item> items = new Vector<item>();
    public Vector<item> aimedItems = new Vector<item>();

    public myPanel(LoF window) {
        this.window = window;
        Loadedmap = new map();
        player = new character(1, 0, 0, 2, this);
        gs = new gameStage(this);
        gs.setMapStage(1);
        setBounds(0, 0, 1100, 800);
        setVisible(true);
        createAimedItems();
    }

    // setup all items that will be released
    private void createAimedItems() {
        for (int i = 0; i < 20; i++) {
            item item = new item(i % 5);
            aimedItems.add(item);
        }
        item item = new item(5);
        aimedItems.add(item);
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public functionalMenu getFM() {
        return window.getFM();
    }

    public void addEnemy(character chara) {
        enemies.add(chara);
    }

    // paint the panel
    public void paint(Graphics g) {
        // draw the buffered image
        Image BufferedImage = drawBufferedImage();
        g.drawImage(BufferedImage, 0, 0, this);
    }

    // buffered image
    private Image drawBufferedImage() {
        Image BufferedImage = createImage(this.getWidth(), this.getHeight());
        Graphics g = BufferedImage.getGraphics();

        g.drawImage(levelMap, 0, 0, this);

        // the door requires crystal
        if (gs.getMapStage() == 4) {
            if (player.myInventory[4] >= 1 && player.getX() > 870 && player.getX() < 990 && player.getY() < 100) {
                g.drawImage(window.imageRes.DoorOpened, 930, 4, 32, 32, this);
            } else {
                g.drawImage(window.imageRes.DoorLocked, 930, 4, 32, 32, this);
            }
        }

        // draw player
        if (player.isLive) {
            drawCharacter(player, g);
        }

        // draw player's bullets
        for (int i = 0; i < player.mybs.size(); i++) {
            Bullet b = player.mybs.get(i);
            if (b.isLive) {
                switch (b.getType()) {
                case 11:
                    g.drawImage(window.imageRes.bullet2, b.getX(), b.getY(), 6, 6, this);
                    break;
                case 12:
                    switch (b.getDrect()) {
                    case 0:
                        switch (b.getY() / 3 % 3) {
                        case 0:
                            g.drawImage(window.imageRes.flameball_bk1, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 1:
                            g.drawImage(window.imageRes.flameball_bk2, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 2:
                            g.drawImage(window.imageRes.flameball_bk3, b.getX(), b.getY(), 32, 32, this);
                            break;
                        }
                        break;
                    case 1:
                        switch (b.getX() / 3 % 3) {
                        case 0:
                            g.drawImage(window.imageRes.flameball_rt1, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 1:
                            g.drawImage(window.imageRes.flameball_rt2, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 2:
                            g.drawImage(window.imageRes.flameball_rt3, b.getX(), b.getY(), 32, 32, this);
                            break;
                        }
                        break;
                    case 2:
                        switch (b.getY() / 3 % 3) {
                        case 0:
                            g.drawImage(window.imageRes.flameball_ft1, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 1:
                            g.drawImage(window.imageRes.flameball_ft2, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 2:
                            g.drawImage(window.imageRes.flameball_ft3, b.getX(), b.getY(), 32, 32, this);
                            break;
                        }
                        break;
                    case 3:
                        switch (b.getX() / 3 % 3) {
                        case 0:
                            g.drawImage(window.imageRes.flameball_lf1, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 1:
                            g.drawImage(window.imageRes.flameball_lf2, b.getX(), b.getY(), 32, 32, this);
                            break;
                        case 2:
                            g.drawImage(window.imageRes.flameball_lf3, b.getX(), b.getY(), 32, 32, this);
                            break;
                        }
                        break;
                    }
                    break;
                }
            } else {
                player.mybs.remove(b);
            }
        }

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            character enemy = enemies.get(i);
            if (enemy.isLive) {
                if (enemy.stage == 0 || enemy.getType() == 3 || enemy.getType() == 6) {
                    drawCharacter(enemy, g);
                } else {
                    int x = enemy.getX() - 40;
                    int y = enemy.getY() - 40;
                    switch (enemy.stage) {
                    case 1:
                        g.drawImage(window.imageRes.explosions_0, x, y, 150, 150, this);
                        break;
                    case 2:
                        g.drawImage(window.imageRes.explosions_1, x, y, 150, 150, this);
                        break;
                    case 3:
                        g.drawImage(window.imageRes.explosions_2, x, y, 150, 150, this);
                        break;
                    case 4:
                        g.drawImage(window.imageRes.explosions_3, x, y, 150, 150, this);
                        break;
                    case 5:
                        g.drawImage(window.imageRes.explosions_4, x, y, 150, 150, this);
                        break;
                    case 6:
                        g.drawImage(window.imageRes.explosions_5, x, y, 150, 150, this);
                        break;
                    case 7:
                        g.drawImage(window.imageRes.explosions_6, x, y, 150, 150, this);
                        break;
                    }
                }
            } else {
                enemy.getThread().interrupt();
                enemies.remove(enemy);
            }

            // draw enemies' bullets
            for (int j = 0; j < enemy.mybs.size(); j++) {
                Bullet eb = enemy.mybs.get(j);
                if (eb.isLive && eb.getType() != 41) {
                    if (eb.getType() == 31) {
                        switch (enemy.stage) {
                        case 1:
                            g.drawImage(window.imageRes.tentacles1, eb.getX() - 12, eb.getY() - 90, 25, 90, this);
                            break;
                        case 2:
                            g.drawImage(window.imageRes.tentacles2, eb.getX() - 12, eb.getY() - 90, 25, 90, this);
                            break;
                        case 3:
                            g.drawImage(window.imageRes.tentacles3, eb.getX() - 12, eb.getY() - 90, 25, 90, this);
                            break;
                        case 4:
                            g.drawImage(window.imageRes.tentacles4, eb.getX() - 12, eb.getY() - 90, 25, 90, this);
                            eb.isLive = false;
                            break;
                        }
                    } else {
                        g.drawImage(window.imageRes.bullet1, eb.getX(), eb.getY(), 6, 6, this);
                    }
                } else {
                    enemy.mybs.remove(eb);
                }
            }
        }

        // draw bomb
        boolean drawnPlayerBomb = false;
        for (int i = 0; i < bs.size(); i++) {
            Bomb bb = bs.get(i);
            if (bb.getIsPlayer() && drawnPlayerBomb) {
                continue;
            }
            if (bb.getType() == 1) {
                if (bb.isLive) {
                    sound.playExplosion();
                    if (bb.getTime() > 6) {
                        try {
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(window.imageRes.Exploding2, bb.getX(), bb.getY() - 32, 32, 64, this);
                    } else if (bb.getTime() > 4) {
                        try {
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(window.imageRes.Exploding3, bb.getX(), bb.getY() - 32, 32, 64, this);
                    } else if (bb.getTime() > 2) {
                        try {
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(window.imageRes.Exploding4, bb.getX(), bb.getY() - 32, 32, 64, this);
                    } else if (bb.getTime() > 0) {
                        try {
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        g.drawImage(window.imageRes.Exploding5, bb.getX(), bb.getY() - 32, 32, 64, this);
                    }
                }
                bb.livedown();
                if (bb.isLive == false) {
                    bs.remove(bb);
                }
            }
        }

        // draw items on the ground
        for (int i = 0; i < items.size(); i++) {
            item item = items.get(i);
            switch (item.getType()) {
            case 0:
                g.drawImage(window.imageRes.addArmour, item.getX(), item.getY(), item.getWidth(), item.getHeight(), this);
                break;
            case 1:
                g.drawImage(window.imageRes.addLife, item.getX(), item.getY(), item.getWidth(), item.getHeight(), this);
                break;
            case 2:
                g.drawImage(window.imageRes.Book, item.getX(), item.getY(), item.getWidth(), item.getHeight(), this);
                break;
            case 3:
                g.drawImage(window.imageRes.Ruby, item.getX(), item.getY(), item.getWidth(), item.getHeight(), this);
                break;
            case 5:
                g.drawImage(window.imageRes.Crystal, item.getX(), item.getY(), item.getWidth(), item.getHeight(), this);
                break;
            }
        }

        drawTimer(g);
        drawInventory(g);
        return BufferedImage;
    }

    
    public void drawCharacter(character chara, Graphics g) {
        // Potagonist: 1, Grey rat: 2, Mad Plant: 3, Bomb Cake: 4, Walking Cake: 5,
        // Cthulhu: 6
        switch (chara.getType()) {
        case 1:
            drawPlayer(chara, g);
            break;
        case 2:
            drawRat(chara, g);
            break;
        case 3:
            drawPlant(chara, g);
            break;
        case 4:
            drawBomb(chara, g);
            break;
        case 5:
            drawWalking(chara, g);
            break;
        case 6:
            drawCthulhu(chara, g);
            break;
        default:
            break;
        }
    }

    private void drawPlayer(character chara, Graphics g) {
        Image image;
        int i;
        int reminder = (chara.getX() + chara.getY()) % 2;
        if (!player.blink) {
            switch (chara.getDrect()) {
            case 1:
                if (reminder == 1) {
                    g.drawImage(window.imageRes.amg1_rt1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                } else {
                    g.drawImage(window.imageRes.amg1_rt2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                }
                break;
            case 2:
                if (reminder == 1) {
                    g.drawImage(window.imageRes.amg1_fr1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                } else {
                    g.drawImage(window.imageRes.amg1_fr2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                }
                break;
            case 3:
                if (reminder == 1) {
                    g.drawImage(window.imageRes.amg1_lf1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                } else {
                    g.drawImage(window.imageRes.amg1_lf2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                }
                break;
            default:
                if (reminder == 1) {
                    g.drawImage(window.imageRes.amg1_bk1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                } else {
                    g.drawImage(window.imageRes.amg1_bk2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(),
                            this);
                }
                break;
            }
        }

        if (chara.defense) {
            g.drawImage(window.imageRes.DEF, chara.getX() - 4, chara.getY() - 4, chara.getWidth() + 8, chara.getHeight() + 8,
                    this);
        }

        for (i = 1; i < chara.getLife(); i++) {
            g.drawImage(window.imageRes.HP, 22 * i, 715, 20, 20, this);
        }
        if (chara.getLife() != 0) {
            g.drawImage(window.imageRes.HP0, 22 * i, 715, 20, 20, this);
        }

        for (i = 1; i < chara.getArmour(); i++) {
            g.drawImage(window.imageRes.Armour, 22 * i, 740, 20, 10, this);
        }
        if (chara.getArmour() != 0) {
            g.drawImage(window.imageRes.Armour0, 22 * i, 740, 20, 10, this);
        }

        if (player.getBulletType() == 11) {
            g.drawImage(window.imageRes.bullet2, 195, 705, 30, 30, this);
            g.drawImage(window.imageRes.flameball_rt1, 185, 730, 40, 40, this);
        } else {
            g.drawImage(window.imageRes.bullet2, 200, 710, 20, 20, this);
            g.drawImage(window.imageRes.flameball_rt1, 180, 725, 50, 50, this);
        }

        for (i = 0; i < 5; i++) {
            int[] itnum = new int[2];
            itnum[0] = player.myInventory[i] / 10;
            itnum[1] = player.myInventory[i] % 10;

            for (int j = 0; j < 2; j++) {
                image = pickNum(itnum[j]);
                
                switch (i) {
                case 0:
                    if (j == 0) {
                        g.drawImage(image, 320, 700, 19, 32, this);
                    } else {
                        g.drawImage(image, 340, 700, 19, 32, this);
                    }
                    break;
                case 1:
                    if (j == 0) {
                        g.drawImage(image, 320, 735, 19, 32, this);
                    } else {
                        g.drawImage(image, 340, 735, 19, 32, this);
                    }
                    break;
                case 2:
                    if (j == 0) {
                        g.drawImage(image, 460, 700, 19, 32, this);
                    } else {
                        g.drawImage(image, 480, 700, 19, 32, this);
                    }
                    break;
                case 3:
                    if (j == 0) {
                        g.drawImage(image, 460, 735, 19, 32, this);
                    } else {
                        g.drawImage(image, 480, 735, 19, 32, this);
                    }
                    break;
                case 4:
                    if (j != 0) {
                        g.drawImage(image, 600, 720, 19, 32, this);
                    }
                    break;
                }
            }
        }
        drawScore(g);
    }

    private void drawScore(Graphics g) {
        int[] digits = new int[4];

        digits[0] = player.score / 1000;
        digits[1] = player.score % 1000 / 100;
        digits[2] = player.score % 100 / 10;
        digits[3] = player.score % 10;

        for (int i = 0; i < digits.length; i++) {
            int digit = digits[i];
            Image image = pickNum(digit);

            switch(i) {
            case 0:
                g.drawImage(image, 700, 720, 19, 32, this);
                break;
            case 1:
                g.drawImage(image, 719, 720, 19, 32, this);
                break;
            case 2:
                g.drawImage(image, 738, 720, 19, 32, this);
                break;
            case 3:
                g.drawImage(image, 757, 720, 19, 32, this);
                break;
            }
        }
    }

    private void drawInventory(Graphics g) {
        if (player.getItemSelect() == 0) {
            g.drawImage(window.imageRes.addArmour, 255, 695, 40, 40, this);
            g.drawImage(window.imageRes.addLife, 260, 735, 30, 30, this);
        } else {
            g.drawImage(window.imageRes.addArmour, 260, 700, 30, 30, this);
            g.drawImage(window.imageRes.addLife, 255, 730, 40, 40, this);
        }
        g.drawImage(window.imageRes.times, 300, 710, 10, 10, this);

        g.drawImage(window.imageRes.times, 440, 710, 10, 10, this);
        
        g.drawImage(window.imageRes.Book, 400, 700, 30, 30, this);
        g.drawImage(window.imageRes.times, 300, 745, 10, 10, this);

        g.drawImage(window.imageRes.Ruby, 400, 735, 30, 30, this);
        g.drawImage(window.imageRes.times, 440, 745, 10, 10, this);

        g.drawImage(window.imageRes.Crystal, 540, 720, 30, 30, this);
        g.drawImage(window.imageRes.times, 580, 730, 10, 10, this);
    }

    private void drawTimer(Graphics g) {
        g.drawImage(window.imageRes.clock, 1000, 699, 100, 70, this);

        for (int i = 0; i < 4; i++) {
            int number = 0;
            switch (i) {
            case 0:
                number = timer.getSecond0();
                break;
            case 1:
                number = timer.getSecond1();
                break;
            case 2:
                number = timer.getMinute0();
                break;
            case 3:
                number = timer.getMinute1();
                break;
            }

            Image image = pickNum(number);
            
            switch (i) {
            case 0:
                g.drawImage(image, 1075, 716, 19, 32, this);
                break;
            case 1:
                g.drawImage(image, 1054, 716, 19, 32, this);
                break;
            case 2:
                g.drawImage(image, 1026, 716, 19, 32, this);
                break;
            case 3:
                g.drawImage(image, 1005, 716, 19, 32, this);
                break;
            }
        }
    }

    private void drawRat(character chara, Graphics g) {
        int status = (chara.getY() + chara.getX()) / 2 % 3;
        switch (chara.getDrect()) {
        case 1:
            if (status == 0) {
                g.drawImage(window.imageRes.rat_rt1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            } else if (status == 1) {
                g.drawImage(window.imageRes.rat_rt2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            } else {
                g.drawImage(window.imageRes.rat_rt3, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            }
            break;
        case 3:
            if (status == 0) {
                g.drawImage(window.imageRes.rat_lf1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            } else if (status == 1) {
                g.drawImage(window.imageRes.rat_lf2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            } else {
                g.drawImage(window.imageRes.rat_lf3, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            }
            break;
        }
    }

    private void drawPlant(character chara, Graphics g) {
        g.drawImage(window.imageRes.tree, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
    }

    private void drawBomb(character chara, Graphics g) {
        if (chara.blink) {
            g.drawImage(window.imageRes.bomb2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
        } else {
            g.drawImage(window.imageRes.bomb1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
        }
    }

    private void drawWalking(character chara, Graphics g) {
        if (chara.getX() / 3 % 2 == 1 && chara.getY() / 3 % 2 == 1) {
            g.drawImage(window.imageRes.walking1, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
        } else {
            g.drawImage(window.imageRes.walking2, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
        }
    }

    private void drawCthulhu(character chara, Graphics g) {
        switch (chara.getDrect()) {
        case 1:
            g.drawImage(window.imageRes.Boss_rt, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            break;
        case 3:
            g.drawImage(window.imageRes.Boss_lf, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            break;
        default:
            g.drawImage(window.imageRes.Boss_ft, chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight(), this);
            break;
        }
    }

    public void setTimer(gameTimer timer) {
        this.timer = timer;
    }

    public int[] getTime() {
        int[] time = new int[4];
        time[3] = timer.getSecond0();
        time[2] = timer.getSecond1();
        time[1] = timer.getMinute0();
        time[0] = timer.getMinute1();

        return time;
    }

    private Image pickNum(int number) {
        Image image;
        switch (number) {
        default:
            image = window.imageRes.zero;
            break;
        case 1:
            image = window.imageRes.one;
            break;
        case 2:
            image = window.imageRes.two;
            break;
        case 3:
            image = window.imageRes.three;
            break;
        case 4:
            image = window.imageRes.four;
            break;
        case 5:
            image = window.imageRes.five;
            break;
        case 6:
            image = window.imageRes.six;
            break;
        case 7:
            image = window.imageRes.seven;
            break;
        case 8:
            image = window.imageRes.eight;
            break;
        case 9:
            image = window.imageRes.nine;
            break;
        }
        return image;

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!impDet.impactLogic()) {
                    Thread.currentThread().interrupt();
                }
                this.repaint();
                Thread.sleep(80);     
            } catch(InterruptedException e) {
                break;
            } 
        }
    }
}