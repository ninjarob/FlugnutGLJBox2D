package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class HighScoreTitle extends ImageDrawable implements Swipeable {
	public final float finalX = 80*IScreen.SSC;

	public HighScoreTitle(GLGame game, String filename) {
		super(game, filename, new Vec2(-192f*IScreen.SSC, IScreen.glGraphics.getHeight()-(80*IScreen.SSC)), 192f*IScreen.SSC, 42.667f*IScreen.SSC, 0f, .25f, 1f, .5f);
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