package org.evertree.lettres.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class HighScores implements Serializable {

	private static final String FILE_NAME = "hiscores.dat";

	private static final long serialVersionUID = 1L;

	public static final int LINES = 5;

	private static HighScores instance;

	private String[] names = new String[LINES];
	private long[] values = new long[LINES];

	public static HighScores load() {
		if (instance == null) {
			FileInputStream input = null;
			try {
				input = new FileInputStream(FILE_NAME);
				ObjectInputStream obj_in = new ObjectInputStream(input);
				Object obj = obj_in.readObject();
				instance = (HighScores) obj;
			} catch (Exception e) {
				if (!(e instanceof FileNotFoundException)){
					e.printStackTrace();
				}
				instance = new HighScores();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

	public void add(String name, long score) throws IOException {
		if (score <= values[LINES - 1]) {
			return;
		}
		for (int i = 0; i < LINES; i++) {
			if (score > values[i]) {
				String previousName = names[i];
				long previousValue = values[i];
				for (int j = i + 1; j < LINES; j++) {
					String auxName = names[j];
					long auxValue = values[j];
					names[j] = previousName;
					values[j] = previousValue;
					previousName = auxName;
					previousValue = auxValue;
				}
				names[i] = name;
				values[i] = score;
				break;
			}
		}
		save();
	}

	public String[] getNames() {
		return names;
	}

	public long[] getValues() {
		return values;
	}

	private void save() throws IOException {
		File file = new File(FILE_NAME);
		FileOutputStream f_out = new FileOutputStream(file);
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this);
	}

	private HighScores() {
		// new instances only through getInstance()
	}

}
