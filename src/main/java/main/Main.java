package main;

import functions.Countdown;
import functions.Methods;
import functions.Output;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.InputMismatchException;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Main {

    private static final Scanner reader = new Scanner(System.in);
    private static final String LANG_RESOURCES = "lang_resources.Lang";
    private static final List<String> unlockedSequence = new ArrayList<>();

    public static void main(String[] args) {

        /*
        - DECLARES VITAL OBJECTS NECESSARY TO RUN
        - TRIES TO PARSE SYSTEM LOCALE, LOADS IT AND INITIALIZES METHODS WITH SAID LOCALE
        - IF SYSTEM LOCALE CANNOT BE PARSED, WARNS THE USER AND LOADS DEFAULTS (English)
         */
        Methods methods;
        ResourceBundle localizedOutput;
        try {
            Locale currentLocale = new Locale.Builder()
                    .setLanguage(System.getProperty("user.language")).build();
            localizedOutput = ResourceBundle.getBundle(LANG_RESOURCES, currentLocale);
            methods = new Methods(localizedOutput);
        } catch (MissingResourceException misResException) {
            System.err.println("Couldn't load language-specific resource bundle. Fallback to defaults!");
            System.err.println(misResException.getLocalizedMessage());
            localizedOutput = ResourceBundle.getBundle(LANG_RESOURCES, Locale.ENGLISH);
            methods = new Methods(localizedOutput);
            methods.clearConsole(true);
        }

        /*
        USER IS SHOWN 5 DIFFERENT DIFFICULTIES TO CHOOSE FROM. IF THE USER INPUTS THE
        RANDOMLY GENERATED DEBUG CODE, DEBUG TRIGGER WILL BE ENABLED FOR THIS ROUND ONLY.
         */
        boolean userChoosesToPlay = true;
        while (userChoosesToPlay) {
            boolean debugModeStatus = false;
            int chosenDifficulty = -1;
            int secretDebugCode = (int) (Math.random() * (9999 - 1000)) + 100;
            while (chosenDifficulty < 1 || chosenDifficulty > 5) {
                methods.clearConsole(false);
                methods.showTitle();
                System.out.println(ansi().fg(CYAN).a(localizedOutput.getString("difficultyMenu_EASY")).reset());
                System.out.println(ansi().fg(GREEN).a(localizedOutput.getString("difficultyMenu_MEDIUM")).reset());
                System.out.println(ansi().fg(YELLOW).a(localizedOutput.getString("difficultyMenu_HARD")).reset());
                System.out.println(ansi().fg(RED).a(localizedOutput.getString("difficultyMenu_EXTREME")).reset());
                System.out.println(ansi().fg(MAGENTA).a(localizedOutput.getString("difficultyMenu_CUSTOM")).reset());
                System.out.print("\n- " + localizedOutput.getString("debugMode_TXT"));
                if (!debugModeStatus) {
                    System.out.print(ansi().fg(RED).a(localizedOutput.getString("debugMode_DISABLED")).reset() + " -\n\n");
                } else {
                    System.out.print(ansi().fg(GREEN).a(localizedOutput.getString("debugMode_ENABLED")).reset() + " -\n\n");
                }
                try {
                    System.out.print(localizedOutput.getString("setUp_difficulty_TXT"));
                    chosenDifficulty = reader.nextInt();
                    if (!debugModeStatus && chosenDifficulty == secretDebugCode)
                        debugModeStatus = true;
                    else if (debugModeStatus && chosenDifficulty == secretDebugCode)
                        debugModeStatus = false;
                } catch (Exception notANumber) {
                    reader.next();
                }
            }
            reader.nextLine();

            /*
            - DEPENDING ON THE DIFFICULTY CHOSEN PREVIOUSLY, THE MINIGAME WILL DISPLAY
            A MESSAGE WITH SAID DIFFICULTY AND PREPARE THE MINIGAME BOARD ACCORDINGLY
             */
            methods.setDifficulty(chosenDifficulty);
            System.out.print("\n" + localizedOutput.getString("setUp_difficulty_TXT"));
            switch (methods.getDifficulty()) {
                case EASY: // EASY DIFFICULTY
                    System.out.print(ansi().fg(CYAN).a(localizedOutput.getString("setUp_difficulty_EASY")).reset() + "\n");
                    break;
                case MEDIUM: // MEDIUM DIFFICULTY
                    System.out.print(ansi().fg(GREEN).a(localizedOutput.getString("setUp_difficulty_MEDIUM")).reset() + "\n");
                    break;
                case HARD: // HARD DIFFICULTY
                    System.out.print(ansi().fg(YELLOW).a(localizedOutput.getString("setUp_difficulty_HARD")).reset() + "\n");
                    break;
                case EXTREME: // EXTREME DIFFICULTY
                    System.out.print(ansi().fg(RED).a(localizedOutput.getString("setUp_difficulty_EXTREME")).reset() + "\n");
                    break;
                case CUSTOM: // CUSTOM DIFFICULTY
                    System.out.print(ansi().fg(MAGENTA).a(localizedOutput.getString("setUp_difficulty_CUSTOM")).reset() + "\n\n");
                    break;
            }

            /*
            IF THE CUSTOM DIFFICULTY WAS CHOSEN, THE USER WILL BE ASKED FOR THE AMOUNT OF ROWS
            AND COLUMNS THEY PREFER. COLUMNS MUST BE DIVISIBLE BY TWO TO PROCEED.
             */
            if (chosenDifficulty == 5) {
                int rows = 0, columns = 0;
                while (rows < 4 || rows > 24) {
                    try {
                        System.out.print(localizedOutput.getString("setUp_rows_TXT"));
                        rows = reader.nextInt();
                        if (rows < 4 || rows > 24)
                            System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");
                    } catch (Exception notANumber) {
                        System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n");
                        reader.next();
                    }
                }
                while (columns < 4 || columns > 24 || columns % 2 != 0) {
                    try {
                        System.out.print(localizedOutput.getString("setUp_columns_TXT"));
                        columns = reader.nextInt();
                        if ((columns >= 4 && columns <= 24) && columns % 2 != 0)
                            System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_columns_NotDivisible")).reset() + "\n");
                        else if (columns < 4 || columns > 24)
                            System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");
                    } catch (Exception notANumber) {
                        System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n");
                        reader.next();
                    }
                }
                methods.setRowsAndColumns(rows, columns);
            }

            // DECLARES THE COUNTDOWN CONTROLLER FOR THE ROUND
            Countdown timerController = new Countdown(localizedOutput);

            /*
            IF DEBUG MODE IS DISABLED (DEFAULT PLAYTHROUGH), THE USER WILL BE ASKED FOR
            THE TIME LIMIT TO COMPLETE THE GAME IN, BETWEEN 10 AND 180 SECONDS.
            AFTERWARD, THE COUNTDOWN WILL BE SET WITH SAID SPECIFIED TIME.
             */
            int maxTimeToCompleteIn = 0;
            if (!debugModeStatus) {
                while (maxTimeToCompleteIn < 10 || maxTimeToCompleteIn > 180) {
                    try {
                        System.out.print(localizedOutput.getString("setUp_timeLimit_TXT")); maxTimeToCompleteIn = reader.nextInt();
                        if (maxTimeToCompleteIn < 10 || maxTimeToCompleteIn > 180) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_OutOfRange")).reset() + "\n");}
                    } catch (Exception notANumber) {System.out.println(ansi().fg(RED).a(localizedOutput.getString("setUp_InputMismatch")).reset() + "\n"); reader.next();}
                }
                timerController.setTimeLimitToComplete(maxTimeToCompleteIn);
            }
            reader.nextLine();

            // OUTPUTS A "Wait.." TEXT, MERELY FOR DECORATION
            System.out.println("\n" + ansi().fg(RED).a(localizedOutput.getString("waitPhase_wait_TXT")).reset());

            // PREPARES THE BOARD AND REQUIRED SEQUENCES
            String[][] gameBoard = methods.setupBoard();
            List<Integer> rowSequence = methods.getRowSequence();
            List<Integer> numberSequence = methods.getNumberSequence();

            // FILLS THE UNLOCKED SEQUENCE WITH "xx", EMPTY
            for (int i = 0; i < numberSequence.size(); i++)
                unlockedSequence.add("xx");
            methods.clearConsole(true);

            /*
            SHOWS THE TITLE OF THE GAME, ALONG WITH DETAILS WHETHER THE DEBUG
            MODE WAS ENABLED THIS ROUND OR NOT.
             */
            methods.showTitle();
            if (debugModeStatus)
                System.out.println(localizedOutput.getString("debugMode_TXT")
                        + ansi().fg(RED).a(localizedOutput.getString("debugMode_ENABLED")).reset()
                        + localizedOutput.getString("debugMode_WARNING"));
            System.out.println(localizedOutput.getString("waitPhase_pressToStart_1") + ansi().fg(RED).a("ENTER").reset() + localizedOutput.getString("waitPhase_pressToStart_2"));
            reader.nextLine();

            // COUNTDOWN TIMER OF THREE SECONDS, WITH COLORS
            for (int i = 3; i > 0; i--) {
                switch (i) {
                    case 3:
                        System.out.print(ansi().fg(GREEN).a(i + ".. ").reset());
                        break;
                    case 2:
                        System.out.print(ansi().fg(YELLOW).a(i + ".. ").reset());
                        break;
                    case 1:
                        System.out.print(ansi().fg(RED).a(i + "..").reset());
                        break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.err.println(ie.getLocalizedMessage());
                }
            }

            // CLEARS THE SCREEN AND STARTS THE COUNTDOWN
            methods.clearConsole(false);
            if (!debugModeStatus) timerController.start();

            int columnCounter = 0;
            while (!numberSequence.isEmpty() && !rowSequence.isEmpty()) {
                int userInput = 0;
                while (userInput < 10 || userInput > 99) {
                    methods.showBoard(gameBoard, columnCounter);
                    methods.showUnlockedSequence(unlockedSequence, numberSequence.size());
                    try {
                        System.out.print("> ");
                        userInput = reader.nextInt();
                    } catch (InputMismatchException notANumber) {
                        userInput = 0;
                        reader.next();
                        methods.clearConsole(false);
                    }
                    if (userInput < 10)
                        methods.clearConsole(false);
                }

                for (int i = 0; i < methods.getRows(); i++) {
                    if (userInput == numberSequence.get(0) && i == rowSequence.get(0)) {
                        unlockedSequence.set(columnCounter, Integer.toString(userInput));
                        gameBoard[i][columnCounter] = gameBoard[i][columnCounter + 1] = "xx";

                        numberSequence.remove(0);
                        rowSequence.remove(0);
                        columnCounter++;
                        break;
                    } else
                        methods.clearConsole(false);
                }

                methods.clearConsole(false);
            }
            columnCounter++;

            // SHOWS THE FINISHED GAME BOARD, THE UNLOCKED SEQUENCE AND THE CORRESPONDING TEXT
            methods.showBoard(gameBoard, columnCounter);
            methods.showUnlockedSequence(unlockedSequence, numberSequence.size());
            System.out.print("- " + localizedOutput.getString("endPhase_sequence_1") + ansi().fg(GREEN).a(localizedOutput.getString("endPhase_sequence_2")).reset());

            /*
            IF DEBUG MODE IS DISABLED, PREPARES THE NECESSARY STUFF TO OUTPUT.
            OTHERWISE, DOES NOT DO ANYTHING (OTHER THAN SHOW A WARNING TEXT).
             */
            if (!debugModeStatus) {
                int timeTakenToComplete = maxTimeToCompleteIn - timerController.getRemainingTimeToComplete();
                Output saveStats = new Output(unlockedSequence, timeTakenToComplete, localizedOutput);
                saveStats.saveRoundStatistics();
                System.out.print(localizedOutput.getString("statsOutput_in") + timeTakenToComplete + localizedOutput.getString("statsOutput_seconds") + "\n");

                System.out.println(localizedOutput.getString("statsOutput_saveLocation") + ansi().fg(YELLOW).a(saveStats.getFileLocation()).reset());
            } else
                System.out.print(" -\n" + localizedOutput.getString("debugMode_TXT") + ansi().fg(RED).a(localizedOutput.getString("debugMode_ENABLED")).reset() + localizedOutput.getString("statsOutput_debugModeEnabled"));

            // ENABLES A CONTROLLER VARIABLE TO DISABLE THE TIMER AND WAITS 3 SECONDS
            timerController.setGameFinishedStatus(true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // SHOWS A MENU TO THE USER, WHO DECIDES IF THEY WANT TO REPLAY OR NOT
            boolean properInput = false;
            while (!properInput) {
                int chosenOption = -1;
                while (chosenOption < 1 || chosenOption > 2) {
                    System.out.println("\n\n" + localizedOutput.getString("replay_MSG"));
                    System.out.println("\n1) " + ansi().fg(GREEN).a(localizedOutput.getString("replay_YES")).reset());
                    System.out.println("2) " + ansi().fg(RED).a(localizedOutput.getString("replay_NO")).reset());
                    try {
                        System.out.print("\n> ");
                        chosenOption = reader.nextInt();
                        userChoosesToPlay = chosenOption == 1;
                        properInput = true;
                    } catch (Exception notANumber) {
                        reader.next();
                    }
                } reader.nextLine();
            }

        }

    }

}
