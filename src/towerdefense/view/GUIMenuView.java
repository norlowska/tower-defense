package towerdefense.view;

import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMenuView extends MenuView {
    private JPanel panel;
    private JButton PLAYERSELECTButton;
    private JButton EXITButton;
    private JButton PLAYButton;
    private JLabel greeting;
    private CurrentPlayer currentPlayer;

    public GUIMenuView(Document document) {
        super(document);
        currentPlayer = document.getCurrentPlayer();
    }

    @Override
    protected void displayGreeting() {
        greeting.setText("Hello, " + currentPlayer.getNickname() + "!");
    }

    @Override
    protected void displayOptions() {
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //start game play
            }
        });
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        PLAYERSELECTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                document.switchToView(new GUIPlayerSelectView(document));
            }
        });
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
