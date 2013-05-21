package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.level.VCPiece;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class Well extends VCPiece {
    public PolygonShape ps;
    public Building building;
    public float startx;

    public Well(GLGame game, float startx, FlugnutWorld flugnutWorld, Building building) {
        super(game, startx,
                GameScreen.BBH/1.5f,
                Constants.F_LAB_WELL_WIDTH, Constants.F_LAB_WELL_HEIGHT,
                Constants.F_LAB_WELL_PATH);
        this.startx = startx;
        this.flugnutWorld = flugnutWorld;
        this.building = building;
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
        if (!engaged && flugnutPresent && !onFire && clicked) {
            timeSpentEngaging += deltaTime;
            if (timeSpentEngaging >= timeToEngage) {
                engaged = true;
                flugnutWorld.guy.hasWater= true;
                flugnutWorld.score += 200;
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