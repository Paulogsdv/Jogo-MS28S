package org.evertree.lettres.action;

import org.evertree.lettres.piece.Block;

public abstract class Action {
	
    protected Block[] blocks;

	protected abstract void execute();

	public void execute(Block[] blocks) {
		this.blocks = blocks;
		execute();
	}
    
}