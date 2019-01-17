package towerdefense.document;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    protected ArrayList<Player> players;
    protected CurrentPlayer currentPlayer;
    protected Map currentMap;
    private Document document;

    public Game(Document document) {
        this.document = document;
        players = new ArrayList<Player>();
        currentPlayer = CurrentPlayer.getInstance();
        currentMap = null;
        readPlayersList();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Map getCurrentMap() {
        return currentMap;
    }
//    public void setPlayers(ArrayList<Player> players) {
    //    this.players = players;
  //  }

    public CurrentPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    private void readPlayersList() {
        String playersFileName = "data/players.txt";
        String line = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(playersFileName));

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                String nickname = parts[0];
                int money = Integer.parseInt(parts[1]);
                Map lastMap = new Map(parts[2]);
                players.add(new Player(nickname, money, lastMap));
            }
            bufferedReader.close();
            if(!players.isEmpty()){
                currentPlayer.setCurrentPlayer(players.get(0));
                currentMap = currentPlayer.getLastMap();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + playersFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + playersFileName + "'");
        }
    }

    public void Timer(){
        Runnable helloRunnable = new Runnable() {
            public void run() {
                System.out.println("Hello world");

            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(helloRunnable, 0, 500, TimeUnit.MILLISECONDS);
    }
 //   public void setCurrentPlayer(CurrentPlayer currentPlayer) {
      //  this.currentPlayer = currentPlayer;
   // }

  /*  public void start() throws InterruptedException {
        try {
            TerminalSize ts = new TerminalSize(110, 50);
            DefaultTerminalFactory dft = new DefaultTerminalFactory();
            dft.setInitialTerminalSize(ts);
            terminal = dft.createTerminal();
            screen = new TerminalScreen(terminal);

            screen.startScreen();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void printTowerDetails(TerminalPosition startPosition, TerminalSize boxSize, TextColor foregroundColor,
                                   TextColor backgroundColor, Tower tower) throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        if (foregroundColor != null) {
            textGraphics.setForegroundColor(foregroundColor);
        }
        if (backgroundColor != null) {
            textGraphics.setBackgroundColor(backgroundColor);
        }

       // drawDoubleLineBox(startPosition.withRelative((boxSize.getColumns() - 10) / 2, 1), new TerminalSize(10, 6),
         //       TextColor.ANSI.RED, null);
        drawTower(startPosition.withRelative((boxSize.getColumns() - 10) / 2 + 2, 2), tower);
        textGraphics.putString(startPosition.withRelative((boxSize.getColumns() - tower.getName().length()) / 2, 7),
                tower.getName());
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() / 4, 9), "POWER: ", SGR.BOLD);
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() * 3 / 4, 9),
                Integer.toString(tower.getDamage()));
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() / 4, 10), "RANGE: ", SGR.BOLD);
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() * 3 / 4, 10),
                Integer.toString(tower.getRange()));
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() / 4, 11), "COST: ", SGR.BOLD);
        textGraphics.putString(startPosition.withRelative(boxSize.getColumns() * 3 / 4, 11),
                Integer.toString(tower.getPrice()));
    }

    private void drawTowersBox(TerminalPosition startPosition, TextColor color, List<Tower> towers) throws IOException {
        TerminalSize towerBoxSize = new TerminalSize(10, 6);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(color);
        Tower currentTower;

        for (int i = 0; i < 4 && i < towers.size(); i++) {
            currentTower = towers.get(i);
          //  drawDoubleLineBox(startPosition.withRelativeColumn(14 * i), towerBoxSize, color, null);
            drawTower(startPosition.withRelative(2 + 14 * i, 1), currentTower);
            textGraphics.putString(
                    startPosition.withRelative(
                            (towerBoxSize.getColumns() - currentTower.getName().length()) / 2 + 14 * i, 6),
                    currentTower.getName());
        }
    }


*/
    public void exit() {
        String playersFileName = "data/players.txt";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(playersFileName));
            for (Player p : players) {
                String result = p.getNickname() + " " + p.getMoney() + " " + p.getLastMap().getMapName();
                bufferedWriter.write(result);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + playersFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + playersFileName + "'");
        }
        System.exit(0);
    }
/*
    public void displayMap() throws IOException {

        screen.clear();
        screen.setCursorPosition(null);
        //screen = new TerminalScreen(terminal);

        //screen.startScreen();
        TerminalSize terminalSize = terminal.getTerminalSize();
        TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns() / 12,
                terminalSize.getRows() / 16);
        TerminalPosition endPosition = new TerminalPosition(terminalSize.getColumns(),
                terminalSize.getRows());
        TextGraphics textGraphics = screen.newTextGraphics();
        //textGraphics.drawLine(startPosition, endPosition, '.');
        drawField(startPosition);
        //textGraphics.drawLine(startPosition.withRelative(1,1), endPosition, '.');
        //drawField(startPosition.withRelative(0,6));
        //textGraphics.drawLine(startPosition, endPosition, 'x');
        screen.refresh();
        //screen.refresh();
        screen.readInput();

    }

    public void drawField(TerminalPosition startPosition) throws IOException {
        TextGraphics textGraphics = screen.newTextGraphics();
        int column = startPosition.getColumn();
        int row = startPosition.getRow();
        for (column = 3; column < 6; column++) {
            for (row = 5; row < 6; row++) {
                screen.setCharacter(column, row, new TextCharacter(
                        ' ',
                        TextColor.ANSI.DEFAULT,
                        TextColor.ANSI.GREEN));
            }
        }
        screen.refresh();
    }*/

}
