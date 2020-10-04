package org.evertree.lettres;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.evertree.lettres.action.Action;
import org.evertree.lettres.action.LetterSource;
import org.evertree.lettres.action.Word;
import org.evertree.lettres.action.WordFinder;
import org.evertree.lettres.piece.Block;
import org.evertree.lettres.piece.Blocks;
import org.evertree.lettres.piece.Piece;
import org.evertree.lettres.resource.HighScores;
import org.evertree.lettres.resource.MessageLine;
import org.evertree.lettres.resource.Parameters;
import org.evertree.lettres.ui.ParametersDialog;
import org.evertree.lettres.ui.UserInterface;

public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ROWS = 18;
	public static final int COLUMNS = 10;

	public enum Status {
		WAITING, PLAYING, PAUSED, OVER
	};

	/** ms **/
	private long lastDrop = 0;
	private long lastVerticalMove = 0;
	private long lastHorizontalMove = 0;

	private Status gameStatus = Status.WAITING;

	private Piece next = null;
	private Piece dropping = null;
	private Blocks blocks = new Blocks();
	private WordFinder wordFinder;

	private long dropInterval;
	private long level;
	private long lines;
	private long score;
	private HighScores highScores;

	private UserInterface userInterface;

	private Map<String, Action> actions;

	public Game() {
		loadActions();
		highScores = HighScores.load();
		userInterface = new UserInterface(this);
		LetterSource.load(actions.keySet());
		wordFinder = new WordFinder(actions.keySet());
		loadActions();
	}

	public void startGame() {
		dropping = null;
		level = 1;
		lines = 0;
		score = 0;
		dropInterval = Parameters.INITIAL_DROP_INTERVAL;
		lastDrop = 0;
		lastHorizontalMove = 0;
		lastVerticalMove = 0;
		userInterface.reset();
		userInterface.playSoundTrack();
		blocks.clear();
		gameStatus = Status.PLAYING;
	}

	public void pause() {
		if (gameStatus == Status.PAUSED) {
			gameStatus = Status.PLAYING;
		} else {
			gameStatus = Status.PAUSED;
		}
	}

	public void exit() {
		System.exit(0);
	}

	public void wordTyped(String word) {
		if (word.indexOf("olecram") > -1) {
			if (gameStatus != Status.PAUSED) {
				pause();
			}
			new ParametersDialog(userInterface);
			pause();
		}
	}

	public HighScores getHighScores() {
		return highScores;
	}

	public Status getGameStatus() {
		return gameStatus;
	}

	public Piece getDropping() {
		return dropping;
	}

	public long getLevel() {
		return level;
	}

	public long getLines() {
		return lines;
	}

	public long getScore() {
		return score;
	}

	public Piece getNext() {
		return next;
	}

	public Block[][] getBlocks() {
		return blocks.getBlocks();
	}

	Set<String> getActionNames() {
		return actions.keySet();
	}

	UserInterface getUserInterface() {
		return userInterface;
	}

	void endGame() {
		userInterface.playGameOver();
		gameStatus = Status.OVER;
		dropping = null;
		addToHighScores();
		userInterface.paintHighScores();
	}

	void addScore(long score) {
		this.score += score;
		userInterface.paintLabels();
	}

	void removeLines(List<Integer> lines) {
		switch (lines.size()) {
		case 1:
			addScore(Parameters.ONE_LINE_FILLED_SCORE);
			break;
		case 2:
			addScore(Parameters.TWO_LINE_FILLED_SCORE);
			break;
		case 3:
			addScore(Parameters.THREE_LINE_FILLED_SCORE);
			break;
		case 4:
			addScore(Parameters.FOUR_LINE_FILLED_SCORE);
			break;
		case 5:
				addScore(Parameters.FOUR_LINE_FILLED_SCORE);
				break;
		default:
			break;
		}
		addLines(lines.size());
		userInterface.playDeleteLines(lines);
		blocks.removeLines(lines);
	}

	Blocks getRealBlocks() {
		return blocks;
	}

	void setDropInterval(long dropInterval) {
		this.dropInterval = dropInterval;
	}

	void checkFilledLines() {
		List<Integer> lines = blocks.getFilledLines();
		if (!lines.isEmpty()) {
			removeLines(lines);
			userInterface.reset();
		}
	}

	private void gameLoop() {
		while (true) {
			if (gameStatus == Status.PLAYING) {
				if (dropPiece()) {
					moveLeft();
					moveRight();
					moveDown();
					rotate();
					userInterface.paint();
				}
			} else if (gameStatus == Status.OVER) {
				userInterface.showMessage(MessageLine.MESSAGE_GAME_OVER, false);
			} else if (gameStatus == Status.WAITING) {
				userInterface.showMessage(MessageLine.MESSAGE_INTRO, true);
			} else if (gameStatus == Status.PAUSED) {
				userInterface.showMessage(MessageLine.MESSAGE_PAUSED, true);
			}
			try {
				Thread.sleep(1000 / Parameters.FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Piece consumeNext() {
		Piece piece = null;
		if (next == null) {
			next = Piece.create(this);
		}
		piece = next;
		next = Piece.create(this);
		return piece;
	}

	private boolean dropPiece() {
		if (dropping == null) {
			lastDrop = System.currentTimeMillis();
			dropping = consumeNext();
			if (!blocks.place(3, 0, dropping)) {
				endGame();
				return false;
			}
		} else {
			if (System.currentTimeMillis() - lastDrop < dropInterval) {
				return true;
			}
			lastDrop = System.currentTimeMillis();
			if (!dropping.moveDown()) {
				droppingHitFloor();
				return false;
			}
		}
		return true;
	}

	private void droppingHitFloor() {
		dropping = null;
		checkWord();
		checkFilledLines();
		checkWord();
		userInterface.reset();
	}

	private void checkWord() {
		Word word = wordFinder.findNewWord(getBlocks());
		while (word != null) {
			userInterface.playWordAnimation(word.getBlocks());
			Action action = actions.get(word.getWord());
			action.execute(word.getBlocks());
			word = wordFinder.findNewWord(getBlocks());
		}
	}

	private void addLines(int lines) {
		this.lines += lines;
		setLevel(this.lines / Parameters.LINES_PER_LEVEL + 1);
		userInterface.paintLabels();
	}

	private void setLevel(long level) {
		if (this.level != level) {
			userInterface.playLevelUp();
			this.level = level;
			dropInterval = dropInterval - Parameters.DROP_INTERVAL_DECREMENT;
		}
	}

	private void moveLeft() {
		if (dropping != null) {
			if (userInterface.isLeftPressed()) {
				if (System.currentTimeMillis() - lastHorizontalMove < Parameters.MOVE_HORIZONTAL_INTERVAL) {
					return;
				}
				dropping.moveLeft();
				lastHorizontalMove = System.currentTimeMillis();
			}
		}
	}

	private void moveRight() {
		if (dropping != null) {
			if (userInterface.isRightPressed()) {
				if (System.currentTimeMillis() - lastHorizontalMove < Parameters.MOVE_HORIZONTAL_INTERVAL) {
					return;
				}
				dropping.moveRight();
				lastHorizontalMove = System.currentTimeMillis();
			}
		}
	}

	private void moveDown() {
		if (dropping != null) {
			if (userInterface.isDownPressed()) {
				if (System.currentTimeMillis() - lastVerticalMove < Parameters.MOVE_VERTICAL_INTERVAL) {
					return;
				}
				lastVerticalMove = System.currentTimeMillis();
				if (!dropping.moveDown()) {
					droppingHitFloor();
				}
			}
		}
	}

	private void rotate() {
		if (dropping != null) {
			if (userInterface.isUpPressed()) {
				if (System.currentTimeMillis() - lastVerticalMove < Parameters.MOVE_HORIZONTAL_INTERVAL) {
					return;
				}
				dropping.rotate();
				lastVerticalMove = System.currentTimeMillis();
			}
		}
	}

	private void loadActions() {
		actions = new HashMap<String, Action>();
		actions.put("DOUBLE", new MultiplyScoreAction(this, 2));
		actions.put("TRIPLE", new MultiplyScoreAction(this, 3));
		actions.put("LIST", new ListActionsAction(this));
		actions.put("MCL", new AddScoreAction(this, 1150));
		actions.put("LETTRES", new AddScoreAction(this, 1000000));
		actions.put("CLEAR", new ClearBlocksAction(this));
		actions.put("LINES", new AddLineAction(this, 4));
		actions.put("FAST", new ChangeSpeedAction(this,
				Parameters.INITIAL_DROP_INTERVAL / 2));
		actions.put("SLOW", new ChangeSpeedAction(this,
				Parameters.INITIAL_DROP_INTERVAL * 2));
		actions.put("GAMEOVER", new EndGameAction(this));
		actions.put("BRAZIL", new BrazilAction(this));
		actions.put("TETRIS", new TetrisAction(this));
		actions.put("BOMB", new BombAction(this));
	}

	private boolean addToHighScores() {
		try {
			highScores.add(userInterface.getHighScoreName(), score);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		Game l = new Game();
		l.gameLoop();
	}

}
