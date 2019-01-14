package towerdefense.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import towerdefense.document.Player;
import towerdefense.textUI.TextUI;

import java.io.IOException;
import java.util.List;

public class ConsolePlayerSelectView extends PlayerSelectView implements ConsoleView {

    List<Player> players = document.getPlayers();
    int currentSelection = 0;
    TerminalPosition startPosition;

    @Override
    protected void displayOptions() {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);

        textGraphics.fillRectangle(startPosition, new TerminalSize(15, 1), ' ');

        for (int i = players.size(); i < players.size() + 2; i++) {
            if (i == currentSelection) {
                if (i == players.size()) {
                    textGraphics.putString(startPosition, "ADD", SGR.ITALIC, SGR.BLINK);
                } else if (i == players.size() + 1) {
                    textGraphics.putString(startPosition.withRelativeColumn(7), "DELETE", SGR.ITALIC, SGR.BLINK);
                }
            } else {
                if (i == players.size()) {
                    textGraphics.putString(startPosition, "ADD", SGR.ITALIC);
                } else if (i == players.size() + 1) {
                    textGraphics.putString(startPosition.withRelativeColumn(7), "DELETE", SGR.ITALIC);
                }
            }
            try {
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void displayPlayersList(){
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.RED);

        for (int i = 0; i < players.size() + 2; i++) {
            if (i == currentSelection) {
                if (i < players.size()) {
                    textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BLINK,
                            SGR.BOLD);
                }
            } else {
                if (i < players.size()) {
                    textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BOLD);
                }
            }
        }
    }

    @Override
    protected void displayWindow() {
        String label = "Choose player";
        int nicknameMaxLength = 15;

        TerminalSize labelBoxSize = new TerminalSize(nicknameMaxLength + 10, players.size() + 8);
        TerminalPosition labelBoxTopRightCorner = startPosition.withRelativeColumn(labelBoxSize.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        // drawDoubleLineBox(startPosition, labelBoxSize, TextColor.ANSI.RED, null);
        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        textGraphics.putString(startPosition.withRelative(2, 2), label);
        displayPlayersList();
    }

}
