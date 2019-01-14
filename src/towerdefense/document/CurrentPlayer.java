package towerdefense.document;

public class CurrentPlayer extends Player {
    private static CurrentPlayer ourInstance = new CurrentPlayer();

    public static CurrentPlayer getInstance() {
        return ourInstance;
    }

    private CurrentPlayer() {
        super(null, null);
    }
}
