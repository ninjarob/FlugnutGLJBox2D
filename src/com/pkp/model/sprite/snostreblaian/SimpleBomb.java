package com.pkp.model.sprite.snostreblaian;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.screen.GameScreen;
import com.pkp.utils.Constants;
import com.pkp.utils.Utilities;
import org.jbox2d.dynamics.World;


public class SimpleBomb extends Bomb {
	public static int score = 30;

	public SimpleBomb(GLGame game, String filename, float startx, World world) {
		super(game, filename, startx/Constants.V_SCALE, GameScreen.TOP_BAR_HEIGHT/Constants.V_SCALE, 12*IScreen.SSC, 6*IScreen.SSC, 1, 1, world);
	}
	
	public void draw(GL10 gl) {
        if (body.m_linearVelocity.y < -50) body.m_linearVelocity.y = -50;
		gl.glLoadIdentity();
		gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
		image.draw(gl);
	}
}