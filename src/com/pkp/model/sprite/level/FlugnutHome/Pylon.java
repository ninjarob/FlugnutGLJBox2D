package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.level.VCPiece;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class Pylon extends VCPiece {
    public PolygonShape ps;
    public Building building;
    public float startx;

    public Pylon(GLGame game, FlugnutWorld flugnutWorld, float startx, Building building) {
        super(game, startx,
                building.body.getPosition().y - building.height/2 + Constants.F_HOME_PYLON_Y_OFFSET,
                Constants.F_HOME_PYLON_WIDTH, Constants.F_HOME_PYLON_HEIGHT,
                Constants.F_HOME_PYLON_PATH);
        this.startx = startx;
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        clickable = true;
        initPhysicsWorld();
    }

    public Pylon(GLGame game, FlugnutWorld flugnutWorld, float startx, float starty, Building building) {
        super(game, startx, starty,
                Constants.F_HOME_PYLON_WIDTH, Constants.F_HOME_PYLON_HEIGHT,
                Constants.F_HOME_PYLON_PATH);
        this.startx = startx;
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        initPhysicsWorld();
    }

    public Pylon(GLGame game, FlugnutWorld flugnutWorld, float startx, float starty, float angle, Building building) {
        super(game, startx, starty,
                Constants.F_HOME_PYLON_WIDTH, Constants.F_HOME_PYLON_HEIGHT,
                Constants.F_HOME_PYLON_PATH);
        this.startx = startx;
        this.flugnutWorld = flugnutWorld;
        this.building = building;
        this.def.angle = angle;
        clickable = true;
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
        if (!engaged && !onFire && clicked && flugnutPresent) {
            timeSpentEngaging += deltaTime;
            if (timeSpentEngaging >= timeToEngage) {
                engaged = true;
                setNewImagefile(Constants.F_HOME_PYLON_CLICKED_PATH);
                flugnutWorld.score += 300;
            }
        }
        else if (onFire && flugnutPresent && flugnutWorld.guy.hasWater) {
            onFire = false;
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
        gl.glRotatef(body.getAngle() * Utilities.TO_DEGREES, 0, 0, 1);
        image.draw(gl);
    }
}