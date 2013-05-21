package com.pkp.model.sprite.flugerian;

import com.pkp.GLGame;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.i.Destroyable;
import com.pkp.model.FlugnutWorld;
import com.pkp.utils.Constants;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import javax.microedition.khronos.opengles.GL10;


public abstract class MiscObject extends ImageDrawable implements Destroyable {
	public Body body;
    public FlugnutWorld flugnutWorld;

	public MiscObject(GLGame game, float startx, float starty, float width, float height, String filename) {
		super(game, filename, new Vec2(startx, starty), width, height, -width/2, -height/2);
	}

    public abstract void update(float deltaTime);

	@Override
	public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef(body.getPosition().x* Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        image.draw(gl);
	}

    public void destroy() {
        //FlugnutWorld.bodiesToDestroy.add(body);
        super.destroy();
    }
}