package org.evertree.lettres;

import org.evertree.lettres.action.Action;

public class AddLineAction extends Action {

    private Game game;
    private int lines;

    public AddLineAction(Game game, int lines) {
	this.game = game;
	this.lines = lines;
    }

    @Override
    public void execute() {
	for (int i = 0; i < lines; i++) {
	    game.getRealBlocks().insertGrayLine(17, i);
	}
    }

}
