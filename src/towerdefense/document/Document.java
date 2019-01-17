package towerdefense.document;

import towerdefense.view.ConsoleMenuView;
import towerdefense.view.View;

import java.util.ArrayList;
public class Document {
    private View currentView;
    private Game game;

    public Document() {
        game = new Game(this);
        currentView = new ConsoleMenuView(this);
    }
    public void switchToView(View view) {
        currentView = view;
        notifyView();
    }

    public void notifyView() {
        currentView.render();
    }

    public void notifyGame(){}

    public CurrentPlayer getCurrentPlayer(){
        return game.getCurrentPlayer();
    }

    public void setCurrentPlayer(Player player) { game.currentPlayer.setCurrentPlayer(player); }

    public Map getCurrentMap() { return game.getCurrentMap(); }

    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }

    public void addPlayer(Player player) {
        game.players.add(player);
    }

    public Game getGame() {return game;}

    public void setGame(Game game) {this.game = game;}

    public void exit(){
        game.exit();
    }
}
