package com.pkp.model.sprite.level;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class EndGameButton extends ImageDrawable implements Swipeable {
	public final float finalX = (IScreen.glGraphics.getWidth()/2)-(width/2);
	
	public EndGameButton(GLGame game, String filename) {
		super(game, filename, new Vec2(-32f*IScreen.SSC, IScreen.glGraphics.getHeight()*.7f*IScreen.SSC), 32f*IScreen.SSC, 32f*IScreen.SSC, 0f, .667f, .5f, 1f);
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