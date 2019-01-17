package towerdefense.document;

import towerdefense.document.towers.ArcherTower;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    protected String nickname;
    protected Map lastMap;
    protected int money;

    public Player(String nickname, int money) {
        this.nickname = nickname;
        this.money = money;
        this.lastMap = new Map("map1");
    }

    public Player(String nickname, int money, Map lastMap) {
        this.nickname = nickname;
        this.money = money;
        this.lastMap = lastMap;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Map getLastMap() {
        return lastMap;
    }

    public void setLastMap(Map lastMap) {
        this.lastMap = lastMap;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void savePlayer() {
        try {
            File file = new File("data/players.txt");
            FileWriter write = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(write);
            String result = nickname + " " + money + " " +lastMap.getMapName();
            bw.write(result);
        }catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + nickname + ".txt" + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + nickname + ".txt "+ "'");
        }
    }

}
