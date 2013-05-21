package com.pkp.gameengine.io.impl;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.pkp.gameengine.i.io.IInput;
import com.pkp.gameengine.i.io.IKeyboardInput.KeyEvent;
import com.pkp.gameengine.i.io.ITouchInput.TouchEvent;
import com.pkp.gameengine.inputhandlers.AccelerometerHandler;
import com.pkp.gameengine.inputhandlers.KeyboardHandler;
import com.pkp.gameengine.inputhandlers.MultiTouchHandler;
import com.pkp.gameengine.inputhandlers.SingleTouchHandler;
import com.pkp.gameengine.inputhandlers.TouchHandler;

public class Input implements IInput {

	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	TouchHandler touchHandler;
	
	@SuppressWarnings("deprecation")
	public Input(Context context, View view, float scaleX, float scaleY) {
		accelHandler = new AccelerometerHandler(context);
		keyHandler = new KeyboardHandler(view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		else
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keyHandler.isKeyPressed(keyCode);
	}

	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	public float getAccelX() {
		return accelHandler.getAccelX();
	}

	public float getAccelY() {
		return accelHandler.getAccelY();
	}

	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	public List<KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}

	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

}
