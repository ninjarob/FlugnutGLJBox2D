package com.pkp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.model.level.Wave;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.MiscObject;
import com.pkp.model.sprite.flugerian.ShwaiGuy;
import com.pkp.model.sprite.flugerian.weapons.*;
import com.pkp.model.sprite.snostreblaian.HeavyBomb;
import com.pkp.model.sprite.snostreblaian.Bomb;
import com.pkp.model.sprite.snostreblaian.SimpleBomb;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import com.pkp.utils.FContactListener;
import com.pkp.utils.GenerateWorldObjects;
import com.pkp.utils.Utilities;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

public class FlugnutWorld {
	static final float MAX_FLIGHT_SPEED = 50.0f * IScreen.SSC;
	static final float ACCELERATION = 800;
	static final float CLOSE_ENOUGH_THRESHOLD = 5;
	static final float START_DEACCELL_DISTANCE = 150;
	public static Vec2 start = new Vec2();
	public static Vec2 empWave = new Vec2();
	public static Vec2 horizEmp = new Vec2();
	public ShwaiGuy guy;
    public List<Wave> waves = new ArrayList<Wave>();
    public float totalWaveTime;
	public List<Building> buildings;
    public List<MiscObject> miscObjects;
	public List<Bomb> bombs;
	public List<EMPCircle> empCircles;
	public List<EMPSuperCircle> empSuperCircles;
	public List<EMPLauncher> empLaunchers;
	public List<EMPHoriz> horizEmps;
	public List<EMPZigZag> empZigZags;
	public boolean gameOver = false;
	public boolean gameWin = false;
	public int score = 0;
	public int weaponsUsed = 0;
	public GLGame game;
	public boolean empCircleFired = false;
	public boolean empSuperCircleFired = false;
	public boolean horizEmpFired = false;
	public boolean zigZagEmpFired = false;
    public static List<Body> bodiesToDestroy = new ArrayList<Body>();
    public static List<Joint> jointsToDestroy = new ArrayList<Joint>();
    public float worldTime = 0;

    private boolean birdAdded = false;
    //physics
    public World world;
    public Body ground;
    public Body leftWall;
    public Body rightWall;
    public Body heaven;
    public GameScreen gameScreen;
	
	public FlugnutWorld(GLGame game, GameScreen gameScreen) {
		this.game = game;
        this.gameScreen = gameScreen;
        bombs = new ArrayList<Bomb>();
        empCircles = new ArrayList<EMPCircle>();
        empSuperCircles = new ArrayList<EMPSuperCircle>();
        empLaunchers = new ArrayList<EMPLauncher>();
        horizEmps = new ArrayList<EMPHoriz>();
        empZigZags = new ArrayList<EMPZigZag>();
        empWave.x = 0;
        empWave.y = 0;
        horizEmp.x = 0;
        horizEmp.y = 0;
        initPhysicsWorld();
        initWorldObjects();
        miscObjects = new ArrayList<MiscObject>();
		guy = new ShwaiGuy(game, 64 * IScreen.SSC, 64 * IScreen.SSC, "NewGuy.png", this);
        bodiesToDestroy = new ArrayList<Body>();
        totalWaveTime = 0;
        if (waves.size() > 0)
        {
            totalWaveTime = waves.get(waves.size()-1).startTime;
        }
	}

    public void initPhysicsWorld() {
        world = new World(new Vec2(0, -30));
        world.setContactListener(new FContactListener());

        BodyDef bd = new BodyDef();
        ground = world.createBody(bd);
        EdgeShape shape = new EdgeShape();
        shape.set(new Vec2(-20, GameScreen.BBH/Constants.V_SCALE-6), new Vec2(IScreen.glGraphics.getWidth()/Constants.V_SCALE+20, GameScreen.BBH/Constants.V_SCALE-6));
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0;
        fd.friction = 0.01f;
        ground.createFixture(fd);

        bd = new BodyDef();
        leftWall = world.createBody(bd);
        shape = new EdgeShape();
        shape.set(new Vec2(-10, -20), new Vec2(-10, GameScreen.TBH/Constants.V_SCALE+20));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0;
        fd.friction = 0.01f;
        leftWall.createFixture(fd);

        bd = new BodyDef();
        rightWall = world.createBody(bd);
        shape = new EdgeShape();
        shape.set(new Vec2(IScreen.glGraphics.getWidth()/Constants.V_SCALE+10, -20), new Vec2(IScreen.glGraphics.getWidth()/Constants.V_SCALE+10, GameScreen.TBH/Constants.V_SCALE+20));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0;
        fd.friction = 0.01f;
        rightWall.createFixture(fd);

        bd = new BodyDef();
        heaven = world.createBody(bd);
        shape = new EdgeShape();
        shape.set(new Vec2(-20, GameScreen.TBH/Constants.V_SCALE+20), new Vec2(IScreen.glGraphics.getWidth()/Constants.V_SCALE+20, GameScreen.TBH/Constants.V_SCALE+20));
        fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0;
        fd.friction = 0.01f;
        heaven.createFixture(fd);
    }

