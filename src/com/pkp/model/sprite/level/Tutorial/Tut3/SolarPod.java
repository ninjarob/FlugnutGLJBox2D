package com.pkp.model.sprite.level.Tutorial.Tut3;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.model.sprite.flugerian.weapons.EMP;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import javax.microedition.khronos.opengles.GL10;


public class SolarPod extends BuildingPiece {
    public CircleShape cs;
    public float startx;
    public Building building;
    public String name;
    public int charge = 0;
    public final int MAX_CHARGE = 3;
    public EMP previousEmp;

    public SolarPod(GLGame game, float startx, float starty, FlugnutWorld flugnutWorld, Building building) {
        super(game, startx, starty,
                Constants.POD_RADIUS, Constants.POD_RADIUS,
                Constants.POD_PATH, 0f, 0f, 1f, 1f);
        this.flugnutWorld = flugnutWorld;
        initPhysicsWorld();
        this.building = building;
    }

    @Override
    public void initPhysicsWorld() {
        def.type = BodyType.DYNAMIC;
        body = flugnutWorld.world.createBody(def);
        cs = new CircleShape();
        cs.setRadius(Constants.POD_RADIUS);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1f;
        fd.friction = .8f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK_GGC_COL;
        body.createFixture(fd);
        body.setUserData(this);
        body.setGravityScale(0);
    }

    public boolean isFullyCharged() {
        return charge >= MAX_CHARGE;
    }

    @Override
    public void update(float deltaTime) {

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