package towerdefense.view;

public abstract class PlayerNewView extends View {
    @Override
    public void render() {
        displayWindow();
        displayLabel();
        displayInput();
    }

    protected abstract void displayInput();

    protected abstract void displayLabel();

    protected abstract void displayWindow();
}
