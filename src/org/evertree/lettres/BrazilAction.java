package org.evertree.lettres;

import static org.evertree.lettres.resource.Colors.DARK_BLUE;
import static org.evertree.lettres.resource.Colors.GREEN;
import static org.evertree.lettres.resource.Colors.YELLOW;

import java.awt.Color;


public class BrazilAction extends ClearBlocksAction {

	private static final Color[][] brazil = {
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{null,null,null,null,null,null,null,null,null},
		{GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN},
		{GREEN,GREEN,GREEN,GREEN,YELLOW,GREEN,GREEN,GREEN,GREEN},
		{GREEN,GREEN,YELLOW,YELLOW,DARK_BLUE,YELLOW,YELLOW,GREEN,GREEN},
		{GREEN,YELLOW,YELLOW,DARK_BLUE,DARK_BLUE,DARK_BLUE,YELLOW,YELLOW,GREEN},
		{GREEN,GREEN,YELLOW,YELLOW,DARK_BLUE,YELLOW,YELLOW,GREEN,GREEN},
		{GREEN,GREEN,GREEN,GREEN,YELLOW,GREEN,GREEN,GREEN,GREEN},
		{GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN,GREEN}
	};
	
	public BrazilAction(Game game) {
		super(game);
	}

	@Override
	public void execute() {
		super.execute();
		game.getRealBlocks().draw(brazil);
	}

}
