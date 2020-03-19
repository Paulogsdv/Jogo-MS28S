package org.evertree.lettres;

import org.evertree.lettres.action.Action;

public class EndGameAction extends Action {
    
    private Game game;

    public EndGameAction(Game game) {
	this.game = game;
    }

    @Override
    public void execute() {
	game.endGame();
    }

}
