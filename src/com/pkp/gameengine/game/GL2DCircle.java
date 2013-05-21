package com.pkp.gameengine.game;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.io.impl.Vertices;

/**
 * Assumptions: Parallel Projection
 * 
 * @author rkevan
 */
public class GL2DCircle extends Drawable {
	String color;
	int num_segments;
	public GL2DCircle(float startx, float starty, float radius, String color) {
		super();
		this.num_segments = 40;
		this.color = color;
		init(startx, starty, radius);
	}
	
	private void init(float startx, float starty, float radius)
	{
		double theta =2 * 3.1415926 / (double)num_segments; 
		double tangetial_factor = Math.tan(theta);//calculate the tangential factor 

		double radial_factor = Math.cos(theta);//calculate the radial factor 
		
		float x = radius;//we start at angle = 0 

		float y = 0;
	    float[] verticesFloatArray = new float[num_segments*6];
	    short[] indicesShortArray = new short[num_segments*6];
		for(int ii = 0; ii < num_segments*6; ii=ii+6) 
		{ 
			verticesFloatArray[ii] = x+startx;
			verticesFloatArray[ii+1] = y+starty; 
	        indicesShortArray[ii] = (short)ii;
	        indicesShortArray[ii+1] = (short)(ii+1);
			//calculate the tangential def.position
			//remember, the radial def.position is (x, y)
			//to get the tangential def.position we flip those coordinates and negate one of them

			float tx = -y; 
			float ty = x; 
	        
			//add the tangential def.position

			x += tx * tangetial_factor; 
			y += ty * tangetial_factor; 
	        
			//correct using the radial factor 

			x *= radial_factor; 
			y *= radial_factor;
			verticesFloatArray[ii+2] = x+startx;
			verticesFloatArray[ii+3] = y+starty;
			indicesShortArray[ii+2] = (short)(ii+2);
	        indicesShortArray[ii+3] = (short)(ii+3);
	        verticesFloatArray[ii+4] = startx;
			verticesFloatArray[ii+5] = starty;
			indicesShortArray[ii+4] = (short)(ii+4);
	        indicesShortArray[ii+5] = (short)(ii+5);
		} 
		vertices = new Vertices(num_segments*6, num_segments*6, false, false, false);
		vertices.setVertices(verticesFloatArray, 0, num_segments*6);
		vertices.setIndices(indicesShortArray, 0, num_segments*6);
	}
	
	public void resetVertices(float startx, float starty, float radius){
		double theta =2 * 3.1415926 / (double)num_segments; 
		double tangetial_factor = Math.tan(theta);//calculate the tangential factor 

		double radial_factor = Math.cos(theta);//calculate the radial factor 
		
		float x = radius;//we start at angle = 0 

		float y = 0;
	    float[] verticesFloatArray = new float[num_segments*6];
		for(int ii = 0; ii < num_segments*6; ii=ii+6) 
		{ 
			verticesFloatArray[ii] = x+startx;
			verticesFloatArray[ii+1] = y+starty; 
			//calculate the tangential def.position
			//remember, the radial def.position is (x, y)
			//to get the tangential def.position we flip those coordinates and negate one of them

			float tx = -y; 
			float ty = x; 
	        
			//add the tangential def.position

			x += tx * tangetial_factor; 
			y += ty * tangetial_factor; 
	        
			//correct using the radial factor 

			x *= radial_factor; 
			y *= radial_factor;
			verticesFloatArray[ii+2] = x+startx;
			verticesFloatArray[ii+3] = y+starty;
	        verticesFloatArray[ii+4] = startx;
			verticesFloatArray[ii+5] = starty;
		} 
		vertices.setVertices(verticesFloatArray, 0, num_segments*6);
	}
	
	@Override
	public void draw(GL10 gl) {
		vertices.draw(GL10.GL_TRIANGLES, 0, num_segments*6);
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
		gl.glDrawElements(GL10.GL_TRIANGLES, num_segments*6, GL10.GL_UNSIGNED_SHORT, vertices.getIndices());
		gl.glColor4f(backgroundRed,backgroundGreen,backgroundBlue,1);
	}
}
