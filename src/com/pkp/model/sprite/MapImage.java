package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.game.Swipeable;
import com.pkp.utils.Utilities;


public class MapImage extends ImageDrawable implements Swipeable {

	public float finalX;
	
	public MapImage(GLGame game, String filename, float x, float y) {
		super(game, filename, 32*IScreen.SSC, 32*IScreen.SSC);
		this.finalX = x;
		def.position = new Vec2(x-IScreen.glGraphics.getWidth()-width, y);
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
		return Utilities.standardTransition(finalX-IScreen.glGraphics.getWidth()-width, def.position, deltaTime, -1);
	}

}