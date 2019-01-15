package towerdefense.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Player;
import towerdefense.textUI.TextUI;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleMenuView extends MenuView implements ConsoleView {
    CurrentPlayer currentPlayer = document.getCurrentPlayer();
    ArrayList<Player> players = document.getPlayers();
    int currentSelection = 0;

    TextUI textUI = TextUI.getInstance();
    Screen screen = textUI.getScreen();
    TerminalPosition startPosition = screen.getCursorPosition();
    TerminalSize terminalSize = textUI.getTerminalSize();

    @Override
    protected void displayGreeting() {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        String greeting = "Hello, " + currentPlayer.getNickname() + "!";
        textGraphics.putString(terminalSize.getColumns() - greeting.length() - 3, 1, greeting);
    }

    @Override
    protected void displayOptions() {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        while (true) {
            screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
            for (int i = 0; i < 4; i++) {
                if (i == currentSelection) {
                    switch (currentSelection) {
                        case 0:
                            textGraphics.putString(startPosition, "PLAY", SGR.BLINK, SGR.BOLD);
                            break;
                        case 1:
                            textGraphics.putString(startPosition.withRelativeRow(1), "PLAYER SELECT", SGR.BLINK, SGR.BOLD);
                            break;
                        case 2:
                            textGraphics.putString(startPosition.withRelativeRow(2), "SHOP", SGR.BLINK, SGR.BOLD);
                            break;
                        case 3:
                            textGraphics.putString(startPosition.withRelativeRow(3), "EXIT", SGR.BLINK, SGR.BOLD);
                            break;
                    }
                } else {
                    switch (i) {
                        case 0:
                            textGraphics.putString(startPosition, "PLAY", SGR.BOLD);
                            break;
                        case 1:
                            textGraphics.putString(startPosition.withRelativeRow(1), "PLAYER SELECT", SGR.BOLD);
                            break;
                        case 2:
                            textGraphics.putString(startPosition.withRelativeRow(2), "SHOP", SGR.BOLD);
                            break;
                        case 3:
                            textGraphics.putString(startPosition.withRelativeRow(3), "EXIT", SGR.BOLD);
                    }
                }
            }

            KeyStroke keyStroke;

            try {
                screen.refresh();
                keyStroke = screen.readInput();
                handleKeysStroke(keyStroke);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void displayWindow() {
        if (players.isEmpty()) {
            document.switchToView(new ConsolePlayerNewView());
        }

        try {
            screen.clear();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startPosition = new TerminalPosition(terminalSize.getColumns() * 2 / 5,
                terminalSize.getRows() * 2 / 5);
    }

    private void handleKeysStroke(KeyStroke keyStroke) {
        KeyType keyType = keyStroke.getKeyType();
        switch (keyType) {
            case ArrowDown:
                currentSelection = (currentSelection + 1) % 4;
                break;
            case ArrowUp:
                currentSelection = (currentSelection - 1 + 4) % 4;
                break;
            case Escape:
                document.exit();
                break;
            case Enter:
                switch (currentSelection) {
                    case 0:
                        document.switchToView(new ConsoleGameView());
                        break;
                    case 1:
                        document.switchToView(new ConsolePlayerSelectView());
                        break;
                    case 2:
                        document.switchToView(new ConsoleShopView());
                        break;
                    case 3:
                        document.exit();
                        break;
                }
                break;
        }
    }
}
