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
    /**
     * Przyciski z wieżami
     */
    private JButton button1, button2, button3, button4;
    private JButton button5, button6, button7, button8;
    /**
     * Obrazki wież
     */
    private Image i1, i2, i3, i4, i5, i6, i7, i8;
    private JPanel mainPanel;
    private JLabel towersLabel;
    private JPanel infoPanel;
    private JLabel moneyLabel;
    private JLabel playerNameLabel;
    private JLabel gameGoalLabel;
    private MapPanel mapPanel;
    private Graphics graphics;
    private int fieldWidth = 70, fieldHeight = 70;
 //   Enemy enemy = new Enemy(40,1);
    private java.util.List<Tower> towers;


    public GUIGameView(Document document) {
        super(document, "GUI");
        currentPlayer = document.getCurrentPlayer();
        currentMap = document.getCurrentMap();
        towers = new ArrayList<Tower>();
    }

    private void createUIComponents() {
        mapPanel = new MapPanel(this);
    }

    @Override
    protected void displayBoughtTowers() {
        try {
            i1 = getTowerImage("archer.png");
            button1.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button1.setIcon(new ImageIcon(i1));
            i2 = getTowerImage("earth.png");
            button2.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button2.setIcon(new ImageIcon(i2));
            i3 = getTowerImage("electric.png");
            button3.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button3.setIcon(new ImageIcon(i3));
            i4 = getTowerImage("fire.png");
            button4.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button4.setIcon(new ImageIcon(i4));
            i5 = getTowerImage("force.png");
            button5.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button5.setIcon(new ImageIcon(i5));
            i6 = getTowerImage("ice.png");
            button6.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button6.setIcon(new ImageIcon(i6));
            i7 = getTowerImage("nuclear.png");
            button7.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button7.setIcon(new ImageIcon(i7));
            i8 = getTowerImage("water.png");
            button8.setBorder(BorderFactory.createEmptyBorder(4, 12, 2, 0));
            button8.setIcon(new ImageIcon(i8));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    @Override
    protected void displayDetails() {
        System.out.println(currentPlayer.getNickname());
        playerNameLabel.setText("Hello, " + currentPlayer.getNickname() + "!");
        moneyLabel.setText("Your money: " + currentPlayer.getMoney());
        gameGoalLabel.setText(convertToMultiline("Your goal is to defend \n your territory against enemies." +
                "\nSelect tower with function keys\n and place it on map,\n along enemies' path of attack," +
                "\n using arrows.\n Enter accepts your choice.\nSurvive all waves of enemies attacks\n to win map."));
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
            graphics.fillRect(x, y, fieldWidth, fieldHeight);
            if(currentField instanceof FieldRoad && ((FieldRoad) currentField).getEnemy()!=null) {
                displayEnemy(graphics, x, y,((FieldRoad) currentField).getEnemy());
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
        /*while(true) {
            mapPanel.repaint();
            try { Thread.sleep(5); } catch (Exception e) {}
        }*/
      //  try { Thread.sleep(1000); } catch (Exception e) {}
      //  mapPanel.repaint();
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

    public class MapPanel extends JPanel implements MouseListener, MouseMotionListener {
        private GUIGameView gameView;
        /**
         * Pozycja kursora myszy
         */
        public int mouseX, mouseY;
        public boolean mouseIsPressed;

        public MapPanel(GUIGameView gameView) {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.gameView = gameView;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            gameView.displayMap(g);
        }

        public Point getPoint()
        {
            return new Point(mouseX, mouseY);
        }

        /**
         * Implementacja metod interfejsu MouseListener
         *
         */

        @Override
        public void mouseClicked(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseIsPressed = true;
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseIsPressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseIsPressed = true;

        }

        /**
         * Implementacja metod interfejsu MouseMotionListener
         *
         */

        @Override
        public void mouseDragged(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseIsPressed = false;
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseIsPressed = false;

        }

    }

    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
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
