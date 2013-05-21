package com.pkp.model.sprite.level.Tutorial;

import com.pkp.GLGame;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class EmptyBuilding extends Building {
    public CircleShape cs;

    //FlugnutLevel\House.png
    //477X238
    public EmptyBuilding(GLGame game, FlugnutWorld flugnutWorld) {
        super(game, 0, 0, 1, 1,
                "FlugnutLevel/Window.png", 1);
        this.flugnutWorld = flugnutWorld;
        this.maxHealth = 1;
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.STATIC;
        body = flugnutWorld.world.createBody(def);
        cs = new CircleShape();
        cs.setRadius(0.5f);
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1f;
        fd.friction = 0f;
        body.createFixture(fd);
        body.setUserData(this);
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