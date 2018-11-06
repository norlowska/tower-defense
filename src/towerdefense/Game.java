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

	public void start() {
		try {
			terminal = new DefaultTerminalFactory().createTerminal();
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

	private void mainMenu() throws IOException {
		if (players.isEmpty()) {
			addPlayer();
		}
		currentPlayer = players.get(0);

		screen.clear();
		screen.refresh();

		TerminalSize terminalSize = terminal.getTerminalSize();
		TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns() / 3,
				terminalSize.getRows() / 3);
		TextGraphics textGraphics = screen.newTextGraphics();

		textGraphics.setForegroundColor(TextColor.ANSI.RED);

		KeyStroke keyStroke;
		KeyType keyType;

		int currentSelection = 0;
		while (true) {
			String greeting = "Hello, " + currentPlayer.getNickname() + "!";
			textGraphics.putString(terminalSize.getColumns() - greeting.length() - 3, 1, greeting);
			screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));
			terminal.setCursorVisible(false); // doesn't work
			for (int i = 0; i < 4; i++) {
				if (i == currentSelection) {
					switch (currentSelection) {
					case 0:
						textGraphics.putString(startPosition, "PLAY", SGR.BLINK, SGR.BOLD);
						break;
					case 1:
						textGraphics.putString(startPosition.withRelativeRow(1), "PLAYER SELECT", SGR.BLINK, SGR.BOLD);
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
						textGraphics.putString(startPosition.withRelativeRow(1), "PLAYER SELECT", SGR.BOLD);
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
			switch (keyType) {
			case ArrowDown:
				currentSelection = (currentSelection + 1) % 4;
				break;
			case ArrowUp:
				currentSelection = (currentSelection - 1 + 4) % 4;
				break;
			case Escape:
				exit();
				break;
			case Enter:
				switch (currentSelection) {
				case 0:
					play();
					break;
				case 1:
					playerSelect();
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

	private void addPlayer() throws IOException {
		screen.clear();
		terminal.setCursorVisible(true);
		TerminalSize terminalSize = terminal.getTerminalSize();
		String nameLabel = "Enter your nickname: (MAX 15 characters)";
		TerminalPosition labelBoxTopLeft = new TerminalPosition(
				(terminalSize.getColumns() - nameLabel.length() - 10) / 2, (terminalSize.getRows() - 6) / 2);
		TerminalSize labelBoxSize = new TerminalSize(nameLabel.length() + 10, 6);
		TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
		textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');
		textGraphics.drawLine(labelBoxTopLeft.withRelativeColumn(1),
				labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);
		textGraphics.drawLine(labelBoxTopLeft.withRelativeRow(3).withRelativeColumn(1),
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

		while (creatingPlayer) {
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

	private void readPlayersList() {
		String playersFileName = "data/players.txt";
		String line = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(playersFileName));

			while ((line = bufferedReader.readLine()) != null) {
				players.add(new Player(line, new Map()));
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + playersFileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + playersFileName + "'");
		}
	}

	private void play() throws IOException {
		screen.clear();
		screen.refresh();
		screen.readInput();
	}

	private void playerSelect() throws IOException {
		screen.clear();
		screen.refresh();

		if (players.isEmpty()) {
			addPlayer();
		}
		printPlayerSelectWindow();

		screen.clear();
		screen.refresh();
	}

	private void printPlayerSelectWindow() throws IOException {
		
		String label = "Choose player";
		TerminalSize terminalSize = terminal.getTerminalSize();
		int nicknameMaxLength = 15;
		TerminalPosition labelBoxTopLeft = new TerminalPosition((terminalSize.getColumns() - nicknameMaxLength - 10) / 2,
				(terminalSize.getRows() - players.size() - 7) / 2);
		TerminalSize labelBoxSize = new TerminalSize(nicknameMaxLength + 10, players.size() + 7);
		TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);

		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(TextColor.ANSI.RED);
		textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

		textGraphics.drawLine(labelBoxTopLeft.withRelativeColumn(1),
				labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);

		textGraphics.drawLine(
				labelBoxTopLeft.withRelativeRow(labelBoxSize.getRows()).withRelativeColumn(1), labelBoxTopLeft
						.withRelativeRow(labelBoxSize.getRows()).withRelativeColumn(labelBoxSize.getColumns() - 2),
				Symbols.DOUBLE_LINE_HORIZONTAL);

		textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
		textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
		textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(labelBoxSize.getRows()),
				Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
		textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(labelBoxSize.getRows()),
				Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

		for (int i = 1; i < labelBoxSize.getRows(); i++) {
			textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(i), Symbols.DOUBLE_LINE_VERTICAL);
			textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(i), Symbols.DOUBLE_LINE_VERTICAL);
		}

		textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(labelBoxSize.getRows() - 1),
				Symbols.DOUBLE_LINE_VERTICAL);
		textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(labelBoxSize.getRows() - 1),
				Symbols.DOUBLE_LINE_VERTICAL);
		textGraphics.putString(labelBoxTopLeft.withRelative(2, 2), label);

		for (int i = 0; i < players.size(); i++) {
			textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(i + 4), Symbols.DOUBLE_LINE_VERTICAL);
			textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(i + 4), Symbols.DOUBLE_LINE_VERTICAL);
		}
		printPlayersList(labelBoxTopLeft.withRelative(3, 4));
		screen.refresh();

		currentPlayer = changePlayer(labelBoxTopLeft.withRelative(3,4));

	}

	private void printPlayersList(TerminalPosition startPosition) throws IOException {
		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(TextColor.ANSI.RED);
		
		int currentSelection = 0;
		for (int i = 0; i < players.size() + 1; i++) {
			if (i == currentSelection) {
				if (i < players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BLINK,
							SGR.BOLD);
				} else if (i == players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(players.size()+1), "ADD", SGR.BOLD,
							SGR.ITALIC, SGR.BLINK);
				}/* else if (i == players.size() + 1) {
					textGraphics.putString(startPosition.withRelative(7, players.size()+1), "DELETE", SGR.BOLD,
							SGR.ITALIC, SGR.BLINK);
				}*/
			} else {
				if (i < players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BOLD);
				} else if (i == players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(players.size()+1), "ADD", SGR.BOLD,
							SGR.ITALIC);
				} /*else if (i == players.size() + 1) {
					textGraphics.putString(startPosition.withRelative(7, players.size()+1), "DELETE", SGR.BOLD,
							SGR.ITALIC);
				}*/
			}
			screen.refresh();
		}
	}

	private Player changePlayer(TerminalPosition startPosition) throws IOException {
		screen.setCursorPosition(startPosition);

		KeyStroke keyStroke;
		KeyType keyType;
		int listSize;
		
		int currentSelection = 0;
		do {
			printPlayersList(startPosition);

			keyStroke = screen.readInput();
			keyType = keyStroke.getKeyType();
			listSize = players.size() + 1;
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
				screen.setCursorPosition(startPosition.withRelativeRow(currentSelection+1));
			} else if (currentSelection == players.size() + 1) {
				screen.setCursorPosition(startPosition.withRelative(7, currentSelection));
			}
		} while (keyType != KeyType.Enter);

		if(currentSelection == listSize - 1) {
			addPlayer();
		}/* else if (currentSelection == listSize - 1) {
			deletePlayer();
		}*/
		
		return players.get(currentSelection);
	}
	
	private void deletePlayer(TerminalPosition startPosition) throws IOException {
		
		screen.refresh();
		screen.readInput();
	}

	private void shop() throws IOException {
		screen.clear();

		screen.refresh();
		screen.clear();
		screen.refresh();
		screen.readInput();
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

}
