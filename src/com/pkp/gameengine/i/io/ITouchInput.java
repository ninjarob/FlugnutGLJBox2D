package com.pkp.gameengine.i.io;

import java.util.List;

import android.view.View.OnTouchListener;

public interface ITouchInput extends OnTouchListener{
	public static class TouchEvent {
		public static final int TOUCH_DOWN=0;
		public static final int TOUCH_UP=1;
		public static final int TOUCH_DRAGGED=2;
		
		public int type;
		public int x, y;
		public int pointer;
	}
	
	public boolean isTouchDown(int pointer);
	public int getTouchX(int pointer);
	public int getTouchY(int pointer);
	public List<TouchEvent> getTouchEvents();
}
