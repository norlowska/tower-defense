package towerdefense.view;

import towerdefense.GameMap;

import java.io.IOException;

public class ConsoleGameView extends GameView implements ConsoleView{
    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayMap() {
        screen.clear();
        try {
            screen.refresh();
            screen.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  GameMap gameMap = new GameMap("map1", terminal, screen);
       // gameMap.displayMap();
        //displayMap();

    }

    @Override
    protected void displayWindow() {

    }
}
