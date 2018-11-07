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
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import data.Player;
import towers.ArcherTower;
import towers.BallistaTower;
import towers.CannonTower;
import towers.DragonTower;
import towers.LaserTower;
import towers.MachineGunTower;
import towers.PoisonTower;
import towers.WizardTower;

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

	public void start() throws InterruptedException {
		try {
			TerminalSize ts = new TerminalSize(100,50);
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
		if (players.isEmpty()) {
			addPlayer();
		}
		currentPlayer = players.get(0);

		screen.clear();
		screen.refresh();

		TerminalSize terminalSize = terminal.getTerminalSize();
		TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns() *2/5,
				terminalSize.getRows() *2/5);
		TextGraphics textGraphics = screen.newTextGraphics();

		textGraphics.setForegroundColor(TextColor.ANSI.RED);

		KeyStroke keyStroke;
		KeyType keyType;

		int currentSelection = 0;
		while (true) {
			String greeting = "Hello, " + currentPlayer.getNickname() + "!";
			textGraphics.putString(terminalSize.getColumns() - greeting.length() - 3, 1, greeting);
			screen.setCursorPosition(startPosition.withRelativeRow(currentSelection));

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
					textGraphics.putString(startPosition, new String());
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

	private void printPlayerSelectWindow(TerminalPosition startPosition) throws IOException {

		String label = "Choose player";
		int nicknameMaxLength = 15;

		TerminalSize labelBoxSize = new TerminalSize(nicknameMaxLength + 10, players.size() + 8);
		TerminalPosition labelBoxTopRightCorner = startPosition.withRelativeColumn(labelBoxSize.getColumns() - 1);
		TextGraphics textGraphics = screen.newTextGraphics();
		drawDoubleLineBox(startPosition, labelBoxSize, TextColor.ANSI.RED, null);
		textGraphics.setForegroundColor(TextColor.ANSI.RED);
		textGraphics.putString(startPosition.withRelative(2, 2), label);
		screen.refresh();
		printPlayersList(0, startPosition.withRelative(3, 4));
	}

	private void printPlayersList(int currentSelection, TerminalPosition startPosition) throws IOException {
		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(TextColor.ANSI.RED);

		for (int i = 0; i < players.size() + 2; i++) {
			if (i == currentSelection) {
				if (i < players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BLINK,
							SGR.BOLD);
				}
			} else {
				if (i < players.size()) {
					textGraphics.putString(startPosition.withRelativeRow(i), players.get(i).getNickname(), SGR.BOLD);
				}
			}
			screen.refresh();
		}
	}

	private void printPSOptionsList(int currentSelection, TerminalPosition startPosition) throws IOException {
		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(TextColor.ANSI.RED);

		textGraphics.fillRectangle(startPosition, new TerminalSize(15, 1), ' ');

		for (int i = players.size(); i < players.size() + 2; i++) {
			if (i == currentSelection) {
				if (i == players.size()) {
					textGraphics.putString(startPosition, "ADD", SGR.ITALIC, SGR.BLINK);
				} else if (i == players.size() + 1) {
					textGraphics.putString(startPosition.withRelativeColumn(7), "DELETE", SGR.ITALIC, SGR.BLINK);
				}
			} else {
				if (i == players.size()) {
					textGraphics.putString(startPosition, "ADD", SGR.ITALIC);
				} else if (i == players.size() + 1) {
					textGraphics.putString(startPosition.withRelativeColumn(7), "DELETE", SGR.ITALIC);
				}
			}
			screen.refresh();
		}

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

		TerminalSize terminalSize = terminal.getTerminalSize();
		TerminalPosition startPosition = new TerminalPosition(terminalSize.getColumns() / 16,
				terminalSize.getRows() / 16);
		TextGraphics textGraphics = screen.newTextGraphics();
		TerminalSize towerBoxSize = new TerminalSize(10, 6);
		textGraphics.setForegroundColor(TextColor.ANSI.RED);

		List<Tower> towersTypes = new ArrayList<Tower>();
		towersTypes.add(new ArcherTower());
		towersTypes.add(new BallistaTower());
		towersTypes.add(new CannonTower());
		towersTypes.add(new DragonTower());
		towersTypes.add(new LaserTower());
		towersTypes.add(new MachineGunTower());
		towersTypes.add(new PoisonTower());
		towersTypes.add(new WizardTower());

		screen.clear();
		screen.setCursorPosition(null);

		for (int i = 0; i < towersTypes.size() / 2; i++) {
			for (int j = 0; j < 2; j++) {
				drawDoubleLineBox(startPosition.withRelative(14 * i, 8 * j), towerBoxSize, TextColor.ANSI.RED, null);
				drawTower(startPosition.withRelative(14 * i + 2, 8 * j + 1), towersTypes.get(i));
				textGraphics.putString(startPosition.withRelative(14 * i, 8 * j + 6),
						towersTypes.get(i + 4 * j).getName());
			}
		}

		textGraphics.drawLine(new TerminalPosition(terminalSize.getColumns() * 3 / 4, 0),
				new TerminalPosition(terminalSize.getColumns() * 3 / 4, terminalSize.getRows() - 1),
				Symbols.DOUBLE_LINE_VERTICAL);

		screen.refresh();
		screen.readInput();
		screen.clear();
	}

	private void drawTower(TerminalPosition startPoistion, Tower tower) {
		String stringTowerIcon = tower.getIcon();
		TextImage towerIcon = new BasicTextImage(new TerminalSize(6, 4));
		TextCharacter textCharacter;

		for (int row = 0, i = 0; row < 4; row++) {
			for (int column = 0; column < 6; column++, i++) {
				textCharacter = new TextCharacter(stringTowerIcon.charAt(i));
				towerIcon.setCharacterAt(column, row, textCharacter);
			}
		}
		TextGraphics textGraphics = screen.newTextGraphics();
		textGraphics.setForegroundColor(tower.getColor());
		textGraphics.drawImage(startPoistion, towerIcon);

	}

	private void drawDoubleLineBox(TerminalPosition startPosition, TerminalSize boxSize, TextColor foregroundColor,
			TextColor backgroundColor) throws IOException {
		TextGraphics textGraphics = screen.newTextGraphics();

		if (foregroundColor != null) {
			textGraphics.setForegroundColor(foregroundColor);
		}
		if (backgroundColor != null) {
			textGraphics.setBackgroundColor(backgroundColor);
		}

		// textGraphics.fillRectangle(startPosition, boxSize, ' ');
		textGraphics.setCharacter(startPosition, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
		textGraphics.setCharacter(startPosition.withRelativeColumn(boxSize.getColumns() - 1),
				Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
		textGraphics.drawLine(startPosition.withRelativeColumn(1),
				startPosition.withRelativeColumn(boxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);
		textGraphics.drawLine(startPosition.withRelativeRow(1), startPosition.withRelativeRow(boxSize.getRows() - 2),
				Symbols.DOUBLE_LINE_VERTICAL);
		textGraphics.drawLine(startPosition.withRelative(boxSize.getColumns() - 1, 1),
				startPosition.withRelative(boxSize.getColumns() - 1, boxSize.getRows() - 2),
				Symbols.DOUBLE_LINE_VERTICAL);
		textGraphics.drawLine(startPosition.withRelative(1, boxSize.getRows() - 1),
				startPosition.withRelative(boxSize.getColumns() - 2, boxSize.getRows() - 1),
				Symbols.DOUBLE_LINE_HORIZONTAL);
		textGraphics.setCharacter(startPosition.withRelativeRow(boxSize.getRows() - 1),
				Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
		textGraphics.setCharacter(startPosition.withRelative(boxSize.getColumns() - 1, boxSize.getRows() - 1),
				Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
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
