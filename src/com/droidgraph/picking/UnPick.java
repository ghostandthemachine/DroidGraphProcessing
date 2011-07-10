package com.droidgraph.picking;

import com.droidgraph.input.DGMotionPointer;
import com.droidgraph.input.MotionManager;

/**
 * 
 * @author ghostandthemachine
 * 
 *         This class ques an UnPick. It is needed to manage the node pointer
 *         locking in the que cycle to avoid null pointers etc
 * 
 */

public class UnPick implements Runnable {

	DGMotionPointer motionPointer;
	MotionManager motionManager;

	public UnPick(MotionManager mm, DGMotionPointer mp) {
		motionPointer = mp;
		motionManager = mm;
	}

	@Override
	public void run() {
		if (motionPointer.locked()) {
			motionPointer.unlock();
		}
	}

}