	private void initWorldObjects() {
        waves = GenerateWorldObjects.generateWaves(gameScreen.level.levelNum);
		buildings = GenerateWorldObjects.generateBuildings(game, this, gameScreen.level.levelNum);
    }

	public void update(float deltaTime) {
        updatePhysicsWorld();

        worldTime += deltaTime;
		if (gameOver || gameWin)
			return;

		if (buildingsDead()) {
			gameOver = true;
			return;
		}
		updateGuy(deltaTime);
        updateBuildings(deltaTime);
        updateWaves(deltaTime);
		updateMissiles(deltaTime);
		updateEMPs(deltaTime);
		updateEMPLaunchers(deltaTime);
		updateHorizEmps(deltaTime);
		updateEMPSuperCircles(deltaTime);
		updateZigZagEmps(deltaTime);
        updateMisc(deltaTime);
	}

    public void updatePhysicsWorld() {
        world.step(1f/60f, 3, 3);
       // world.getContactManager().m_contactListener.endContact();
        destroyBodies();
    }

    public void destroyBodies() {
        if(!world.isLocked()) {
            if (jointsToDestroy != null && jointsToDestroy.size() > 0) {
                for (Joint b : jointsToDestroy) {
                    world.destroyJoint(b);
                }
                jointsToDestroy.clear();
            }
            if (bodiesToDestroy != null && bodiesToDestroy.size() > 0) {
                for (Body b : bodiesToDestroy) {
                     world.destroyBody(b);
                }
                bodiesToDestroy.clear();
            }
        }
    }

