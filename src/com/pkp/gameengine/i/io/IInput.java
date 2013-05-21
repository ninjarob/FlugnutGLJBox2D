package com.pkp.gameengine.i.io;

import java.util.List;

import com.pkp.gameengine.i.io.IKeyboardInput.KeyEvent;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;

public interface IInput {
	public boolean isKeyPressed(int keyCode);
	public boolean isTouchDown(int pointer);
	public int getTouchX(int pointer);
	public int getTouchY(int pointer);
	public float getAccelX();
	public float getAccelY();
	public float getAccelZ();
	public List<KeyEvent> getKeyEvents();
	public List<TouchEvent> getTouchEvents();
}
