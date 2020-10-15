package org.evertree.lettres.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class SubsetLetterSource {


	private static String[] letters = new String[0];

	private final static Random RANDOM = new Random();
	
	private static int subset = 0;
	private static int readLetters = 0;
	private static int subsetSize;
	private static final int NUMBER_SUBSETS = 3;

	private SubsetLetterSource() {
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
		subsetSize = letters.length/NUMBER_SUBSETS;
		System.out.println("LETTERS:");
		System.out.println(Arrays.toString(letters));
		System.out.println("TOTAL: " + letters.length);
		System.out.println("NUMBER OF SUBSETS: " + NUMBER_SUBSETS);
		System.out.println("SUBSETSIZE: " + subsetSize);
	}

	public static String getLetter() {
		readLetters++;
		if (readLetters > letters.length){
			readLetters = 0;
			subset = subset + 1 % NUMBER_SUBSETS;
		}
		System.out.println("READ: " + readLetters);
		System.out.println("SUBSET: " + subset);

		return readLetters > letters.length ? letters[subsetSize * subset + RANDOM.nextInt(subsetSize)] : letters[subsetSize * subset + RANDOM.nextInt(subsetSize)];
	}

}
