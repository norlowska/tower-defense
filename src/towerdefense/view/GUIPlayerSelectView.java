package towerdefense.view;

import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIPlayerSelectView extends PlayerSelectView {
    private JPanel panel;
    private JLabel currentPlayerNameLabel;
    private JComboBox comboBox;
    private JButton done;
    private JTextField newPlayerTextField;
    private JButton addButton;
    private String newPlayerName;
    private CurrentPlayer currentPlayer;
    private ArrayList<Player> playersList;

    public GUIPlayerSelectView(Document document) {
        super(document);
        currentPlayer = document.getCurrentPlayer();
        playersList = document.getPlayers();
    }

    @Override
    protected void displayContent() {
        for (Player p : playersList) {
            comboBox.addItem(p.getNickname());
        }

        currentPlayerNameLabel.setText("Current player: " + currentPlayer.getNickname());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayerName = newPlayerTextField.getText();
//                if (!newPlayerName.equals("")) {
//                    comboBox.addItem(newPlayerName);
//                    document.addPlayer(new Player(newPlayerName, 500, null));
//                }
                try {
                    if (!newPlayerName.equals("")) {
                        for (Player p : playersList) {
                            if (!p.getNickname().equals(newPlayerName)) {
                                comboBox.addItem(newPlayerName);
                                document.addPlayer(new Player(newPlayerName, 500, null));
                            } else {
                                System.out.println("Such nickname already exists");
                            }
                        }
                    }
                } catch (java.util.ConcurrentModificationException exception) {
                    exception.getMessage();
                }
                newPlayerTextField.setText("");
                newPlayerTextField.grabFocus();
            }
        });

        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayerName = comboBox.getSelectedItem().toString();
                System.out.println(newPlayerName);
                for (Player p : playersList) {
                    if (p.getNickname().equals(newPlayerName)) {
                        document.setCurrentPlayer(p);
                    }
                }

                //powrót do Menu
                window.setVisible(false);
                document.switchToView(new GUIMenuView(document));
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
                newPlayerTextField.setEditable(true);
            }
        });
    }
}
