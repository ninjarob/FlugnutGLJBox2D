package com.pkp.model.sprite.level.Tutorial.Tut1;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.GL2DRectImage;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.sprite.flugerian.Bird;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javax.microedition.khronos.opengles.GL10;


public class BirdCage extends Building {
    public PolygonShape ps;
    public Body leftWall;
    public Body rightWall;
    public Body cieling;
    public GL2DRectImage cageBackground;

    public BirdCage(GLGame game, FlugnutWorld flugnutWorld) {
        super(game, Constants.BIRD_CAGE_STARTX, Constants.BIRD_CAGE_STARTY, 88, 119,
                "Tutorial/birdcageFront.png", Constants.F_HOME_HEALTH);
        this.flugnutWorld = flugnutWorld;
        this.maxHealth = Constants.F_HOME_HEALTH;
        cageBackground = new GL2DRectImage(game, "Tutorial/birdcageBack.png", -88/2, -119/2, 88, 119);
        initPhysicsWorld();
    }

    public void initPhysicsWorld() {
        def.type = BodyType.STATIC;
        def.position.x = def.position.x - 22;
        def.position.y = def.position.y - 30;
        body = flugnutWorld.world.createBody(def);
        CircleShape cshape = new CircleShape();
        cshape.setRadius(2);
        FixtureDef fd = new FixtureDef();
        fd.shape = cshape;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.NON_COL_MASK;
        body.createFixture(fd);
        body.setUserData(this);


        def.type = BodyType.STATIC;
        leftWall = flugnutWorld.world.createBody(def);
        EdgeShape shape = new EdgeShape();
        shape.set(new Vec2((-width/3), (-height/3)), new Vec2((-width/3), (height/3)));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 10;
        fd.friction = 0f;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.BUILDING_MASK;
        leftWall.createFixture(fd);
        leftWall.setUserData(this);

        rightWall = flugnutWorld.world.createBody(def);
        shape = new EdgeShape();
        shape.set(new Vec2(width/3, -height/3), new Vec2(width/3, height/3));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 10;
        fd.friction = 0;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.BUILDING_MASK;
        rightWall.createFixture(fd);
        rightWall.setUserData(this);

        cieling = flugnutWorld.world.createBody(def);
        shape = new EdgeShape();
        shape.set(new Vec2(-width/3, (height/3)-15), new Vec2(width/3, (height/2)-15));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 10;
        fd.friction = 0;
        fd.filter.categoryBits = Constants.BUILDING_CATEGORY;
        fd.filter.maskBits = Constants.BUILDING_MASK;
        cieling.createFixture(fd);
        cieling.setUserData(this);

//        ground = flugnutWorld.world.createBody(def);
//        shape = new EdgeShape();
//        shape.set(new Vec2(width/2,  height/2), new Vec2(width/2, height/2));
//        fd = new FixtureDef();
//        fd.shape = shape;
//        fd.density = 0;
//        fd.friction = 0.01f;
//        ground.createFixture(fd);
//        ground.setUserData(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glLoadIdentity();
        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        cageBackground.draw(gl);
        for(int i = 0; i < buildingPieces.size(); i++) {
            BuildingPiece bp = buildingPieces.get(i);
            if(bp instanceof Bird) {
                if (((Bird) bp).caged) {
                    bp.draw(gl);
                }
            }
        }
        gl.glLoadIdentity();
        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
        image.draw(gl);
        for(int i = 0; i < buildingPieces.size(); i++) {
            BuildingPiece bp = buildingPieces.get(i);
            if(bp instanceof Bird) {
                if (!((Bird) bp).caged) {
                    bp.draw(gl);
                }
            }
            else {
                if (!bp.done) {
                    bp.draw(gl);
                }
            }
        }
    }
}