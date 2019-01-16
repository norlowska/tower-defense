package towerdefense.view;

import towerdefense.document.Document;

public abstract class PlayerNewView extends View {
    public PlayerNewView(Document document) {
        super(document);
    }

    @Override
    public final void render() {
        displayWindow();
        displayLabel();
        displayInput();
    }

    protected abstract void displayInput();

    protected abstract void displayLabel();

    protected abstract void displayWindow();
}
