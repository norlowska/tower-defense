package towerdefense.view;

import towerdefense.document.Tower;

import javax.swing.*;

public class GUIShopView extends ShopView {
    private JFrame window;
    private JPanel panel;

    public GUIShopView() {
        window = new JFrame("Tower-Defense SHOP");
    }

    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayDetails() {

    }

    @Override
    protected void displayTowers() {

    }

    @Override
    protected void displayTower(Tower tower) {

    }

    @Override
    protected void displayWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                window.setContentPane(new GUIShopView().panel); //do zrobienia
                window.setSize(900, 800);
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
            }
        });
    }
}
