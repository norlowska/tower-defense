package towerdefense.document;

import towerdefense.view.View;

import java.util.List;

public class Document {
    private View currentView;
    private CurrentPlayer currentPlayer;

    public void switchToView(View View) {
        currentView = View;
    }

    public void notifyView() {
        currentView.render();
    }

    public Document() {

    }

    public CurrentPlayer getPlayer() {
        return currentPlayer.getInstance(); //?
    }

}
