package towerdefense.view;

import towerdefense.document.Document;
import towerdefense.document.Tower;

public abstract class ShopView extends View {
    public ShopView(Document document) {
        super(document);
    }

    @Override
    public final void render() {
        displayWindow();
        displayTowers();
        displayBoughtTowers();
        displayDetails();
    }

    protected abstract void displayBoughtTowers();

    protected abstract void displayDetails();

    protected abstract void displayTowers();

    protected abstract void displayTower(Tower tower);

    protected abstract void displayWindow();
}
