package com.pkp.gameengine.inputhandlers;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.pkp.gameengine.i.io.ITouchInput;
import com.pkp.gameengine.io.impl.Pool;
import com.pkp.gameengine.io.impl.Pool.PoolObjectFactory;

public abstract class TouchHandler implements ITouchInput {
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	public TouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public abstract boolean onTouch(View v, MotionEvent event);
	
	public abstract boolean isTouchDown(int pointer);

	public abstract int getTouchX(int pointer);
	
	public abstract int getTouchY(int pointer);
	
	public List<TouchEvent> getTouchEvents() {
		synchronized(this) {
			int len = touchEvents.size();
			for (int i = 0; i < len; i++) {
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}
}
