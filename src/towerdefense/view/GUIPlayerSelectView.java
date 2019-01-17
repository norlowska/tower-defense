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
    private int exist = 0;

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
                try {
                    if (!newPlayerName.equals("")) {
                        for (Player p : playersList) {
                            if (p.getNickname().equals(newPlayerName)) {
                                exist++;
                            }
                        }
                        if (exist == 0) {
                            comboBox.addItem(newPlayerName);
                            document.addPlayer(new Player(newPlayerName, 500, null));
                            JOptionPane.showMessageDialog(window,
                                    "You have just created a new player with the nickname: "+newPlayerName);
                        } else {
                            JOptionPane.showMessageDialog(window,
                                    "Such a player already exists. Create a player with a different name!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        exist = 0;
                    } else {
                        JOptionPane.showMessageDialog(window,
                                "Text field is empty!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
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
