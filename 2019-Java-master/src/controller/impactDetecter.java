package controller;

import model.*;
import view.*;

public class impactDetecter {
    private myPanel mp;

    public impactDetecter(myPanel mp) {
        this.mp = mp;
    }

    // whether the character is hit
    public boolean isHitEnemy(Bullet b, character ek) {
        int mainX = 0;
        int mainY = 0;
        if (b.getType() == 12) {
            switch (b.getDrect()) {
            case 0:
                mainX = b.getX() + 8;
                mainY = b.getY() + 4;
                break;
            case 1:
                mainX = b.getX() + 12;
                mainY = b.getY() + 8;
                break;
            case 2:
                mainX = b.getX() + 8;
                mainY = b.getY() + 12;
                break;
            case 3:
                mainX = b.getX() + 4;
                mainY = b.getY() + 8;
                break;
            }
        } else if (b.getType() == 31) {
            mainX = b.getX();
            mainY = b.getY();
        } else if (b.getType() != 41) {
            mainX = b.getX() + 3;
            mainY = b.getY() + 3;
        }

        if (mainX >= ek.getX() && mainX <= ek.getX() + ek.getWidth() && mainY >= ek.getY()
                && mainY <= ek.getY() + ek.getHeight()) {
            int i = -1;

            if (ek.getType() == 1) {
                for (i = 0; i < mp.bs.size(); i++) {
                    if (mp.bs.get(i).getIsPlayer()) {
                        break;
                    }
                }
            }
            b.isLive = false;
            if (!ek.defense && (i == -1 || i == mp.bs.size())) {
                ek.isLive = false;
                if (ek.getType() != 1) {
                    ek.getThread().interrupt();
                }
                Bomb bb = new Bomb(ek.getX(), ek.getY(), 1, ek.getType() == 1);
                mp.bs.add(bb);
                return true;
            }
        }
        return false;
    }

    public boolean impactLogic() {
        // whether the player's bullets hit the enemies
        for (int i = 0; i < mp.player.mybs.size(); i++) {
            Bullet mb = mp.player.mybs.get(i);
            if (mb.isLive) {
                for (int j = 0; j < mp.enemies.size(); j++) {
                    character enemy = mp.enemies.get(j);
                    if (enemy.isLive) {
                        if (isHitEnemy(mb, enemy)) {
                            enemy.cleanLocation();
                            // release the item if the enemy is killed
                            if (mp.aimedItems.size() != 0) {
                                int itemIndex = (int) (Math.random() * mp.aimedItems.size());
                                item item = mp.aimedItems.get(itemIndex);
                                mp.aimedItems.remove(itemIndex);
                                if (item.getType() != 4) {
                                    item.setX(enemy.getX());
                                    item.setY(enemy.getY());
                                    mp.items.add(item);
                                }
                            }
                            mp.player.score += enemy.score;
                        }
                    }
                    if (enemy.getType() == 6 && !enemy.isLive) {
                        mp.window.finishGame();
                    }
                }
            }
        }

        // whether the enemies' bullets hit the player
        for (int i = 0; i < mp.enemies.size(); i++) {
            character enemy = mp.enemies.get(i);
            for (int j = 0; j < enemy.mybs.size(); j++) {
                Bullet eb = enemy.mybs.get(j);
                if (eb.getType() == 31 && enemy.stage == 1) {
                    continue;
                }
                if (eb.isLive) {
                    isHitEnemy(eb, mp.player);
                }
            }
        }

        // whether the items are picked up
        for (int i = 0; i < mp.items.size(); i++) {
            item item = mp.items.get(i);
            int mainX = item.getX() + item.getWidth() / 2;
            int mainY = item.getY() + item.getHeight() / 2;
            if (mainX >= mp.player.getX() && mainX <= mp.player.getX() + mp.player.getWidth() && mainY >= mp.player.getY()
                    && mainY <= mp.player.getY() + mp.player.getHeight()) {
                mp.items.remove(i);

                int type = item.getType();
                if (type != 4) {
                    if (type == 5) {
                        type--;
                    }
                    mp.player.myInventory[type]++;
                    mp.player.score += item.score;
                }
            }
        }

        // kill the player if the enemies near
        if (mp.player.nearEnemy()) {
            if (!mp.player.defense) {
                mp.player.isLive = false;
            }
        }

        // if the player is hit
        if (!mp.player.isLive) {
            if (mp.player.getArmour() > 0) {
                mp.player.reduceArmour();
            } else if (mp.player.getLife() > 0) {
                mp.player.reduceLife();
            } else {
                mp.window.loseGame();
                return false;
            }

            mp.player.reborn = true;
            mp.player.setDefense(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int i = 0;
                        while (i < 20){
                            i++;
                            mp.player.blink = !mp.player.blink;
                            Thread.sleep(100);
                        }
                        mp.player.blink = false;
                        mp.player.defense = false;
                        mp.player.reborn = false;
                        Thread.currentThread().interrupt();
                    } catch (InterruptedException e) {
                        
                    }
                }
            }).start();
            mp.player.isLive = true;
        }
        return true;
    }
}