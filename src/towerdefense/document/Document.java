package towerdefense.document;

import towerdefense.view.View;

public class Document {
    private View currentView;

    public void switchToView(View view) {
        currentView = view;
    }

    public void notifyView() {
        currentView.render();
    }

    public Document() {

    }
}
