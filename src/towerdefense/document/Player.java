package towerdefense.document;

public class Player {
    private static Player ourInstance = new Player();

    public static Player getInstance() {
        return ourInstance;
    }

    private Player() {
    }
}
