package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class SoundButton extends ImageDrawable implements Swipeable {
	public final float finalX = 0;

	public SoundButton(GLGame game, String filename) {
		super(game, filename, new Vec2(0, 0), 32*IScreen.SSC, 32*IScreen.SSC, 0, 0, .5f, .333f);
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
	}
	
	public void toggleSound(boolean soundOn) {
		if (soundOn) {
			image.resetVertices(0.0f, 0.0f, width, height, 0, 0, .5f, .333f);
		}
		else {
			image.resetVertices(0.0f, 0.0f, width, height, .5f, 0, 1f, .333f);
		}
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