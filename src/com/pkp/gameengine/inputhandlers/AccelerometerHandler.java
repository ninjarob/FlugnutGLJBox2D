package com.pkp.gameengine.inputhandlers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler implements SensorEventListener {

	float accelX;
	float accelY;
	float accelZ;
	
	public AccelerometerHandler(Context context) {
		SensorManager man = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if (man.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0)
		{
			Sensor accelerometer = man.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			man.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
	}

	public float getAccelX() {
		return accelX;
	}
	
	public float getAccelY() {
		return accelY;
	}
	
	public float getAccelZ() {
		return accelZ;
	}
	
}
