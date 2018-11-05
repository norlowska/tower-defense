package towerdefense;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import data.Player;

public class Game {
	protected Terminal terminal = null;
	protected Screen screen = null;
	protected List<Player> players;
	protected Player currentPlayer;
	protected Mode currentMode;
	protected List<Mode> modes;
	
	public Game() {
		players = new ArrayList<Player>();
		modes = new ArrayList<Mode>();
		modes.add(new Mode("Easy", 1, 1, 1));
		modes.add(new Mode("Medium", 0.98, 1.05, 1.1));
		modes.add(new Mode("Hard", 0.95, 1.1, 1.2));
		currentMode = modes.get(0);
	}
	
	
	public void start(){
		try {
    		terminal = new DefaultTerminalFactory().createTerminal();
    		screen = new TerminalScreen(terminal);
    		
    		screen.startScreen();
    		mainMenu();
    		
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	} finally {
            if(terminal != null) {
                try {
                    terminal.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
    	}
	}
	
	public void mainMenu() throws IOException {
		if(players.isEmpty()) {
			addPlayer();
		}
		
	}
	
	public void addPlayer() throws IOException {
		terminal.clearScreen();
		TerminalSize terminalSize = terminal.getTerminalSize();
		String nameLabel = "Enter your nickname: (MAX 15 characters)";
        TerminalPosition labelBoxTopLeft = new TerminalPosition((terminalSize.getColumns() - nameLabel.length()-10)/2, (terminalSize.getRows()-6)/2);
        TerminalSize labelBoxSize = new TerminalSize(nameLabel.length() + 10, 6);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(3).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(3).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(3), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(3), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), nameLabel);
        screen.setCursorPosition(labelBoxTopLeft.withRelative(1, 2));
        screen.refresh();
        
        KeyStroke keyStroke = screen.readInput();
        StringBuilder sb = new StringBuilder();
        KeyType keyType;
    	TerminalPosition startPosition = screen.getCursorPosition();
    	Boolean creatingPlayer = true;
    	
        while(creatingPlayer) {
        	keyType = keyStroke.getKeyType();
        	switch(keyType) {
        	case Enter:
        		players.add(new Player(sb.toString(), new Map()));
        		creatingPlayer = false;
        		break;
        	case Escape:
        		if(players.isEmpty())
        			exit();
        		creatingPlayer = false;
        		break;
        	case Character:
        		if(sb.length()<=15) {
        			sb.append(keyStroke.getCharacter());
                	textGraphics.putString(startPosition, sb.toString());
                	screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(1));
        		}
        		break;
        	case Backspace:
        		if(sb.length()>0 ) {
	        		sb.deleteCharAt(sb.length()-1);
	        		textGraphics.putString(startPosition, new String());
	            	screen.refresh();
	        		textGraphics.putString(startPosition, sb.toString() + " ");
	        		screen.setCursorPosition(screen.getCursorPosition().withRelativeColumn(-1));
        		}
        		break;
        	}
        	screen.refresh();
        	keyStroke = screen.readInput();
        }
	}
	
	public void exit() {
	}
	
}
