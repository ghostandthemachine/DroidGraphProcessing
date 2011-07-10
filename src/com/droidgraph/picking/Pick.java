package com.droidgraph.picking;

import android.graphics.Color;
import android.view.MotionEvent;

import com.droidgraph.input.MotionManager;
import com.droidgraph.util.Shared;

public class Pick implements Runnable {

	String TAG = "Pick";

	MotionEvent me;
	MotionManager motionManager;
	
	int globalPointerID;
	int touchDownX;
	int touchDownY;
	int actionCode;

	public Pick(MotionManager mm, MotionEvent me, int globalPointerID, int actionCode) {

		this.me = me;
		this.globalPointerID = globalPointerID;
		this.actionCode = actionCode;
		this.motionManager = mm;
		
		if (globalPointerID < me.getPointerCount()) {
			touchDownX = (int) (me.getX(globalPointerID));
			touchDownY = (int) (me.getY(globalPointerID));
		}
	}

	@Override
	public void run() {
		
		int bc = Shared.offscreenBuffer.get(touchDownX, touchDownY);

		int r = Color.red(bc);
		int g = Color.green(bc);
		int b = Color.blue(bc);
//		int a = Color.alpha(bc);
		
		// So we can start the list call at 0, not 1
		r--;
		
		// If this node is in the Shareables list, it must be in this range
		if (r >= 0 && r <= 765 && r <= Shared.touchables.size() - 1) {

//			Shared.p("Pick, Shared.touchables.get, numPointers =", Shared.touchables.get(r + g + b).getPointerCount());
			// Since android actionCodes additional touches as MotionEvent.ACTION_POINTER_DOWN, we need to manipulate that
			// action code so it is sent on to our nodes as new ACTION_DOWN events, or else they never see a first touch
			actionCode = (Shared.touchables.get(r + g + b).getPointerCount() == 0) ? MotionEvent.ACTION_DOWN : actionCode;
			
			//Register a new pointer/node lock with the motion manager
			motionManager.registerLock(r + g + b, globalPointerID);
			
			// update that node with the initial event info
			motionManager.updatePointer(globalPointerID, me, actionCode);
		}
	}

}
