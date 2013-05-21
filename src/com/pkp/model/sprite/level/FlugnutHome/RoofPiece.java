package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.FrictionJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import javax.microedition.khronos.opengles.GL10;
import java.util.Random;


public class RoofPiece extends BuildingPiece {
    public PolygonShape ps;
    public Building building;
    public Joint topJoint;
    public Joint bottomJoint;
    public int state = 0;
    public Random r = new Random();
    public float random1;
    public float random2;
    //FlugnutLevel\House.png
    //477X238
    public RoofPiece(GLGame game, float startx, FlugnutWorld flugnutWorld, Building building) {
        super(game, startx,
                building.body.getPosition().y - (building.height/2) + Constants.F_HOME_RP_Y_OFFSET,
                Constants.F_HOME_RP_WIDTH,
                Constants.F_HOME_RP_HEIGHT,
                Constants.F_HOME_RP_PATH);
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        initPhysicsWorld();
        random1 = r.nextFloat();
        random2 = r.nextFloat();
        if (random1 > .75) random1 -= .25;
        if (random2 > .75) random2 -= .25;
    }

    public void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        body = flugnutWorld.world.createBody(def);
        ps = new PolygonShape();
        Vec2[] verts = new Vec2[8];
        verts[0] = new Vec2(-6, 4);
        verts[1] = new Vec2(6, 4);
        verts[2] = new Vec2(5.5f, 2.5f);
        verts[3] = new Vec2(4, 1f);
        verts[4] = new Vec2(.5f, -3.5f);
        verts[5] = new Vec2(-.5f, -3.5f);
        verts[6] = new Vec2(-4, 1f);
        verts[7] = new Vec2(-5.5f, 2.5f);

        ps.set(verts, 8);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = .1f;
        fd.friction = 3f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);


        RevoluteJointDef topJointDef = new RevoluteJointDef();
        topJointDef.bodyA = body;
        topJointDef.bodyB = building.body;
        topJointDef.collideConnected = false;
        topJointDef.localAnchorA.set(-5,4);
        topJointDef.localAnchorB.set((-building.body.getPosition().x)+(body.getPosition().x)-5,
                (-building.height/2)+Constants.F_HOME_RP_Y_OFFSET+4);
        topJointDef.maxMotorTorque = 1;
        topJointDef.enableMotor = true;
        topJoint = flugnutWorld.world.createJoint(topJointDef);

        RevoluteJointDef bottomJointDef = new RevoluteJointDef();
        bottomJointDef.bodyA = body;
        bottomJointDef.bodyB = building.body;
        bottomJointDef.collideConnected = false;
        bottomJointDef.localAnchorA.set(5,4);
        bottomJointDef.localAnchorB.set((-building.body.getPosition().x)+(body.getPosition().x)+5,
                (-building.height/2)+Constants.F_HOME_RP_Y_OFFSET+4);//center of the circle
        bottomJointDef.maxMotorTorque = 1;
        bottomJointDef.enableMotor = true;
        bottomJoint = flugnutWorld.world.createJoint(bottomJointDef);
    }

    @Override
    public void update(float deltaTime) {
        float healthPerc = (float)building.health/(float)building.maxHealth;
        if (healthPerc < random1) {
            flugnutWorld.world.destroyJoint(topJoint);
        }
        else if (healthPerc < random2) {
            flugnutWorld.world.destroyJoint(bottomJoint);
        }
    }

    @Override
    public void click(boolean click) {
    }

    @Override
    public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        gl.glRotatef(body.getAngle()* Utilities.TO_DEGREES, 0, 0, 1);
        image.draw(gl);
    }
}