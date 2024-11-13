package functions;

import java.io.IOException;
import java.util.*;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Methods {

	private int rows, columns;

	private final Scanner reader = new Scanner(System.in);

	private final List<Integer> rowSequence = new ArrayList<>();
	private final List<Integer> numberSequence = new ArrayList<>();

	private final ResourceBundle localizedOutput;

	private Difficulty difficulty;

    public Methods(ResourceBundle language) {
        this.localizedOutput = language;
    }

	/**
	 * Returns the amount of rows of the board
	 * @return Current rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets the rows and columns required for generating the minigame board
	 * @param rows Rows of the minigame board
	 * @param columns Columns of the minigame board
	 */
	public void setRowsAndColumns(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * Returns the current difficulty
	 * @return Current difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Returns the rows sequence (location of matching numbers) required to complete the minigame
	 * @return Current rows
	 */
	public List<Integer> getRowSequence() {
		return rowSequence;
	}

	/**
	 * Returns the numbers sequence required to complete the minigame
	 * @return Current rows
	 */
	public List<Integer> getNumberSequence() {
		return numberSequence;
	}

	/**
	 * Sets the difficulty (rows and columns), required to create the game board
	 * @param choice Input choice controlled by a menu (1: easy, 2: medium, 3: hard, 4: extreme, 5: custom)
	 */
	public void setDifficulty(int choice) {
		switch (choice) {
			case 1:
				this.difficulty = Difficulty.EASY;
				this.rows = this.columns = 6;
				break;
			case 2:
				this.difficulty = Difficulty.MEDIUM;
				this.rows = this.columns = 10;
				break;
			case 3:
				this.difficulty = Difficulty.HARD;
				this.rows = this.columns = 14; break;
			case 4:
				this.difficulty = Difficulty.EXTREME;
				this.rows = this.columns = 18;
				break;
			case 5:
				this.difficulty = Difficulty.CUSTOM;
				break;
		}
	}

	/**
	 * Prepares the minigame board, generating random integers and matching patterns
	 * @return Minigame board
	 */
	public String[][] setupBoard() {
		String[][] board = new String[rows][columns];

		for (int i = 0; i < rows; i++) {
			HashSet<Integer> randomNumerList = new HashSet<>(); int j = 0;
			while (randomNumerList.size() != columns) {randomNumerList.add((int) (Math.random() * (99 - 10)) + 10);}
            for (Integer integer : randomNumerList) {
                board[i][j] = Integer.toString(integer);
                j++;
            }
		}

		for (int iterator = 0; iterator < columns - 1; iterator++) {
			rowSequence.add((int) (Math.random() * rows));
			while (iterator >= 1 && rowSequence.get(iterator).equals(rowSequence.get(iterator-1))) {
				rowSequence.set(iterator, (int) (Math.random() * rows));
			}
		}

		for (int column = 0; column < columns; column++) {
			if (column + 1 != columns) {
				int chosenRow = rowSequence.get(column);
				board[chosenRow][column + 1] = board[chosenRow][column];
				numberSequence.add(Integer.parseInt(board[chosenRow][column]));
			}
		} return board;
	}

	/**
	 * Returns the current remaining time before the minigame ends.
	 * @return Remaining time in seconds
	 */
	public int getTimeLimit() {
		int timeLimit = 0;
		AnsiConsole.systemInstall();
		while (timeLimit < 10 || timeLimit > 180) {
			try {
				System.out.print(localizedOutput.getString("setUp_timeLimit_TXT")); timeLimit = reader.nextInt();
				if (timeLimit < 10 || timeLimit > 180) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");}
			} catch (Exception notANumber) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n"); reader.next();}
		} return timeLimit;
	}

	/**
	 * Shows the title of the minigame.
	 */
	public void showTitle() {
		AnsiConsole.systemInstall();
		System.out.println(ansi().fg(GREEN).a("  ____                   ____                      _           __  __ ____  _     ").reset());
		System.out.println(ansi().fg(GREEN).a(" / ___|  __ _ _ __      / ___|  ___  __ _ _ __ ___| |__       |  \\/  |  _ \\| |    ").reset());
		System.out.println(ansi().fg(GREEN).a(" \\___ \\ / _` | '_ \\ ____\\___ \\ / _ \\/ _` | '__/ __| '_ \\ _____| |\\/| | | | | |    ").reset());
		System.out.println(ansi().fg(GREEN).a("  ___) | (_| | | | |_____|__) |  __/ (_| | | | (__| | | |_____| |  | | |_| | |___ ").reset());
		System.out.println(ansi().fg(GREEN).a(" |____/ \\__, |_| |_|    |____/ \\___|\\__,_|_|  \\___|_| |_|     |_|  |_|____/|_____|").reset());
		System.out.println(ansi().fg(GREEN).a("           |_|                                                                    ").reset() + "\n");
	}

	/**
	 * Shows the current game board to the user, drawing borders and
	 * coloring numbers respectively depending on the "counter" variable.
	 * @param board Current game board
	 * @param counter Current column number
	 */
	public void showBoard(String[][] board, int counter) {
		int boardBorders = (columns - 1) + (columns * 4);
		showTitle(); counter--;

		System.out.print("╔");
		for (int drawnBorder = 0; drawnBorder < boardBorders; drawnBorder++) {
			System.out.print("═");
		} System.out.print("╗\n");

		for (int i = 0; i < rows; i++) {
			System.out.print("║ ");
			for (int j = 0; j < columns; j++) {
				if (board[i][j].equals("xx")) {
					if (j + 1 == columns) {System.out.print(ansi().fg(GREEN).a(board[i][j]).reset());}
					else {System.out.print(ansi().fg(GREEN).a(board[i][j]).reset() + " - ");}
				} else {
					if (j <= counter) {
						if (j + 1 == columns) {System.out.print(ansi().fg(RED).a(board[i][j]).reset());}
						else {System.out.print(ansi().fg(RED).a(board[i][j]).reset() + " - ");}
					} else {
						if (j + 1 == columns) {System.out.print(board[i][j]);}
						else {System.out.print(board[i][j] + " - ");}
					}
				}
			} System.out.print(" ║\n");
		}

		System.out.print("╚");
		for (int drawnBorder = 0; drawnBorder < boardBorders; drawnBorder++) {
			System.out.print("═");
		} System.out.print("╝\n");

	}

	/**
	 * Outputs the current sequence that the user has managed to unlock.
	 * @param sequence List with current unlocked sequence
	 * @param numbersToUnlock Numbers remaining to fully unlock
	 */
	public void showUnlockedSequence(List<String> sequence, int numbersToUnlock) {
		AnsiConsole.systemInstall();

		if (numbersToUnlock == 0) {System.out.print("\n" + ansi().fg(GREEN).a(localizedOutput.getString("gamePhase_unlockedSequence")).reset() + "\n[");}
		else {System.out.print("\n" + ansi().fg(CYAN).a(localizedOutput.getString("gamePhase_lockedSequence")).reset() + "\n[");}

		for (int i = 0; i < sequence.size(); i++) {
			if (i + 1 != sequence.size()) {System.out.print(sequence.get(i) + "-");}
			else {System.out.print(sequence.get(i));}
		} System.out.print("]\n\n");
	}

	/**
	 * Clears the console screen, avoiding "black flashes" that occur with loops.
	 * @param timerEnabled If enabled, waits 2 seconds before clearing console.
	 *                        If false, clears directly.
	 * @throws InterruptedException Interruption Exception
	 * @throws IOException Input / Output Exception
	 */
	public void clearConsole(boolean timerEnabled) throws InterruptedException, IOException {
		if (timerEnabled) {Thread.sleep(2000);}
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

}
