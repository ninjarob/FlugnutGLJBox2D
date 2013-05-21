package com.pkp.gameengine.game;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.io.impl.Vertices;

/**
 * Assumptions: Parallel Projection
 * 
 * @author rkevan
 */
public class GL2DRect extends Drawable {
	String color;
	public GL2DRect(float startx, float starty, float width, float height, String color) {
		super();
		this.color = color;
		init(startx, starty, startx+width, starty+height);
	}
	
	private void init(float startx, float starty, float endx, float endy)
	{
		vertices = new Vertices(4, 6, false, false, false);
		vertices.setVertices(new float[] { startx, starty, 
										endx, starty, 
										endx, endy, 
										startx, endy}, 0, 8);
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0, 6);
	}
	
	public void resetVertices(float startx, float starty, float width, float height) {
		float endx = startx+width;
		float endy = starty+height;
		vertices.setVertices(new float[] { startx, starty, 
				endx, starty, 
				endx, endy, 
				startx, endy}, 0, 8);
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0, 6);
	}
	
	@Override
	public void draw(GL10 gl) {
		vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		if (color != null && color.length() > 0) {
			String[] colorSplit = color.split(" ");
			float r;
			float g;
			float b;
			float a = 1;
			r = Float.parseFloat(colorSplit[0]);
			g = Float.parseFloat(colorSplit[1]);
			b = Float.parseFloat(colorSplit[2]);
			if (colorSplit.length == 4) a = Float.parseFloat(colorSplit[3]);
			gl.glColor4f(r,g,b,a);
		}
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, vertices.getIndices());
		gl.glColor4f(backgroundRed,backgroundGreen,backgroundBlue,1);
	}
}
