package com.pkp.gameengine.i.io;

public interface IAudio {
	public IMusic newMusic(String filename); 
	public ISound newSound(String filename);
}
