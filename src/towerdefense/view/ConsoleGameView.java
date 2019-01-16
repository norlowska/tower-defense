package towerdefense.view;


import towerdefense.document.Document;

import java.io.IOException;

public class ConsoleGameView extends GameView {

    public ConsoleGameView(Document document) {
        this.document = document;
    }

    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayDetails() {

    }

    @Override
    protected void displayMap() {
        try {
            screen.clear();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void displayWindow() {

    }
}
