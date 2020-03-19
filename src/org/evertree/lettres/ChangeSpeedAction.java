package org.evertree.lettres;

import org.evertree.lettres.action.Action;

public class ChangeSpeedAction extends Action {

    private Game game;
    private long newSpeed;

    public ChangeSpeedAction(Game game, long newSpeed) {
	this.game = game;
	this.newSpeed = newSpeed;
    }

    @Override
    public void execute() {
	game.setDropInterval(newSpeed);
    }

}
