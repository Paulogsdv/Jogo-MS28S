package org.evertree.lettres.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.evertree.lettres.Game;
import org.evertree.lettres.Game.Status;

public class InputHandler extends KeyAdapter {

    private Game game;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean upPressed = false;

    private StringBuffer letters;
    private long lastTyped = 0;

    public InputHandler(Game game) {
	this.game = game;
	this.letters = new StringBuffer("XXXXXXXXXX");
    }

    public void reset() {
	leftPressed = false;
	rightPressed = false;
	downPressed = false;
	upPressed = false;
	lastTyped = 0;
    }

    public void keyPressed(KeyEvent e) {
	if (game.getGameStatus() != Status.PLAYING) {
	    return;
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	    leftPressed = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    rightPressed = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	    downPressed = true;
	}
	if (e.getKeyCode() == KeyEvent.VK_UP) {
	    upPressed = true;
	}
    }

    public void keyReleased(KeyEvent e) {
	if (game.getGameStatus() != Status.PLAYING) {
	    return;
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	    leftPressed = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    rightPressed = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	    downPressed = false;
	}
	if (e.getKeyCode() == KeyEvent.VK_UP) {
	    upPressed = false;
	}
    }

    public void keyTyped(KeyEvent e) {
	if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
	    game.exit();
	}

	long now = System.currentTimeMillis();
	if (now - lastTyped > 500) {
	    letters.replace(0, 10, "XXXXXXXXXX");
	}
	letters.insert(0, e.getKeyChar());
	letters.deleteCharAt(letters.length() - 1);
	lastTyped = now;

	game.wordTyped(letters.toString());

	if (game.getGameStatus() == Status.PAUSED
		|| game.getGameStatus() == Status.PLAYING) {
	    game.setGameStatus();
	} else if (game.getGameStatus() == Status.WAITING
		|| game.getGameStatus() == Status.OVER) {
	    game.startGame();
	}
    }

    public boolean isLeftPressed() {
	return leftPressed;
    }

    public boolean isRightPressed() {
	return rightPressed;
    }

    public boolean isDownPressed() {
	return downPressed;
    }

    public boolean isUpPressed() {
	return upPressed;
    }

}
