package towerdefense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

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
		readPlayersList();
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
		} else {
			currentPlayer = players.get(0);
		}
		
		screen.clear();
		
		TerminalSize terminalSize = terminal.getTerminalSize();
		TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns()/3, terminalSize.getRows()/3);
		TextGraphics textGraphics = screen.newTextGraphics();
		String greeting = "Hello, " + currentPlayer.getNickname() + "!";
        
		textGraphics.setForegroundColor(TextColor.ANSI.RED);
		
        KeyStroke keyStroke;
        KeyType keyType;
        
        int currentSelection = 0;
        while(true) {
    		textGraphics.putString(terminalSize.getColumns()-greeting.length() - 3, 1, greeting);
        	screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
        	terminal.setCursorVisible(false); //doesn't work
    		for (int i = 0; i < 4; i++) {
        		if ( i == currentSelection) {
        			switch(currentSelection) {
        			case 0:
        				textGraphics.putString(startPosition, "PLAY", SGR.BLINK, SGR.BOLD);
        				break;
        			case 1:
        				textGraphics.putString(startPosition.withRelativeRow(1), "CHANGE PLAYER", SGR.BLINK, SGR.BOLD);
        				break;
        			case 2:
        				textGraphics.putString(startPosition.withRelativeRow(2), "SHOP", SGR.BLINK, SGR.BOLD);
        				break;
        			case 3:
        				textGraphics.putString(startPosition.withRelativeRow(3), "EXIT", SGR.BLINK, SGR.BOLD);
        				break;
        			}
        		} else {
        				switch (i) {
        				case 0:
            				textGraphics.putString(startPosition, "PLAY", SGR.BOLD);
            				break;
            			case 1:
            				textGraphics.putString(startPosition.withRelativeRow(1), "CHANGE PLAYER", SGR.BOLD);
            				break;
            			case 2:
            				textGraphics.putString(startPosition.withRelativeRow(2), "SHOP", SGR.BOLD);
            				break;
            			case 3:
            				textGraphics.putString(startPosition.withRelativeRow(3), "EXIT", SGR.BOLD);
        				}
        			}
        		}
    		screen.refresh();
    		
        	keyStroke = screen.readInput();
        	keyType = keyStroke.getKeyType();
        	switch(keyType) {
        	case ArrowDown:
        		currentSelection = (currentSelection+1)%4;
        		break;
        	case ArrowUp:
        		currentSelection = (currentSelection-1+4)%4;
        		break;
        	case Escape:
        		exit();
        		break;
        	case Enter:
        		switch(currentSelection) {
                case 0:
                	play();
                	break;
                case 1:
                	changePlayer();
                	break;
                case 2:
                	shop();
                	break;
                case 3:
                	exit();
                	break;
                }
        		break;
        	}
		}
	}
	
	public void addPlayer() throws IOException {
		screen.clear();
        terminal.setCursorVisible(true);
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
        		if(sb.length()>0) {
        			players.add(new Player(sb.toString(), new Map()));
            		currentPlayer = players.get(0);
            		creatingPlayer = false;
        		}
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
	
	public void readPlayersList() {
		String playersFileName = "data/players.txt";
        String line = null;

		 try {
	            BufferedReader bufferedReader = new BufferedReader(new FileReader(playersFileName));

	            while((line = bufferedReader.readLine()) != null) {
	                players.add(new Player(line, new Map()));
	            }   
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + playersFileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" + playersFileName + "'");                  
	        }
	}
	
	public void play() throws IOException {
		screen.clear();
		screen.refresh();
		screen.readInput();
	}
	
	public void changePlayer() throws IOException {
		screen.clear();
		screen.refresh();

		while(!players.isEmpty()) {
			screen.readInput();
		}
		addPlayer();
		screen.readInput();
	}
	
	public void shop() throws IOException {
		screen.clear();
		screen.refresh();
		screen.readInput();
	}
	
	public void exit() {
		String playersFileName = "data/players.txt";
		
		try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(playersFileName));

            for(Player p : players) {
            	bufferedWriter.write(p.getNickname());
            }
            bufferedWriter.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + playersFileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" + playersFileName + "'");                  
        }
		System.exit(0);
	}
	
}
