package towerdefense.view;

import towerdefense.document.CurrentPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUIMenuView extends MenuView {
    private JFrame window;
    private JPanel panel;
    private JButton PLAYERSELECTButton;
    private JButton SHOPButton;
    private JButton EXITButton;
    private JButton PLAYButton;
    private JLabel greeting;
    private CurrentPlayer currentPlayer = document.getPlayer();

    public GUIMenuView () {
        window = new JFrame("Tower-Defense MENU");
        displayOptions();
    }

    @Override
    protected void displayGreeting() {
        greeting.setText("Hello, " + currentPlayer.getNickname() + "!"); //dlaczego nie działa?
    }

    @Override
    protected void displayOptions() {
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        SHOPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View shop = new GUIShopView();
                shop.render();
            }
        });
    }

    @Override
    protected void displayWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setContentPane(new GUIMenuView().panel);
                window.setSize(900 , 800);
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
