package org.evertree.lettres;

import java.util.ArrayList;
import java.util.List;

import org.evertree.lettres.action.Action;
import org.evertree.lettres.piece.Block;

public class TetrisAction extends Action {
	
	protected Game game;

	public TetrisAction(Game game) {
		super();
		this.game = game;
	}

	@Override
	public void execute() {
		Block[][] blocks = game.getBlocks();
		List<Block> newBlocks = new ArrayList<Block>();
		for (int row = 17; row > 13; row--) {
			for (int column = 0; column < blocks[0].length; column++) {
				if (blocks[row][column] == null){
					newBlocks.add(game.getRealBlocks().forceGrayBlock(column, row));
				}
			}
		}
		game.getUserInterface().playBlocksShowing(newBlocks);
		game.checkFilledLines();
	}

}
