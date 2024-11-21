package main;

import java.io.IOException;
import java.util.*;
import functions.*;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Main {

	private static final Scanner reader = new Scanner(System.in);
    private static final String LANG_RES_ACCESS = "lang_resources.Lang";
    private static final List<String> unlockedSequence = new ArrayList<>();

	private static boolean debugModeStatus = false;

	public static void main(String[] args) throws InterruptedException, IOException {

        Methods methods;
        ResourceBundle localizedOutput;
        try {
			Locale currentLocale = new Locale.Builder()
					.setLanguage(System.getProperty("user.language"))
					.setRegion(System.getProperty("user.region")).build();
			localizedOutput = ResourceBundle.getBundle(LANG_RES_ACCESS, currentLocale);
			methods = new Methods(localizedOutput);
		} catch (MissingResourceException misResException) {
			System.err.println("Couldn't load language-specific resource bundle. Fallback to defaults!");
			System.err.println(misResException.getMessage());
			localizedOutput = ResourceBundle.getBundle(LANG_RES_ACCESS, Locale.ENGLISH);
			methods = new Methods(localizedOutput);
			methods.clearConsole(true);
		}

		int chosenDifficulty = -1;
		int secretDebugCode = (int) (Math.random() * (9999 - 1000)) + 100;
		while (chosenDifficulty < 1 || chosenDifficulty > 5) {
			methods.clearConsole(false); methods.showTitle();
			System.out.println(ansi().fg(CYAN).a(localizedOutput.getString("difficultyMenu_EASY")).reset());
			System.out.println(ansi().fg(GREEN).a(localizedOutput.getString("difficultyMenu_MEDIUM")).reset());
			System.out.println(ansi().fg(YELLOW).a(localizedOutput.getString("difficultyMenu_HARD")).reset());
			System.out.println(ansi().fg(RED).a(localizedOutput.getString("difficultyMenu_EXTREME")).reset());
			System.out.println(ansi().fg(MAGENTA).a(localizedOutput.getString("difficultyMenu_CUSTOM")).reset());
			System.out.print("\n- " + localizedOutput.getString("debugMode_TXT"));
			if (!debugModeStatus) {System.out.print(ansi().fg(RED).a(localizedOutput.getString("debugMode_DISABLED")).reset() + " -\n\n");}
			else {System.out.print(ansi().fg(GREEN).a(localizedOutput.getString("debugMode_ENABLED")).reset() + " -\n\n");}
			try {
				System.out.print(localizedOutput.getString("setUp_difficulty_TXT")); chosenDifficulty = reader.nextInt();
				if (!debugModeStatus && chosenDifficulty == secretDebugCode)
					debugModeStatus = true;
				else if (debugModeStatus && chosenDifficulty == secretDebugCode)
					debugModeStatus = false;
			} catch (Exception noEsNumero) {reader.next();}
		} reader.nextLine();

		methods.setDifficulty(chosenDifficulty);
		System.out.print("\n" + localizedOutput.getString("setUp_difficulty_TXT"));
		if (methods.getDifficulty() == Difficulty.EASY) {System.out.print(ansi().fg(CYAN).a(localizedOutput.getString("setUp_difficulty_EASY")).reset() + "\n");}
		else if (methods.getDifficulty() == Difficulty.MEDIUM) {System.out.print(ansi().fg(GREEN).a(localizedOutput.getString("setUp_difficulty_MEDIUM")).reset() + "\n");}
		else if (methods.getDifficulty() == Difficulty.HARD) {System.out.print(ansi().fg(YELLOW).a(localizedOutput.getString("setUp_difficulty_HARD")).reset() + "\n");}
		else if (methods.getDifficulty() == Difficulty.EXTREME) {System.out.print(ansi().fg(RED).a(localizedOutput.getString("setUp_difficulty_EXTREME")).reset() + "\n");}
		else {System.out.print(ansi().fg(MAGENTA).a(localizedOutput.getString("setUp_difficulty_CUSTOM")).reset() + "\n\n");}

		if (chosenDifficulty == 5) {
			int rows = 0, columns = 0;
			while (rows < 4 || rows > 24) {
				try {
					System.out.print(localizedOutput.getString("setUp_rows_TXT")); rows = reader.nextInt();
					if (rows < 4 || rows > 24) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");}
				} catch (Exception notANumber) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n"); reader.next();}
			}
			while (columns < 4 || columns > 24 || columns % 2 != 0) {
				try {
					System.out.print(localizedOutput.getString("setUp_columns_TXT")); columns = reader.nextInt();
					if ((columns >= 4 && columns <= 24) && columns % 2 != 0) {
						System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_columns_NotDivisible")).reset() + "\n");
					} else if (columns < 4 || columns > 24) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");}
				} catch (Exception notANumber) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n"); reader.next();}
			} methods.setRowsAndColumns(rows, columns);
		}

		int timeLimitToComplete = 0;
		Countdown timerController = new Countdown(localizedOutput);
		if (!debugModeStatus) {
			timeLimitToComplete = methods.getTimeLimit();
			timerController.setTimeLimit(timeLimitToComplete);
		} System.out.println("\n" + ansi().fg(RED).a(localizedOutput.getString("waitPhase_wait_TXT")).reset());

        String[][] gameBoard = methods.setupBoard();
        List<Integer> rowSequence = methods.getRowSequence();
        List<Integer> numberSequence = methods.getNumberSequence();

		for (int i = 0; i < numberSequence.size(); i++) {
			unlockedSequence.add("xx");
		} methods.clearConsole(true);

		if (debugModeStatus) {System.out.println(localizedOutput.getString("debugMode_TXT") + ansi().fg(RED).a(localizedOutput.getString("debugMode_ENABLED")).reset() + localizedOutput.getString("debugMode_WARNING"));}
		System.out.println(localizedOutput.getString("waitPhase_pressToStart_1") + ansi().fg(RED).a("ENTER").reset() + localizedOutput.getString("waitPhase_pressToStart_2"));
		reader.nextLine();

		for (int i = 3; i > 0; i--) {
			switch (i) {
				case 3: System.out.print(ansi().fg(GREEN).a(i + ".. ").reset()); Thread.sleep(1000); break;
				case 2: System.out.print(ansi().fg(YELLOW).a(i + ".. ").reset()); Thread.sleep(1000); break;
				case 1: System.out.print(ansi().fg(RED).a(i + "..").reset()); Thread.sleep(1000); break;
			}
		} methods.clearConsole(false);

		if (!debugModeStatus) {timerController.start();}

		int columnCounter = 0;
		while (!numberSequence.isEmpty() && !rowSequence.isEmpty()) {
			int userInput = 0;
			while (userInput < 10 || userInput > 99) {
				methods.showBoard(gameBoard, columnCounter);
				methods.showUnlockedSequence(unlockedSequence, numberSequence.size());
				try {System.out.print("> "); userInput = reader.nextInt();}
				catch (InputMismatchException notANumber) {userInput = 0; reader.next(); methods.clearConsole(false);}
				if (userInput < 10) {
                    methods.clearConsole(false);}
			}

			for (int i = 0; i < methods.getRows(); i++) {
				if (userInput == numberSequence.get(0) && i == rowSequence.get(0)) {
					unlockedSequence.set(columnCounter, Integer.toString(userInput));
					gameBoard[i][columnCounter] = gameBoard[i][columnCounter+1] = "xx";

					numberSequence.remove(0); rowSequence.remove(0);
					columnCounter++; break;
				} else {
                    methods.clearConsole(false);}
			} methods.clearConsole(false);
		} columnCounter++;

		methods.showBoard(gameBoard, columnCounter);
		methods.showUnlockedSequence(unlockedSequence, numberSequence.size());
		System.out.print("- " + localizedOutput.getString("endPhase_sequence_1") + ansi().fg(GREEN).a(localizedOutput.getString("endPhase_sequence_2")).reset());

		if (!debugModeStatus) {
			int timeTakenToComplete = timeLimitToComplete - timerController.getRemainingTime();
			Stats saveStats = new Stats(unlockedSequence, timeTakenToComplete, localizedOutput);
			saveStats.saveRoundStatistics();
			System.out.print(localizedOutput.getString("statsOutput_in") + timeTakenToComplete + localizedOutput.getString("statsOutput_seconds") + "\n");

			System.out.println(localizedOutput.getString("statsOutput_saveLocation") + ansi().fg(YELLOW).a(saveStats.getFileLocation()).reset());
		} else {System.out.print(" -\n" + localizedOutput.getString("debugMode_TXT") + ansi().fg(RED).a(localizedOutput.getString("debugMode_ENABLED")).reset() + localizedOutput.getString("statsOutput_debugModeEnabled"));}

		timerController.interrupt();

	}

}
