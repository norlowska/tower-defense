package towerdefense.view;

import javafx.stage.Screen;
import towerdefense.document.Document;

import javax.swing.*;
import java.awt.*;

public class GUIGameView extends GameView {
    private JPanel panel;

    public GUIGameView(Document document) {
        super(document, "GUI");
        panel = new GUIGamePanel();
    }

    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayDetails() {

    }

    @Override
    protected void displayMap() {

    }

    @Override
    protected void displayWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setLayout(new GridLayout(1,1,0,0));
                window.setContentPane(panel);
                window.setVisible(true);
            }
        });
    }

    @Override
    protected void handleInput(){
        
    }
}
