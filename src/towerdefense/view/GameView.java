package towerdefense.view;

public abstract class GameView extends View {
    @Override
    public void render() {
        displayWindow();
        displayBoughtTowers();
        displayMap();
    }

    protected abstract void displayBoughtTowers();

    protected abstract void displayMap();

    protected abstract void displayWindow();
}
