package towerdefense.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import towerdefense.textUI.TextUI;

public interface ConsoleView {
    TextUI textUI = TextUI.getInstance();
    Screen screen = textUI.getScreen();
    TerminalSize terminalSize = textUI.getTerminalSize();
}
