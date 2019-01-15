package towerdefense.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Map;
import towerdefense.document.Player;
import towerdefense.textUI.TextUI;

import java.io.IOException;
import java.util.ArrayList;

public class ConsolePlayerNewView extends PlayerNewView implements ConsoleView {
    String label = "Enter your nickname: (MAX 15 characters)";
    TerminalPosition startPosition;
    ArrayList<Player> players = document.getPlayers();
    CurrentPlayer currentPlayer = document.getCurrentPlayer();

    @Override
    protected void displayInput() {
        KeyStroke keyStroke = null;
        StringBuilder sb = new StringBuilder();
        KeyType keyType = null;
        TerminalPosition startPosition = screen.getCursorPosition();
        TextGraphics textGraphics = screen.newTextGraphics();

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
        textGraphics.putString(startPosition.withRelative(1, 1), label);
    }

    @Override
    protected void displayWindow() {
        screen.clear();
        startPosition = new TerminalPosition(
                (terminalSize.getColumns() - label.length() - 10) / 2, (terminalSize.getRows() - 6) / 2);
        TerminalSize labelBoxSize = new TerminalSize(label.length() + 10, 4);
        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        try {
            TextUI.getInstance().displayDoubleLineBox(startPosition, labelBoxSize, TextColor.ANSI.CYAN, null);
            screen.setCursorPosition(startPosition.withRelative(1, 2));
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
