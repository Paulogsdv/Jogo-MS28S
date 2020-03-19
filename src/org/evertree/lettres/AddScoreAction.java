package org.evertree.lettres;

import org.evertree.lettres.action.Action;

public class AddScoreAction extends Action {

	protected Game game;
	protected long score;

	public AddScoreAction(Game game, long score) {
		this.game = game;
		this.score = score;
	}

	@Override
	public void execute() {
		this.game.addScore(score);
		this.game.getUserInterface().playScoreUp();
	}

}
