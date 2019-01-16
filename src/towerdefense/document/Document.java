package towerdefense.document;

import towerdefense.view.ConsoleMenuView;
import towerdefense.view.View;

import java.util.ArrayList;
public class Document {
    private View currentView;
    private Game game;

    public Document() {
        game = new Game();
        currentView = new ConsoleMenuView(this);
    }
    public void switchToView(View view) {
        currentView = view;
        notifyView();
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

    public void exit(){
        game.exit();
    }
}
