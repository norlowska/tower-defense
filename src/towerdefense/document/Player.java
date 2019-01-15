package towerdefense.document;

import towerdefense.document.towers.ArcherTower;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    protected String nickname;
    protected List<Tower> towers;
    protected Map lastMap;
    protected int money;
    private static Player instance = new Player();

    public Player(String nickname, int money, Map lastMap, List<Tower> towers) {
        this.nickname = nickname;
        this.money = money;
        this.lastMap = lastMap;
        this.towers = towers;
    }

    public Player(String nickname, int money) {
        this.nickname = nickname;
        this.money = money;
        this.lastMap = new Map(); //pierwsza mapa w kolejności
        this.towers = new ArrayList<Tower>();
    }

    private Player() {
    }

    public static Player getInstance() { return instance; }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
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
            File file = new File(nickname + ".txt");
            FileWriter write = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(nickname);
            bw.newLine();
            String result = new String();
            for(Tower x: towers) {
                result = result + x.getName();
            }
            bw.write(result);

        }catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + nickname + ".txt" + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + nickname + ".txt "+ "'");
        }
    }

}
