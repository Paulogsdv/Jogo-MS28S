package org.evertree.lettres.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.evertree.lettres.piece.Block;

public final class WordFinder {

    private List<Word> currentWords;
    private Set<String> words;

    public WordFinder(Set<String> words) {
	this.words = words;
	this.currentWords = new ArrayList<Word>();
    }

    public Word findNewWord(Block[][] blocks) {
	Word word = findNewHorizontalWord(blocks);
	if (word == null) {
	    word = findNewVerticalWord(blocks);
	}
	return word;
    }

    private Word findNewHorizontalWord(Block[][] blocks) {
	for (int row = 0; row < blocks.length; row++) {
	    StringBuilder letters = new StringBuilder();
	    for (int column = 0; column < blocks[0].length; column++) {
		Block block = blocks[row][column];
		if (block != null) {
		    letters.append(block.getLetter());
		} else {
		    letters.append(" ");
		}
	    }
	    if (letters.toString().trim().length() > 0) {
		for (String word : words) {
		    int begin = 0;
		    int index = 0;
		    index = letters.toString().indexOf(word, begin);
		    while (index > -1) {
			Block[] wordBlocks = new Block[word.length()];
			for (int i = 0; i < wordBlocks.length; i++) {
			    wordBlocks[i] = blocks[row][index + i];
			}
			Word foundWord = new HorizontalWord(word, wordBlocks);
			if (!isCurrentWord(foundWord)) {
			    currentWords.add(foundWord);
			    return foundWord;
			}
			begin = index + 1;
			index = letters.toString().indexOf(word, begin);
			;
		    }
		}
	    }
	}
	return null;
    }

    private Word findNewVerticalWord(Block[][] blocks) {
	for (int column = 0; column < blocks[0].length; column++) {
	    StringBuilder letters = new StringBuilder();
	    for (int row = 0; row < blocks.length; row++) {
		Block block = blocks[row][column];
		if (block != null) {
		    letters.append(block.getLetter());
		} else {
		    letters.append(" ");
		}
	    }
	    if (letters.toString().trim().length() > 0) {
		for (String word : words) {
		    int begin = 0;
		    int index = 0;
		    index = letters.toString().indexOf(word, begin);
		    while (index > -1) {
			Block[] wordBlocks = new Block[word.length()];
			for (int i = 0; i < wordBlocks.length; i++) {
			    wordBlocks[i] = blocks[index + i][column];
			}
			Word foundWord = new VerticalWord(word, wordBlocks);
			if (!isCurrentWord(foundWord)) {
			    currentWords.add(foundWord);
			    return foundWord;
			}
			begin = index + 1;
			index = letters.toString().indexOf(word, begin);
			;
		    }
		}
	    }
	}
	return null;
    }

    private boolean isCurrentWord(Word word) {
	for (Iterator<Word> iterator = currentWords.iterator(); iterator
		.hasNext();) {
	    Word current = iterator.next();
	    if (!current.validate()) {
		iterator.remove();
	    } else {
		if (current.equals(word)) {
		    return true;
		}
	    }
	}
	return false;
    }

}
