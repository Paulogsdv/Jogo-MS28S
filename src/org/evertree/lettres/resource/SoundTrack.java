package org.evertree.lettres.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

public class SoundTrack {

	private File[] songs;
	private int index = 0;
	private SoundTrackThread player;

	public SoundTrack() {
		loadSongs();
	}

	public void play() {
		if (songs != null && songs.length > 0) {
			if (player == null){
				player = new SoundTrackThread(songs[index]);
				player.start();
			}
		}
	}

	public void nextSong() {
		if (songs != null && songs.length > 0) {
			index = (index + 1) % songs.length;
			player.end();
			player = null;
			play();
		}
	}
	
	public void stop(){
		if (songs != null && songs.length > 0) {
			player.end();
			player = null;
		}
	}
	
	public void reset(){
		index = 0;
	}
	
	private void loadSongs() {
		File mp3Dir = new File("mp3");
		if (mp3Dir.exists() && mp3Dir.isDirectory()) {
			songs = mp3Dir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return (pathname.isFile() && pathname.getAbsolutePath()
							.endsWith(".mp3"));
				}
			});
		}
	}

	class SoundTrackThread extends Thread {
		
		private AdvancedPlayer player;
		private File song;
		private boolean active = true;
		
		public SoundTrackThread(File song) {
			this.song = song;
		}
		
		@Override
		public void run() {
			while (active) {
				try {
					playSong();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void playSong() throws Exception {
			player = new AdvancedPlayer(new FileInputStream(song));
			player.setPlayBackListener(new PlaybackListener() {
			});
			player.play();
		}
		
		public void end(){
			this.active = false;
			player.stop();
		}

		
	}

}
