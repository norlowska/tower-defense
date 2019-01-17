/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

//import towerdefense.document.towers.EarthTower;

import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Game;
import towerdefense.view.ConsoleMenuView;
import towerdefense.view.GUIMenuView;
import towerdefense.view.GUIPlayerSelectView;
import towerdefense.view.View;

public class TowerDefense {

    /**
     * @param args the command line arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //Game game = new Game();
        Document doc = new Document();

        //Console
//        View menuConsole = new ConsoleMenuView(doc);
//        doc.switchToView(menuConsole);
        //GUI
        View menuGUI = new GUIMenuView(doc);
        doc.switchToView(menuGUI);

        return;
    }

}
