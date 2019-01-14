package towerdefense.view;

import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public abstract class PlayerSelectView extends View {
    @Override
    public void render() {
        displayWindow();
        displayPlayersList();
        displayOptions();
    }

    protected abstract void displayOptions();

    protected abstract void displayPlayersList();

    protected abstract void displayWindow();
}
