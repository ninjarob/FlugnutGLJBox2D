package com.pkp.model.sprite.level;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class LevelCompleted extends ImageDrawable implements Swipeable {
	public final float finalX = (IScreen.glGraphics.getWidth()/2)-(width/2);
	
	public LevelCompleted(GLGame game, String filename) {
		super(game, filename, 196.0f*IScreen.SSC, 50.0f*IScreen.SSC);
		def.position = new Vec2(-width, IScreen.glGraphics.getHeight()*.8f*IScreen.SSC);
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		return Utilities.standardTransition(finalX, def.position, deltaTime, 1);
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return Utilities.standardTransition(-width, def.position, deltaTime, -1);
	}
}