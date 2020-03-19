package org.evertree.lettres.action;

import java.util.Arrays;

import org.evertree.lettres.piece.Block;

public abstract class Word {

    protected String word;
    protected Block[] blocks;

    public Word(String word, Block[] blocks) {
	super();
	this.word = word;
	this.blocks = blocks;
    }
    
    public String getWord() {
	return word;
    }
    
    public Block[] getBlocks() {
	return blocks;
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(blocks);
	result = prime * result + ((word == null) ? 0 : word.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Word))
	    return false;
	Word other = (Word) obj;
	if (!Arrays.equals(blocks, other.blocks))
	    return false;
	if (word == null) {
	    if (other.word != null)
		return false;
	} else if (!word.equals(other.word))
	    return false;
	return true;
    }

    public abstract boolean validate();

}
