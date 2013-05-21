package com.pkp.model.sprite.level.Tutorial.Tut1;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Bird;
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


public class BirdCageDoor extends BuildingPiece {
    public CircleShape ps;
    public Building building;

    public BirdCageDoor(GLGame game, FlugnutWorld flugnutWorld, Building building) {
        super(game, (building.body.getPosition().x),
                (building.body.getPosition().y - building.height/4 + Constants.F_HOME_DOOR_Y_OFFSET),
                Constants.F_HOME_DOOR_RAD*2,
                Constants.F_HOME_DOOR_RAD*2,
                Constants.TUT_BIRD_DOOR_PATH, false);
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        clickable = true;
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.STATIC;
        body = flugnutWorld.world.createBody(def);
        ps = new CircleShape();
        ps.setRadius(Constants.F_HOME_DOOR_RAD);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);
    }

    @Override
    public void update(float deltaTime) {
        if (clicked && flugnutPresent) {
            clicked = false;
            if (flugnutWorld.guy.objectCarried != null) {
                if (flugnutWorld.guy.objectCarried instanceof Bird) {
                    Bird b = (Bird)flugnutWorld.guy.objectCarried;
                    b.caged = true;
                    b.clickable = false;
                    flugnutWorld.guy.objectCarried.setPickedUp(false);
                    flugnutWorld.guy.objectCarried.initPhysicsWorld();
                    flugnutWorld.guy.objectCarried = null;
                }
            }
        }
    }

    @Override
    public void click(boolean click) {
        clicked = click;
    }

    @Override
    public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef((body.getPosition().x-width/4)*Constants.V_SCALE, (body.getPosition().y-width/4)*Constants.V_SCALE, 0);
        gl.glRotatef(body.getAngle()*Utilities.TO_DEGREES, 0, 0, 1);
        image.draw(gl);
    }
}