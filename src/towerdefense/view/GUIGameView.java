package towerdefense.view;

import javafx.stage.Screen;
import towerdefense.document.Document;

import javax.swing.*;

public class GUIGameView extends GameView {
    private JPanel panel;

    public GUIGameView(Document document) {
        super(document, "GUI");

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
                window.setContentPane(panel);
                window.setVisible(true);
            }
        });
    }
}
