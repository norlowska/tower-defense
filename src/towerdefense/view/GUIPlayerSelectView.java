package towerdefense.view;

import towerdefense.document.CurrentPlayer;
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
    private CurrentPlayer currentPlayer = document.getCurrentPlayer();
    private ArrayList<Player> playersList = document.getPlayers();

    @Override
    protected void displayContent() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayerName = newPlayerTextField.getText();
                comboBox.addItem(newPlayerName);
//                playersList.add(new Player(...)); //dodać do dokumentu gracza
                newPlayerTextField.setText("");
                newPlayerTextField.grabFocus();
            }
        });

        currentPlayerNameLabel.setText("Current player: " + currentPlayer.getNickname());

        for (Player pl : playersList) {
            comboBox.addItem(pl.getNickname());
        }

        //mockup
        comboBox.addItem("One");
        comboBox.addItem("Two");

        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayerName = comboBox.getSelectedItem().toString();
//                System.out.println(newPlayerName);
                //aktualnego gracza zamienić na tego z nazwą -> newPlayerName


                //powrót do Menu

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
