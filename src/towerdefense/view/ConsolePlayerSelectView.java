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
import java.util.List;

public class ConsolePlayerSelectView extends PlayerSelectView {

    List<Player> players;
    CurrentPlayer currentPlayer;
    int currentSelection = 0;
    TerminalPosition startPosition;
    TerminalSize terminalSize;

    public ConsolePlayerSelectView(Document document) {
        this.document = document;
        players = document.getPlayers();
        currentPlayer = document.getCurrentPlayer();
    }

    private void displayOptions() {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        TerminalPosition optionsStartPosition = startPosition.withRelativeRow(players.size() +1);
        textGraphics.fillRectangle(startPosition, new TerminalSize(15, 1), ' ');

        for (int i = players.size(); i < players.size() + 2; i++) {
            if (i == currentSelection) {
                if (i == players.size()) {
                    textGraphics.putString(optionsStartPosition, "ADD", SGR.ITALIC, SGR.BLINK);
                }
            } else {
                if (i == players.size()) {
                    textGraphics.putString(optionsStartPosition, "ADD", SGR.ITALIC);
                }
            }
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayPlayersList(){
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        TerminalPosition listStartPosition = startPosition.withRelative(3,4);

        for (int i = 0; i < players.size() + 2; i++) {
            if (i == currentSelection) {
                if (i < players.size()) {
                    textGraphics.putString(listStartPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BLINK,
                            SGR.BOLD);
                }
            } else {
                if (i < players.size()) {
                    textGraphics.putString(listStartPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BOLD);
                }
            }
        }
    }

    @Override
    protected void displayWindow() {
        screen.clear();
        terminalSize = screen.getTerminalSize();
        String label = "Choose player";
        int nicknameMaxLength = 15;
        TerminalPosition labelBoxTopLeft = new TerminalPosition(
                (terminalSize.getColumns() - nicknameMaxLength - 10) / 2,
                (terminalSize.getRows() - players.size() - 7) / 2);
        startPosition = labelBoxTopLeft;
        TerminalSize labelBoxSize = new TerminalSize(nicknameMaxLength + 10, players.size() + 8);
        //TerminalPosition labelBoxTopRightCorner = startPosition.withRelativeColumn(labelBoxSize.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();

         //   TextUI.getInstance().displayDoubleLineBox(startPosition, labelBoxSize, TextColor.ANSI.RED, null);

        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        textGraphics.putString(startPosition.withRelative(2, 2), label);
        screen.setCursorPosition(startPosition);
    }

    @Override
    protected void displayContent() {
        KeyStroke keyStroke = null;
        KeyType keyType;

        while(true) {
            if(players.isEmpty()) {
                document.switchToView(new ConsolePlayerNewView(document));
            }
            do {
                displayPlayersList();
                displayOptions();
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

}
