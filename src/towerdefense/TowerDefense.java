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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class TowerDefense {
    /**
     * @param args the command line arguments
     * @throws InterruptedException
     */
    private Document document;
    private JFrame introFrame;
    private JPanel panel;
    private JButton CONSOLEButton;
    private JButton guiButton;
    private JButton exitButton;

    public TowerDefense() {
        introFrame = new JFrame("Tower-Defense");
        document = new Document();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                introFrame.setSize(400, 400);
                introFrame.setResizable(false);
                introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                introFrame.setLocationRelativeTo(null);
                introFrame.setContentPane(panel);
                introFrame.setVisible(true);
            }
        });
        CONSOLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introFrame.setVisible(false);
                View menuConsole = new ConsoleMenuView(document);
                document.switchToView(menuConsole);
            }
        });
        guiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introFrame.setVisible(false);
                View menuGUI = new GUIMenuView(document);
                document.switchToView(menuGUI);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introFrame.setVisible(false);
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
//        1
//        TowerDefense towerDefense = new TowerDefense();


//        2
        //Game game = new Game();
        Document doc = new Document();
        //Console
        View menuConsole = new ConsoleMenuView(doc);
        doc.switchToView(menuConsole);
        //GUI
//        View menuGUI = new GUIMenuView(doc);
//        doc.switchToView(menuGUI);

        return;
    }

}
