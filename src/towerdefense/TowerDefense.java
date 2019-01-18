/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

import towerdefense.document.Document;
import towerdefense.view.ConsoleMenuView;
import towerdefense.view.GUIMenuView;
import towerdefense.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TowerDefense {
    /**
     * @param args the command line arguments
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        Document document = new Document();
        if(args.length!= 0 && args[0].equals("GUI")) {
            //GUI
             View menuGUI = new GUIMenuView(document);
             document.switchToView(menuGUI);
        } else {
            View menuConsole = new ConsoleMenuView(document);
            document.switchToView(menuConsole);
        }
    }
}
