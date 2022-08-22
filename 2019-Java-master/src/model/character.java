package model;

import java.util.Vector;

import view.myPanel;

public class character implements Runnable {
    private int type;
    // Potagonist: 1, Grey rat: 2, Mad Plant: 3, Bomb Cake: 4, Walking Cake: 5,
    // Cthulhu: 6
    private int x;
    private int y;
    private int width;
    private int height;
    private int drect;
    private int speed;
    private int life, armour;
    private boolean inside;
    private boolean isHitaWall;
    private int bulletType;
    private Bullet myBullet;
    private int itemSelect = 0;
    private character player;
    private Thread thread;
    private boolean suspended = false;
    private myPanel mp;
    public boolean isLive = true;
    public boolean defense = false;
    public boolean blink, reborn;
    public int stage = 0;
    public Vector<Bullet> mybs = new Vector<Bullet>();
    public int[] myInventory;
    public int score;
    public boolean start = true;

    public character(int type, int x, int y, int drect, myPanel mp) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.drect = drect;
        this.mp = mp;
        this.player = mp.player;
        setAttribute();
        setLocation(); // Set flags into map
    }


    private void setAttribute() {
        switch (type) {
        case 1:
            speed = 1;
            width = 32;
            height = 32;
            bulletType = 11;
            life = 3;
            armour = 3;
            defense = false;
            blink = false;
            reborn = false;
            myInventory = new int[5];
            myInventory[2] = 3;
            score = 0;
            break;
        case 2:
            speed = 5;
            width = 69;
            height = 55;
            score = 50;
            break;
        case 3:
            speed = 0;
            width = 72;
            height = 64;
            bulletType = 31;
            score = 100;
            break;
        case 4:
            speed = 1;
            width = 32;
            height = 32;
            blink = false;
            inside = false;
            bulletType = 41;
            score = 80;
            break;
        case 5:
            speed = 1;
            width = 32;
            height = 32;
            bulletType = 51;
            score = 80;
            break;
        case 6:
            speed = 1;
            width = 57;
            height = 88;
            bulletType = 61;
            defense = true;
            score = 500;
            break;
        }
    }

    private boolean compAbs(int m, int n) {
        if (Math.abs(m) >= Math.abs(n)) {
            return true;
        }
        return false;
    }

    // set the flags in map
    public void setLocation() {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                mp.Loadedmap.location[i][j] = type;
            }
        }
    }

    // clean the flags in map
    public void cleanLocation() {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (mp.Loadedmap.location[i][j] == type) {
                    mp.Loadedmap.location[i][j] = 0;
                }
            }
        }
    }

    public int getBulletType() {
        return bulletType;
    }

    public void switchBulletType() {
        if (bulletType == 11) {
            bulletType = 12;
        } else {
            bulletType = 11;
        }
    }

    public void setDefense(boolean defense) {
        this.defense = defense;
    }

    public void switchItem() {
        if (itemSelect == 0) {
            itemSelect = 1;
        } else {
            itemSelect = 0;
        }
    }

    // use the selected item
    public void useItem() {
        if (myInventory[itemSelect] > 0) {
            if (itemSelect == 0) {
                if (getArmour() < 4) {
                    addArmour();
                } else {
                    return;
                }
            } else {
                if (getLife() < 4) {
                    addLife();
                } else {
                    return;
                }
            }
            myInventory[itemSelect]--;
        }
    }

    public int getItemSelect() {
        return itemSelect;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDrect() {
        return drect;
    }

    public void setDrect(int drect) {
        this.drect = drect;
    }

    public int getType() {
        return type;
    }

    public map getMap() {
        return mp.Loadedmap;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public int getLife() {
        return life;
    }

    public void addLife() {
        life++;
    }

    public void reduceLife() {
        life--;
    }

    public int getArmour() {
        return armour;
    }

    public void addArmour() {
        armour++;
    }

    public void reduceArmour() {
        armour--;
    }

    // suspend thread
    public void suspend() {
        suspended = true;
        cleanLocation();
    }

    // resume thread
    public synchronized void resume() {
        suspended = false;
        notify();
        setLocation();
    }

    // whether the play nears an enemy
    public boolean nearEnemy() {
        for (int i = 0; i <= getWidth() + 1; i++) {
            for (int j = 0; j <= getHeight() + 1; j++) {
                if (i == 0 || i == getWidth() + 1 || j == 0 || j == getHeight() + 1) {
                    if (mp.Loadedmap.location[getX() + i - 1][getY() + j - 1] >= 2
                            && mp.Loadedmap.location[getX() + i - 1][getY() + j - 1] <= 6) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // whether the character nears the wall
    public boolean nearWall() {
        for (int i = 0; i <= getWidth() + 1; i++) {
            for (int j = 0; j <= getHeight() + 1; j++) {
                if (i == 0 || i == getWidth() + 1 || j == 0 || j == getHeight() + 1) {
                    if (mp.Loadedmap.location[getX() + i - 1][getY() + j - 1] == 11) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // the distance the character can go up
    public int upValid() {
        for (int j = 1; j <= speed; j++) {
            for (int i = 0; i < width; i++) {
                if (mp.Loadedmap.location[x + i][y - j] != 0) {
                    if (j == 1 && mp.Loadedmap.location[x + i][y - j] == 14) {
                        for (; i < width; i++) {
                            if (mp.Loadedmap.location[x + i][y - j] != 14) {
                                break;
                            }
                            if (i == width - 1 && getType() == 1) {
                                mp.gs.setMapStage(mp.gs.getMapStage() + 1);
                            }
                        }
                    }
                    if (j == 1 && (mp.Loadedmap.location[x + i][y - j] == 11) && type == 1) {
                        isHitaWall = true;
                    }
                    return j - 1;
                }
            }
        }
        isHitaWall = false;
        return speed;
    }

    // move the character up
    public void moveUp() {
        int valid = upValid();
        if (valid != 0) {
            for (int j = 0; j < valid; j++) {
                for (int i = 0; i < width; i++) {
                    mp.Loadedmap.location[x + i][y + height - 1 - j] = 0;
                    mp.Loadedmap.location[x + i][y - 1 - j] = type;
                }
            }
        }
        y -= valid;
    }

    // the distance the character can go down
    public int downValid() {
        for (int j = 1; j <= speed; j++) {
            for (int i = 0; i < width; i++) {
                if (mp.Loadedmap.location[x + i][y + height - 1 + j] != 0) {
                    if (j == 1 && mp.Loadedmap.location[x + i][y + height - 1 + j] == 14) {
                        for (; i < width; i++) {
                            if (mp.Loadedmap.location[x + i][y + height - 1 + j] != 14) {
                                break;
                            }
                            if (i == width - 1 && getType() == 1) {
                                mp.gs.setMapStage(mp.gs.getMapStage() - 1);
                            }
                        }
                    }
                    if (j == 1 && (mp.Loadedmap.location[x + i][y + height - 1 + j] == 11) && type == 1) {
                        isHitaWall = true;
                    }
                    return j - 1;
                }
            }
        }
        isHitaWall = false;
        return speed;
    }

    // move the character down
    public void moveDown() {
        int valid = downValid();
        if (valid != 0) {
            for (int j = 0; j < valid; j++) {
                for (int i = 0; i < width; i++) {
                    mp.Loadedmap.location[x + i][y + j] = 0;
                    mp.Loadedmap.location[x + i][y + height + j] = type;
                }
            }
            y += valid;
        }
    }

    // the distance the character can go right
    public int rightValid() {
        for (int i = 1; i <= speed; i++) {
            for (int j = 0; j < height; j++) {
                if (mp.Loadedmap.location[x + width - 1 + i][y + j] != 0) {
                    if (i == 1 && (mp.Loadedmap.location[x + width - 1 + i][y + j] == 11) && type == 1) {
                        isHitaWall = true;
                    }

                    return i - 1;
                }
            }
        }
        isHitaWall = false;
        return speed;
    }

    // move the character right
    public void moveRight() {
        int valid = rightValid();

        if (valid != 0) {
            for (int i = 0; i < valid; i++) {
                for (int j = 0; j < height; j++) {
                    mp.Loadedmap.location[x + i][y + j] = 0;
                    mp.Loadedmap.location[x + width + i][y + j] = type;
                }
            }
            x += valid;
        }
    }

    // the distance the character can go left
    public int leftValid() {
        for (int i = 1; i <= speed; i++) {
            for (int j = 0; j < height; j++) {
                if (mp.Loadedmap.location[x - i][y + j] != 0) {
                    if (i == 1 && (mp.Loadedmap.location[x - i][y + j] == 11) && type == 1) {
                        isHitaWall  = true;
                    }
                    return i - 1;
                }
            }
        }
        isHitaWall = false;
        return speed;
    }

    // move the character left
    public void moveLeft() {
        int valid = leftValid();

        if (valid != 0) {
            for (int i = 0; i < valid; i++) {
                for (int j = 0; j < height; j++) {
                    mp.Loadedmap.location[x + width - 1 - i][y + j] = 0;
                    mp.Loadedmap.location[x - 1 - i][y + j] = type;
                }
            }
            x -= valid;
        }
    }

    // shot
    public void shot() {
        if (type == 1) {

        }
        if (getBulletType() != 12) {
            switch (getDrect()) {
            case 0:
                myBullet = new Bullet(getX() + getWidth() / 2, getY(), 1, getDrect(), getBulletType(), getMap());
                mybs.add(myBullet);
                break;
            case 1:
                myBullet = new Bullet(getX() + getWidth() - 1, getY() + getHeight() / 2, 1, getDrect(), getBulletType(),
                        getMap());
                mybs.add(myBullet);
                break;
            case 2:
                myBullet = new Bullet(getX() + getWidth() / 2, getY() + getHeight() - 1, 1, getDrect(), getBulletType(),
                        getMap());
                mybs.add(myBullet);
                break;
            case 3:
                myBullet = new Bullet(getX(), getY() + getHeight() / 2, 1, getDrect(), getBulletType(), getMap());
                mybs.add(myBullet);
                break;
            }
        } else {
            if (myInventory[2] != 0) {
                myBullet = new Bullet(getX(), getY(), 1, getDrect(), getBulletType(), getMap());
                mybs.add(myBullet);
                myInventory[2]--;
            } else {
                switchBulletType();
            }
        }
    }

    // mad plant shot
    public void shotTree() {
        int attX = (int) (player.getX() - player.getWidth() + Math.random() * 3 * player.getWidth());
        int attY = (int) (player.getY() - player.getHeight() + Math.random() * 3 * player.getWidth());

        myBullet = new Bullet(attX, attY, 0, getDrect(), getBulletType(), getMap());
        mybs.add(myBullet);
    }

    // bomb cake explosion
    public void shotBombCake(int stage) {
        int mainX = getX() + getWidth() / 2;
        int mainY = getY() + getHeight() / 2;

        for (int i = mainX - 13 * stage; i < mainX + 13 * stage; i++) {
            for (int j = mainY - 13 * stage; j < mainY + 13 * stage; j++) {
                if (mp.Loadedmap.location[i][j] == 1) {
                    inside = true;
                }
            }
        }
    }

    public boolean getIsHitaWall(){
        return isHitaWall;
    }

    @Override
    public void run() {
        int tempX = -1;
        int tempY = -1;
        while (true) {
            try {
                Thread.sleep(30);

                // resume or suspend the thread
                synchronized (this) {
                    while (suspended) {
                        if (getType() == 4 && stage != 0) {
                            stage = 8;
                        }
                        Thread.sleep(20);
                        wait();
                    }
                }
                
                if (start) {
                    if (type != 1) {
                        int step, x_diff, y_diff, s;

                        switch (type) {
                        case 2: // Grey rat
                            switch (drect) {
                            case 1:
                                while (rightValid() != 0) {
                                    moveRight();
                                    Thread.sleep(20);
                                    if (suspended) {
                                        break;
                                    }
                                }
                                drect = 3;
                                break;
                            case 3:
                                while (leftValid() != 0) {
                                    moveLeft();
                                    Thread.sleep(20);
                                    if (suspended) {
                                        break;
                                    }
                                }
                                drect = 1;
                                break;
                            }
                            Thread.sleep(20);
                            break;
                        case 3: // Mad Plant
                            if (stage == 0) {
                                s = (int) (Math.random() * 10);
                                if (s > 8) {
                                    shotTree();
                                    stage++;
                                    Thread.sleep(600);
                                }
                            } else {
                                stage++;
                            }
                            if (stage == 5) {
                                stage = 0;
                            }
                            Thread.sleep(80);
                            break;
                        case 4: // Bomb Cake
                            x_diff = player.getX() - getX();
                            y_diff = player.getY() - getY();
                            if (x_diff < 200 && y_diff < 200) {
                                if (x_diff > 0) {
                                    moveRight();
                                } else if (x_diff < 0) {
                                    moveLeft();
                                }
                                if (y_diff > 0) {
                                    moveDown();
                                } else if (y_diff < 0) {
                                    moveUp();
                                }
                            }
                            if (Math.abs(x_diff) <= 100 && Math.abs(y_diff) <= 100) {
                                blink = !blink;
                            } else {
                                blink = false;
                            }
                            if (Math.abs(x_diff) <= 60 && Math.abs(y_diff) <= 60) {
                                for (stage = 1; stage < 8; stage++) {
                                    if (stage < 6) {
                                        shotBombCake(stage);
                                        if (inside == true) {
                                            break;
                                        }
                                    }
                                    Thread.sleep(100);
                                }
                                if (inside == true) {
                                    Bomb bb = new Bomb(player.getX(), player.getY(), 1, false);
                                    mp.bs.add(bb);
                                    if (player.defense == false) {
                                        player.isLive = false;
                                    }
                                    inside = false;
                                }

                                cleanLocation();
                                isLive = false;
                            }
                            Thread.sleep(20);
                            break;
                        case 5: // Walking Cake
                            x_diff = player.getX() - getX();
                            y_diff = player.getY() - getY();
                            if (x_diff < 200 && y_diff < 200) {
                                if (compAbs(x_diff, y_diff)) {
                                    if (y_diff > 0) {
                                        moveDown();
                                    } else if (y_diff < 0) {
                                        moveUp();
                                    }
                                    if (x_diff > 150 || (x_diff > -120 && x_diff < 0)) {
                                        moveRight();
                                    } else if (x_diff < -150 || (x_diff < 120 && x_diff > 0)) {
                                        moveLeft();
                                    }
                                    if (x_diff > 0) {
                                        setDrect(1);
                                    } else {
                                        setDrect(3);
                                    }
                                } else {
                                    if (x_diff > 0) {
                                        moveRight();
                                    } else if (x_diff < 0) {
                                        moveLeft();
                                    }
                                    if (y_diff > 150 || (y_diff > -120 && y_diff < 0)) {
                                        moveDown();
                                    } else if (y_diff < -150 || (y_diff < 120 && y_diff > 0)) {
                                        moveUp();
                                    }
                                    if (y_diff > 0) {
                                        setDrect(2);
                                    } else {
                                        setDrect(0);
                                    }
                                }
                                s = (int) (Math.random() * 100);
                                if (s > 90) {
                                    if (mybs.size() < 5) {
                                        shot();
                                    }
                                }
                            } else {
                                drect = (int) (Math.random() * 4);
                                switch (drect) {
                                case 0:
                                    step = (int) (Math.random() * 30);
                                    for (int i = 0; i < step; i++) {
                                        if (upValid() == 0) {
                                            break;
                                        }
                                        moveUp();
                                        Thread.sleep(20);
                                    }
                                    break;
                                case 1:
                                    step = (int) (Math.random() * 30);
                                    for (int i = 0; i < step; i++) {
                                        if (rightValid() == 0) {
                                            break;
                                        }
                                        moveRight();
                                        Thread.sleep(20);
                                    }
                                    break;
                                case 2:
                                    step = (int) (Math.random() * 30);
                                    for (int i = 0; i < step; i++) {
                                        if (downValid() == 0) {
                                            break;
                                        }
                                        moveDown();
                                        Thread.sleep(20);
                                    }
                                    break;
                                case 3:
                                    step = (int) (Math.random() * 30);
                                    for (int i = 0; i < step; i++) {
                                        if (leftValid() == 0) {
                                            break;
                                        }
                                        moveLeft();
                                        Thread.sleep(20);
                                    }
                                    break;
                                }
                            }
                            Thread.sleep(20);
                            break;
                        case 6: // Cthulhu
                            if (stage == 0) {
                                x_diff = (player.getX() + player.getWidth() / 2) - (getX() + getWidth() / 2);
                                y_diff = (player.getY() + player.getHeight() / 2) - (getY() + getHeight() / 2);
                                if ((x_diff < 0 && leftValid() == 0 || (x_diff > 0 && rightValid() == 0))) {
                                    moveDown();
                                    setDrect(2);
                                } else {
                                    if (compAbs(x_diff, y_diff)) {
                                        if (y_diff > 0) {
                                            moveDown();
                                            setDrect(2);
                                        } else if (y_diff < 0) {
                                            moveUp();
                                            setDrect(0);
                                        }
                                        if (Math.abs(x_diff) < 150) {
                                            if (x_diff < 0) {
                                                moveRight();
                                                setDrect(3);
                                            } else {
                                                moveLeft();
                                                setDrect(1);
                                            }
                                        } else if (Math.abs(x_diff) > 200) {
                                            if (x_diff < 0) {
                                                moveLeft();
                                                setDrect(3);
                                            } else {
                                                moveRight();
                                                setDrect(1);
                                            }
                                        }
                                    } else {
                                        if (x_diff > 0) {
                                            moveRight();
                                            setDrect(1);
                                        } else if (x_diff < 0) {
                                            moveLeft();
                                            setDrect(3);
                                        }
                                        if (Math.abs(y_diff) < 250) {
                                            if (y_diff < 0) {
                                                moveDown();
                                                setDrect(0);
                                            } else {
                                                moveUp();
                                                setDrect(2);
                                            }
                                        } else if (Math.abs(y_diff) > 300) {
                                            if (y_diff < 0) {
                                                moveUp();
                                                setDrect(0);
                                            } else {
                                                moveDown();
                                                setDrect(2);
                                            }
                                        }
                                    }
                                    if (x_diff == 0) {
                                        if (y_diff < 0) {
                                            setDrect(0);
                                        } else {
                                            setDrect(2);
                                        }
                                        Thread.sleep(1000);
                                        stage++;
                                        tempX = x_diff;
                                        tempY = y_diff;
                                    }
                                    if (y_diff == 0) {
                                        if (x_diff < 0) {
                                            setDrect(3);
                                        } else {
                                            setDrect(1);
                                        }
                                        Thread.sleep(1000);
                                        stage++;
                                        tempX = x_diff;
                                        tempY = y_diff;
                                    }
                                }

                                s = (int) (Math.random() * 100);
                                if (s > 95) {
                                    shot();
                                }
                                Thread.sleep(20);
                            } else if (stage == 1) {
                                if (tempX == 0) {
                                    if (tempY < 0) {
                                        while (upValid() != 0) {
                                            moveUp();
                                            setDrect(0);
                                            Thread.sleep(10);
                                        }
                                    } else {
                                        while (downValid() != 0) {
                                            moveDown();
                                            setDrect(2);
                                            Thread.sleep(10);
                                        }
                                    }
                                } else {
                                    if (tempX < 0) {
                                        while (leftValid() != 0) {
                                            moveLeft();
                                            setDrect(3);
                                            Thread.sleep(10);
                                        }
                                    } else {
                                        while (rightValid() != 0) {
                                            moveRight();
                                            setDrect(1);
                                            Thread.sleep(10);
                                        }
                                    }
                                }
                                if (nearWall()) {
                                    stage++;
                                } else {
                                    stage--;
                                }
                            } else if (stage == 2) {
                                defense = false;
                                Thread.sleep(1000);
                                defense = true;
                                stage = 0;
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}