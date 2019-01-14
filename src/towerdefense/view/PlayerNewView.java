package towerdefense.view;

public abstract class PlayerNewView extends View {
    @Override
    public void render() {
        displayWindow();
        displayInfo();
        displayOptions();
    }

    protected abstract void displayOptions();

    protected abstract void displayInfo();

    protected abstract void displayWindow();
}
