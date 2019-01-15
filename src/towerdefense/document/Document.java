package towerdefense.document;

import towerdefense.view.ConsoleMenuView;
import towerdefense.view.View;

import java.util.List;

public class Document {
    private View currentView;
    private Game game;

    public Document() {
        game = new Game();
        currentView = new ConsoleMenuView();
    }
    public void switchToView(View View) {
        currentView = View;
    }

    public void notifyView() {
        currentView.render();
    }

    public CurrentPlayer getCurrentPlayer(){
        return game.getCurrentPlayer();
    }

    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }
}
