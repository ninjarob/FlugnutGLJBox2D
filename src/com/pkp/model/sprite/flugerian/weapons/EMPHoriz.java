package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.FlugnutWorld;
import org.jbox2d.common.Vec2;


public class EMPHoriz extends EMP {
	public final int MH = 5;
	public int health = 5;
	public float time = 0;
	
	public EMPHoriz(GLGame game, String filename, float x, float y, FlugnutWorld flugnutWorld) {
		super(game, filename, new Vec2(x, y), 1*IScreen.SSC, 4*IScreen.SSC, flugnutWorld);
	}
	
	public void draw(GL10 gl) {
		image.resetVertices(-width/2, -2, width, 2);
		alphaValue = health/MH;
		super.draw(gl);
	}
}