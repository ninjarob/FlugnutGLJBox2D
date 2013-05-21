package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.game.GL2DCircle;


public class AntiMissile extends Drawable {
	public float x;
	public float y;
	public float radius;
	public boolean growing;
	GL2DCircle antiMissileCircle;
	
	public AntiMissile(float x, float y) {
		this.x = x;
		this.y = y;
		this.radius = 0;
		this.growing = true;
		antiMissileCircle = new GL2DCircle(0.0f, 0.0f, 0.0f, "0.5 1.0 0.73 1");
	}
	
	public void draw(GL10 gl) {
		antiMissileCircle.resetVertices(0.0f, 0.0f, radius);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		antiMissileCircle.draw(gl);
	}
}