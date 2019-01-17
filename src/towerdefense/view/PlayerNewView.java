package towerdefense.view;

import towerdefense.document.Document;

public abstract class PlayerNewView extends View {
    public PlayerNewView(Document document, String mode) {
        super(document, mode);
    }

    @Override
    public final void render() {
        displayWindow();
        displayLabel();
        handleInput();
    }
    
    protected abstract void displayLabel();

    protected abstract void displayWindow();

    protected abstract void handleInput();
}
