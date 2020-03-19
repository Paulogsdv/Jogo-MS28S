package org.evertree.lettres.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TestColorFade {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(200, 200));
		final DrawablePanel dp;
		frame.getContentPane().add(dp = new DrawablePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		new Timer(1, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dp.color = new Color(
						Math.max(0, dp.color.getRed() - 3), 
						Math.max(0, dp.color.getGreen() - 3), 
						Math.max(0, dp.color.getBlue() - 3));
				dp.repaint();

				if (dp.color.equals(Color.BLACK)) {
					((Timer) e.getSource()).stop();
				}
			}
		}).start();
	}

}

class DrawablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Color color = Color.YELLOW;

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 200, 200);
		g.setColor(color);
		g.fill3DRect(50, 50, 50, 50, true);
	}

}
