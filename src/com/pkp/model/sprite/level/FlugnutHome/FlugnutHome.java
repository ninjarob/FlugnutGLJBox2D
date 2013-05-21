package com.pkp.model.sprite.level.FlugnutHome;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class FlugnutHome extends Building {
    public PolygonShape ps;
    public Body collisionBody;

    //FlugnutLevel\House.png
    //477X238
    public FlugnutHome(GLGame game, FlugnutWorld flugnutWorld) {
        super(game, Constants.F_HOME_STARTX, Constants.F_HOME_STARTY, Constants.F_HOME_WIDTH, Constants.F_HOME_HEIGHT,
                Constants.F_HOME_PATH, Constants.F_HOME_HEALTH);
        this.flugnutWorld = flugnutWorld;
        this.maxHealth = Constants.F_HOME_HEALTH;
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.STATIC;
        body = flugnutWorld.world.createBody(def);
        CircleShape cs = new CircleShape();
        cs.setRadius(1);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1f;
        fd.friction = 0f;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.NON_COL_MASK;
        body.createFixture(fd);
        body.setUserData(this);

        collisionBody = flugnutWorld.world.createBody(def);
        ps = new PolygonShape();
        Vec2[] vertices = {new Vec2(-(width/3), (height/3)-20),
                new Vec2(0, (height/3)),
                new Vec2(width/3, (height/3)-20)};
        ps.set(vertices, 3);
        fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.BUILDING_MASK;
        collisionBody.createFixture(fd);
        collisionBody.setUserData(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        image.draw(gl);
        super.draw(gl);
    }
}