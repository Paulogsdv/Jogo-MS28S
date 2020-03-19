package org.evertree.lettres.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.UIManager;

import org.evertree.lettres.Game;
import org.evertree.lettres.piece.Block;
import org.evertree.lettres.piece.Piece;
import org.evertree.lettres.resource.MessageLine;

public class MainCanvas extends Canvas {

	private static final long serialVersionUID = 1L;

	private static final Dimension CANVAS_DIMENSION = new Dimension(300, 540);
	private static final int BLOCK_WIDTH = 30;
	private static final int BLOCK_HEIGHT = 30;
	private Font blockFont;

	private BufferStrategy strategy;
	protected Game game;
	protected Color backgroundColor;

	public MainCanvas(Game game, Color backgroundColor) {

		this.game = game;
		this.backgroundColor = backgroundColor;
		this.blockFont = createFont();

		setLocation(0, 0);
		setSize(getCanvasDimension());
		setIgnoreRepaint(true);
	}

	public void paint() {
		Graphics2D g = (Graphics2D) getStrategy().getDrawGraphics();
		paintBackground(g);
		paintPiece(g, game.getDropping());
		paintDropped(g);
		g.dispose();
		getStrategy().show();
	}

	public void paintSquare(int x, int y, Color color) {
		Graphics2D g = (Graphics2D) getStrategy().getDrawGraphics();
		g.setColor(color);
		g.fillRect(x * getBlockWidth(), y * getBlockHeight(), getBlockWidth(), getBlockHeight());
		g.dispose();
		getStrategy().show();
	}
	
	public void showMessage(MessageLine[] messageLines, boolean clearBackground) {
		Graphics2D g = (Graphics2D) getStrategy().getDrawGraphics();
		if (clearBackground) {
			paintBackground(g);
		}
		int height = g.getFontMetrics().getHeight();
		int width = g.getFontMetrics().stringWidth("W");
		for (MessageLine line : messageLines) {
			g.setColor(line.getColor());
			g.drawString(line.getMessage(), width + line.getX() * width, height
					+ line.getY() * height);
		}

		g.dispose();
		getStrategy().show();
	}

	protected void paintBackground(Graphics2D g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, CANVAS_DIMENSION.width, CANVAS_DIMENSION.height);
	}

	protected void paintPiece(Graphics2D g, Piece piece) {
		if (piece != null) {
			for (Block block : piece.getBlocks()) {
				paintBlock(g, block);
			}
		}
	}

	protected void paintDropped(Graphics2D g) {
		Block[][] blocks = game.getBlocks();
		for (int line = 0; line < blocks.length; line++) {
			for (int column = 0; column < blocks[line].length; column++) {
				paintBlock(g, blocks[line][column]);
			}
		}
	}

	protected void paintBlock(Graphics2D g, Block block) {
		if (block == null) {
			return;
		}
		g.setColor(block.getColor());
		g.fill3DRect(block.getX() * getBlockWidth(), block.getY()
				* getBlockHeight(), getBlockWidth(), getBlockHeight(), true);
		g.setColor(block.getFontColor());
		g.drawRect(block.getX() * getBlockWidth() + getBlockWidth() / 10 - 1,
				block.getY() * getBlockHeight() + getBlockHeight() / 10 - 1,
				getBlockWidth() - getBlockWidth() / 5, getBlockHeight()
						- getBlockHeight() / 5);

		g.setFont(blockFont);

		g.drawString(block.getLetter(), block.getX()
				* getBlockWidth()
				+ (getBlockWidth() - g.getFontMetrics().stringWidth(
						block.getLetter())) / 2, (block.getY() + 1)
				* getBlockHeight() - getBlockHeight() / 4);
	}

	protected BufferStrategy getStrategy() {
		if (strategy == null) {
			createBufferStrategy(2);
			strategy = getBufferStrategy();
		}
		return strategy;
	}

	protected Dimension getCanvasDimension() {
		return CANVAS_DIMENSION;
	}

	protected int getBlockWidth() {
		return BLOCK_WIDTH;
	}

	protected int getBlockHeight() {
		return BLOCK_HEIGHT;
	}

	protected Font createFont() {
		return ((Font) UIManager.getDefaults().get("Panel.font"))
				.deriveFont((getBlockHeight() / 3) * 2.0f);
	}

}
