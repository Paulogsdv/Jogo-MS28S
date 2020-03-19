package org.evertree.lettres.ui;

import org.evertree.lettres.action.Action;

public class PlayLevelUpAction extends Action {

	private UserInterface userInterface;

	public PlayLevelUpAction(UserInterface userInterface) {
		this.userInterface = userInterface;
	}

	@Override
	public void execute() {
		userInterface.playLevelUp();
	}

}
