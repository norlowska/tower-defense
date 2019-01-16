package towerdefense.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import towerdefense.document.Document;

import java.io.IOException;


public class View {
    public Document document;
    protected static Terminal terminal;
    protected static Screen screen;

    public Document getDoc(){
        return document;
    }

    public void render() {

    }

    public View() {
        if (terminal == null) {
            TerminalSize terminalSize = new TerminalSize(110, 50);
            DefaultTerminalFactory dft = new DefaultTerminalFactory();
            dft.setInitialTerminalSize(terminalSize);
            try {
                terminal = dft.createTerminal();
                screen = new TerminalScreen(terminal);
                screen.startScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
