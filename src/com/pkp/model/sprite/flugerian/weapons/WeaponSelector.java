package com.pkp.model.sprite.flugerian.weapons;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.GLGame;
import com.pkp.gameengine.i.Destroyable;
import org.jbox2d.common.Vec2;
import com.pkp.gameengine.game.Drawable;

public class WeaponSelector extends Drawable implements Destroyable {
	public List<SelectorElement> elements;
	public int selectedIndex = 0;
    public boolean weaponsDisabled=false;
	public WeaponSelector(GLGame game) {
		elements = new ArrayList<SelectorElement>();
		elements.add(new SelectorElement(game, "Weapon/empWeaponSelector.png", new Vec2(10,2), 30, 30, true, -1));
		elements.add(new SelectorElement(game, "Weapon/empLauncherWeaponSelector.png", new Vec2(50,2), 30, 30, false, 10));
		elements.add(new SelectorElement(game, "Weapon/horizSelector.png", new Vec2(90,2), 30, 30, false, 2));
		elements.add(new SelectorElement(game, "Weapon/empWeaponSelector.png", new Vec2(130,2), 30, 30, false, 1));
		elements.add(new SelectorElement(game, "Weapon/empZigZagSelector.png", new Vec2(170,2), 30, 30, false, 7));
	}
	
	public void selectWeapon(float xval) {
		if (xval > 10 && xval < 40) {
            selectWeapon(selectedIndex, 0);
		}
		else if (xval > 50 && xval < 80) {
			selectedIndex = 1;
		}
		else if (xval > 90 && xval < 120) {
			selectedIndex = 2;
		}
		else if (xval > 130 && xval < 160) {
			selectedIndex = 3;
		}
		else if (xval > 170 && xval < 200) {
			selectedIndex = 4;
		}
		else {
			selectedIndex = 0;
		}
	}

    private void selectWeapon(int prevWeapon, int newWeapon) {
        if (prevWeapon == newWeapon) return;
        switch (prevWeapon) {
            case 0:
                elements.get(prevWeapon).img.setNewImagefile("Weapon/empWeaponSelector.png");
                break;
            case 1:
                elements.get(prevWeapon).img.setNewImagefile("Weapon/empLauncherWeaponSelector.png");
                break;
            case 2:
                elements.get(prevWeapon).img.setNewImagefile("Weapon/horizSelector.png");
                break;
            case 3:
                elements.get(prevWeapon).img.setNewImagefile("Weapon/empWeaponSelector.png");
                break;
            case 4:
                elements.get(prevWeapon).img.setNewImagefile("Weapon/empZigZagSelector.png");
                break;
        }
        switch (newWeapon) {
            case 0:
                elements.get(newWeapon).img.setNewImagefile("Weapon/empWeaponSelectorSelected.png");
                break;
            case 1:
                elements.get(newWeapon).img.setNewImagefile("Weapon/empLauncherWeaponSelectorSelected.png");
                break;
            case 2:
                elements.get(newWeapon).img.setNewImagefile("Weapon/horizSelectorSelected.png");
                break;
            case 3:
                elements.get(newWeapon).img.setNewImagefile("Weapon/empWeaponSelectorSelected.png");
                break;
            case 4:
                elements.get(newWeapon).img.setNewImagefile("Weapon/empZigZagSelectorSelected.png");
                break;
        }
        selectedIndex =  newWeapon;
    }
	
	@Override
	public void draw(GL10 gl) {
        if (!weaponsDisabled) {
            for (SelectorElement se : elements) {
                se.draw(gl);
            }
        }
	}

    public void destroy() {
        for (SelectorElement se : elements) {
            se.numbers.destroy();
            se.img.destroy();
        }
    }
}