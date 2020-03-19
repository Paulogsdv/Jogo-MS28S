package org.evertree.lettres.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class LetterSource {

	private static String[] letters = new String[0];

	private final static Random RANDOM = new Random();

	private LetterSource() {
		// Static class
	}

	public static void load(Set<String> words) {
		List<String> lettersList = new ArrayList<String>();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				lettersList.add(word.substring(i, i + 1));
			}
		}
		letters = lettersList.toArray(new String[lettersList.size()]);
	}

	public static String getLetter() {
		return letters[(RANDOM.nextInt(letters.length))];
	}

}
