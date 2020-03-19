package org.evertree.lettres.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds {

	public static Clip disappearClip;
	public static Clip gameoverClip;
	public static Clip levelUpClip;
	public static Clip wordFoundClip;
	public static Clip moveDownClip;

	static {
		try {
			disappearClip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(ClassLoader
							.getSystemResourceAsStream("org/evertree/lettres/resource/disappear.wav"));
			disappearClip.open(ais);
			gameoverClip = AudioSystem.getClip();
			AudioInputStream ais2 = AudioSystem
					.getAudioInputStream(ClassLoader
							.getSystemResourceAsStream("org/evertree/lettres/resource/gameover.wav"));
			gameoverClip.open(ais2);
			levelUpClip = AudioSystem.getClip();
			AudioInputStream ais3 = AudioSystem
					.getAudioInputStream(ClassLoader
							.getSystemResourceAsStream("org/evertree/lettres/resource/levelup.wav"));
			levelUpClip.open(ais3);
			wordFoundClip = AudioSystem.getClip();
			AudioInputStream ais4 = AudioSystem
					.getAudioInputStream(ClassLoader
							.getSystemResourceAsStream("org/evertree/lettres/resource/wordfound.wav"));
			wordFoundClip.open(ais4);
			moveDownClip = AudioSystem.getClip();
			AudioInputStream ais5 = AudioSystem
					.getAudioInputStream(ClassLoader
							.getSystemResourceAsStream("org/evertree/lettres/resource/movedown.wav"));
			moveDownClip.open(ais5);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
