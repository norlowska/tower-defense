package towerdefense.view;

public abstract class PlayerSelectView extends View {
    @Override
    public void render() {
        displayWindow();
        displayContent();
    }

    protected abstract void displayContent();

    protected abstract void displayWindow();
}
