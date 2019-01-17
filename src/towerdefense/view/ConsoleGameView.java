package towerdefense.view;


import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import towerdefense.document.*;

import java.io.IOException;
import java.util.Iterator;

public class ConsoleGameView extends GameView {
    private Map currentMap;
    public ConsoleGameView(Document document) {
        super(document);
    }

    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayDetails() {

    }

    @Override
    protected void displayMap() {
        TerminalPosition startPosition = new TerminalPosition(0,0);
        Iterator<Field> iterator = currentMap.iterator();
        int column = 0;
        Field currentField;
        while(iterator.hasNext()) {

            currentField = iterator.next();
            displayField(startPosition, currentField);
            System.out.println(column);
            System.out.println(currentField);
            if(currentField instanceof FieldRoad && ((FieldRoad) currentField).getEnemy()!=null) {
                displayEnemy(startPosition.withRelative(1,1),((FieldRoad) currentField).getEnemy());
            } else if (currentField instanceof FieldTerrain && ((FieldTerrain)currentField).getTower()!=null) {
                displayTower(startPosition.withRelative(1,1), ((FieldTerrain)currentField).getTower());
            }
            if(column == 11) {
                startPosition = startPosition.withRelativeRow(5);
                startPosition = startPosition.withColumn(0);
                column = 0;
            } else {
                column++;
                startPosition = startPosition.withRelativeColumn(6);
            }
        }
        try {
            screen.refresh();
            screen.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void displayWindow() {
        currentMap = document.getCurrentMap();
        screen.setCursorPosition(null);
        screen.clear();
    }

    private void displayField(TerminalPosition startPosition, Field field) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;
        TextImage fieldImage = new BasicTextImage(new TerminalSize(6, 5));
        TextColor color = getTextColor(field.getColor());

        for (int row = 0, i = 0; row < 5; row++) {
            for (int column = 0; column < 6; column++, i++) {
                textCharacter = new TextCharacter(' ', textGraphics.getForegroundColor(), color, SGR.BOLD);
                fieldImage.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, fieldImage);
    }

    private void displayEnemy(TerminalPosition startPosition, Enemy enemy) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;
        TextImage enemyImage = new BasicTextImage(new TerminalSize(4, 3));
        TextColor color = getTextColor(enemy.getColor());

        for (int row = 0, i = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++, i++) {
                textCharacter = new TextCharacter(' ', textGraphics.getForegroundColor(), color, SGR.BOLD);
                enemyImage.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, enemyImage);
    }

    private void displayTower(TerminalPosition startPosition, Tower tower) {
        String stringTowerIcon = tower.getIcon();
        TextColor color = getTextColor(tower.getColor());

        TextImage towerIcon = new BasicTextImage(new TerminalSize(4, 3));
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;

        for (int row = 0, i = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++, i++) {
                textCharacter = new TextCharacter(stringTowerIcon.charAt(i), color, textGraphics.getBackgroundColor(),
                        SGR.BOLD);
                towerIcon.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, towerIcon);
    }

    private TextColor getTextColor(Color color) {
        switch(color) {
            case BLACK:
                return TextColor.ANSI.BLACK;
            case BLUE:
                return TextColor.ANSI.BLUE;
            case CYAN:
                return TextColor.ANSI.CYAN;
            case GREEN:
                return TextColor.ANSI.GREEN;
            case MAGENTA:
                return TextColor.ANSI.MAGENTA;
            case RED:
                return TextColor.ANSI.RED;
            case YELLOW:
                return TextColor.ANSI.YELLOW;
            case WHITE:
                return TextColor.ANSI.WHITE;
            default:
                return TextColor.ANSI.DEFAULT;
        }
    }
}
