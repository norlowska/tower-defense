package towerdefense.view;

public abstract class MenuView extends View {
    @Override
    public void render() {
        displayWindow();
        displayGreeting();
        displayOptions();
    }

    protected abstract void displayGreeting();

    protected abstract void displayOptions();

    protected abstract void displayWindow();
}
