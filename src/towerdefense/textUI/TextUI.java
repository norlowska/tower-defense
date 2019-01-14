package towerdefense.textUI;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TextUI {
    private static TextUI ourInstance = new TextUI();
    private static Terminal terminal;
    private static Screen screen;
    private static TerminalSize terminalSize;

    private TextUI() {
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            terminal.getTerminalSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TextUI getInstance() {
        return ourInstance;
    }

    public Screen getScreen() {
        return screen;
    }

    public TerminalSize getTerminalSize() { return terminalSize; }

    public void displayDoubleLineBox(TerminalPosition startPosition, TerminalSize boxSize, TextColor foregroundColor, TextColor backgroundColor)
            throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();

        if (foregroundColor != null) {
            textGraphics.setForegroundColor(foregroundColor);
        }
        if (backgroundColor != null) {
            textGraphics.setBackgroundColor(backgroundColor);
        }

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
