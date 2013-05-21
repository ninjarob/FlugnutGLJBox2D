package com.pkp.gameengine.inputhandlers;

import android.view.MotionEvent;
import android.view.View;

import com.pkp.gameengine.i.io.ITouchInput;

public class SingleTouchHandler extends TouchHandler implements ITouchInput {
	boolean isTouched;
	int touchX;
	int touchY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		super(view, scaleX, scaleY);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			TouchEvent touchEvent = touchEventPool.newObject();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = true;
				break;
			}
			touchEvent.x = touchX = (int)(event.getX() * scaleX);
			touchEvent.y = touchY = (int)(event.getY() * scaleY);
			return true;
		}
	}
	
	@Override
	public boolean isTouchDown(int pointer) {
		synchronized(this) {
			if (pointer == 0)
				return isTouched;
			else
				return false;
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized(this) {
			return touchX;
		}
	}
	
	@Override
	public int getTouchY(int pointer) {
		synchronized(this) {
			return touchY;
		}
	}
}
