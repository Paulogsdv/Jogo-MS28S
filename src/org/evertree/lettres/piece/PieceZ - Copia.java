package org.evertree.lettres.piece;

import java.awt.Point;
import java.util.ArrayList;

import org.evertree.lettres.resource.Colors;

public class PieceZ extends Piece {

	private static final Point[][] deltas = new Point[][] {
		{ p(+1, +1), p(+0, +0), p(+1, -1), p(+0, -2) },
		{ p(+1, -1), p(+0, +0), p(-1, -1), p(-2, +0) },
		{ p(-1, -1), p(+0, +0), p(-1, +1), p(+0, +2) },
		{ p(-1, +1), p(+0, +0), p(+1, +1), p(+2, +0) } };

	public PieceZ() {
		blocks = new ArrayList<Block>();
		blocks.add(new Block(0, 0, Colors.VIOLET));
		blocks.add(new Block(1, 0, Colors.VIOLET));
		blocks.add(new Block(1, 1, Colors.VIOLET));
		blocks.add(new Block(2, 1, Colors.VIOLET));
		orientation = 0;
	}

	@Override
	protected Point[][] getDeltas() {
		return deltas;
	}

	@Override
	protected Point getLeftCorner() {
		switch (orientation) {
		case 0:
			return p(blocks.get(0).getX(), blocks.get(0).getY());
		case 1:
			return p(blocks.get(1).getX(), blocks.get(3).getY());
		case 2:
			return p(blocks.get(3).getX(), blocks.get(3).getY());
		case 3:
			return p(blocks.get(2).getX(), blocks.get(0).getY());
		}
		return null;
	}

}
