package com.pkp.gameengine.game;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.io.impl.Texture;
import com.pkp.gameengine.io.impl.Vertices;

/**
 * Assumptions: Parallel Projection
 * 
 * @author rkevan
 */
public class GL2DRectImage extends Drawable {
	private Texture texture;
	private boolean background;

	public GL2DRectImage(GLGame game, String imageUrl,
			float startx, float starty, float width, float height) {
		super();
		background = false;
		init(imageUrl, game, startx, starty, startx+width, starty+height, 0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public GL2DRectImage(GLGame game, String imageUrl,
			float startx, float starty, float width, float height, boolean background) {
		super();
		this.background = background;
		init(imageUrl, game, startx, starty, startx+width, starty+height, 0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public GL2DRectImage(GLGame game, String imageUrl,
			float startx, float starty, float width, float height,
			float tcstartx, float tcstarty, float tcendx, float tcendy) {
		super();
		init(imageUrl, game, startx, starty, startx+width, starty+height, tcstartx, tcstarty, tcendx, tcendy);
	}
	
	private void init(String imageUrl, GLGame game,
			float startx, float starty, float endx, float endy,
			float tcstartx, float tcstarty, float tcendx, float tcendy)
	{
		vertices = new Vertices(4, 6, false, true, background);
		if (!background) {
			vertices.setVertices(new float[] { startx, starty, tcstartx, tcendy,
											endx, starty, tcendx, tcendy,
											endx, endy, tcendx, tcstarty,
											startx, endy, tcstartx,tcstarty}, 0, 16);
		}
		else {
			vertices.setVertices(new float[] { startx, starty, -1, tcstartx, tcendy, -1,
					endx, starty, -1, tcendx, tcendy, -1,
					endx, endy, -1, tcendx, tcstarty, -1,
					startx, endy, -1, tcstartx,	tcstarty, -1 }, 0, 24);
		}
		vertices.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0, 6);
		texture = new Texture(game, imageUrl);
	}
	
	public void resetVertices(float startx, float starty, float endx, float endy) {
		resetVertices(startx, starty, endx, endy, 0f, 0f, 1f, 1f);
	}
	
	public void resetVertices(float startx, float starty, float endx, float endy,
			float tcstartx, float tcstarty, float tcendx, float tcendy)
	{
		if (!background) {
			vertices.setVertices(new float[] { startx, starty, tcstartx, tcendy,
											endx, starty, tcendx, tcendy,
											endx, endy, tcendx, tcstarty,
											startx, endy, tcstartx,	tcstarty }, 0, 16);
		}
		else {
			vertices.setVertices(new float[] { startx, starty, -1, tcstartx, tcendy, -1,
					endx, starty, -1, tcendx, tcendy, -1,
					endx, endy, -1, tcendx, tcstarty, -1,
					startx, endy, -1, tcstartx,	tcstarty, -1 }, 0, 24);
		}
	}
	
	@Override
	public void draw(GL10 gl) {
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
