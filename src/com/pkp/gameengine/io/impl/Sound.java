package com.pkp.gameengine.io.impl;

import android.media.SoundPool;

import com.pkp.gameengine.i.io.ISound;

public class Sound implements ISound {
	int soundId;
	SoundPool soundPool;
	
	public Sound(SoundPool soundPool, int soundId)
	{
		this.soundId = soundId;
		this.soundPool = soundPool;
	}
	
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	public void dispose() {
		soundPool.unload(soundId);
	}

}
