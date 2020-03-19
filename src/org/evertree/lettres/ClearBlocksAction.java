package org.evertree.lettres;

import java.util.ArrayList;
import java.util.List;

import org.evertree.lettres.action.Action;

public class ClearBlocksAction extends Action {

	protected Game game;

	public ClearBlocksAction(Game game) {
		this.game = game;
	}

	@Override
	public void execute() {
		List<Integer> lines = new ArrayList<Integer>();
		for (int i = 0; i < game.getBlocks().length; i++) {
			lines.add(i);
		}
		this.game.getUserInterface().playDeleteLines(lines);
		this.game.getRealBlocks().removeLines(lines);
	}

}
