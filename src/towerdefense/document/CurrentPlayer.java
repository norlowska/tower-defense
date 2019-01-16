package towerdefense.document;

import towerdefense.document.towers.ArcherTower;

import java.util.ArrayList;

public class CurrentPlayer extends Player {
    private static CurrentPlayer ourInstance = new CurrentPlayer();

    public static CurrentPlayer getInstance() {
        return ourInstance;
    }

    private CurrentPlayer() {
        //super("Marek", 300); this.lastMap = null; this.towers = new ArrayList<Tower>();
        //towers.add(new ArcherTower());
        super(null, 0);
    }

    public void setCurrentPlayer(Player player) {
        this.nickname = player.nickname;
        this.money = player.money;
        this.lastMap = player.lastMap;
    }
}
