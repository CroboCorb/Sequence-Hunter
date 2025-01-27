package functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.HashSet;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;

public class Methods {

	private int rows, columns;

	private final List<Integer> rowSequence = new ArrayList<>();
	private final List<Integer> numberSequence = new ArrayList<>();

	private final ResourceBundle localizedOutput;

	private Difficulty difficulty;

    public Methods(ResourceBundle language) {
        this.localizedOutput = language;
    }

	/**
	 * @return Current amount of rows of the board
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets the rows and columns required for building the game board
	 * @param rows Rows for the minigame board
	 * @param columns Columns for the minigame board
	 */
	public void setRowsAndColumns(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * @return Current difficulty of the game
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @return Sequence of rows with location of matching numbers
	 */
	public List<Integer> getRowSequence() {
		return rowSequence;
	}

	/**
	 * @return Sequence of matching numbers required to complete the game
	 */
	public List<Integer> getNumberSequence() {
		return numberSequence;
	}

	/**
	 * Sets the difficulty (rows and columns), required to create the game board
	 * @param choice chosen difficulty from 1 to 5 (easy, medium, hard, extreme & custom)
	 */
	public void setDifficulty(int choice) {
		switch (choice) {
			case 1: // EASY DIFFICULTY
				this.difficulty = Difficulty.EASY;
				this.rows = this.columns = 6;
				break;
			case 2: // MEDIUM DIFFICULTY
				this.difficulty = Difficulty.MEDIUM;
				this.rows = this.columns = 10;
				break;
			case 3: // HARD DIFFICULTY
				this.difficulty = Difficulty.HARD;
				this.rows = this.columns = 14; break;
			case 4: // EXTREME DIFFICULTY
				this.difficulty = Difficulty.EXTREME;
				this.rows = this.columns = 18;
				break;
			case 5: // CUSTOM DIFFICULTY
				this.difficulty = Difficulty.CUSTOM;
				break;
		}
	}

	/**
	 * Prepares the minigame board, generating numbers and building matching patterns
	 * @return Prepared game board
	 */
	public String[][] setupBoard() {
		String[][] board = new String[rows][columns];

  		/*
  		Declares a HashSet to prevent duplicate numbers from being
  		generated. Afterward, those numbers are cast into Strings
  		and assigned to their respective positions on the board.
  		 */
        for (int i = 0; i < rows; i++) {
			int j = 0;
			HashSet<Integer> randomNumberList = new HashSet<>();
			while (randomNumberList.size() != columns)
				randomNumberList.add((int) (Math.random() * (99 - 10)) + 10);
            for (Integer integer : randomNumberList) {
                board[i][j] = Integer.toString(integer);
                j++;
            }
		}

		/*
		After filling the board with random and unique numbers, a loop
		runs over each column and adds a randomly chosen row into the list.
		If said randomly chosen row is the same as the previously chosen
		row, another one will be chosen until it no longer the same.
		 */
		for (int iterator = 0; iterator < columns - 1; iterator++) {
			rowSequence.add((int) (Math.random() * rows));
			while (iterator >= 1 && rowSequence.get(iterator).equals(rowSequence.get(iterator-1)))
				rowSequence.set(iterator, (int) (Math.random() * rows));
		}

		/*
		After choosing the rows where the matching numbers will be,
		the loop will iterate over every column (except the last one) and
		switch the number in the next column (EX: R5-C1, changes number in
		R5-C2), and afterward it will be added into the number sequence.
		 */
		for (int column = 0; column < columns; column++) {
			if (column + 1 != columns) {
				int chosenRow = rowSequence.get(column);
				board[chosenRow][column + 1] = board[chosenRow][column];
				numberSequence.add(Integer.parseInt(board[chosenRow][column]));
			}
		} return board;
	}

	/**
	 * Shows the title of the game.
	 */
	public void showTitle() {
		AnsiConsole.systemInstall();
		System.out.println(ansi().fg(GREEN).a("   _____                         _    _       _        ").reset());
		System.out.println(ansi().fg(GREEN).a("  / ____|                       | |  | |     | |       ").reset());
		System.out.println(ansi().fg(GREEN).a(" | (___   __ _ _ __   ___ ______| |__| |_ __ | |_ _ __ ").reset());
		System.out.println(ansi().fg(GREEN).a("  \\___ \\ / _` | '_ \\ / __|______|  __  | '_ \\| __| '__|").reset());
		System.out.println(ansi().fg(GREEN).a("  ____) | (_| | | | | (__       | |  | | | | | |_| |   ").reset());
		System.out.println(ansi().fg(GREEN).a(" |_____/ \\__, |_| |_|\\___|      |_|  |_|_| |_|\\__|_|   ").reset());
		System.out.println(ansi().fg(GREEN).a("            | |                                        ").reset());
		System.out.println(ansi().fg(GREEN).a("            |_|                                        ").reset() + "\n");
	}

	/**
	 * Shows the current game board to the user, drawing borders and coloring the numbers
	 * of the board depending on the required "counter" (current column) parameter.
	 * @param board Current game board
	 * @param counter Current column number
	 */
	public void showBoard(String[][] board, int counter) {
		// Calculates the horizontal board borders depending on the amount of columns
		int horizontalBoardBorders = (columns - 1) + (columns * 4);
		showTitle(); counter--;

		// Draws the upper border of the game board
		System.out.print("╔");
		for (int drawnBorder = 0; drawnBorder < horizontalBoardBorders; drawnBorder++) {
			System.out.print("═");
		} System.out.print("╗\n");

		// Draws the inner area of the game board
		for (int i = 0; i < rows; i++) {
			System.out.print("║ "); // Left borders
			for (int j = 0; j < columns; j++) {
				if (board[i][j].equals("xx")) { // If current position is "XX", color is set to green
					if (j + 1 == columns) {System.out.print(ansi().fg(GREEN).a(board[i][j]).reset());}
					else {System.out.print(ansi().fg(GREEN).a(board[i][j]).reset() + " - ");}
				} else { // Otherwise, check the following conditions
					if (j <= counter) { // If column iterator is less or equal than counter, set to red
						if (j + 1 == columns) {System.out.print(ansi().fg(RED).a(board[i][j]).reset());}
						else {System.out.print(ansi().fg(RED).a(board[i][j]).reset() + " - ");}
					} else { // Otherwise, keep the default color output
						if (j + 1 == columns) {System.out.print(board[i][j]);}
						else {System.out.print(board[i][j] + " - ");}
					}
				}
			} System.out.print(" ║\n"); // Right borders
		}

		// Draws the bottom border of the game board
		System.out.print("╚");
		for (int drawnBorder = 0; drawnBorder < horizontalBoardBorders; drawnBorder++) {
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

		/*
		If all numbers were unlocked, changes the title output color to green.
		Otherwise, displays the current title output color to cyan.
		 */
		if (numbersToUnlock == 0) {System.out.print("\n" + ansi().fg(GREEN).a(localizedOutput.getString("gamePhase_unlockedSequence")).reset() + "\n[");}
		else {System.out.print("\n" + ansi().fg(CYAN).a(localizedOutput.getString("gamePhase_lockedSequence")).reset() + "\n[");}

		/*
		Prints the current unlocked sequence, whether the number
		has already been unlocked or not.
		 */
		for (int i = 0; i < sequence.size(); i++) {
			if (i + 1 != sequence.size()) {System.out.print(sequence.get(i) + "-");}
			else {System.out.print(sequence.get(i));}
		} System.out.print("]\n\n");
	}

	/**
	 * Clears the console screen by using
	 * @param timerEnabled If enabled, waits 2 seconds before clearing the console. If false, clears instantly.
	 */
	public void clearConsole(boolean timerEnabled) {
		try {
			if (timerEnabled) // If timer is enabled, waits 2 seconds
				Thread.sleep(2000);

			// Obtains the OS name where the game is running in
			String osName = System.getProperty("os.name");
			if (osName.toUpperCase().contains("WINDOWS")) // If System is Windows, uses CMD to clear
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else // Else if system is either Mac or Linux, uses an output with an ANSI escape sequence
				System.out.print("\033[H\033[2J");
		} catch (InterruptedException | IOException ie) {
			System.err.println(ie.getLocalizedMessage());
		}
    }

}
