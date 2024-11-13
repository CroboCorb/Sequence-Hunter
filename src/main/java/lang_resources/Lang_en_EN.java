package lang_resources;

import java.util.*;

public class Lang_en_EN extends ListResourceBundle {

    public String[][] getContents() {
        return localizedStrings;
    }

    private String[][] localizedStrings = {
            // DIFFICULTY MENU STRINGS
            { "difficultyMenu_EASY", "1) Easy [6x6] (FxC)" },
            { "difficultyMenu_MEDIUM", "2) Medium [10x10] (FxC)" },
            { "difficultyMenu_HARD", "3) Hard [14x14]" },
            { "difficultyMenu_EXTREME", "4) Extreme [18x18] (FxC)" },
            { "difficultyMenu_CUSTOM", "5) Custom [-x-] (FxC)" },

            // DEBUG MODE
            { "debugMode_TXT", "Debug Mode: " },
            { "debugMode_ENABLED", "ENABLED" },
            { "debugMode_DISABLED", "DISABLED" },
            { "debugMode_WARNING", ". Timer will not be enabled." },

            // SET-UP STRINGS
            { "setUp_difficulty_TXT", "Choose the difficulty: " },
            { "setUp_difficulty_EASY", "EASY" },
            { "setUp_difficulty_MEDIUM", "MEDIUM" },
            { "setUp_difficulty_HARD", "HARD" },
            { "setUp_difficulty_EXTREME", "EXTREME" },
            { "setUp_difficulty_CUSTOM", "CUSTOM" },
            { "setUp_difficulty_CHOSEN", "Chosen difficulty: " },
            { "setUp_rows_TXT", "Choose the amount of rows (4 to 24): " },
            { "setUp_columns_TXT", "Choose the amount of columns (4 to 24): " },
            { "setUp_OutOfRange", "Number is out of range.." },
            { "setUp_InputMismatch", "Chosen value is not a number." },
            { "setUp_columns_NotDivisible", "The columns must be divisible by two.." },
            { "setUp_timeLimit_TXT", "Input the time limit (10s to 180s): " },

            // WAIT PHASE
            { "waitPhase_wait_TXT", "Wait.." },
            { "waitPhase_pressToStart_1", "Press " },
            { "waitPhase_pressToStart_2", " to start the minigame." },

            // GAME PHASE
            { "gamePhase_lockedSequence", "LOCKED SEQUENCE: " },
            { "gamePhase_unlockedSequence", "UNLOCKED SEQUENCE: " },
            { "gamePhase_timerFinished_1", "- SEQUENCE SEARCH " },
            { "gamePhase_timerFinished_2", "FAILED" },
            { "gamePhase_timerFinished_3", "- Time has run out." },

            // END PHASE
            { "endPhase_sequence_1", "SEQUENCE " },
            { "endPhase_sequence_2", "COMPLETED" },

            // STATS OUTPUT
            { "statsOutput_in", " in " },
            { "statsOutput_seconds", " seconds -" },
            { "statsOutput_saveLocation", "Round stats saved in " },
            { "statsOutput_debugModeEnabled", ": stats will not be saved." },
            { "statsFileOutput_1", "- Completed en " },
            { "statsFileOutput_2", " seconds -" }
    };

}
