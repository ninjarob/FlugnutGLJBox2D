package com.pkp.model.sprite.flugerian.weapons;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.IScreen;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.Drawable;
import com.pkp.gameengine.game.ImageDrawable;
import com.pkp.model.sprite.Numbers;
import com.pkp.utils.Utilities;

public class SelectorElement extends Drawable  {
	public ImageDrawable img;
	public Numbers numbers;
	public boolean selected;
	public int ammo;
	
	public SelectorElement(GLGame game, String filename, Vec2 position, float width, float height, boolean selected, int ammo)
	{
		this.img = new ImageDrawable(game, filename, position, width, height);
		numbers = new Numbers(game, "numbers.png", .2f);
		numbers.def.position.x = position.x+(20*IScreen.SSC);
		numbers.def.position.y = position.y+(40*IScreen.SSC);
		this.selected = selected;
		this.ammo = ammo;
	}

	@Override
	public void draw(GL10 gl) {
		img.draw(gl);
		if (ammo != -1) {
			Utilities.drawText(""+ammo, gl, IScreen.glGraphics, numbers);
		}
	}

}
