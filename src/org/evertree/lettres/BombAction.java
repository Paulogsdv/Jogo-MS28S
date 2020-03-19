package org.evertree.lettres;

import org.evertree.lettres.action.Action;

public class BombAction extends Action {

	private Game game;

	public BombAction(Game game) {
		this.game = game;
	}

	@Override
	public void execute() {
		int x = blocks[0].getX();
		int y = blocks[0].getY();
		game.getUserInterface().playBomb(x, y);
		game.getRealBlocks().remove(x, y);
		game.getRealBlocks().remove(x - 1, y);
		game.getRealBlocks().remove(x + 1, y);
		game.getRealBlocks().remove(x, y + 1);
		game.getRealBlocks().remove(x, y - 1);
		game.getRealBlocks().remove(x - 2, y);
		game.getRealBlocks().remove(x + 2, y);
		game.getRealBlocks().remove(x, y + 2);
		game.getRealBlocks().remove(x, y - 2);
		game.getRealBlocks().remove(x + 1, y + 1);
		game.getRealBlocks().remove(x + 1, y - 1);
		game.getRealBlocks().remove(x - 1, y + 1);
		game.getRealBlocks().remove(x - 1, y - 1);
	}

}
