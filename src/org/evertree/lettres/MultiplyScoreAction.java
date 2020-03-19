package org.evertree.lettres;


public class MultiplyScoreAction extends AddScoreAction {

	private int factor;

	public MultiplyScoreAction(Game game, int factor) {
		super(game, 0);
		this.factor = factor;
	}

	@Override
	public void execute() {
		score = (factor - 1) * game.getScore();
		super.execute();
	}

}
