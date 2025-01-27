package functions;

import java.util.ResourceBundle;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;

public class Countdown extends Thread {

	private final ResourceBundle localizedOutput;

	private int timeLimitToComplete;
	private boolean gameStatus;

	/**
	 * @param language Current language
	 */
	public Countdown(ResourceBundle language) {
		this.localizedOutput = language;
		this.timeLimitToComplete = 0;
		this.gameStatus = false;
	}

	/**
	 * Sets the time limit, in seconds, in which the minigame must be completed
	 * @param timeLimitToComplete Minigame time limit (seconds)
	 */
	public void setTimeLimitToComplete(int timeLimitToComplete) {
		this.timeLimitToComplete = timeLimitToComplete;
	}

	/**
	 * Returns the current remaining time to complete
	 * @return Remaining time in seconds
	 */
	public int getRemainingTimeToComplete() {
		return this.timeLimitToComplete;
	}

	/**
	 * Sets the game's current status
	 */
	public void setGameFinishedStatus(boolean gameStatus) {
		this.gameStatus = gameStatus;
	}

	@Override
	public void run() {
		boolean timerHasEnded = false;
		// Countdown timer with specified seconds
		while (!this.gameStatus && this.timeLimitToComplete != 0) {
			try {Thread.sleep(1000);}
			catch (InterruptedException e) {System.exit(1);}
			this.timeLimitToComplete--;

			if (this.timeLimitToComplete == 0)
				timerHasEnded = true;
		}

		// If countdown has ended, outputs specified messages and finishes the game.
		if (timerHasEnded) {
			AnsiConsole.systemInstall();
			System.out.println(localizedOutput.getString("gamePhase_timerFinished_1")
					+ ansi().fg(RED).a(localizedOutput.getString("gamePhase_timerFinished_2")).reset()
					+ localizedOutput.getString("gamePhase_timerFinished_3"));
			System.exit(0);
		}
	}

}
