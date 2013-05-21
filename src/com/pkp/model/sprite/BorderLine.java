package com.pkp.model.sprite;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.game.GLLine;


public class BorderLine extends Drawable {
	public float startx;
	public float starty;
	public float endx;
	public float endy;
	private float lineWidth = 2;
	private String color = "0.5 .75 0.40 1";
	public GLLine borderLine; 
	
	public BorderLine(float startx, float starty, float endx, float endy) {
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
		borderLine = new GLLine(startx, starty, endx-startx, endy-starty, lineWidth, color);
	}
	
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, 0);
		borderLine.draw(gl);
	}
}