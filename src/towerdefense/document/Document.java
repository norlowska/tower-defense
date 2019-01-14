package towerdefense.document;

import towerdefense.view.View;

public class Document {
    private View currentView;

    public void switchToView(View View) {
        currentView = View;
    }

    public void notifyView() {
        currentView.render();
    }

    public Document() {

    }
}
