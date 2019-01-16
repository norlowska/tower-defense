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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayerName = newPlayerTextField.getText();
                if(!newPlayerName.equals("")){
                    comboBox.addItem(newPlayerName);
//                    playersList.add(new Player(...)); //dodać do gry (listy graczy) obiekt nowego gracza
                }
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
                System.out.println(newPlayerName);
                //aktualnego gracza zamienić na tego z nazwą -> newPlayerName (obiekt jego został już wcześniej stworzony, wystarczy po liście graczy odnaleźć nazwę pasującą do newPlayerGame)
                //jak to zrobić z poziomu tej klasy -> nie mamy dostępu do game

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
