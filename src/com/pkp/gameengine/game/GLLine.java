package com.pkp.gameengine.game;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.io.impl.Vertices;

/**
 * Assumptions: Parallel Projection
 * 
 * @author rkevan
 */
public class GLLine extends Drawable {
	float linewidth;
	String color;
	
	public GLLine(float startx, float starty, float width, float height, float linewidth) {
		super();
		this.linewidth=linewidth;
		this.color="1 1 1 1";
		init(startx, starty, startx+width, starty+height);
	}
	
	public GLLine(float startx, float starty, float width, float height, float linewidth, String color) {
		super();
		this.linewidth=linewidth;
		this.color=color;
		init(startx, starty, startx+width, starty+height);
	}
	
	private void init(float startx, float starty, float endx, float endy)
	{  
		vertices = new Vertices(2, 2, false, false, false);
		vertices.setVertices(new float[] { startx, starty,
										endx, endy,}, 0, 4);
		vertices.setIndices(new short[] { 0, 1}, 0, 2);
	}
	
	public void resetVertices(float startx, float starty, float endx, float endy) {
		vertices.setVertices(new float[] { startx, starty,
				endx, endy,}, 0, 4);
	}
	
	@Override
	public void draw(GL10 gl) {
		vertices.draw(GL10.GL_LINES, 0, 2);
		gl.glLineWidth(linewidth);
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
		gl.glDrawElements(GL10.GL_LINES, 2, GL10.GL_UNSIGNED_SHORT, vertices.getIndices());
		gl.glLineWidth(1);
		gl.glColor4f(backgroundRed,backgroundGreen,backgroundBlue,1);
	}
}
