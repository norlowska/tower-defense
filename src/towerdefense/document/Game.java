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
import towerdefense.document.towers.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    protected Terminal terminal = null;
    protected Screen screen = null;
    protected List<Player> players;
    protected Player currentPlayer;

    public Game() {
        players = new ArrayList<Player>();
        readPlayersList();
    }

    public void start() throws InterruptedException {
        try {
            TerminalSize ts = new TerminalSize(110, 50);
            DefaultTerminalFactory dft = new DefaultTerminalFactory();
            dft.setInitialTerminalSize(ts);
            terminal = dft.createTerminal();
            screen = new TerminalScreen(terminal);

            screen.startScreen();
            mainMenu();

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

    private void mainMenu() throws IOException, InterruptedException {

    }

    private void addPlayer() throws IOException {
        screen.clear();
        TerminalSize terminalSize = terminal.getTerminalSize();
        String nameLabel = "Enter your nickname: (MAX 15 characters)";
        TerminalPosition labelBoxTopLeft = new TerminalPosition(
                (terminalSize.getColumns() - nameLabel.length() - 10) / 2, (terminalSize.getRows() - 6) / 2);
        TerminalSize labelBoxSize = new TerminalSize(nameLabel.length() + 10, 4);
        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        //drawDoubleLineBox(labelBoxTopLeft, labelBoxSize, TextColor.ANSI.CYAN, null);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), nameLabel);
        screen.setCursorPosition(labelBoxTopLeft.withRelative(1, 2));
        screen.refresh();

        KeyStroke keyStroke;
        StringBuilder sb = new StringBuilder();
        KeyType keyType;
        TerminalPosition startPosition = screen.getCursorPosition();
        Boolean creatingPlayer = true;

        while (creatingPlayer) {
            keyStroke = screen.readInput();
            keyType = keyStroke.getKeyType();
            switch (keyType) {
                case Enter:
                    if (sb.length() > 0) {
                        players.add(new Player(sb.toString(), new Map()));
                        currentPlayer = players.get(0);
                        creatingPlayer = false;
                    }
                    break;
                case Escape:
                    if (players.isEmpty())
                        exit();
                    creatingPlayer = false;
                    break;
                case Character:
                    if (sb.length() <= 15) {
                        sb.append(keyStroke.getCharacter());
                        textGraphics.putString(startPosition, sb.toString());
                        screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(1));
                    }
                    break;
                case Backspace:
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                        textGraphics.putString(startPosition, "");
                        screen.refresh();
                        textGraphics.putString(startPosition, sb.toString() + " ");
                        screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(-1));
                    }
                    break;
            }
            screen.refresh();
        }
    }

    private void readPlayersList() {
        String playersFileName = "data/players.txt";
        String line = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(playersFileName));

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                List<Tower> towers = new ArrayList<Tower>();
                Tower tower;
                for(int i = 3; i < parts.length; i++) {
                    switch(parts[i]) {

                    }
                }
                players.add(new Player(line, new Map()));
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + playersFileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + playersFileName + "'");
        }
    }

    private void playerSelect() throws IOException, InterruptedException {
        screen.clear();

        int nicknameMaxLength = 15;
        TerminalSize terminalSize = terminal.getTerminalSize();
        TerminalPosition labelBoxTopLeft = new TerminalPosition(
                (terminalSize.getColumns() - nicknameMaxLength - 10) / 2,
                (terminalSize.getRows() - players.size() - 7) / 2);
        TerminalPosition startPosition = labelBoxTopLeft.withRelative(3, 4);

        printPlayerSelectWindow(labelBoxTopLeft);
        screen.setCursorPosition(startPosition);

        KeyStroke keyStroke;
        KeyType keyType;
        int listSize, currentSelection = 0;
        while (true) {
            if (players.isEmpty()) {
                addPlayer();
                screen.clear();
                printPlayerSelectWindow(startPosition.withRelative(-3, -4));
                screen.setCursorPosition(startPosition);
            }
            do {
                printPlayersList(currentSelection, startPosition);
                printPSOptionsList(currentSelection, startPosition.withRelativeRow(players.size() + 1));

                keyStroke = screen.readInput();
                keyType = keyStroke.getKeyType();
                listSize = players.size() + 2;
                switch (keyType) {
                    case ArrowDown:
                        currentSelection = (currentSelection + 1) % listSize;
                        break;
                    case ArrowUp:
                        currentSelection = (currentSelection - 1 + listSize) % listSize;
                        break;
                    case ArrowRight:
                        if (currentSelection == players.size()) {
                            currentSelection = (currentSelection + 1) % listSize;
                        }
                        break;
                    case ArrowLeft:
                        if (currentSelection == players.size() + 1) {
                            currentSelection = (currentSelection - 1 + listSize) % listSize;
                        }
                        break;
                }

                if (currentSelection < players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
                } else if (currentSelection == players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection + 1));
                } else if (currentSelection == players.size() + 1) {
                    screen.setCursorPosition(startPosition.withRelative(7, currentSelection));
                }
            } while (keyType != KeyType.Enter);
            if (currentSelection == listSize - 2) {
                addPlayer();
                screen.clear();
                printPlayerSelectWindow(startPosition.withRelative(-3, -4));
                screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
            } else if (currentSelection == listSize - 1) {
                deletePlayer(startPosition);
            } else {
                break;
            }
            screen.refresh();
        }
        currentPlayer = players.get(currentSelection);
        screen.clear();
    }

    

    private void deletePlayer(TerminalPosition startPosition) throws IOException, InterruptedException {
        int nicknameMaxLength = 15;
        TerminalSize boxSize = new TerminalSize(nicknameMaxLength, players.size() + 3);
        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setForegroundColor(TextColor.ANSI.RED);
        screen.setCursorPosition(startPosition);

        int currentSelection = 0;
        KeyStroke keyStroke;
        KeyType keyType;
        while (true) {
            if (players.isEmpty() || currentSelection == players.size() + 1) {
                textGraphics.putString(startPosition, "List is empty", SGR.BOLD);
                screen.refresh();
                currentPlayer = null;
                Thread.sleep(1500);
                break;
            }
            do {
                if (currentSelection < players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
                } else if (currentSelection == players.size()) {
                    screen.setCursorPosition(startPosition.withRelativeRow(currentSelection + 1));
                }
                textGraphics.fillRectangle(startPosition, boxSize, ' ');
                printPlayersList(currentSelection, startPosition);
                textGraphics.putString(startPosition.withRelativeRow(players.size() + 1), "DONE", SGR.ITALIC);
                screen.refresh();
                keyStroke = screen.readInput();
                keyType = keyStroke.getKeyType();

                int listSize = players.size() + 1;
                switch (keyType) {
                    case ArrowDown:
                        currentSelection = (currentSelection + 1) % listSize;
                        break;
                    case ArrowUp:
                        currentSelection = (currentSelection - 1 + listSize) % listSize;
                        break;
                }
            } while (keyType != KeyType.Enter);
            if (currentSelection == players.size()) {
                break;
            }
            players.remove(currentSelection);
        }
    }

    private void shop() throws IOException, InterruptedException {


    }

    private void printShop(TerminalPosition startPosition, List<Tower> towersTypes, Tower currentTower)
            throws IOException, InterruptedException {

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

    private void drawTower(TerminalPosition startPosition, Tower tower) {
        String stringTowerIcon = tower.getIcon();
        Color c = tower.getColor();
        TextColor color;
        switch(c) {
            case BLACK:
                color = TextColor.ANSI.BLACK;
                break;
            case BLUE:
                color = TextColor.ANSI.BLUE;
                break;
            case CYAN:
                color = TextColor.ANSI.CYAN;
                break;
            case GREEN:
                color = TextColor.ANSI.GREEN;
                break;
            case MAGENTA:
                color = TextColor.ANSI.MAGENTA;
                break;
            case RED:
                color = TextColor.ANSI.RED;
                break;
            case YELLOW:
                color = TextColor.ANSI.YELLOW;
                break;
            case WHITE:
                color = TextColor.ANSI.WHITE;
                break;
            default:
                color = TextColor.ANSI.DEFAULT;
        }

        TextImage towerIcon = new BasicTextImage(new TerminalSize(6, 4));
        TextGraphics textGraphics = screen.newTextGraphics();
        TextCharacter textCharacter;

        for (int row = 0, i = 0; row < 4; row++) {
            for (int column = 0; column < 6; column++, i++) {
                textCharacter = new TextCharacter(stringTowerIcon.charAt(i), color, textGraphics.getBackgroundColor(),
                        SGR.BOLD);
                towerIcon.setCharacterAt(column, row, textCharacter);
            }
        }
        textGraphics.drawImage(startPosition, towerIcon);

    }

    private void exit() {
        String playersFileName = "data/players.txt";

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(playersFileName));

            for (Player p : players) {
                bufferedWriter.write(p.getNickname());
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
                        // This will pick a random background color
                        TextColor.ANSI.GREEN));
            }
        }
        screen.refresh();
    }

}
