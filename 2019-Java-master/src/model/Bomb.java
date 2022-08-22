package model;

public class Bomb {
    private int x;
    private int y;
    private int type;
    private boolean isPlayer;
    public boolean isLive = true;
    private int time = 9;

    // normal explosion: 1; Bomb Cake explosion: 2
    public Bomb(int x, int y, int type, boolean isPlayer) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.isPlayer = isPlayer;
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

    public int getTime() {
        return time;
    }

    public boolean getIsPlayer() {
        return isPlayer;
    }
    
    public void livedown(){
        if(time > 0){
            time--;
        } else {
            isLive = false;
        }
    }
}