package towerdefense.view;

public abstract class PlayerView extends View {
    @Override
    public void render() {
        displayWindow();
        displayPlayerList();
        displayOptions();
    }

    protected abstract void displayOptions();

    protected abstract void displayPlayerList();

    protected abstract void displayWindow();
}
