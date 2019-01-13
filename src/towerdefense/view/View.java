package towerdefense.view;

import towerdefense.document.Document;

public abstract class View {
    public Document document;

    public Document getDoc() {
        return document;
    }

    public abstract void render();

    public View() {
    }
}
