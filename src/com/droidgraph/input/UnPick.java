package com.droidgraph.input;

import java.util.HashMap;

import android.os.SystemClock;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public class UnPick implements Runnable {

	long startTime;
	HashMap<Integer, DGNode> pointerToNodeMap;
	DGMotionEvent me;
	MultiTouchManager manager;

	public UnPick(MultiTouchManager manager) {
		this.manager = manager;
	}

	public void setStartTime(long time) {
		this.startTime = time;
	}

	public void run() {
		final long start = startTime;
		long millis = SystemClock.uptimeMillis() - start;
		int seconds = (int) (millis / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		manager.handlePointerUp(me, true);
		
		long ellpased = start - SystemClock.uptimeMillis();
//		Shared.p("UnPick ellapsed time = ", ellpased);
	}

	public void updateUnPick(DGMotionEvent me, HashMap<Integer, DGNode> map) {
		pointerToNodeMap = map;
		this.me = me;
	}

	public void setUnPickEvent(DGMotionEvent me) {

	}
};