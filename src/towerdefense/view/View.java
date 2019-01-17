package towerdefense.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import towerdefense.document.Document;

import javax.swing.*;
import java.io.IOException;

public class View {
    protected Document document;
    protected JFrame window;
    protected static Terminal terminal;
    protected static Screen screen;

    public Document getDoc() {
        return document;
    }

    public void render(){
    }

    public View(Document document, String mode) {
        this.document = document;
        System.out.println(mode);
        switch(mode) {
            case "Console":
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
                break;
            case "GUI":
            default:
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        window = new JFrame("Tower-Defense");
                        window.setSize(900, 800);
                        window.setResizable(false);
                        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        window.setLocationRelativeTo(null);
                    }
                });
                break;
        }
    }
}
