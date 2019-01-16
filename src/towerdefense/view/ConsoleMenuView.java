package towerdefense.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Player;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleMenuView extends MenuView {
    CurrentPlayer currentPlayer;
    ArrayList<Player> players;
    int currentSelection = 0;

    TerminalSize terminalSize;
    TerminalPosition startPosition;


    public ConsoleMenuView(Document document){
        this.document = document;
        currentPlayer = document.getCurrentPlayer();
        players = document.getPlayers();
    }

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
            for (int i = 0; i < 3; i++) {
                if (i == currentSelection) {
                    switch (currentSelection) {
                        case 0:
                            textGraphics.putString(startPosition, "PLAY", SGR.BLINK, SGR.BOLD);
                            break;
                        case 1:
                            textGraphics.putString(startPosition.withRelativeRow(1), "PLAYER SELECT", SGR.BLINK, SGR.BOLD);
                            break;
                        case 2:
                            textGraphics.putString(startPosition.withRelativeRow(2), "EXIT", SGR.BLINK, SGR.BOLD);
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
                            textGraphics.putString(startPosition.withRelativeRow(2), "EXIT", SGR.BOLD);
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
       /* if (players.isEmpty()) {
            document.switchToView(new ConsolePlayerNewView());
        }*/

        try {
            screen.clear();
            screen.refresh();
            terminalSize = terminal.getTerminalSize();
            startPosition = new TerminalPosition(terminalSize.getColumns() * 2 / 5,
                    terminalSize.getRows() * 2 / 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleKeysStroke(KeyStroke keyStroke) {
        KeyType keyType = keyStroke.getKeyType();
        switch (keyType) {
            case ArrowDown:
                currentSelection = (currentSelection + 1) % 3;
                break;
            case ArrowUp:
                currentSelection = (currentSelection - 1 + 3) % 3;
                break;
            case Escape:
                document.exit();
                break;
            case Enter:
                switch (currentSelection) {
                    case 0:
                        document.switchToView(new ConsoleGameView(document));
                        break;
                    case 1:
                        document.switchToView(new ConsolePlayerSelectView(document));
                        break;
                    case 2:
                   //     document.switchToView(new ConsoleShopView());
                        break;
                    case 3:
                        document.exit();
                        break;
                }
                break;
        }
    }
}
