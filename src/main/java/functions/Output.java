package functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Output {

	private final Date dateCurrent = new Date();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private final String fileName = dateFormat.format(dateCurrent) + ".log";
	private final File directoryName = new File(System.getenv("USERPROFILE") + File.separator + "dbrusev" + File.separator + "SequenceHunter");
	private final File file = new File(directoryName + File.separator + fileName);

	private final List<String> unlockedSequence;
	private final Integer timeTakenToComplete;

	private final ResourceBundle localizedOutput;

	/**
	 * @param sequence Unlocked sequence
	 * @param completedIn Seconds taken
	 * @param localizedOutput Current language
	 */
	public Output(List<String> sequence, Integer completedIn, ResourceBundle localizedOutput) {
		this.unlockedSequence = sequence;
		this.timeTakenToComplete = completedIn;
		this.localizedOutput = localizedOutput;
	}

	/**
	 * Returns the relative location of the stats file
	 * @return Relative file location
	 */
	public String getFileLocation() { return file.getAbsolutePath(); }

	/**
	 * Saves the round stats in a file which, if not created, is created,
	 * and if it's already created, appends in a new line.
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void saveRoundStatistics() {
		try {
			/*
			If the directory exists and the file is writable, prepares a BufferedWriter
			to write the round statistics in a file specified in the local variable "file"
			and a SimpleDateFormat with a time pattern to append to said file prepared above.
			 */
			if (directoryName.exists() && file.canWrite()) {
				BufferedWriter dataWriter = new BufferedWriter(new FileWriter(file, true));
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

				dataWriter.append(dateFormat.format(dateCurrent)).append(" -> [");
				for (int i = 0; i < unlockedSequence.size(); i++) {
					if (i + 1 == unlockedSequence.size())
						dataWriter.append(unlockedSequence.get(i));
					else
						dataWriter.append(unlockedSequence.get(i)).append("-");
				}

				dataWriter.append("] ").append(localizedOutput.getString("statsFileOutput_1"))
						.append(String.valueOf(timeTakenToComplete))
						.append(localizedOutput.getString("statsFileOutput_2"))
						.append("\n");
				dataWriter.close();
				file.setWritable(false);
			}
		} catch (IOException io) {
			System.err.println(io.getLocalizedMessage());
		} catch (SecurityException se) {
			System.err.println("PUT-ERROR-MSG-HERE");
		}

	}

}
