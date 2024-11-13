package lang_resources;

import java.util.*;

public class Lang_es_ES extends ListResourceBundle {

    public String[][] getContents() {
        return localizedStrings;
    }

    private String[][] localizedStrings = {
            // DIFFICULTY MENU STRINGS
            { "difficultyMenu_EASY", "1) Facil [6x6] (FxC)" },
            { "difficultyMenu_MEDIUM", "2) Media [10x10] (FxC)" },
            { "difficultyMenu_HARD", "3) Dificil [14x14]" },
            { "difficultyMenu_EXTREME", "4) Extrema [18x18] (FxC)" },
            { "difficultyMenu_CUSTOM", "5) Personalizada [-x-] (FxC)" },

            // DEBUG MODE
            { "debugMode_TXT", "Modo Debug: " },
            { "debugMode_ENABLED", "ACTIVADO" },
            { "debugMode_DISABLED", "DESACTIVADO" },
            { "debugMode_WARNING", ". No se activará el temporizador." },

            // SET-UP STRINGS
            { "setUp_difficulty_TXT", "Escoja la dificultad: " },
            { "setUp_difficulty_EASY", "FACIL" },
            { "setUp_difficulty_MEDIUM", "MEDIA" },
            { "setUp_difficulty_HARD", "DIFICIL" },
            { "setUp_difficulty_EXTREME", "EXTREMA" },
            { "setUp_difficulty_CUSTOM", "PERSONALIZADA" },
            { "setUp_difficulty_CHOSEN", "Dificultad escogida: " },
            { "setUp_rows_TXT", "Escoge la cantidad de filas (4 a 24): " },
            { "setUp_columns_TXT", "Escoge la cantidad de columnas (4 a 24): " },
            { "setUp_OutOfRange", "Numero fuera de rango.." },
            { "setUp_InputMismatch", "El valor introducido no es un numero." },
            { "setUp_columns_NotDivisible", "Las columnas deben de ser divisibles por dos.." },
            { "setUp_timeLimit_TXT", "Introduzca el tiempo límite (10s a 180s): " },

            // WAIT PHASE
            { "waitPhase_wait_TXT", "Espera.." },
            { "waitPhase_pressToStart_1", "Pulsa " },
            { "waitPhase_pressToStart_2", " para comenzar con el minijuego." },

            // GAME PHASE
            { "gamePhase_lockedSequence", "SECUENCIA BLOQUEADA: " },
            { "gamePhase_unlockedSequence", "SECUENCIA DESBLOQUEADA: " },
            { "gamePhase_timerFinished_1", "- BÚSQUEDA DE SECUENCIA " },
            { "gamePhase_timerFinished_2", "FALLIDA" },
            { "gamePhase_timerFinished_3", "- El tiempo se ha acabado." },

            // END PHASE
            { "endPhase_sequence_1", "SECUENCIA " },
            { "endPhase_sequence_2", "COMPLETADA" },

            // STATS OUTPUT
            { "statsOutput_in", " en " },
            { "statsOutput_seconds", " segundos -" },
            { "statsOutput_saveLocation", "Estadísticas de ronda guardadas en " },
            { "statsOutput_debugModeEnabled", ": las estadísticas no serán guardadas." },
            { "statsFileOutput_1", "- Completada en " },
            { "statsFileOutput_2", " segundos -" }

    };

}