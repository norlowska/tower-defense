package towerdefense.view;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Document;
import towerdefense.document.Player;

import java.io.IOException;
import java.util.List;

public class ConsolePlayerSelectView extends PlayerSelectView {

    List<Player> players;
    CurrentPlayer currentPlayer;
    int currentSelection = 0;
    TerminalPosition startPosition;
    TerminalSize terminalSize;
    String label = "Choose player";

    public ConsolePlayerSelectView(Document document) {
        this.document = document;
        players = document.getPlayers();
        currentPlayer = document.getCurrentPlayer();
    }

    private void displayOptions(TerminalPosition optionsPosition) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        textGraphics.fillRectangle(optionsPosition, new TerminalSize(15, 1), ' ');

        for (int i = players.size(); i < players.size() + 1; i++) {
            if (i == currentSelection) {
                if (i == players.size()) {
                    textGraphics.putString(optionsPosition, "ADD", SGR.ITALIC, SGR.BLINK);
                }
            } else {
                if (i == players.size()) {
                    textGraphics.putString(optionsPosition, "ADD", SGR.ITALIC);
                }
            }
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayPlayersList(TerminalPosition listPosition){
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);

        for (int i = 0; i < players.size() + 1; i++) {
            if (i == currentSelection) {
                if (i < players.size()) {
                    textGraphics.putString(listPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BLINK,
                            SGR.BOLD);
                }
            } else {
                if (i < players.size()) {
                    textGraphics.putString(listPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BOLD);
                }
            }
        }
    }

    @Override
    protected void displayWindow() {
        screen.clear();
        terminalSize = screen.getTerminalSize();
        int nicknameMaxLength = 15;
        startPosition = new TerminalPosition(
                (terminalSize.getColumns() - nicknameMaxLength - 10) / 2,
                (terminalSize.getRows() - players.size() - 7) / 2);
        displayDoubleLineBox(nicknameMaxLength);
        TextGraphics textGraphics = screen.newTextGraphics();
        startPosition = startPosition.withRelative(2, 2);
        System.out.println("Label " + startPosition);
        textGraphics.putString(startPosition, label);
        startPosition = startPosition.withRelative(1, 2);
    }

    @Override
    protected void displayContent() {
        KeyStroke keyStroke = null;
        KeyType keyType;
        while(true) {
           if(players.isEmpty()) {
                document.switchToView(new ConsolePlayerNewView(document));
                displayWindow();
            }
            screen.setCursorPosition(startPosition);
            do {
                displayPlayersList(startPosition);
                displayOptions(startPosition.withRelativeRow(players.size() + 1));
                try {
                    keyStroke = screen.readInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                keyType = keyStroke.getKeyType();
                handleKeyStroke(keyType);
                if(currentSelection < players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
                } else if (currentSelection == players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection + 1));
                }
            } while(keyType != KeyType.Enter);

            if (currentSelection == players.size()) {
                document.switchToView(new ConsolePlayerNewView(document));
                currentSelection = 0;
                displayWindow();
            } else {
                break;
            }
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentPlayer.setCurrentPlayer(players.get(currentSelection));
        screen.clear();
    }

    private void handleKeyStroke(KeyType keyType) {
        int optionsNumber = players.size() + 1;
        switch (keyType) {
            case ArrowDown:
                currentSelection = (currentSelection + 1) % optionsNumber;
                break;
            case ArrowUp:
                currentSelection = (currentSelection - 1 + optionsNumber) % optionsNumber;
                break;
            case ArrowRight:
                if (currentSelection == players.size()) {
                    currentSelection = (currentSelection + 1) % optionsNumber;
                }
                break;
            case ArrowLeft:
                if (currentSelection == players.size() + 1) {
                    currentSelection = (currentSelection - 1 + optionsNumber) % optionsNumber;
                }
                break;
        }
    }

    private void displayDoubleLineBox(int contentLength) {
        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize boxSize = new TerminalSize(contentLength + 10, players.size() + 8);
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
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
