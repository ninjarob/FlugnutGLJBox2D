package com.pkp.model.sprite.snostreblaian;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.i.Destroyable;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.weapons.EMP;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import com.pkp.utils.Utilities;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Bomb extends ImageDrawable implements Destroyable {

	public float time = 0;
	public int health;
	public int damage;
	public float startx;
	public float starty;
	public boolean done;
	public EMP previousEMP;
    public Body body;
    public CircleShape ps;
    public FlugnutWorld flugnutWorld;
	
	public Bomb(GLGame game, String filename, float startx, float starty, float width, float height, int health, int damage, World world) {
		super(game, filename, new Vec2(startx, starty), width, height, -width/2, -height/2);
		this.startx = startx;
		this.starty = starty;
		this.health = health;
		this.damage = damage;
		this.done = false;

        def.type = BodyType.DYNAMIC;
        body = world.createBody(def);
        ps = new CircleShape();

        ps.setRadius(width/2);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 100f;
        fd.friction = 1f;
        fd.filter.categoryBits = Constants.BAD_GUY_CATEGORY;
        fd.filter.maskBits = Constants.BAD_GUY_MASK;
        body.createFixture(fd);
        body.setUserData(this);
	}

	@Override
	public abstract void draw(GL10 gl);

    public void destroy() {
        //FlugnutWorld.bodiesToDestroy.add(body);
        super.destroy();
    }
}
