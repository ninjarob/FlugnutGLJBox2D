package com.pkp.model.sprite.flugerian;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.GL2DRect;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.gameengine.i.Destroyable;
import com.pkp.model.FlugnutWorld;
import com.pkp.model.level.VCPiece;
import com.pkp.model.sprite.level.FlugnutHome.Pylon;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


public class Building extends ImageDrawable implements Destroyable {
    public Body body;
    public float[] strikePointx;
    public float[] strikePointy;
	public List<BuildingPiece> buildingPieces;
	public int maxHealth;
    public int health;
    public FlugnutWorld flugnutWorld;
    public BuildingPiece clickedPiece;
	
	public Building(GLGame game, float startx, float starty, float width, float height, String filename, int health) {
        super(game, filename, new Vec2(startx+(width/2), starty+(height/2)), width, height, -width/2, -height/2);
        this.health = health;
        this.maxHealth = health;
        buildingPieces = new ArrayList<BuildingPiece>();
	}

    public void update(float deltaTime) {
        for(int i = 0; i < buildingPieces.size(); i++) {
            BuildingPiece bp = buildingPieces.get(i);
            //if the piece is not done and not picked up, then check to see if flugnut is present
            if (!bp.getPickedUp() && !bp.done) {
                bp.flugnutPresent = false;
                float dist = Utilities.dist(flugnutWorld.guy.body.getPosition(), bp.body.getPosition());
                if (dist <= 10) {
                    bp.flugnutPresent = true;
                }
            }
            if (bp.done) { //if the piece is done, remove it.
                bp.destroy();
                buildingPieces.remove(i);
                i--;
            }
            else {  //otherwise, update it
                bp.update(deltaTime);
            }
        }
    }

	@Override
	public void draw(GL10 gl) {
        for(int i = 0; i < buildingPieces.size(); i++) {
            BuildingPiece bp = buildingPieces.get(i);
            if (!bp.done) {
                bp.draw(gl);
            }
        }
	}

    public BuildingPiece handleObjClick(float x, float y) {
        BuildingPiece returnPiece = null;
        boolean itemClicked = false;
        for (BuildingPiece bp : buildingPieces) {
            if (bp.clickable) {
                float dist = Utilities.dist(bp.body.getPosition(), x, y);
                if (dist < 15 && !itemClicked) {
                    itemClicked = true;
                    bp.click(true);
                    returnPiece = bp;
                }
                else {
                    if (bp.clicked) {
                        bp.click(false);
                    }
                }
            }
        }
        return returnPiece;
    }

    public void destroy() {
        super.destroy();
        for(BuildingPiece bp : buildingPieces) {
            bp.destroy();
        }
    }
}