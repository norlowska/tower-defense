package towerdefense.view;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import jdk.nashorn.internal.ir.Terminal;
import org.omg.CORBA.Current;
import towerdefense.document.CurrentPlayer;
import towerdefense.document.Player;
import towerdefense.document.Tower;
import towerdefense.document.towers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleShopView extends ShopView implements ConsoleView {
    TerminalPosition startPosition;
    CurrentPlayer currentPlayer = document.getCurrentPlayer();
    List<Tower> towersTypes = new ArrayList<Tower>();
    List<Tower> boughtTowers = currentPlayer.getTowers();
    Tower currentTower = towersTypes.get(0);

    @Override
    protected void displayBoughtTowers() {

    }

    @Override
    protected void displayDetails() {

    }

    @Override
    protected void displayTowers() {

    }

    @Override
    protected void displayTower(Tower tower) {

    }

    @Override
    protected void displayWindow() {

        startPosition = new TerminalPosition(terminalSize.getColumns() / 12,
                terminalSize.getRows() / 16);




        TextGraphics textGraphics = screen.newTextGraphics();
        TerminalSize towerBoxSize = new TerminalSize(10, 6);
        textGraphics.setForegroundColor(TextColor.ANSI.RED);

        List<Tower> boughtTowers = currentPlayer.getTowers();

        screen.clear();
        screen.setCursorPosition(null);

        for (int i = 0; i < 2; i++) {
            drawTowersBox(startPosition.withRelativeRow(i * 8), TextColor.ANSI.RED,
                    towersTypes.subList(i * 4, i * 4 + 4));
        }

        TerminalPosition towerDetailsPosition = new TerminalPosition(startPosition.withRelativeColumn(60).getColumn(),
                0);
        TerminalPosition boughtTowersPosition = new TerminalPosition(0, startPosition.withRelativeRow(17).getRow());
        TerminalPosition boughtTowersListPosition = new TerminalPosition(startPosition.getColumn(),
                boughtTowersPosition.withRelativeRow(4).getRow());
        textGraphics.drawLine(towerDetailsPosition,
                new TerminalPosition(towerDetailsPosition.getColumn(), boughtTowersPosition.getRow()),
                Symbols.DOUBLE_LINE_VERTICAL);

        textGraphics.drawLine(boughtTowersPosition,
                new TerminalPosition(terminalSize.getColumns(), boughtTowersPosition.getRow()),
                Symbols.DOUBLE_LINE_HORIZONTAL);

        String boughtTowersTitle = "YOUR TOWERS";
        String noTowers = "YOU HAVE NO TOWERS";
        textGraphics.putString(
                boughtTowersPosition.withRelative((terminalSize.getColumns() - boughtTowersTitle.length()) / 2 - 4, 2),
                boughtTowersTitle, SGR.BOLD);

        if (!boughtTowers.isEmpty()) {
            drawTowersBox(boughtTowersListPosition, TextColor.ANSI.RED, boughtTowers);
        } else {
            textGraphics.putString(
                    boughtTowersPosition.withRelative((terminalSize.getColumns() - noTowers.length()) / 2 - 4, 4),
                    noTowers, SGR.BOLD, SGR.ITALIC);
        }

        String shopTitle = "S H O P";
        String shopInfo = "Press <Enter> to buy or sell tower";
        String escapeInfo = "Press <ESC> to leave shop";
        TerminalSize towerDetailsBoxSize = new TerminalSize(
                terminalSize.getColumns() - startPosition.withRelativeColumn(60).getColumn(),
                terminalSize.getRows() - startPosition.withRelativeRow(17).getRow());
        textGraphics.putString(
                towerDetailsPosition.withRelative((towerDetailsBoxSize.getColumns() - shopTitle.length()) / 2, 1),
                shopTitle, SGR.BOLD);
        textGraphics.putString(
                towerDetailsPosition.withRelative((towerDetailsBoxSize.getColumns() - shopInfo.length()) / 2, 2),
                shopInfo, SGR.ITALIC);
        textGraphics.putString(
                towerDetailsPosition.withRelative((towerDetailsBoxSize.getColumns() - escapeInfo.length()) / 2, 3),
                escapeInfo, SGR.ITALIC);
        printTowerDetails(towerDetailsPosition.withRelativeRow(3), towerDetailsBoxSize, TextColor.ANSI.RED, null,
                currentTower);

        String buyLabel = "B U Y";
        String sellLabel = "S E L L";
        textGraphics.fillRectangle(towerDetailsPosition.withRelative(1, 16),
                new TerminalSize(terminalSize.getColumns() - towerDetailsPosition.getColumn() - 1, 2), ' ');

        for (Tower t : boughtTowers) {
            if (currentTower.getClass().equals(t.getClass())) {
                textGraphics
                        .putString(
                                towerDetailsPosition
                                        .withRelative((towerDetailsBoxSize.getColumns() - sellLabel.length()) / 2, 17),
                                sellLabel, SGR.ITALIC, SGR.BOLD);
                break;
            } else {
                textGraphics
                        .putString(
                                towerDetailsPosition
                                        .withRelative((towerDetailsBoxSize.getColumns() - buyLabel.length()) / 2, 17),
                                buyLabel, SGR.ITALIC, SGR.BOLD);
            }
        }
        screen.refresh();
    }

    private void handleKeysStroke(KeyStroke keyStroke) {
        /*towersTypes.add(new ArcherTower());
        towersTypes.add(new ForceTower());
        towersTypes.add(new ElectricityTower());
        towersTypes.add(new FireTower());
        towersTypes.add(new EarthTower());
        towersTypes.add(new WaterTower());
        towersTypes.add(new IceTower());
        towersTypes.add(new NuclearTower());*/

        KeyType keyType;
        int currentSelection = 0;
        do {
            printShop(startPosition, towersTypes, currentTower);
            if (currentSelection < 4) {
                screen.setCursorPosition(startPosition.withRelative(
                        (10 - currentTower.getName().length()) / 2 + 14 * (currentSelection % 4),
                        6 * ((currentSelection + 4) / 4 % 3)));

            } else {
                screen.setCursorPosition(startPosition.withRelative(
                        (10 - currentTower.getName().length()) / 2 + 14 * (currentSelection % 4),
                        6 * ((currentSelection + 4) / 4 % 3) + 2));
            }
            screen.refresh();
            keyStroke = screen.readInput();
            keyType = keyStroke.getKeyType();

            switch (keyType) {
                case ArrowRight:
                    currentSelection = (currentSelection + 1) % towersTypes.size();
                    break;
                case ArrowLeft:
                    currentSelection = (currentSelection - 1 + towersTypes.size()) % towersTypes.size();
                    break;
                case ArrowUp:
                    if (currentSelection > 3)
                        currentSelection = (currentSelection + 4) % towersTypes.size();
                    break;
                case ArrowDown:
                    if (currentSelection < 4)
                        currentSelection = (currentSelection - 4 + towersTypes.size()) % towersTypes.size();
                    break;
                case Enter:
                    Boolean removed = false;
                    if (boughtTowers.size() <= 4) {
                        for (int i = 0; i < boughtTowers.size(); i++) {
                            if (currentTower.getClass().equals(boughtTowers.get(i).getClass())) {
                                boughtTowers.remove(i);
                                removed = true;
                                break;
                            }
                        }
                        if (!removed && boughtTowers.size() < 4) {
                            boughtTowers.add(currentTower);
                        }
                    }
                    break;
            }
            currentTower = towersTypes.get(currentSelection);
        } while (keyType != KeyType.Escape);
        screen.clear();
    }
}
