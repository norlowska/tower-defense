package towerdefense.view;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import towerdefense.textUI.TextUI;

public interface ConsoleView {
    TextUI textUI = TextUI.getInstance();
    Screen screen = textUI.getScreen();
}
