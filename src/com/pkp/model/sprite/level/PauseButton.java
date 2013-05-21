package com.pkp.model.sprite.level;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class PauseButton extends ImageDrawable implements Swipeable {
	public final float finalX = 0;
	
	
	public PauseButton(GLGame game, String filename) {
		super(game, filename, new Vec2(-25*IScreen.SSC, IScreen.glGraphics.getHeight()-25*IScreen.SSC), 25*IScreen.SSC, 25*IScreen.SSC, .5f, .667f, 1f, 1f);
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