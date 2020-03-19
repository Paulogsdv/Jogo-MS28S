package org.evertree.lettres.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.evertree.lettres.Game;

public class NextPieceCanvas extends MainCanvas {

	private static final long serialVersionUID = 1L;

	private static final int BLOCK_WIDTH = 20;
	private static final int BLOCK_HEIGHT = 20;
	private static final Dimension CANVAS_DIMENSION = new Dimension(BLOCK_WIDTH*8, BLOCK_HEIGHT*2);

	public NextPieceCanvas(Game game, Color backgroundColor) {
		super(game, backgroundColor);
		setFocusable(false);
	}
	
	public void paint() {
		Graphics2D g = (Graphics2D) getStrategy().getDrawGraphics();
		paintBackground(g);
		g.translate(BLOCK_WIDTH*2, 0);
		paintPiece(g, game.getNext());
		g.dispose();
		getStrategy().show();
	}
	
	protected Dimension getCanvasDimension(){
		return CANVAS_DIMENSION;
	}

	protected int getBlockWidth() {
		return BLOCK_WIDTH;
	}

	protected int getBlockHeight() {
		return BLOCK_HEIGHT;
	}

}
