package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.*;
import view.myPanel;

public class gameStage {
    private Vector<character> enemies1 = new Vector<character>();
    private Vector<character> enemies2 = new Vector<character>();
    private Vector<character> enemies3 = new Vector<character>();
    private Vector<character> enemies4 = new Vector<character>();
    private Vector<character> enemies5 = new Vector<character>();
    private map map1 = new map();
    private map map2 = new map();
    private map map3 = new map();
    private map map4 = new map();
    private map map5 = new map();
    private Image mapImg1, mapImg2, mapImg3, mapImg4, mapImg5;
    private Vector<item> items1 = new Vector<item>();
    private Vector<item> items2 = new Vector<item>();
    private Vector<item> items3 = new Vector<item>();
    private Vector<item> items4 = new Vector<item>();
    private Vector<item> items5 = new Vector<item>();
    private int x1, y1, x2, y2, x3, y3, x4, y4, x5, y5;
    private myPanel mp;
    private int mapStage;
    private int loadedStage = 0;
    public BufferedImage GameMap = null;

    public gameStage(myPanel mp) {
        this.mp = mp;
    }

    public void setMapStage(int newStage) {
        if (newStage != -1) {
            if (newStage == 5 && mp.player.myInventory[4] == 0) {
                return;
            }
            suspendEnemies();
            saveOldVectors();
            saveFinalPosition();
            saveMap();
            
            this.mapStage = newStage;

            if (loadedStage < newStage) {
                loadedStage = newStage;
                initStage();
            } else {
                loadMap();
                loadNewVectors();
                resumeEnemies();
            }

            for (int i = 0; i < mp.player.mybs.size(); i++) {
                mp.player.mybs.get(i).isLive = false;
            }
            for (int i = 0; i < mp.bs.size(); i++) {
                mp.bs.get(i).isLive = false;
            }
        } else {
            for (int i = 0; i < mp.enemies.size(); i++) {
                mp.enemies.get(i).getThread().interrupt();
            }
            for (int i = 0; i < mp.player.mybs.size(); i++) {
                mp.player.mybs.get(i).isLive = false;
            }
            for (int i = 0; i < mp.bs.size(); i++) {
                mp.bs.get(i).isLive = false;
            }
        }
    }

    // go to cheat mode with pg_dn button
    public void cheatMode() {
        for (int i = 0; i < mp.aimedItems.size(); i++) {
            item item = mp.aimedItems.get(i);
            int type = item.getType();
            if (type != 4) {
                if (type == 5) {
                    type--;
                }
                mp.player.myInventory[type]++;
            }
        }
        setMapStage(5);
    }

    public int getMapStage() {
        return mapStage;
    }

    private void initStage() {
        String[] paths = getPaths();
        loadMap(paths[0],paths[1]);
        mp.Loadedmap.initialMap(GameMap, mp);
    }

