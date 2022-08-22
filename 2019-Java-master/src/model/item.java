package model;

public class item {
    private int type;
    // addArmour: 0, addLife : 1; Book : 2, Ruby : 3, Crystal : 5
    private int x;
    private int y;
    private int width;
    private int height;
    public int score;

    public item(int type) {
        this.type = type;
        setSize();
    }

    private void setSize() {
        switch (type) {
        case 0:
            width = 20;
            height = 20;
            score = 30;
            break;
        case 1:
            width = 20;
            height = 20;
            score = 30;
            break;
        case 2:
            width = 20;
            height = 20;
            score = 30;
            break;
        case 3:
            width = 20;
            height = 20;
            score = 80;
            break;
        case 5:
            width = 20;
            height = 20;
            break;
        }
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}