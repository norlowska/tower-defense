package towerdefense.view;

import towerdefense.document.Document;

public abstract class MenuView extends View {
    public MenuView(Document document, String mode) {
        super(document, mode);
    }

    @Override
    public final void render() {
        displayWindow();
        displayGreeting();
        displayOptions();
        handleInput();
    }

    protected abstract void displayGreeting();

    protected abstract void displayOptions();

    protected abstract void displayWindow();

    protected abstract void handleInput();
}
