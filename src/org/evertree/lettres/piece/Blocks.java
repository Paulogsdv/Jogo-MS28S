/**
 * 
 */
package org.evertree.lettres.piece;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.evertree.lettres.Game;

public class Blocks {

	private Block[][] array;

	public Blocks() {
		clear();
	}

	public void clear() {
		array = new Block[Game.ROWS][Game.COLUMNS];
	}

	public boolean place(int x, int y, Piece piece) {
		return piece.placeIn(array, x, y);
	}

	public Block[][] getBlocks() {
		return Arrays.copyOf(array, array.length);
	}

	public List<Integer> getFilledLines() {
		ArrayList<Integer> filledLines = new ArrayList<Integer>();
		for (int line = 0; line < array.length; line++) {
			boolean free = false;
			for (Block block : array[line]) {
				if (block == null) {
					free = true;
					break;
				}
			}
			if (!free) {
				filledLines.add(line);
			}
		}
		return filledLines;
	}

	public void removeLines(List<Integer> lines) {
		int shift = 0;
		for (int line = array.length - 1; line >= 0; line--) {
			if (lines.contains(line)) {
				shift++;
				for (int column = 0; column < array[line].length; column++) {
					if (array[line][column] != null) {
						array[line][column].moveOut();
					}
				}
			} else {
				for (int column = 0; column < array[line].length; column++) {
					Block block = array[line][column];
					if (block != null) {
						block.moveTo(block.getX(), block.getY() + shift);
					}
				}
			}
		}
		for (int line = 0; line < shift; line++) {
			array[line] = new Block[array[0].length];
		}
	}

	public void insertGrayLine(int line, int blankColumn) {
		removeLines(Collections.singletonList(0));
		for (int row = 1; row <= line; row++) {
			for (int column = 0; column < array[0].length; column++) {
				if (array[row][column] != null) {
					array[row][column].moveUp();
				}
			}
		}
		for (int column = 0; column < array[0].length; column++) {
			if (column != blankColumn) {
				GrayBlock grayBlock = new GrayBlock();
				grayBlock.placeIn(array, column, line);
			}
		}
	}

	public void draw(Color[][] blocks) {
		for (int row = 0; row < blocks.length; row++) {
			for (int column = 0; column < blocks[0].length; column++) {
				if (blocks[row][column] != null) {
					new Block(0, 0, blocks[row][column]).placeIn(array, column,
							row);
				}
			}
		}
	}
	
	public Block forceGrayBlock(int x, int y){
		if (array[y][x] != null){
			array[y][x].moveOut();
		}
		Block block = new GrayBlock(); 
		block.placeIn(array, x, y);
		return block;
	}

	public void remove(int x, int y) {
		if (x >= 0 && x < array[0].length){
			if (y >= 0 && y < array.length){
				if (array[y][x] != null){
					array[y][x].moveOut();
				}
			}
		}
	}

}