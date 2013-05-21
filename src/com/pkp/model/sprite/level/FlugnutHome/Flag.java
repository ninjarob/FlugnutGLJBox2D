package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.level.VCPiece;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class Flag extends VCPiece {
    public PolygonShape ps;
    public Building building;
    public float startx;

    public Flag(GLGame game, float xstart, float starty, FlugnutWorld flugnutWorld, Building building) {
        super(game, xstart,
                building.body.getPosition().y - building.height/2 + starty,
                Constants.F_HOME_FLAG_WIDTH, Constants.F_HOME_FLAG_HEIGHT,
                Constants.F_HOME_FLAG_PATH);
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        clickable = true;
        initPhysicsWorld();
    }

    public Flag(GLGame game, FlugnutWorld flugnutWorld, Building building) {
        super(game, building.body.getPosition().x,
                building.body.getPosition().y - building.height/2 + Constants.F_HOME_FLAG_Y_OFFSET,
                Constants.F_HOME_FLAG_WIDTH, Constants.F_HOME_FLAG_HEIGHT,
                Constants.F_HOME_FLAG_PATH);
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.STATIC;
        body = flugnutWorld.world.createBody(def);
        ps = new PolygonShape();
        Vec2[] verts = new Vec2[4];
        verts[0] = new Vec2(-4.5f, 8.5f);
        verts[1] = new Vec2(4.5f, 8.5f);
        verts[2] = new Vec2(4.5f, -8.5f);
        verts[3] = new Vec2(-4.5f, -8.5f);
        ps.set(verts, 4);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = .1f;
        fd.friction = 0.25f;
        fd.filter.categoryBits = Constants.PIECE_CATEGORY;
        fd.filter.maskBits = Constants.PIECE_MASK;
        body.createFixture(fd);
        body.setUserData(this);
    }

    @Override
    public void update(float deltaTime) {
        if (!engaged && flugnutPresent && !onFire && clicked) {
            timeSpentEngaging += deltaTime;
            if (timeSpentEngaging >= (1f/6f)* timeToEngage && timeSpentEngaging < (2f/6f)* timeToEngage) {
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + "2.png");
            }
            else if (timeSpentEngaging >= (2f/6f)* timeToEngage && timeSpentEngaging < (3f/6f)* timeToEngage) {
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + "3.png");
            }
            else if (timeSpentEngaging >= (3f/6f)* timeToEngage && timeSpentEngaging < (4f/6f)* timeToEngage) {
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + "4.png");
            }
            else if (timeSpentEngaging >= (4f/6f)* timeToEngage && timeSpentEngaging < (5f/6f)* timeToEngage) {
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + "5.png");
            }
            else if (timeSpentEngaging >= (5f/6f)* timeToEngage && timeSpentEngaging < timeToEngage) {
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + "6.png");
            }
            else if (timeSpentEngaging >= timeToEngage) {
                engaged = true;
                if (notScored) {
                    flugnutWorld.score += 500;
                }
                setNewImagefile(Constants.F_HOME_FLAG_PATH_ONLY + Constants.F_HOME_FLAG_NAME + ".png");
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
        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        image.draw(gl);
    }
}