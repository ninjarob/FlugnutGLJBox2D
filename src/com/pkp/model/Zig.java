package com.pkp.model;

import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.GLLine;
import com.pkp.model.sprite.flugerian.weapons.EMP;

public class Zig extends EMP {
	public final int MH = 2;
	public int health = 2;
	public Vec2 sv;
	public Vec2 ev;
	public GLLine line;
	public float time = 0;
	
	public Zig(Vec2 sv, Vec2 ev) {
		this.sv = sv;
		this.ev = ev;
		line = new GLLine(sv.x, sv.y, ev.x-sv.x, ev.y-sv.y, 3, "1 1 1 1");
	}
	
	public void draw(GL10 gl) {
		line.resetVertices(sv.x, sv.y, ev.x, ev.y);
		gl.glLoadIdentity();
		line.draw(gl);
	}
}
