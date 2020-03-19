package org.evertree.lettres.piece;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.evertree.lettres.Game;

public abstract class Piece {

	protected int orientation;
	protected List<Block> blocks;
	private Block[][] space;

	private final static Random RANDOM = new Random();

	protected Piece() {
		super();
	}

	public static Piece create(Game game) {
		int n = RANDOM.nextInt(7);
		switch (n) {
		case 0:
			return new PieceI();
		case 1:
			return new PieceO();
		case 2:
			return new PieceT();
		case 3:
			return new PieceL();
		case 4:
			return new PieceR();
		case 5:
			return new PieceZ();
		case 6:
			return new PieceS();
		}
		return null;
	}

	public boolean moveDown() {
		return move(0, +1);
	}

	public boolean moveRight() {
		return move(+1, 0);
	}

	public boolean moveLeft() {
		return move(-1, 0);
	}

	public boolean placeIn(Block[][] space, int x, int y) {
		if (blocks == null || blocks.isEmpty()) {
			return false;
		}
		int referenceX = (int) getLeftCorner().getX();
		int referenceY = (int) getLeftCorner().getY();
		for (Block block : blocks) {
			int deltaX = block.getX() - referenceX;
			int deltaY = block.getY() - referenceY;
			if (!Block.canPlaceIn(space, x + deltaX, y + deltaY)) {
				return false;
			}
		}
		this.space = space;
		for (Block block : blocks) {
			int deltaX = block.getX() - referenceX;
			int deltaY = block.getY() - referenceY;
			block.placeIn(space, x + deltaX, y + deltaY);
		}
		return true;
	}

	public boolean rotate() {
		moveOut();
		int deltaX;
		int deltaY;
		Point[][] deltas = getDeltas();
		int i = 0;
		boolean canPlace = true;
		for (Block block : blocks) {
			deltaX = deltas[orientation][i].x;
			deltaY = deltas[orientation][i].y;
			if (!Block.canPlaceIn(space, block.getX() + deltaX, block.getY()
					+ deltaY)) {
				canPlace = false;
				break;
			}
			i++;
		}
		i = 0;
		for (Block block : blocks) {
			deltaX = deltas[orientation][i].x;
			deltaY = deltas[orientation][i].y;
			if (canPlace) {
				block.placeIn(space, block.getX() + deltaX, block.getY()
						+ deltaY);
			} else {
				block.placeIn(space, block.getX(), block.getY());
			}
			i++;
		}
		if (canPlace) {
			orientation = (orientation + 1) % deltas.length;
		}
		return canPlace;
	}

	public List<Block> getBlocks() {
		return Collections.unmodifiableList(blocks);
	}

	protected abstract Point[][] getDeltas();

	protected abstract Point getLeftCorner();

	protected boolean move(int deltaX, int deltaY) {
		if (space == null) {
			for (Block block : blocks) {
				block.move(deltaX, deltaY);
			}
			return true;
		}
		moveOut();
		boolean canPlace = true;
		for (Block block : blocks) {
			if (!Block.canPlaceIn(space, block.getX() + deltaX, block.getY()
					+ deltaY)) {
				canPlace = false;
				break;
			}
		}
		for (Block block : blocks) {
			if (canPlace) {
				block.placeIn(space, block.getX() + deltaX, block.getY()
						+ deltaY);
			} else {
				block.placeIn(space, block.getX(), block.getY());
			}
		}
		return canPlace;
	}

	protected void moveOut() {
		for (Block block : blocks) {
			block.moveOut();
		}
	}

	protected static Point p(int x, int y) {
		return new Point(x, y);
	}

}
