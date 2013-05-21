package com.pkp.gameengine.i.io;

import java.util.List;

public interface IKeyboardInput {
	public static class KeyEvent {
		public static final int KEY_DOWN = 0;
		public static final int KEY_UP = 1;
		
		public int type;
		public int keyCode;
		public char keyChar;
	}
	
	public boolean isKeyPressed(int keyCode);
	public List<KeyEvent> getKeyEvents();
}
