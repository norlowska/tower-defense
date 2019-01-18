package towerdefense.view;

import javafx.stage.Screen;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Tower;
import towerdefense.document.towers.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIGameView extends GameView {
    private CurrentPlayer currentPlayer;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JPanel mainPanel;
    private JLabel towersLabel;
    private JPanel mapPanel;
    private JPanel infoPanel;
    private JLabel moneyLabel;
    private JLabel playerNameLabel;
    private JLabel gameGoalLabel;
    private List<Tower> towers;

    public GUIGameView(Document document) {
        super(document, "GUI");
        currentPlayer = document.getCurrentPlayer();
        towers = new ArrayList<>();
        towers.add(new ArcherTower());
        towers.add(new EarthTower());
        towers.add(new ElectricTower());
        towers.add(new FireTower());
        towers.add(new ForceTower());
        towers.add(new IceTower());
        towers.add(new NuclearTower());
        towers.add(new WaterTower());
    }

    @Override
    protected void displayBoughtTowers() {
        //na każdy button ustawić obrazek z data/towersPNG
    }

    @Override
    protected void displayDetails() {
        System.out.println(currentPlayer.getNickname());
        playerNameLabel.setText("Hello, " + currentPlayer.getNickname() + "!"); //dlaczego nie wyświetla?
        moneyLabel.setText("Your money: " + currentPlayer.getMoney());
        gameGoalLabel.setText("Your goal is to defend \n your territory against enemies." +
                "\nSelect tower with function keys\n and place it on map,\n along enemies' path off attack," +
                "\n using arrows.\n Enter accepts your choice.\nSurvive all waves of enemies attacks\n to win map.");
    }

    @Override
    protected void displayMap() {

    }

    @Override
    protected void displayWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setSize(1050, 600);
                window.setContentPane(mainPanel);
                window.setVisible(true);
            }
        });
    }

    @Override
    protected void handleInput() {
        
    }
}
