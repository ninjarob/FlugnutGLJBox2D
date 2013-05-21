package com.pkp.gameengine;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.utils.Utilities;

public abstract class IScreen implements Swipeable {
	protected final IGame game;
	public static GLGraphics glGraphics;
	public static GL10 gl;
	public static float SSC; //SCREEN_SIZE_CONSTANT
	protected double timePassed = 0;
	protected boolean transitionIn;
	protected boolean transitionOut;
	public enum ScreenType{
		MainMenu,
		Game,
		Help,
		Story,
		Map
	}
	protected ScreenType nextScreen;
	
	
	public IScreen (IGame game) {
		this.game = game;
		if (IScreen.glGraphics == null)
			glGraphics = ((GLGame) game).getGLGraphics();
		if (IScreen.gl == null)
			gl = glGraphics.getGL();
		int height = glGraphics.getHeight();
		int width = glGraphics.getWidth();
		SSC = ((float)(height+width))/853f;
		transitionIn = true;
		transitionOut = false;
	}
	
	public void update(float deltaTime)
	{
		if (transitionIn) {
			transitionIn = transitionIn(deltaTime);
		}
		else if (transitionOut) {
			transitionOut = transitionOut(deltaTime);
			if (!transitionOut) {
				if (nextScreen != null) {
					Utilities.setTheNextScreen((GLGame)game, nextScreen);
				}
				else {
					System.out.println("here");
				}
			}
		}
	}
	
	public abstract void present(float deltaTime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
	//public abstract void onBackPressed();
}
