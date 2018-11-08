package towerdefense;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Random;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

public class GameMap {

	Map map = new Map();
	ArrayList<ArrayList<Field>> maps;
	Terminal terminal;
	Screen screen;
	
	public GameMap(String name,Terminal terminal, Screen screen) {
		maps = map.readMapLayout(name);
		this.terminal = terminal;
		this.screen = screen;
	}
	
	
	public void displayMap() throws IOException {
		
		screen.clear();
		screen.setCursorPosition(null);
		//screen = new TerminalScreen(terminal);

		//screen.startScreen();
		TerminalSize terminalSize = terminal.getTerminalSize();
		TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns() / 4,
				terminalSize.getRows() / 3);
		TerminalPosition endPosition = new TerminalPosition(terminalSize.getColumns(),
				terminalSize.getRows());
		TextGraphics textGraphics = screen.newTextGraphics();
		//textGraphics.drawLine(startPosition, endPosition, '.');
		//drawField(startPosition);
		//textGraphics.drawLine(startPosition.withRelative(1,1), endPosition, '.');
		//drawField(startPosition.withRelative(0,6));
		//textGraphics.drawLine(startPosition, endPosition, 'x');
		int y = 0, x = 0;
		for(y = 0; y < maps.size();y++) {
			for(x = 0; x < maps.get(y).size();x++) {
				drawField(startPosition,maps.get(y).get(x));
				startPosition = startPosition.withRelative(6,0);
				screen.refresh();
			}
			startPosition = startPosition.withRelative(-((x-6)*(maps.get(y).size())),6);
		}
		
	}
	
	public void drawField(TerminalPosition startPosition, Field field) throws IOException {
		TextGraphics textGraphics = screen.newTextGraphics();
		for(int column = 0; column < 6; column++) {
            for(int row = 0; row < 6; row++) {
            	if(field.getTerrain().getColor() == 1) {
	                screen.setCharacter(startPosition.getColumn(), startPosition.getRow(), new TextCharacter(
	                        ' ',
	                        TextColor.ANSI.DEFAULT,
	                        TextColor.ANSI.GREEN));
	                startPosition = startPosition.withRelative(0,1);
            	}else {
            		screen.setCharacter(startPosition.getColumn(), startPosition.getRow(), new TextCharacter(
	                        ' ',
	                        TextColor.ANSI.DEFAULT,
	                        TextColor.ANSI.WHITE));
	                startPosition = startPosition.withRelative(0,1);
            	}
            }
            startPosition = startPosition.withRelative(1,-6);
        }
	}
	
	
}
