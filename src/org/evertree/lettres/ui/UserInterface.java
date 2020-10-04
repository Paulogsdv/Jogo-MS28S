package org.evertree.lettres.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.evertree.lettres.Game;
import org.evertree.lettres.piece.Block;
import org.evertree.lettres.resource.HighScores;
import org.evertree.lettres.resource.Icons;
import org.evertree.lettres.resource.MessageLine;
import org.evertree.lettres.resource.Parameters;
import org.evertree.lettres.resource.SoundTrack;
import org.evertree.lettres.resource.Sounds;

public class UserInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat(
			"###,###,###,##0");

	private Game game;
	private MainCanvas mainCanvas;
	private NextPieceCanvas nextPieceCanvas;
	private InputHandler inputHandler;

	private JLabel level;
	private JLabel lines;
	private JLabel score;
	private JLabel[] hiScoresName;
	private JLabel[] hiScoresValue;

	private BlinkingTimer levelTimer;
	private BlinkingTimer scoreTimer;

	private SoundTrack soundTrack;

	public UserInterface(Game game) {
		super("Lettres " + Parameters.LETTRES_VERSION);

		this.game = game;
		this.inputHandler = new InputHandler(game);

		configureWindow();
		buildContentPanel(getContentPane());

		pack();

		setLocationRelativeTo(null);

		setVisible(true);

		this.levelTimer = new BlinkingTimer(level);
		this.scoreTimer = new BlinkingTimer(score);
		
		this.soundTrack = new SoundTrack();
		
		this.nextPieceCanvas.paint();
		this.mainCanvas.requestFocus();
	}

	public void paint() {
		mainCanvas.paint();
		nextPieceCanvas.paint();
	}

	public void paintNext() {
		nextPieceCanvas.paint();
	}

	public void paintLabels() {
		level.setText(formatNumber(game.getLevel()));
		lines.setText(formatNumber(game.getLines()));
		score.setText(formatNumber(game.getScore()));
	}

	public void paintHighScores() {
		HighScores highScores = game.getHighScores();
		for (int i = 0; i < hiScoresName.length; i++) {
			if (highScores.getNames()[i] != null) {
				hiScoresName[i].setText(highScores.getNames()[i]);
				hiScoresValue[i]
						.setText(formatNumber(highScores.getValues()[i]));
			} else {
				hiScoresName[i].setText("");
				hiScoresValue[i].setText("");
			}
		}
	}

	public void playDeleteLines(List<Integer> lines) {

		if (Sounds.disappearClip != null) {
			Sounds.disappearClip.setFramePosition(0);
			Sounds.disappearClip.stop();
			Sounds.disappearClip.start();
		}

		long animationStart = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		while (now - animationStart < Parameters.VANISH_ANIMATION_DURATION) {

			double brightness = (-1 * (now - animationStart))
					/ (double) Parameters.VANISH_ANIMATION_DURATION;

			Block[][] blocks = game.getBlocks();
			for (int line = 0; line < blocks.length; line++) {
				if (lines.contains(line)) {
					for (int column = 0; column < blocks[line].length; column++) {
						Block block = blocks[line][column];
						if (block != null) {
							block.setBrightness(brightness);
							block.setFontBrightness(brightness);
						}
					}
				}
			}

			mainCanvas.paint();

			try {
				Thread.sleep(1000 / Parameters.FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			now = System.currentTimeMillis();
		}
	}

	public void playWordAnimation(Block[] blocks) {
		if (Sounds.wordFoundClip != null) {
			Sounds.wordFoundClip.setFramePosition(0);
			Sounds.wordFoundClip.stop();
			Sounds.wordFoundClip.start();
		}

		for (Block[] blockLine : game.getBlocks()) {
			for (Block block : blockLine) {
				if (block != null) {
					block.setBrightness(-0.6);
					block.setFontBrightness(-0.6);
				}
			}
		}

		long duration = Parameters.LIGHT_ANIMATION_DURATION;
		long animationStart = System.currentTimeMillis();
		long past = System.currentTimeMillis() - animationStart;

		while (past < duration) {

			double brightness = 0;
			if (past < duration / 2) {
				brightness = past / (duration / 2.5);
			} else {
				brightness = (duration - past) / (duration / 2.5);
			}

			for (Block block : blocks) {
				if (block != null) {
					block.setFontBrightness(brightness);
				}
			}

			mainCanvas.paint();

			try {
				Thread.sleep(1000 / Parameters.FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			past = System.currentTimeMillis() - animationStart;
		}

		for (Block[] blockLine : game.getBlocks()) {
			for (Block block : blockLine) {
				if (block != null) {
					block.setBrightness(0);
					block.setFontBrightness(0);
				}
			}
		}

		mainCanvas.paint();
	}

	public void playBlocksShowing(List<Block> blocks) {

		if (Sounds.wordFoundClip != null) {
			Sounds.wordFoundClip.setFramePosition(0);
			Sounds.wordFoundClip.stop();
			Sounds.wordFoundClip.start();
		}

		for (Block block : blocks) {
			block.setBrightness(-1);
			block.setFontBrightness(-1);
		}

		long animationStart = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		while (now - animationStart < Parameters.LIGHT_ANIMATION_DURATION) {

			double brightness = -1 + (now - animationStart)
					/ (double) Parameters.LIGHT_ANIMATION_DURATION;

			for (Block block : blocks) {
				block.setBrightness(brightness);
				block.setFontBrightness(brightness);
			}

			mainCanvas.paint();

			try {
				Thread.sleep(1000 / Parameters.FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			now = System.currentTimeMillis();
		}
	}

	public void playGameOver() {
		
		soundTrack.stop();
		
		if (Sounds.gameoverClip != null) {
			Sounds.gameoverClip.setFramePosition(0);
			Sounds.gameoverClip.stop();
			Sounds.gameoverClip.start();
		}
		for (Block[] blockLine : game.getBlocks()) {
			for (Block block : blockLine) {
				if (block != null) {
					block.setBrightness(-0.7);
					block.setFontBrightness(-0.7);
				}
			}
		}
		if (game.getDropping() != null) {
			game.getDropping().moveRight();
			game.getDropping().moveRight();
			game.getDropping().moveRight();
		}
		paint();
	}

	public void playLevelUp() {
		if (Sounds.levelUpClip != null) {
			Sounds.levelUpClip.setFramePosition(0);
			Sounds.levelUpClip.stop();
			Sounds.levelUpClip.start();
		}
		levelTimer.reset();
		levelTimer.start();
		soundTrack.nextSong();
	}

	public void playScoreUp() {
		if (Sounds.levelUpClip != null) {
			Sounds.levelUpClip.setFramePosition(0);
			Sounds.levelUpClip.stop();
			Sounds.levelUpClip.start();
		}
		scoreTimer.reset();
		scoreTimer.start();
	}

	public void playBomb(int x, int y) {
		if (Sounds.gameoverClip != null) {
			Sounds.gameoverClip.setFramePosition(0);
			Sounds.gameoverClip.stop();
			Sounds.gameoverClip.start();
		}
		long duration = Parameters.BOMB_ANIMATION_DURATION;
		long animationStart = System.currentTimeMillis();
		long past = System.currentTimeMillis() - animationStart;
		int factor = 0;
		while (past < duration) {
			if (past < duration / 2) {
				factor = (int) (past / (duration / 2.0) * 511);
			} else {
				factor = (int) ((duration - past) / (duration / 2.0) * 511);
			}
			paintBomb1(x, y, factor);
			paintBomb2(x, y, factor);
			paintBomb3(x, y, factor);
			try {
				Thread.sleep(1000 / Parameters.FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			past = System.currentTimeMillis() - animationStart;
		}
	}

	public void playSoundTrack() {
		soundTrack.play();
	}

	private void paintBomb1(int x, int y, int factor) {
		Color color = new Color(Math.min(255, factor), Math
				.max(0, factor - 256), 0);
		paintSquare(x, y, color);
	}

	private void paintBomb2(int x, int y, int factor) {
		if (factor > 127) {
			Color color = new Color(Math.min(255, Math.max(0, factor - 128)),
					Math.max(0, factor - 384), 0);
			paintSquare(x - 1, y, color);
			paintSquare(x + 1, y, color);
			paintSquare(x, y + 1, color);
			paintSquare(x, y - 1, color);
		}
	}

	private void paintBomb3(int x, int y, int factor) {
		if (factor > 255) {
			Color color = new Color(Math.max(0, factor - 256), 0, 0);
			paintSquare(x - 2, y, color);
			paintSquare(x + 2, y, color);
			paintSquare(x, y + 2, color);
			paintSquare(x, y - 2, color);
			paintSquare(x + 1, y + 1, color);
			paintSquare(x + 1, y - 1, color);
			paintSquare(x - 1, y + 1, color);
			paintSquare(x - 1, y - 1, color);
		}
	}

	private void paintSquare(int x, int y, Color color) {
		if (x >= 0 && x < Game.COLUMNS) {
			if (y >= 0 && y < Game.ROWS) {
				mainCanvas.paintSquare(x, y, color);
			}
		}
	}

	public void showMessage(MessageLine[] messageLines, boolean clearBackground) {
		mainCanvas.showMessage(messageLines, clearBackground);
		nextPieceCanvas.paint();
	}

	public void reset() {
		inputHandler.reset();
		paintLabels();
		paintHighScores();
	}

	public boolean isLeftPressed() {
		return inputHandler.isLeftPressed() && !inputHandler.isRightPressed();
	}

	public boolean isRightPressed() {
		return inputHandler.isRightPressed() && !inputHandler.isLeftPressed();
	}

	public boolean isDownPressed() {
		return inputHandler.isDownPressed() && !inputHandler.isUpPressed();
	}

	public boolean isUpPressed() {
		return inputHandler.isUpPressed() && !inputHandler.isDownPressed();
	}

	private void configureWindow() {
		UIDefaults uiDefaults = UIManager.getDefaults();
		uiDefaults.put("Label.font", ((Font) uiDefaults.get("Label.font"))
				.deriveFont(20.0f));
		uiDefaults.put("Panel.font", ((Font) uiDefaults.get("Label.font"))
				.deriveFont(20.0f));
		List<Image> icons = new ArrayList<Image>();
		icons.add(Icons.LETTRES_16);
		icons.add(Icons.LETTRES_32);
		setIconImages(icons);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				mainCanvas.requestFocus();
			}
		});
	}

	private void buildContentPanel(Container container) {
		Border panelsBorder = BorderFactory
				.createLineBorder(getBackground(), 5);
		Border numbersBorder = BorderFactory.createCompoundBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED), BorderFactory
				.createLineBorder(getBackground(), 3));

		this.mainCanvas = new MainCanvas(game, Color.BLACK);
		this.mainCanvas.addKeyListener(inputHandler);
		this.nextPieceCanvas = new NextPieceCanvas(game, getBackground());

		JLabel labelNext = new JLabel("Next", JLabel.LEFT);
		JLabel labelLevel = new JLabel("Level", JLabel.LEFT);
		JLabel labelLines = new JLabel("Lines", JLabel.LEFT);
		JLabel labelScore = new JLabel("Score", JLabel.LEFT);
		JLabel labelHiScores = new JLabel("High Scores", JLabel.LEFT);

		this.level = new JLabel(formatNumber(game.getLevel()), JLabel.RIGHT);
		this.level.setBorder(numbersBorder);
		this.level.setOpaque(true);
		this.lines = new JLabel(formatNumber(game.getLines()), JLabel.RIGHT);
		this.lines.setBorder(numbersBorder);
		this.score = new JLabel(formatNumber(game.getScore()), JLabel.RIGHT);
		this.score.setBorder(numbersBorder);
		this.score.setOpaque(true);
		this.hiScoresName = new JLabel[HighScores.LINES];
		this.hiScoresValue = new JLabel[HighScores.LINES];
		for (int i = 0; i < HighScores.LINES; i++) {
			this.hiScoresName[i] = new JLabel("", JLabel.LEFT);
			this.hiScoresName[i].setForeground(getBackground().darker());
			this.hiScoresValue[i] = new JLabel("", JLabel.RIGHT);
			this.hiScoresValue[i].setForeground(this.hiScoresName[i]
					.getForeground());
		}
		paintHighScores();

		JPanel canvasPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		canvasPanel.setBorder(panelsBorder);
		canvasPanel.add(this.mainCanvas);

		JPanel hiScoresPanel = new JPanel(new GridLayout(HighScores.LINES, 2,
				1, 1));
		hiScoresPanel.setBorder(panelsBorder);
		for (int i = 0; i < HighScores.LINES; i++) {
			hiScoresPanel.add(this.hiScoresName[i]);
			hiScoresPanel.add(this.hiScoresValue[i]);
		}

		JPanel infoPanel = new JPanel(new GridLayout(9, 1, 3, 3));
		infoPanel.setBorder(panelsBorder);
		infoPanel.add(labelNext);
		infoPanel.add(nextPieceCanvas);
		infoPanel.add(labelLevel);
		infoPanel.add(level);
		infoPanel.add(labelLines);
		infoPanel.add(lines);
		infoPanel.add(labelScore);
		infoPanel.add(score);
		infoPanel.add(labelHiScores);

		JPanel otherPanel = new JPanel(new BorderLayout());
		otherPanel.add(infoPanel, BorderLayout.NORTH);
		otherPanel.add(hiScoresPanel, BorderLayout.CENTER);

		container.setLayout(new BorderLayout());
		container.add(canvasPanel, BorderLayout.WEST);
		container.add(otherPanel, BorderLayout.EAST);
	}

	private String formatNumber(long number) {
		return NUMBER_FORMAT.format(number);
	}

	public String getHighScoreName() {
		String name = JOptionPane.showInputDialog(this, "Your name", null,
				JOptionPane.PLAIN_MESSAGE);
		if (name == null || name.isEmpty()) {
			name = "...";
		}
		return name.substring(0, Math.min(5, name.length()));
	}

}

class BlinkingTimer extends Timer {

	private static final long serialVersionUID = 1L;
	private int times = 0;

	public BlinkingTimer(final JLabel thelabel) {
		super(150, null);
		addActionListener(new ActionListener() {
			private JLabel label = thelabel;
			private Color defaultColor = thelabel.getBackground();
			private Color blinkColor = defaultColor.brighter();

			public void actionPerformed(ActionEvent e) {
				if (times > 14) {
					((Timer) e.getSource()).stop();
					return;
				}
				if (times % 2 == 0) {
					label.setBackground(defaultColor);
				} else {
					label.setBackground(blinkColor);
				}
				times++;
			}

		});
	}

	public void reset() {
		this.times = 0;
	}
}
