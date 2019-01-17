package towerdefense.view;


import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import towerdefense.document.*;

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
        screen.clear();
    }

    @Override
    protected void displayWindow() {
        currentMap = document.getCurrentMap();
        TerminalPosition startPosition = new TerminalPosition(0,0);
    }

    private void drawTower(TerminalPosition startPosition, Tower tower) {
        String stringTowerIcon = tower.getIcon();
        TextColor color = getTextColor(tower.getColor());

        TextImage towerIcon = new BasicTextImage(new TerminalSize(6, 4));
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;

        for (int row = 0, i = 0; row < 4; row++) {
            for (int column = 0; column < 6; column++, i++) {
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
