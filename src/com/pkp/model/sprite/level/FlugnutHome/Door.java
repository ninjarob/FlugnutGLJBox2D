package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import javax.microedition.khronos.opengles.GL10;


public class Door extends BuildingPiece {
    public CircleShape ps;
    public Building building;
    public Joint topJoint;
    public Joint bottomJoint;
    public int state = 0;
    //FlugnutLevel\House.png
    //477X238
    public Door(GLGame game, FlugnutWorld flugnutWorld, Building building) {
        super(game, building.body.getPosition().x,
                building.body.getPosition().y - building.height/2 + Constants.F_HOME_DOOR_Y_OFFSET,
                Constants.F_HOME_DOOR_RAD*2,
                Constants.F_HOME_DOOR_RAD*2,
                Constants.F_HOME_DOOR_PATH);
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        clickable = true;
        clickable = true;
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        body = flugnutWorld.world.createBody(def);
        ps = new CircleShape();
        ps.setRadius(Constants.F_HOME_DOOR_RAD);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = .1f;
        fd.friction = 0.8f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);


        RevoluteJointDef topJointDef = new RevoluteJointDef();
        topJointDef.bodyA = body;
        topJointDef.bodyB = building.body;
        topJointDef.collideConnected = false;
        topJointDef.localAnchorA.set(-3,4);
        topJointDef.localAnchorB.set(-3,(-building.height/2)+Constants.F_HOME_DOOR_Y_OFFSET+4);//from 0,0 being center of the circle
        topJoint = flugnutWorld.world.createJoint(topJointDef);

        RevoluteJointDef bottomJointDef = new RevoluteJointDef();
        bottomJointDef.bodyA = body;
        bottomJointDef.bodyB = building.body;
        bottomJointDef.collideConnected = false;
        bottomJointDef.localAnchorA.set(-3,-4);
        bottomJointDef.localAnchorB.set(-3,(-building.height/2)+Constants.F_HOME_DOOR_Y_OFFSET-4);//center of the circle
        bottomJoint = flugnutWorld.world.createJoint(bottomJointDef);
    }

    @Override
    public void update(float deltaTime) {
        float healthPerc = (float)building.health/(float)building.maxHealth;
        if (state == 0 && healthPerc < 1) {
            flugnutWorld.world.destroyJoint(topJoint);
            state=1;
        }
        else if (state == 1 && healthPerc < 0.5) {
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
        gl.glRotatef(body.getAngle()*Utilities.TO_DEGREES, 0, 0, 1);
        image.draw(gl);
    }
}