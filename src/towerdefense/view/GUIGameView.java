package towerdefense.view;

import towerdefense.document.*;
import towerdefense.document.Point;
import towerdefense.document.towers.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GUIGameView extends GameView {
    private Map currentMap;
    private CurrentPlayer currentPlayer;
    private ButtonGroup towersGroup;
    /**
     * Przyciski z wieżami
     */
    private JToggleButton[] towersButtons = new JToggleButton[8];
    /**
     * Obrazki wież
     */
    private Image[] towersImages = new Image[8];
    private JPanel mainPanel;
    private JLabel towersLabel;
    private JPanel infoPanel;
    private JLabel gameGoalLabel;
    private JLabel greetingLabel;
    private JLabel towerDetailsLabel;
    private MapPanel mapPanel;
    private Graphics graphics;
    private Tower selectedTower;
    private int fieldSize = 70, characterSize = 64;
 //   Enemy enemy = new Enemy(40,1);
    private java.util.List<Tower> towers;


    public GUIGameView(Document document) {
        super(document, "GUI");
        currentPlayer = document.getCurrentPlayer();
        currentMap = document.getCurrentMap();
        towers = new ArrayList<Tower>();
        selectedTower = null;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
        displayMap();
        displayDetails();
    }

    private void createUIComponents() {
        mapPanel = new MapPanel(this);
    }

    @Override
    protected void displayBoughtTowers() {
        towersGroup = new ButtonGroup();
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String type = actionEvent.getActionCommand();

                switch (type) {
                    case "archer":
                        setSelectedTower(new ArcherTower());
                        break;
                    case "earth":
                        setSelectedTower(new EarthTower());
                        break;
                    case "electric":
                        setSelectedTower(new ElectricTower());
                        break;
                    case "fire":
                        setSelectedTower(new FireTower());
                        break;
                    case "force":
                        setSelectedTower(new ForceTower());
                        break;
                    case "ice":
                        setSelectedTower(new IceTower());
                        break;
                    case "nuclear":
                        setSelectedTower(new NuclearTower());
                        break;
                    case "water":
                        setSelectedTower(new WaterTower());
                        break;
                }
            }
        };

        try {
            towersImages[0] = getTowerImage("archer.png");
            towersImages[1] = getTowerImage("earth.png");
            towersImages[2] = getTowerImage("electric.png");
            towersImages[3] = getTowerImage("fire.png");
            towersImages[4] = getTowerImage("force.png");
            towersImages[5] = getTowerImage("ice.png");
            towersImages[6] = getTowerImage("nuclear.png");
            towersImages[7] = getTowerImage("water.png");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        for (int i = 0; i < 8; i++) {
            towersButtons[i] = new JToggleButton();
            towersButtons[i].addActionListener(listener);
            towersGroup.add(towersButtons[i]);
            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 0.4;
            c.gridx = i;
            c.gridy = 3;
            mainPanel.add(towersButtons[i], c);
            towersButtons[i].setSize(characterSize,characterSize);
            towersButtons[i].setBackground(new Color(253, 180, 96));
            towersButtons[i].setOpaque(true);
            towersButtons[i].setIcon(new ImageIcon(towersImages[i]));
        }
        towersButtons[0].setActionCommand("archer");
        towersButtons[1].setActionCommand("earth");
        towersButtons[2].setActionCommand("electric");
        towersButtons[3].setActionCommand("fire");
        towersButtons[4].setActionCommand("force");
        towersButtons[5].setActionCommand("ice");
        towersButtons[6].setActionCommand("nuclear");
        towersButtons[7].setActionCommand("water");
    }

    @Override
    protected void displayDetails() {
        greetingLabel.setText(convertToMultiline("Hello, " + currentPlayer.getNickname() + "!\n Your money: " + currentPlayer.getMoney()));
        towerDetailsLabel.setMinimumSize(new Dimension(50, 150));
        if(selectedTower != null) {
            towerDetailsLabel.setText(convertToMultiline( selectedTower.getName() + "\n Price: " + selectedTower.getPrice() +
                    "\nDamage: " + selectedTower.getDamage() + "\n Range: " + selectedTower.getRange()));
        }
        gameGoalLabel.setText(convertToMultiline("Your goal is to defend your territory\n against enemies." +
                "\nSelect tower by clicking on its image\n and place it on map,\n along enemies' path of attack," +
                "\n clicking on map.\n Survive all waves of enemies attacks\n to win map."));
        gameGoalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameGoalLabel.setVerticalAlignment(SwingConstants.CENTER);
        if(!document.getGame().isTimerOn()){
            document.getGame().Timer();
        }
    }

    @Override
    protected void displayMap() {
        int x = 0, y = 0;
        CheckAllFieldIterator checkAllFieldIterator = currentMap.checkAllFieldIterator();
        Field currentField;
        while (checkAllFieldIterator.hasNext()) {
            currentField = (Field) checkAllFieldIterator.next();
            graphics.setColor(getAWTColor(currentField.getColor()));
            graphics.fillRect(x, y, fieldSize, fieldSize);
            if(currentField instanceof FieldRoad && ((FieldRoad) currentField).getEnemy()!=null) {
                displayEnemy(graphics, x, y,((FieldRoad) currentField).getEnemy());
                int currentHealthPercent = ((FieldRoad) currentField).getCurrentHealth() / ((FieldRoad) currentField).getEnemy().getMaxHealth() * 100;
                String currentHealthLabel = Integer.toString(currentHealthPercent) + "%";
                graphics.setColor(Color.BLACK);
                graphics.drawString(currentHealthLabel, (x + characterSize - currentHealthLabel.length()) /2 + 1,   characterSize +1);
            } else if (currentField instanceof FieldTerrain && ((FieldTerrain)currentField).getTower()!=null) {
                displayTower(graphics, x, y, ((FieldTerrain)currentField).getTower());
            }
            x+=70;
            if(x == 840) {
                y+=70;
                x=0;
            }
        }
    }

    @Override
    protected void displayWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setSize(1175, 600);
                window.setContentPane(mainPanel);
                window.setVisible(true);
            }
        });
    }

    @Override
    protected void handleInput() {
        Point mouseLocation = mapPanel.getMouseLocation();
        CheckAllFieldIterator checkAllFieldIterator = currentMap.checkAllFieldIterator();
        int iterationsNumber = (mouseLocation.getX() / fieldSize + 1 ) + (mouseLocation.getY() / fieldSize) * 12;
        int iteration = 0;
        Field selectedField = null;
        while(iteration < iterationsNumber && checkAllFieldIterator.hasNext()) {
            selectedField = (Field) checkAllFieldIterator.next();
            iteration++;
        }
        if(selectedField != null && !selectedField.isRoad()) {
            ((FieldTerrain) selectedField).setTower(selectedTower);
            mapPanel.repaint();
        }
    }

    protected void displayMap(Graphics g) {
        this.graphics = g;
        displayMap();
    }

    protected void displayTower(Graphics g, int x, int y, Tower tower) {
        g.drawImage(tower.getImage(), x, y, null);
    }

    protected void displayEnemy(Graphics g, int x, int y, Enemy enemy) {
        g.drawImage(enemy.getImage(), x, y, null);
    }

    public class MapPanel extends JPanel {
        private GUIGameView gameView;
        /**
         * Pozycja kursora myszy
         */
        private int mouseX, mouseY;

        public MapPanel(GUIGameView gameView) {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mouseX = e.getX();
                    mouseY = e.getY();
                    handleInput();
                }
            });
            this.gameView = gameView;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            gameView.displayMap(g);
        }

        public Point getMouseLocation()
        {
            return new Point(mouseX, mouseY);
        }

    }

    public static String convertToMultiline(String orig) {
        return "<html><div style='text-align: center;'>" + orig.replaceAll("\n", "<br>") + "</div></html>";
    }

    private Color getAWTColor(towerdefense.document.Color color) {
        switch (color) {
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.BLUE;
            case CYAN:
                return Color.CYAN;
            case GREEN:
                return Color.GREEN;
            case MAGENTA:
                return Color.MAGENTA;
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            case WHITE:
            default:
                return Color.WHITE;
        }
    }

    public Image getTowerImage(String filename) throws IOException {
        return ImageIO.read(new File("data/towersPNG/" + filename));
    }
}
