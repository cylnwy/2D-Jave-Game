package model;

public class Bullet {
    private int x;
    private int y;
    private int speed;
    private int drect;
    private int type;
    public boolean isLive = true;
    private map map;
    private Thread thread;

    // Potagonist: 11 & 12, Mad Plant: 31, Boom Cake: 41, Walking Cake: 51, Cthulhu: 61
    public Bullet(int x, int y, int speed, int drect, int type, map map) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.drect = drect;
        this.type = type;
        this.map = map;
        thread = new Thread(new BulletThread());
        thread.start();
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

    public int getType() {
        return type;
    }

    public int getDrect() {
        return drect;
    }

    public Thread getThread() {
        return thread;
    }

    class BulletThread implements Runnable {
        @Override
        public void run() {
            while (isLive) {
                try {
                    switch (drect) {// 判断方向坐标移动
                    case 0:
                        y -= speed;
                        break;
                    case 1:
                        x += speed;
                        break;
                    case 2:
                        y += speed;
                        break;
                    case 3:
                        x -= speed;
                        break;
                    }
                    if (getType() != 12) {
                        if (map.location[x][y] == 11) {
                            isLive = false;
                            getThread().interrupt();
                        }
                    } else {
                        if (getX() <= 40 || getX() >= 1100 - 40 - 32 || getY() <= 40 || getY() >= 700 - 40 -32) {
                            isLive = false;
                            getThread().interrupt();
                        }
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}