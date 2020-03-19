package org.evertree.lettres.piece;

import java.awt.Color;

import org.evertree.lettres.action.LetterSource;
import org.evertree.lettres.resource.BlockColor;

public class Block {

	private int x = 0;
	private int y = 0;

	private BlockColor color;
	private BlockColor fontColor;

	protected String letter;
	private Block[][] space;

	public Block(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = new BlockColor(color);
		this.fontColor = new BlockColor(color.brighter().brighter());
		this.letter = LetterSource.getLetter();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color.getColor();
	}

	public void setBrightness(double brightness) {
		color.setBrightness(brightness);
	}

	public Color getFontColor() {
		return fontColor.getColor();
	}

	public void setFontBrightness(double brightness) {
		fontColor.setBrightness(brightness);
	}

	public String getLetter() {
		return letter;
	}

	public boolean isInTheSameSpace(Block other) {
		return space == other.space;
	}

	protected boolean moveUp() {
		return moveTo(x, y - 1);
	}

	protected boolean moveDown() {
		return moveTo(x, y + 1);
	}

	protected boolean moveLeft() {
		return moveTo(x - 1, y);
	}

	protected boolean moveRight() {
		return moveTo(x + 1, y);
	}

	protected boolean moveTo(int newX, int newY) {
		if (canMove(newX, newY)) {
			if (space != null) {
				space[y][x] = null;
			}
			x = newX;
			y = newY;
			if (space != null) {
				space[newY][newX] = this;
			}
			return true;
		}
		return false;
	}

	protected boolean move(int deltaX, int deltaY) {
		return moveTo(x + deltaX, y + deltaY);
	}

	protected boolean canMove(int newX, int newY) {
		return canPlaceIn(space, newX, newY);
	}

	protected static boolean canPlaceIn(Block[][] newSpace, int newX, int newY) {
		if (newSpace == null) {
			return true;
		}
		if (newY >= 0 && newY < newSpace.length) {
			if (newX >= 0 && newX < newSpace[0].length) {
				if (newSpace[newY][newX] == null) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean placeIn(Block[][] newSpace, int newX, int newY) {
		if (canPlaceIn(newSpace, newX, newY)) {
			if (space != null) {
				space[y][x] = null;
			}
			this.space = newSpace;
			this.x = newX;
			this.y = newY;
			this.space[newY][newX] = this;
			return true;
		}
		return false;
	}

	protected void moveOut() {
		if (space != null) {
			this.space[y][x] = null;
			this.space = null;
		}
	}

	@Override
	public String toString() {
		return "[" + letter + "]";
	}

}