    private void loadMap(String texturedMap,String B_WMap) {
        mp.levelMap = new ImageIcon(texturedMap).getImage();
        try {
            GameMap = ImageIO.read(new FileInputStream(B_WMap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // save mp.map into the local map
    private void saveLocation(map map) {
        for (int i = 0; i < map.mapWidth; i++) {
            for (int j = 0; j < map.mapHeight; j++) {
                map.location[i][j] = mp.Loadedmap.location[i][j];
            }
        }
    }

    // save the fianl positon
    private void saveFinalPosition() {
        switch (getMapStage()) {
        case 1:
            x1 = mp.player.getX();
            y1 = mp.player.getY();
            break;
        case 2:
            x2 = mp.player.getX();
            y2 = mp.player.getY();
            break;
        case 3:
            x3 = mp.player.getX();
            y3 = mp.player.getY();
            break;
        case 4:
            x4 = mp.player.getX();
            y4 = mp.player.getY();
            break;
        case 5:
            x5 = mp.player.getX();
            y5 = mp.player.getY();
            break;
        }
    }

    private void saveMap() {
        switch (getMapStage()) {
        case 1:
            saveLocation(map1);
            mapImg1 = mp.levelMap;
            break;
        case 2:
            saveLocation(map2);
            mapImg2 = mp.levelMap;
            break;
        case 3:
            saveLocation(map3);
            mapImg3 = mp.levelMap;
            break;
        case 4:
            saveLocation(map4);
            mapImg4 = mp.levelMap;
            break;
        case 5:
            saveLocation(map5);
            mapImg5 = mp.levelMap;
            break;
        }
    }

    // load location
    private void loadLocation(map map) {
        for (int i = 0; i < map.mapWidth; i++) {
            for (int j = 0; j < map.mapHeight; j++) {
                mp.Loadedmap.location[i][j] = map.location[i][j];
            }
        }
    }
    
    // load map
    private void loadMap() {
        switch (getMapStage()) {
        case 1:
            loadLocation(map1);
            mp.levelMap = mapImg1;
            mp.player.setX(x1);
            mp.player.setY(y1);
            break;
        case 2:
            loadLocation(map2);
            mp.levelMap = mapImg2;
            mp.player.setX(x2);
            mp.player.setY(y2);
            break;
        case 3:
            loadLocation(map3);
            mp.levelMap = mapImg3;
            mp.player.setX(x3);
            mp.player.setY(y3);
            break;
        case 4:
            loadLocation(map4);
            mp.levelMap = mapImg4;
            mp.player.setX(x4);
            mp.player.setY(y4);
            break;
        case 5:
            loadLocation(map5);
            mp.levelMap = mapImg5;
            mp.player.setX(x5);
            mp.player.setY(y5);
            break;
        }
    }

    private void saveVectors(Vector<character> ems, Vector<item> its) {
        ems.removeAllElements();
        its.removeAllElements();
        for (int i = 0; i < mp.enemies.size(); i++) {
            ems.add(mp.enemies.get(i));
        }
        for (int i = 0; i < mp.items.size(); i++) {
            its.add(mp.items.get(i));
        }
        mp.enemies.removeAllElements();
        mp.items.removeAllElements();
    }

    private void saveOldVectors() {
        switch (getMapStage()) {
        case 1:
            saveVectors(enemies1, items1);
            break;
        case 2:
            saveVectors(enemies2, items2);
            break;
        case 3:
            saveVectors(enemies3, items3);
            break;
        case 4:
            saveVectors(enemies4, items4);
            break;
        case 5:
            saveVectors(enemies5, items5);
            break;
        }
    }

    private void loadVectors(Vector<character> ems, Vector<item> its) {
        for (int i = 0; i < ems.size(); i++) {
            mp.enemies.add(ems.get(i));
        }
        for (int i = 0; i < its.size(); i++) {
            mp.items.add(its.get(i));
        }
    }

    private void loadNewVectors() {
        switch (getMapStage()) {
        case 1:
            loadVectors(enemies1, items1);
            break;
        case 2:
            loadVectors(enemies2, items2);
            break;
        case 3:
            loadVectors(enemies3, items3);
            break;
        case 4:
            loadVectors(enemies4, items4);
            break;
        case 5:
            loadVectors(enemies5, items5);
            break;
        }
    }

    private String[] getPaths() {
        String[] paths = new String[2];
        switch (getMapStage()) {
        case 1:
            paths[0] = "./res/texture/map/map1.png";
            paths[1] = "./res/texture/map/map1_1.png";
            break;
        case 2:
            paths[0] = "./res/texture/map/map2.png";
            paths[1] = "./res/texture/map/map2_1.png";
            break;
        case 3:
            paths[0] = "./res/texture/map/map3.png";
            paths[1] = "./res/texture/map/map3_1.png";
            break;
        case 4:
            paths[0] = "./res/texture/map/map4.png";
            paths[1] = "./res/texture/map/map4_1.png";
            break;
        case 5:
            paths[0] = "./res/texture/map/mapBoss.png";
            paths[1] = "./res/texture/map/mapBoss_1.png";
            break;
        }
        return paths;
    }

    public void suspendEnemies() {
        for (int i = 0; i < mp.enemies.size(); i++) {
            mp.enemies.get(i).suspend();
        }
    }

    public void resumeEnemies() {
        for (int i = 0; i < mp.enemies.size(); i++) {
            mp.enemies.get(i).resume();
        }
    }
}