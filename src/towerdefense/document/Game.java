package towerdefense.document;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    protected ArrayList<Player> players;
    protected CurrentPlayer currentPlayer;
    protected Map currentMap;
    protected HashSet<Point> enemyPosition = new HashSet<Point>();
    private Document document;
    private boolean timerOn = false;

    public Game(Document document) {
        this.document = document;
        players = new ArrayList<Player>();
        currentPlayer = CurrentPlayer.getInstance();
        currentMap = new Map("mapTest");
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
                Map lastMap;
                if(Objects.equals(parts[2], "null")) {
                    lastMap = new Map("mapTest");
                } else {
                    lastMap = new Map(parts[2]);
                }
                players.add(new Player(nickname, money, lastMap));
            }
            bufferedReader.close();
            if(!players.isEmpty()){
                currentPlayer.setCurrentPlayer(players.get(0));
                currentMap = currentPlayer.getLastMap();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open players list file '" + playersFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading players list file '" + playersFileName + "'");
        }
    }

    public void Timer(){
        CheckAllFieldIterator<Field> iterator = currentMap.checkAllFieldIterator();
        Field startField = null;
        Point startPoint = null;
        while(iterator.hasNext()){
            Field tmp = iterator.next();
            if(tmp.isStart()){
                startField = tmp;
                startPoint = iterator.fieldPoint();
            }
        }
        final Field realStartField = startField;
        int interval = 0;
        enemyPosition.add(startPoint);
        //document.notifyView();
        GameLogic helloRunnable = new GameLogic(interval,currentMap,startField, enemyPosition);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(helloRunnable, 0, 500, TimeUnit.MILLISECONDS);

        timerOn = true;
    }

    public boolean isTimerOn(){
        return timerOn;
    }

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

}
