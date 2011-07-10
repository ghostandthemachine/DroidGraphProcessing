package com.droidgraph.util;

public class Timer {
	
	
	private float[] averages;
	private int numTimers = 0;
	private int currentPosition = 0;
	private long lastTime = 0l;
	
	public Timer(int nTimers) {
		averages = new float[nTimers];
		for (int i = 0; i < nTimers; i++) {
			averages[i] = 0.0f;
		}
		numTimers = nTimers;
	}
	
	public void setMarker() {
		long time = System.currentTimeMillis();
		averages[currentPosition] = (time - lastTime);
		
		lastTime = time;
		currentPosition++;
	}
	

}
