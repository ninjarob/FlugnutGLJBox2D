package com.pkp.model.level;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.game.GL2DCircle;
import com.pkp.gameengine.game.GLLine;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.screen.GameScreen;

public class WaveBar extends Drawable {
	public float backWidth = 180 * IScreen.SSC;
	public float frontWidth = 180 * IScreen.SSC;
	public GLLine backWaveBarLine;
	public GLLine frontWaveBarLine;
	public GL2DCircle circle;
	public float startx;
	private float starty;
	private float backLineWidth = 2;
	private String backLineColor = "0.9 0.02 0.30 1.0";
	private float frontLineWidth = 4;
	private String frontLineColor = "0.9 0.3 0.60 0.8";
	private float circleWidth = 4;
	private float[] waveXValues;
	
	public WaveBar(GLGame game, GLGraphics glGraphics, List<Wave> waves) {
		startx = glGraphics.getWidth() / 2 - backWidth / 2;
		starty = GameScreen.TOP_BAR_HEIGHT + ((glGraphics.getHeight() - GameScreen.TOP_BAR_HEIGHT) / 2);
		backWaveBarLine = new GLLine(
				startx,
				starty,
				backWidth, 0, backLineWidth, backLineColor);
		frontWaveBarLine = new GLLine(
				startx,
				starty,
				frontWidth, 0, frontLineWidth, frontLineColor);
		waveXValues = new float[waves.size()];
		circle = new GL2DCircle(0, 0, circleWidth, ".2 .2 .2 1");
		float maxTime = (waves.get(waves.size()-1).startTime);
		for (int i = 0; i < waves.size(); i++) {
			Wave wave = waves.get(i);
			waveXValues[i] = (backWidth-((wave.startTime/maxTime)*backWidth))+startx;
		}
	}

	@Override
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, 0);
		backWaveBarLine.draw(gl);
		gl.glLoadIdentity();
		frontWaveBarLine.resetVertices(startx, starty, frontWidth+startx, starty);
		gl.glTranslatef(0, 0, 0);
		frontWaveBarLine.draw(gl);
		for(int i = 0; i < waveXValues.length; i++)
		{
			gl.glLoadIdentity();
			gl.glTranslatef(waveXValues[i], starty, 0);
			circle.draw(gl);
		}
	}

}