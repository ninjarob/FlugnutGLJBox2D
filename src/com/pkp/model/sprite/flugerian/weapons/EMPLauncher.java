package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.FlugnutWorld;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.utils.Utilities;


public class EMPLauncher extends ImageDrawable {
	public float endx;
	public float endy;
	private float distance = 100000;
	public boolean done = false;
	public static float speed = 140.0f * IScreen.SSC;
    public FlugnutWorld flugnutWorld;
	
	public EMPLauncher(GLGame game, String filename, float x, float y, float endx, float endy) {
		super(game, filename, new Vec2(x, y), 12*IScreen.SSC, 6*IScreen.SSC);
		this.endx = endx;
		this.endy = endy;
		def.linearVelocity.x = speed;
		def.angle = Utilities.findAngleBetweenVectorsRad(def.position, new Vec2(endx, endy))*Utilities.TO_DEGREES;
	}
	
	public void draw(GL10 gl) {
		super.draw(gl);
	}
	
	public void update(float deltaTime) {
		Utilities.updateVectorByTime(def, deltaTime);
		float newDist = Utilities.dist(def.position, endx, endy);
		if (newDist > distance) {
			done = true;
		}
		distance = newDist;
	}
}