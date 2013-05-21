package com.pkp.gameengine.io.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.pkp.gameengine.i.io.IAudio;
import com.pkp.gameengine.i.io.IMusic;
import com.pkp.gameengine.i.io.ISound;

public class Audio implements IAudio {
	AssetManager assets;
	SoundPool soundPool;
	
	public Audio(Activity activity)
	{
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	public IMusic newMusic(String filename)
	{
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new Music(assetDescriptor);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}
	
	public ISound newSound(String filename)
	{
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetDescriptor, 0);
			return new Sound(soundPool, soundId);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
	}
}
