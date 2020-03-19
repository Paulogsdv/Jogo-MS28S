package org.evertree.lettres.test;

import org.evertree.lettres.Game;
import org.evertree.lettres.ListActionsAction;

public class ListActionsActionTest {
	
	public static void main(String[] args) {
		ListActionsAction action = new ListActionsAction(new Game());
		action.execute();
	}

}
