package functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Stats {

	private final Date dateCurrent = new Date();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private final String fileName = dateFormat.format(dateCurrent) + ".log";
	private final File directoryName = new File("SequenceHuntStats");
	private final File file = new File(directoryName + File.separator + fileName);

	private final List<String> unlockedSequence;
	private final Integer completedIn;

	private final ResourceBundle localizedOutput;

	/**
	 * @param sequence Unlocked sequence
	 * @param completedIn Seconds taken
	 * @param localizedOutput Current language
	 */
	public Stats(List<String> sequence, Integer completedIn, ResourceBundle localizedOutput) {
		this.unlockedSequence = sequence;
		this.completedIn = completedIn;
		this.localizedOutput = localizedOutput;
	}

	/**
	 * Returns the relative file location of the statistics file
	 * @return Relative file location
	 */
	public String getFileLocation() {
		return "./" + directoryName + "/" + fileName;
	}

	/**
	 * Saves the round stats in a file which, if not created, is created,
	 * and if it's already created, appends in a new line.
	 * @throws IOException
	 */
	public void saveRoundStatistics() throws IOException {
		if (!directoryName.exists()) directoryName.mkdirs();
        file.setWritable(true);
		BufferedWriter dataWriter = new BufferedWriter(new FileWriter(file, true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		dataWriter.append(dateFormat.format(dateCurrent)).append(" -> [");
		for (int i = 0; i < unlockedSequence.size(); i++) {
			if (i + 1 == unlockedSequence.size()) {
				dataWriter.append(unlockedSequence.get(i));
			} else {
                dataWriter.append(unlockedSequence.get(i)).append("-");
			}
		}

		dataWriter.append("] ").append(localizedOutput.getString("statsFileOutput_1"))
				.append(String.valueOf(completedIn))
				.append(localizedOutput.getString("statsFileOutput_2"))
				.append("\n");
		dataWriter.close();
		file.setWritable(false);

	}

}
