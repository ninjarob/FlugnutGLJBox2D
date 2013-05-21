package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.FlugnutWorld;
import com.pkp.utils.Constants;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class EMPCircle extends EMP {
	public float radius;
	public static final float EMP_MAX_RADIUS = 25 * IScreen.SSC;
	public static final float EMP_GROWTH_SPEED = 20.0F * IScreen.SSC;

	public EMPCircle(GLGame game, String filename, float x, float y, FlugnutWorld world) {
		super(game, filename, new Vec2(x, y), 2, 2, world);
	}
	
	public void draw(GL10 gl) {
        FlugnutWorld.bodiesToDestroy.add(body);
        body = flugnutWorld.world.createBody(def);
        FixtureDef fd = new FixtureDef();
        ps.setRadius(radius);
        fd.shape = ps;
        fd.filter.categoryBits = Constants.GOOD_GUY_CATEGORY;
        fd.filter.maskBits = Constants.GOOD_GUY_MASK;
        body.createFixture(fd);
        body.setUserData(this);
		image.resetVertices(-radius, -radius, radius*2, radius*2);
		alphaValue = (EMP_MAX_RADIUS-radius)/EMP_MAX_RADIUS;
		super.draw(gl);
	}
}