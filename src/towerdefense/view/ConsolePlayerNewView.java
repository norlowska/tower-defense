package towerdefense.view;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Player;

import java.io.IOException;
import java.util.ArrayList;

public class ConsolePlayerNewView extends PlayerNewView {
    String label = "Enter your nickname: (MAX 15 characters)";
    ArrayList<Player> players;
    CurrentPlayer currentPlayer;
    TerminalSize terminalSize;
    TerminalPosition startPosition;

    public ConsolePlayerNewView(Document document) {
        this.document = document;
        players = document.getPlayers();
        currentPlayer = document.getCurrentPlayer();
    }

    @Override
    protected void displayInput() {
        KeyStroke keyStroke = null;
        StringBuilder sb = new StringBuilder();
        KeyType keyType = null;
        TextGraphics textGraphics = screen.newTextGraphics();
    startPosition = startPosition.withRelativeRow(1);
        Boolean creatingPlayer = true;

        while (creatingPlayer) {
            try {
                keyStroke = screen.readInput();
                keyType = keyStroke.getKeyType();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (keyType) {
                case Enter:
                    if (sb.length() > 0) {
                        players.add(new Player(sb.toString(), 500));
                        creatingPlayer = false;
                    }
                    break;
                case Escape:
                    if (players.isEmpty())
                        document.exit();
                    creatingPlayer = false;
                    break;
                case Character:
                    if (sb.length() <= 15) {
                        sb.append(keyStroke.getCharacter());
                        textGraphics.putString(startPosition, sb.toString());
                        screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(1));
                    }
                    break;
                case Backspace:
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                        textGraphics.putString(startPosition, "");
                        try {
                            screen.refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textGraphics.putString(startPosition, sb.toString() + " ");
                        screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(-1));
                    }
                    break;
            }
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void displayLabel() {
        TextGraphics textGraphics = screen.newTextGraphics();
        startPosition = startPosition.withRelative(1, 1);
        textGraphics.putString(startPosition, label);
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void displayWindow() {
        screen.clear();
        terminalSize = screen.getTerminalSize();
        startPosition = new TerminalPosition(
                (terminalSize.getColumns() - label.length() - 10) / 2, (terminalSize.getRows() - 6) / 2);
            displayDoubleLineBox();
            screen.setCursorPosition(startPosition.withRelative(1, 2));
    }

    private void displayDoubleLineBox() {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize boxSize = new TerminalSize(label.length() + 10, 4);
        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        textGraphics.setCharacter(startPosition, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(startPosition.withRelativeColumn(boxSize.getColumns() - 1),
                Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.drawLine(startPosition.withRelativeColumn(1),
                startPosition.withRelativeColumn(boxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(startPosition.withRelativeRow(1), startPosition.withRelativeRow(boxSize.getRows() - 2),
                Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.drawLine(startPosition.withRelative(boxSize.getColumns() - 1, 1),
                startPosition.withRelative(boxSize.getColumns() - 1, boxSize.getRows() - 2),
                Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.drawLine(startPosition.withRelative(1, boxSize.getRows() - 1),
                startPosition.withRelative(boxSize.getColumns() - 2, boxSize.getRows() - 1),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.setCharacter(startPosition.withRelativeRow(boxSize.getRows() - 1),
                Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(startPosition.withRelative(boxSize.getColumns() - 1, boxSize.getRows() - 1),
                Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
    }
}
