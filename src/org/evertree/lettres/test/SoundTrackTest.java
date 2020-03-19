package org.evertree.lettres.test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import javazoom.jl.player.advanced.AdvancedPlayer;

import org.evertree.lettres.resource.SoundTrack;
import org.evertree.lettres.resource.Sounds;

public class SoundTrackTest extends JFrame {

	private static final long serialVersionUID = 1L;
	protected SoundTrack st;

	public SoundTrackTest() throws MalformedURLException {
		super("test");

		getContentPane().setLayout(new FlowLayout());

		final JButton start = new JButton("start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				st = new SoundTrack();
				st.play();
			}
		});
		add(start);

		JButton next = new JButton("next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				st.nextSong();
			}
		});
		add(next);

		JButton fx1 = new JButton("fx1");
		fx1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Sounds.wordFoundClip != null) {
					Sounds.wordFoundClip.setFramePosition(0);
					Sounds.wordFoundClip.stop();
					System.out.println("stop");
					System.out.println("0 - "
							+ Sounds.wordFoundClip.getFramePosition());
					Sounds.wordFoundClip.start();
					System.out.println("play");
				}
			}
		});
		add(fx1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		// new SoundTrackTest();
		AdvancedPlayer player = new AdvancedPlayer(new FileInputStream(
				"/shotgun.mp3"));
		player.play();
		player.play();
	}
}
