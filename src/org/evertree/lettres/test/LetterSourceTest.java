package org.evertree.lettres.test;

import java.util.Arrays;
import java.util.HashSet;

import org.evertree.lettres.action.LetterSource;

public class LetterSourceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] words = { "MCL", "GAMEOVER", "BOMB", "SLOW", "LETTRES",
				"TETRIS", "LINES", "DOUBLE", "FAST", "BRAZIL", "TRIPLE",
				"LIST", "CLEAR" };
		LetterSource.load(new HashSet<String>(Arrays.asList(words)));
		for (int i = 0; i < 100; i++) {
			LetterSource.getLetter();
		}
	}

}