	public boolean buildingsDead() {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).health > 0) return false;
		}
		return true;
	}
	
	private void updateGuy(float deltaTime) {
        //set destination to wherever the clicked object is.
        if (guy.getObjectClicked() != null) {
            guy.newDestination.x = guy.getObjectClicked().body.getPosition().x;
            guy.newDestination.y = guy.getObjectClicked().body.getPosition().y;
        }
        //check to see if the destination has changed.  If you don't do this check then
		//   the velocities and dirs can get screwed up because of threading issues.
		if (guy.destination.x != guy.newDestination.x || guy.destination.y != guy.newDestination.y) {
			start = new Vec2(guy.body.getPosition().x, guy.body.getPosition().y);
			guy.destination.x=guy.newDestination.x;
			guy.destination.y=guy.newDestination.y;
		}
		double totalDistance = guy.distanceToDest();
		float angleBetweenGuyAndDest = Utilities.findAngleBetweenVectorsRad(guy.body.getPosition(), guy.destination);
        guy.def.angle = angleBetweenGuyAndDest;
		if (totalDistance < 15 && !guy.missileArrived) {
			if (empCircleFired) {
				empCircles.add(new EMPCircle(game, "emp1.png", empWave.x, empWave.y, this));
				empCircleFired = false;
			}
			else if (empSuperCircleFired) {
				empSuperCircles.add(new EMPSuperCircle(game, "emp1.png", empWave.x, empWave.y, this));
				empSuperCircleFired = false;
			}
			else if (horizEmpFired) {
				horizEmps.add(new EMPHoriz(game, "emp1.png", horizEmp.x, horizEmp.y, this));
				horizEmpFired = false;
			}
			else if (zigZagEmpFired) {
				empZigZags.get(empZigZags.size()-1).updateLastZig(empWave.x, empWave.y);
				zigZagEmpFired = false;
			}
			guy.missileArrived = true;
        }
		if (!guy.arrived) {
			if (guy.distanceToDest() < 8 && Utilities.getVelocityMagnitude(guy.body.getLinearVelocity()) < 20) { //we've arrived, get us arrived.
                guy.body.setLinearVelocity(Utilities.diminishVelocityToMag(guy.body.getLinearVelocity(), 10));
				guy.arrived = true;
			}
			else { //otherwise, update as needed
				guy.updateAccordingToDirAndVel(deltaTime);
			}
		}
		//rotateGuy(deltaTime);
    }

	private void rotateGuy(float deltaTime) {
		boolean turnClockwise = true;
		float diff = 0;
		if (guy.def.angle < Math.PI/2 && guy.rotationAngle > guy.def.angle+Math.PI) {
			float newPreviousDir = (float)(guy.rotationAngle-(Math.PI*2));
			diff = guy.def.angle - newPreviousDir;
			turnClockwise = false;
		}
		else if (guy.rotationAngle < Math.PI/2 && guy.def.angle > guy.rotationAngle+Math.PI) {
			float newDir = (float)(guy.def.angle+(Math.PI*2));
			diff = guy.rotationAngle - newDir;
		}
		else {
			diff = guy.def.angle - guy.rotationAngle;
			if (diff > 0)
				turnClockwise = false;
		}
		if (Math.abs(diff) >= Math.PI/16) 
		{
			if (turnClockwise)
			{
				guy.rotationAngle = (float)(guy.rotationAngle-((2*Math.PI)*deltaTime));
				if (guy.rotationAngle < 0)
				{
					int rotations = (int)(-guy.rotationAngle/(Math.PI*2))+1;
					guy.rotationAngle = (float)(guy.rotationAngle + (Math.PI*2*rotations));
					//guy.rotationAngle = (float)((Math.PI*2)-(guy.rotationAngle%(Math.PI*2))*(Math.PI*2));
				}
			}
			else
			{
				guy.rotationAngle = (float)(guy.rotationAngle+(2*(Math.PI)*deltaTime));
				if (guy.rotationAngle >= Math.PI*2)
				{
					int rotations = (int)(guy.rotationAngle/(Math.PI*2));
					guy.rotationAngle = (float)(guy.rotationAngle - (Math.PI*2*rotations));
					//guy.rotationAngle = (float)((guy.rotationAngle%(Math.PI*2))*(Math.PI*2));
				}
			}
		}
		else {
			guy.rotationAngle = guy.def.angle;
		}
	}


    private void updateBuildings(float deltaTime) {
        for (Building b: buildings) {
            b.update(deltaTime);
        }
    }

    private void updateWaves(float deltaTime) {
        for (int i=0; i < waves.size(); i++) {
            Wave wave = waves.get(i);
            if (wave.nextStartTime > worldTime)
            {
                continue;
            }
            switch (wave.waveType) {
                case SIMPLE_MISSILE:
                    spawnNewMissiles(wave.quantity);
                    wave.nextStartTime += totalWaveTime; //run again after the last one runs
                    break;
                case HEAVY_MISSILE:
                    spawnNewHeavyMissiles(wave.quantity);
                    wave.nextStartTime += totalWaveTime; //run again after the last one runs
                    break;
                case FIRE:
                    break;
                default:
                    break;
            }
        }
    }

	private void updateMissiles(float deltaTime) {
		if (bombs.size() > 0 && allMissilesDone()) {
			bombs.clear();
		}
		if (bombs.size() > 0) {
			for (int i = 0; i < bombs.size(); i++) {
				Bomb b = bombs.get(i);
                if (b.done)
                {
                    b.destroy();
                    bombs.remove(i);
                    i--;
                }
                if (b.health <= 0 && !b.done) {
                    b.done = true;
                }
			}
		}
	}

    private void updateMisc(float deltaTime) {
        for (int i = 0; i < miscObjects.size(); i++) {
            MiscObject mo = miscObjects.get(i);
            if (mo.body.getPosition().x < -200) {
                bodiesToDestroy.add(mo.body);
                miscObjects.remove(i);
                i--;
                birdAdded = false;
            }
            else {
                miscObjects.get(i).update(deltaTime);
            }
        }
    }
	
	private void updateEMPs(float deltaTime) {
		for (int i = 0; i < empCircles.size(); i++) {
			EMPCircle am = empCircles.get(i);

			// anti-missile is done, remove it.
			if (am.radius >= EMPCircle.EMP_MAX_RADIUS) {
				am.image.getTexture().dispose();
                bodiesToDestroy.add(am.body);
				empCircles.remove(i);
				i = i - 1;
				continue;
			}

			// change anti-missile radius.
			am.radius += EMPCircle.EMP_GROWTH_SPEED * deltaTime;
		}
	}
	
	private void updateEMPSuperCircles(float deltaTime) {
		for (int i = 0; i < empSuperCircles.size(); i++) {
			EMPSuperCircle am = empSuperCircles.get(i);

			// anti-missile is done, remove it.
			if (am.radius >= EMPSuperCircle.EMP_MAX_RADIUS) {
				am.image.getTexture().dispose();
				empSuperCircles.remove(i);
				i = i - 1;
				continue;
			}

			// change anti-missile radius.
			am.radius += EMPSuperCircle.EMP_GROWTH_SPEED * deltaTime;
		}
	}
	
	private void updateEMPLaunchers(float deltaTime) {
		for (int i = 0; i < empLaunchers.size(); i++) {
			EMPLauncher empl = empLaunchers.get(i);
			empl.update(deltaTime);
			if (empl.done) {
				empCircles.add(new EMPCircle(game, "emp1.png", empl.endx, empl.endy, this));
				empLaunchers.remove(i);
				i=i-1;
				continue;
			}
		}
	}

	private void updateHorizEmps(float deltaTime) {
		for (int i = 0; i < horizEmps.size(); i++) {
			EMPHoriz he = horizEmps.get(i);

			// anti-missile is done, remove it.
			if (he.health == 0 || he.time > 10) {
				he.image.getTexture().dispose();
				horizEmps.remove(i);
				i = i - 1;
				continue;
			}

			// change anti-missile radius.
			if (he.width < IScreen.glGraphics.getWidth() * 2)
				he.width += EMPCircle.EMP_GROWTH_SPEED * 50 * deltaTime;
			he.time += deltaTime;
		}
	}
	
	private void updateZigZagEmps(float deltaTime) {
		for (int i = 0; i < empZigZags.size(); i++) {
			EMPZigZag he = empZigZags.get(i);
			for (int j = 0; j < he.zigs.size(); j++) {
				Zig zig = he.zigs.get(j);
				// anti-missile is done, remove it.
				if (zig.health == 0 || zig.time > 15) {
					he.zigs.remove(j);
					j = j - 1;
					continue;
				}
				zig.time += deltaTime;
			}
		}
	}
	
	private boolean allMissilesDone() {
		for (int i = 0; i < bombs.size(); i++) {
			if (!bombs.get(i).done)
				return false;
		}
		return true;
	}

	public void spawnNewMissiles(int missileToSpawn) {
		Random r = new Random();
		for (int i = 0; i < missileToSpawn; i++) {
			int missileStartX = r.nextInt(IScreen.glGraphics.getWidth());
			int tbIndex = r.nextInt(buildings.size());
			int count = 0;
			while (buildings.get(tbIndex).health <= 0 && count < 20)
			{
				tbIndex = r.nextInt(buildings.size());
				count++;
			}
			if (buildings.get(tbIndex).health <=0)
			{
				tbIndex = -1;
				for (int j = 0; j< buildings.size(); j++) {
					Building building = buildings.get(j);
					if (building.health >= 0)
					{
						tbIndex = j;
						break;
					}
				}
			}
			if (tbIndex != -1) {  //If it were -1, that means there are no buildings left.
                Bomb b =  new SimpleBomb(game, "missile.png", missileStartX, world);
                b.flugnutWorld = this;
				bombs.add(b);
			}
		}
	}
	
	public void spawnNewHeavyMissiles(int missileToSpawn) {
		Random r = new Random();
		for (int i = 0; i < missileToSpawn; i++) {
			int missileStartX = r.nextInt(IScreen.glGraphics.getWidth());
			int tbIndex = r.nextInt(buildings.size());
			int count = 0;
			while (buildings.get(tbIndex).health <= 0 && count < 20)
			{
				tbIndex = r.nextInt(buildings.size());
				count++;
			}
			if (buildings.get(tbIndex).health <=0)
			{
				tbIndex = -1;
				for (int j = 0; j< buildings.size(); j++) {
					Building building = buildings.get(j);
					if (building.health >= 0)
					{
						tbIndex = j;
						break;
					}
				}
			}
			if (tbIndex != -1) {  //If it were -1, that means there are no buildings left.
                Bomb b =  new HeavyBomb(game, "missile.png", missileStartX, world);
                b.flugnutWorld = this;
				bombs.add(b);
			}
		}
	}

    public void destroy() {
        guy.destroy();
        for (Building b : buildings){
            b.destroy();
        }
        for (MiscObject mo : miscObjects){
            mo.destroy();
        }
        for(Bomb b : bombs) {
            b.destroy();
        }
        for (EMP ec : empCircles) {
            ec.destroy();
        }
        for (EMP ec : empSuperCircles) {
            ec.destroy();
        }
        for (EMPLauncher el : empLaunchers) {
            el.destroy();
        }
        for (EMP ec : horizEmps) {
            ec.destroy();
        }
        for (EMPZigZag ez : empZigZags) {
            ez.destroy();
        }
        FlugnutWorld.bodiesToDestroy = null;
    }

}