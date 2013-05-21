package com.pkp.gameengine.i;

import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.i.io.IAudio;
import com.pkp.gameengine.i.io.IFileIO;
import com.pkp.gameengine.i.io.IInput;

public interface IGame {
	public IInput getInput();
	public IFileIO getFileIO();
	public IAudio getAudio();
	public void setScreen(IScreen screen);
	public IScreen getCurrentScreen();
	public IScreen getStartScreen();
}
