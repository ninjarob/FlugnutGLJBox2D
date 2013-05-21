package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.FlugnutWorld;
import org.jbox2d.common.Vec2;

public class EMPSuperCircle extends EMP {
	public float radius;
	public static final float EMP_MAX_RADIUS = 200 * IScreen.SSC;
	public static final float EMP_GROWTH_SPEED = 50.0F * IScreen.SSC;

	public EMPSuperCircle(GLGame game, String filename, float x, float y, FlugnutWorld flugnutWorld) {
		super(game, filename, new Vec2(x, y), 2, 2, flugnutWorld);
		this.radius = 1f;							
	}
	
	public void draw(GL10 gl) {
		image.resetVertices(-radius, -radius, radius*2, radius*2);
		alphaValue = (EMP_MAX_RADIUS-radius)/EMP_MAX_RADIUS;
		super.draw(gl);
	}
}