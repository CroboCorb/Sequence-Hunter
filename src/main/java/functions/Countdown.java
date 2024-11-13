package functions;

import java.util.ResourceBundle;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Countdown extends Thread {

	private final ResourceBundle localizedOutput;

	private int timeLimit;

	/**
	 * @param language Current language
	 */
	public Countdown(ResourceBundle language) {
		this.localizedOutput = language;
	}

	/**
	 * Returns the current remaining time
	 * @return Remaining time in seconds
	 */
	public int getRemainingTime() {
		return timeLimit;
	}

	/**
	 * Sets the time limit, in seconds, in which the minigame must be completed
	 * @param timeLimit Minigame time limit (seconds)
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	public void run() {
		AnsiConsole.systemInstall();
		while (timeLimit != 0) {
			try {Thread.sleep(1000);}
			catch (InterruptedException e) {System.exit(1);}
			timeLimit--;
		}

		System.out.println(localizedOutput.getString("gamePhase_timerFinished_1")
				+ ansi().fg(RED).a(localizedOutput.getString("gamePhase_timerFinished_2")).reset()
				+ localizedOutput.getString("gamePhase_timerFinished_3"));
		System.exit(0);
	}

}
