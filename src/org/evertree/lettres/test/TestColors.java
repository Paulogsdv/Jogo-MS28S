package org.evertree.lettres.test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestColors {

	static String[] colors = new String[] { "#151B8D", "#571B7e", "#800517", "#306754", "#C35617", "#E9AB17", "#4863A0", "#C0C0C0" };

	public static void main(String[] args) {
		JFrame f = new JFrame();
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());

		for (int i = 0; i < colors.length; i++) {
			JPanel p = new JPanel();
			p.setPreferredSize(new Dimension(100, 100));
			p.setBackground(Color.decode(colors[i]));
			c.add(p);
		}

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
