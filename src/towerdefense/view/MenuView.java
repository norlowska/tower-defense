package towerdefense.view;

import towerdefense.document.Document;

public abstract class MenuView extends View {
    public MenuView(Document document) {
        super(document);
    }

    @Override
    public final void render() {
        displayWindow();
        displayGreeting();
        displayOptions();
    }

    protected abstract void displayGreeting();

    protected abstract void displayOptions();

    protected abstract void displayWindow();
}
