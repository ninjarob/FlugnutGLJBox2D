package com.pkp.gameengine.game;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.io.impl.Vertices;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

public abstract class Drawable {
	protected Vertices vertices;
	protected float backgroundRed=1;
	protected float backgroundGreen=1;
	protected float backgroundBlue=1;
	
	//position
	public BodyDef def;
    public Body body;

	public Drawable(){
		this.def = new BodyDef();
        def.position.set(0,0);
	}
	
	public Drawable(Vec2 position){
        this.def = new BodyDef();
		def.position = position;
	}
	
	public abstract void draw(GL10 gl);
}
