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
import java.util.Random;


public class CircleWindow extends BuildingPiece {
    public CircleShape ps;
    public Building building;
    public int state = 0;
    public Joint anchor;
    public float startx;
    public float yoffset = 10;
    Random r = new Random();
    float random;
    //FlugnutLevel\House.png
    //477X238
    public CircleWindow(GLGame game, FlugnutWorld flugnutWorld, float startx, Building building) {
        super(game, startx,
                building.body.getPosition().y - building.height/2 + Constants.F_HOME_CWIN_Y_OFFSET,
                Constants.F_HOME_CWIN_RAD*2, Constants.F_HOME_CWIN_RAD*2,
                Constants.F_HOME_CWIN_PATH);
        this.startx = startx;
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        initPhysicsWorld();
        random = r.nextFloat();
        if (random < .25) random += .5;
    }

    public void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        body = flugnutWorld.world.createBody(def);
        ps = new CircleShape();
        ps.setRadius(Constants.F_HOME_CWIN_RAD);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = .1f;
        fd.friction = 0.25f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);

        RevoluteJointDef anchorDef = new RevoluteJointDef();
        anchorDef.bodyA = body;
        anchorDef.bodyB = building.body;
        anchorDef.collideConnected = false;
        anchorDef.localAnchorA.set(0,0);//the top right corner of the box
        anchorDef.localAnchorB.set(startx - building.body.getPosition().x, (-building.height/2)+Constants.F_HOME_CWIN_Y_OFFSET);//center of the circle
        anchor = flugnutWorld.world.createJoint(anchorDef);
    }

    @Override
    public void update(float deltaTime) {
        float healthPerc = (float)building.health/(float)building.maxHealth;
        if (state == 0 && healthPerc < 1) {
            setNewImagefile(Constants.F_HOME_CWIN_PATH2);
            state = 1;
        }
        if (state == 1 && healthPerc < random) {
            flugnutWorld.world.destroyJoint(anchor);
            state=1;
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