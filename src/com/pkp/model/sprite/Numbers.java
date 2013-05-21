package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class Numbers extends ImageDrawable implements Swipeable {

	public float finalX;
	public float scale;
	public float timePassed = 0;
	
	public Numbers(GLGame game, String filename, float sizeScale) {
		super(game, filename, new Vec2(-IScreen.glGraphics.getWidth()-20,0), 20.0f*sizeScale, 32.0f*sizeScale, 0, 0, .1f, .1f);
		this.scale = sizeScale;
	}

	@Override
	public void draw(GL10 gl) {
		super.draw(gl);
	}

	@Override
	public void drawWithTrans(GL10 gl, float transx, float transy) {
		super.drawWithTrans(gl, transx, transy);
	}
	
	@Override
	public boolean transitionIn(float deltaTime) {
		return Utilities.standardTransition(finalX, def.position, deltaTime, 1);
	}

	@Override
	public boolean transitionOut(float deltaTime) {
		return Utilities.standardTransition(finalX-IScreen.glGraphics.getWidth()-width, def.position, deltaTime, -1);
	}
}