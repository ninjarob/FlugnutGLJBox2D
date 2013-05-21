package com.pkp.model.level;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.model.sprite.Background;
import com.pkp.model.sprite.MapImage;
import com.pkp.model.sprite.Numbers;
import com.pkp.utils.Utilities;


public class Level extends Drawable {	
	public GLGame game;
	
	//map
	public MapImage mapImage;
	public Background background;
	public float mapx;
	public float mapy;
	public int levelNum;
	private String bgImageFile;
	public boolean locked;
    public boolean weapons;
	public int score;
	public int oneSymbolScore=1000;
	public int twoSymbolScore=2000;
	public int threeSymbolScore=3000;
	public Numbers numbers;
    public List<VictoryCondition> victoryConditions;

	//level

	public float totalTime;
	
	public Level(GLGame game, String mapImageFile, int levelNum, float mapx, float mapy, String bgImageFile, boolean locked, int score, boolean weapons) {
		this.game = game;
		this.levelNum = levelNum;
		this.mapx = mapx;
		this.mapy = mapy;
		this.bgImageFile = bgImageFile;
		this.score = score;
		mapImage = new MapImage(game, mapImageFile, mapx, mapy);
		numbers = new Numbers(game, "numbers.png", .25f);
		numbers.def.position.y=mapy+45;
		numbers.finalX=mapx+25;
        this.locked = locked;
        victoryConditions = new ArrayList<VictoryCondition>();
        this.weapons = weapons;
	}

	public void initLevel()
	{
		background =  new Background(game, bgImageFile);
	}

    public boolean victory() {
        for (VictoryCondition v : victoryConditions) {
            if (!v.checkVictoryConditionsMet()) return false;
        }
        return true;
    }

	@Override
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(mapx, mapy, 0);
		mapImage.draw(gl);
		if (score != -1)
		{
			Utilities.drawText(score+"", gl, IScreen.glGraphics, numbers);
		}
	}
}