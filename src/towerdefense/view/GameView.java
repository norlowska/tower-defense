package towerdefense.view;

import towerdefense.document.Document;

public abstract class GameView extends View {
    public GameView(Document document, String mode) {
        super(document, mode);
    }

    @Override
    public final void render() {
        displayWindow();
        displayDetails();
        displayBoughtTowers();
        displayMap();
        handleInput();
    }

    protected abstract void displayBoughtTowers();

    protected  abstract void displayDetails();

    protected abstract void displayMap();

    protected abstract void displayWindow();

    protected abstract void handleInput();

}
