package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.i.Destroyable;
import com.pkp.model.FlugnutWorld;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.ImageDrawable;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class EMP extends ImageDrawable implements Destroyable {
	protected float alphaValue;
    public Body body;
    public CircleShape ps;
    public FlugnutWorld flugnutWorld;

	public EMP() {
		super();
	}
	
	public EMP(GLGame game, String filename, Vec2 pos, float w, float h, FlugnutWorld world) {
		super(game, filename, pos, w, h);
        this.flugnutWorld = world;
        def.type = BodyType.STATIC;
        body = flugnutWorld.world.createBody(def);
        ps = new CircleShape();
        ps.setRadius(width/2);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = Constants.GOOD_GUY_CATEGORY;
        fd.filter.maskBits = Constants.GOOD_GUY_MASK;
        body.createFixture(fd);
        body.setUserData(this);
	}
	
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4f(1 * alphaValue, 1 * alphaValue, 1 * alphaValue, alphaValue);
		image.draw(gl);
		gl.glColor4f(backgroundRed,backgroundGreen,backgroundBlue,1);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

    public void destroy() {
        //FlugnutWorld.bodiesToDestroy.add(body);
        super.destroy();
    }
}