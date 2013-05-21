package com.pkp.model.sprite.snostreblaian;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.game.GLLine;
import com.pkp.model.FlugnutWorld;


public class SuperBomb extends Drawable {
	public int targetBuilding;
	public float startx;
	public float starty;
	public double currentx;
	public double currenty;
	public float endx;
	public float endy;
	public boolean done;
	public GLLine missileLine;
    public FlugnutWorld flugnutWorld;
	
	public SuperBomb(int targetBuilding, float startx, float starty, float endx, float endy) {
		this.targetBuilding = targetBuilding;
		this.startx = startx;
		this.starty = starty;
		this.currentx = (double)startx;
		this.currenty = (double)starty;
		this.endx = endx;
		this.endy = endy;
		this.done = false;
		missileLine = new GLLine(startx, starty, startx, starty, 2.0f, "0.5 .75 0.40 1");
	}
	
	public void draw(GL10 gl) {
		missileLine.resetVertices((float)startx, (float)starty, (float)currentx, (float)currenty);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, 0);
		missileLine.draw(gl);
	}
}