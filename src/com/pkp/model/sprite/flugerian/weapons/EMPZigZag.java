package com.pkp.model.sprite.flugerian.weapons;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.i.Destroyable;
import org.jbox2d.common.Vec2;
import com.pkp.model.Zig;


public class EMPZigZag implements Destroyable {
	public List<Zig> zigs;
	
	public EMPZigZag(float x, float y) {
		zigs = new ArrayList<Zig>();
		addZig(x, y);
	}
	
	public void addZig(float startx, float starty) {
		zigs.add(new Zig(new Vec2(startx,starty), new Vec2(startx, starty)));
	}
	
	public void updateLastZig(float endx, float endy) {
		zigs.get(zigs.size()-1).ev.x = endx;
		zigs.get(zigs.size()-1).ev.y = endy;
	}
	
	public void draw(GL10 gl) {
		for (Zig zig : zigs) {
			zig.draw(gl);
		}
	}

    public void destroy () {
        for (Zig z : zigs) {
            z.destroy();
        }
    }
}