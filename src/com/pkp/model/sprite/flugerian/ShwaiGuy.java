package com.pkp.model.sprite.flugerian;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.model.FlugnutWorld;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;


public class ShwaiGuy extends ImageDrawable {
    public Body body;
    public CircleShape cs;
	public static final float shwaiGuySizeX = 23*IScreen.SSC;
	public static final float shwaiGuySizeY = 25*IScreen.SSC;
	public Vec2 destination;
	public Vec2 newDestination;
	public boolean arrived;
	public boolean missileArrived;
	public float rotationAngle = 0;
	public int health = 8;
	public int energy = 100;
    public FlugnutWorld flugnutWorld;
    public boolean hasWater = false;
    private BuildingPiece objectClicked;
    public BuildingPiece objectCarried;

	public ShwaiGuy(GLGame game, float x, float y, String filename, FlugnutWorld flugnutWorld) {
		super(game, filename, new Vec2(x,y), shwaiGuySizeX, shwaiGuySizeY, -shwaiGuySizeX/2, -shwaiGuySizeY/2);
		destination = new Vec2(x,y);
		newDestination = new Vec2(-1,-1);
		this.arrived = true;
        this.flugnutWorld = flugnutWorld;
        initPhysicsWorld();
	}

    private void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        def.linearDamping = .1f;
        def.angularDamping = 1f;
        body = flugnutWorld.world.createBody(def);
        cs = new CircleShape();
        cs.setRadius(shwaiGuySizeY/2);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = .075f;
        fd.friction = 0.35f;
        fd.filter.categoryBits = Constants.GUY_CATEGORY;
        fd.filter.maskBits = Constants.GUY_MASK;
        body.createFixture(fd);
        body.setUserData(this);
        body.setGravityScale(0);
    }

    public BuildingPiece getObjectClicked() {
        return objectClicked;
    }

    public void setObjectClicked(BuildingPiece objectClicked) {
        this.objectClicked = objectClicked;
    }

	@Override
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        gl.glRotatef(body.getAngle()*Utilities.TO_DEGREES, 0, 0, 1);
		image.draw(gl);
	}
	
	public float distanceToDest() {
        return Utilities.dist(body.getPosition(), destination);
	}

	public void updateAccordingToDirAndVel(float deltaTime) {
        float forceMagnitude = 450000*deltaTime;
        float dist = distanceToDest();
        if (dist < 10) {
            body.setLinearDamping(0.8f);
        }
        if (dist < 32) {
            body.setLinearDamping(2.8f);
        }
        else if (dist < 60)
        {
            body.setLinearDamping(0.6f);
        }
        else {
            body.setLinearDamping(.2f);
        }
        float currentYAmount = forceMagnitude*(FloatMath.sin(def.angle));
        float currentXAmount = forceMagnitude*(FloatMath.cos(def.angle));
        body.applyForceToCenter(new Vec2(currentXAmount, currentYAmount));
        body.setLinearVelocity(new Vec2(body.getLinearVelocity().x,body.getLinearVelocity().y));
	}
}