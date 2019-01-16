/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

//import towerdefense.document.towers.EarthTower;

import towerdefense.document.Document;
import towerdefense.document.Game;

public class TowerDefense {

    /**
     * @param args the command line arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //EarthTower lt = new EarthTower();
       // Game game = new Game();
        //game.start();
        //Map map = new Map();
        //map.readMapLayout("map1");
        Document document = new Document();
        document.notifyView();
        return;
    }

}
