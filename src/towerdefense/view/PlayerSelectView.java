package towerdefense.view;

import com.googlecode.lanterna.TerminalPosition;
import towerdefense.document.Document;

import java.io.IOException;

public abstract class PlayerSelectView extends View {
    public PlayerSelectView(Document document, String mode) {
        super(document, mode);
    }

    @Override
    public final void render() {
        displayWindow();
        displayContent();
    }

    protected abstract void displayContent();

    protected abstract void displayWindow();
}
