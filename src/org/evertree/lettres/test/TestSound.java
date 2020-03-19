package org.evertree.lettres.test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TestSound {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/* Create our clip object */
		Clip clickClip = AudioSystem.getClip();
		 
		/* Open our file as an AudioStream */
		AudioInputStream ais = AudioSystem.getAudioInputStream(ClassLoader.getSystemResourceAsStream("org/evertree/lettres/resource/wordfound.wav"));
		 
		/* Put out audio input stream into our clip */
		clickClip.open(ais);
		 
		/* Play the clip */
		clickClip.start();
		
		Thread.sleep(5000);
	}

}
