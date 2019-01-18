package towerdefense.view;


import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import towerdefense.document.*;
import towerdefense.document.towers.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConsoleGameView extends GameView {
    private Map currentMap;
    private CurrentPlayer currentPlayer;
    private Field selectedField;
    private Tower selectedTower;
    private final int fieldWidth = 6;
    private final int fieldHeight = 5;
    private final int characterWidth = 4;
    private final int characterHeight = 3;
    private List<Tower> towers;


    public ConsoleGameView(Document document) {
        super(document, "Console");
        currentPlayer = document.getCurrentPlayer();
        selectedField = null;
        selectedTower = null;
        towers = new ArrayList<>();
        towers.add(new ArcherTower());
        towers.add(new EarthTower());
        towers.add(new ElectricTower());
        towers.add(new FireTower());
        towers.add(new ForceTower());
        towers.add(new IceTower());
        towers.add(new NuclearTower());
        towers.add(new WaterTower());
    }

    @Override
    protected void displayBoughtTowers() {
        TerminalPosition startPosition = new TerminalPosition(0,6* fieldHeight);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.drawLine(startPosition, startPosition.withRelativeColumn(12* fieldWidth -1),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.setCharacter(startPosition.withRelativeColumn(12* fieldWidth),
                Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        startPosition = startPosition.withRelativeRow(2);
        TerminalSize boughtTowersBoxSize = null;
        try {
            boughtTowersBoxSize = new TerminalSize(terminal.getTerminalSize().getColumns(), 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = "TOWERS";
        String hint = "Press number of selected tower";
        textGraphics.putString(startPosition.withRelativeColumn((boughtTowersBoxSize.getColumns() - title.length()) / 2), title, SGR.BOLD);
        startPosition = startPosition.withRelativeRow(2);
        textGraphics.putString(startPosition.withRelativeColumn((boughtTowersBoxSize.getColumns() - hint.length()) / 2), hint, SGR.ITALIC);
        startPosition = startPosition.withRelative(6,2);
        int i = 1;

        for(Tower t : towers) {
            displayTower(startPosition, t);
            textGraphics.putString(startPosition.withRelativeRow(characterHeight + 1), 'F' + Integer.toString(i) + ' ' + t.getName());
            i++;
            startPosition = startPosition.withRelativeColumn(characterWidth + 9);
        }
    }

    @Override
    protected void displayDetails() {
        TerminalPosition startPosition = new TerminalPosition(12* fieldWidth,0);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.drawLine(startPosition, startPosition.withRelativeRow(6* fieldHeight -1),
                Symbols.DOUBLE_LINE_VERTICAL);
        TerminalSize boughtTowersBoxSize = null;
        try {
            boughtTowersBoxSize = new TerminalSize(terminal.getTerminalSize().getColumns() - startPosition.getColumn(), 6*fieldHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startPosition = startPosition.withRelativeRow(1);
        String title = "Hello, " + currentPlayer.getNickname();
        textGraphics.putString(startPosition.withRelativeColumn((boughtTowersBoxSize.getColumns() - title.length()) / 2), title, SGR.BOLD);
        startPosition = startPosition.withRelativeRow(3);
        title = "Your money: " + currentPlayer.getMoney();
        textGraphics.putString(startPosition.withRelativeColumn((boughtTowersBoxSize.getColumns() - title.length()) / 2), title, SGR.BOLD);
        startPosition = startPosition.withRelativeRow(3);
        String details = "Your goal is to defend\nyour territory against enemies.\nSelect tower with function keys\n and place it on map,\n along enemies' path of attack,\n using arrows.\n Enter accepts your choice.\nSurvive all waves of enemies attacks\n to win map.";
        String parts[] = details.split("\n");
        for(String part : parts) {
            textGraphics.putString(startPosition.withRelativeColumn((boughtTowersBoxSize.getColumns() - part.length()) / 2), part);
            startPosition = startPosition.withRelativeRow(1);
        }
        if(selectedTower != null) {
            startPosition = startPosition.withRelative((boughtTowersBoxSize.getColumns() - characterWidth) / 2,2);
            displayTower(startPosition, selectedTower);
            startPosition = startPosition.withRelative(-2, characterHeight + 1);
            textGraphics.fillRectangle(startPosition, new TerminalSize(15, 1), ' ');
            textGraphics.putString(startPosition, selectedTower.getName(), SGR.BOLD);
            textGraphics.putString(startPosition.withRelativeRow(1), "Price: " + selectedTower.getPrice());
            textGraphics.putString(startPosition.withRelativeRow(2), "Damage: " + selectedTower.getDamage());
            textGraphics.putString(startPosition.withRelativeRow(3), "Range: " + selectedTower.getRange());
        }
    }

    @Override
    protected void displayMap() {
        //document.getGame().Timer();
        TerminalPosition startPosition = new TerminalPosition(0,0);
        Iterator<Field> iterator = currentMap.iterator();
        int column = 0;
        Field currentField;
        while(iterator.hasNext()) {
            currentField = iterator.next();
            displayField(startPosition, currentField);
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

    @Override
    protected void handleInput() {
        KeyStroke keyStroke = null;
        KeyType keyType = null;
        MoveIterator<Field> moveIterator = currentMap.iteratorMove();
        while(true) {
            try {
                keyStroke = screen.readInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            keyType = keyStroke.getKeyType();
            switch (keyType){
                case F1:
                    selectedTower = towers.get(0);
                    break;
                case F2:
                    selectedTower = towers.get(1);
                    break;
                case F3:
                    selectedTower = towers.get(2);
                    break;
                case F4:
                    selectedTower = towers.get(3);
                    break;
                case F5:
                    selectedTower = towers.get(4);
                    break;
                case F6:
                    selectedTower = towers.get(5);
                    break;
                case F7:
                    selectedTower = towers.get(6);
                    break;
                case F8:
                    selectedTower = towers.get(7);
                    break;
            }

            if(selectedTower != null) {
                if (selectedField == null) selectedField = currentMap.iterator().next();
                switch (keyType) {
                    case ArrowDown:
                        selectedField = moveIterator.down();
                        break;
                    case ArrowUp:
                        selectedField = moveIterator.up();
                        break;
                    case ArrowLeft:
                        selectedField = moveIterator.left();
                        break;
                    case ArrowRight:
                        selectedField = moveIterator.right();
                        break;
                    case Enter:
                        System.out.println("Enter");
                        break;
                }
            }
            System.out.println("Sprawdzam klawisz");
            //break;
           displayWindow();
            displayBoughtTowers();
            displayDetails();
            displayMap();
        }
       // document.notifyView();
    }

    private void displayField(TerminalPosition startPosition, Field field) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;
        TextImage fieldImage = new BasicTextImage(new TerminalSize(fieldWidth, fieldHeight));
        TextColor color = getTextColor(field.getColor());
        if(field == selectedField) {
            color = getTextColor(Color.BLUE);
        }
        for (int row = 0; row < fieldHeight; row++) {
            for (int column = 0; column < fieldWidth; column++) {
                textCharacter = new TextCharacter(' ', textGraphics.getForegroundColor(), color, SGR.BOLD);
                fieldImage.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, fieldImage);
    }

    private void displayEnemy(TerminalPosition startPosition, Enemy enemy) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;
        TextImage enemyImage = new BasicTextImage(new TerminalSize(characterWidth, characterHeight));
        TextColor color = getTextColor(enemy.getColor());

        for (int row = 0, i = 0; row < characterHeight; row++) {
            for (int column = 0; column < characterWidth; column++, i++) {
                textCharacter = new TextCharacter(' ', textGraphics.getForegroundColor(), color, SGR.BOLD);
                enemyImage.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, enemyImage);
    }

    private void displayTower(TerminalPosition startPosition, Tower tower) {
        String stringTowerIcon = tower.getIcon();
        TextColor color = getTextColor(tower.getColor());

        TextImage towerIcon = new BasicTextImage(new TerminalSize(characterWidth, characterHeight));
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;

        for (int row = 0, i = 0; row < characterHeight; row++) {
            for (int column = 0; column < characterWidth; column++, i++) {
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
