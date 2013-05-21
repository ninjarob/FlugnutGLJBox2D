package com.pkp.model.sprite.level.Tutorial.Tut2;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import javax.microedition.khronos.opengles.GL10;


public class TowerBox extends BuildingPiece {
    public PolygonShape ps;
    public float startx;
    public float timeSpent = 0;
    public boolean interact = false;
    public Building building;
    public String name;
    Joint j1;

    public TowerBox(GLGame game, float startx, float starty, FlugnutWorld flugnutWorld, Building building) {
        super(game, startx, starty,
                Constants.BOX_WIDTH, Constants.BOX_HEIGHT,
                Constants.BOX_PATH);
        this.flugnutWorld = flugnutWorld;
        initPhysicsWorld();
        this.building = building;
    }

    public Vec2[] getVerts() {
        Vec2[] verts = new Vec2[4];
        verts[0] = new Vec2(-width/4, height/4);
        verts[1] = new Vec2(width/4, height/4);
        verts[2] = new Vec2(width/4, -height/4);
        verts[3] = new Vec2(-width/4, -height/4);
        return verts;
    }

    @Override
    public void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        def.angularDamping = .7f;
        body = flugnutWorld.world.createBody(def);
        ps = new PolygonShape();
        Vec2[] verts = getVerts();
        ps.set(verts, 4);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = .1f;
        fd.friction = .8f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);
    }

    public void createCarryJoint() {
        WeldJointDef topJointDef = new WeldJointDef();
        topJointDef.bodyA = body;
        topJointDef.bodyB = flugnutWorld.guy.body;
        topJointDef.collideConnected = false;
        topJointDef.localAnchorA.set(0,0);
        topJointDef.localAnchorB.set(0,0);
        j1 = flugnutWorld.world.createJoint(topJointDef);
    }

    @Override
    public void update(float deltaTime) {
        timeSpent += deltaTime;
        if (!getPickedUp() && flugnutPresent && clicked) {
            setPickedUp(true);
            click(false);
            flugnutWorld.guy.setObjectClicked(null);
            flugnutPresent = false;
            if (flugnutWorld.guy.objectCarried != null) {
                flugnutWorld.guy.objectCarried.setPickedUp(false);
                flugnutWorld.guy.objectCarried.initPhysicsWorld();
            }
            flugnutWorld.guy.objectCarried = this;
            createCarryJoint();
        }
        //for when it falls, it'll fall slowly.
        if (!getPickedUp()) {
            if (j1 != null) {
                FlugnutWorld.jointsToDestroy.add(j1);
                j1 = null;
                def.position = flugnutWorld.guy.body.getPosition();
                def.angle = body.getAngle();
                FlugnutWorld.bodiesToDestroy.add(body);
                initPhysicsWorld();
                flugnutWorld.guy.objectCarried = null;
            }
            float speed = (float)Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
            if (speed > 100) {
                float ratio = 100/speed;
                body.setLinearVelocity(new Vec2(body.getLinearVelocity().x*ratio,body.getLinearVelocity().y*ratio));
            }
        }
        //reset timer its moving or height is less than 100
        if (body != null && body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0 && body.getPosition().y < 50) {
            timeSpent = 0;
        }
    }


    //TODO both clicked and nonclicked versions of the images need to be on the same sprite.
    @Override
    public void click(boolean click) {
        clicked = click;
    }

    @Override
    public void draw(GL10 gl) {
        super.draw(gl);
    }
}