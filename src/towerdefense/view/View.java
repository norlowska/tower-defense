package towerdefense.view;

import towerdefense.document.Document;

public class View {
    public Document document;
    public View view;

    public Document getDoc(){
        return document;
    }

    public void render(){
        view.render();
    }

    public View() {
    }
}
