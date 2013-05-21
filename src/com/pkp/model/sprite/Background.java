package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.ImageDrawable;

public class Background extends ImageDrawable {

	public Background(GLGame game, String filename) {
		super(game, filename, IScreen.glGraphics.getWidth(), IScreen.glGraphics.getHeight(), true);
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
	}
}