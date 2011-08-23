package com.droidgraph.input;


import android.view.MotionEvent;

import com.droidgraph.util.Shared;


public class DGGestureManager implements android.view.GestureDetector.OnGestureListener {
	
	private boolean DEBUG = false;
	

	@Override
	public boolean onDown(MotionEvent me) {
		if(DEBUG) {
			Shared.p("GestureListener - onDown()", me);
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent me1, MotionEvent me2, float arg2, float arg3) {
		if(DEBUG) {
//			Shared.p("GestureListener - onFling()", me1, me2, arg2, arg3);
			Shared.p("GestureListener - onFling()");
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent me) {
		if(DEBUG) {
			Shared.p("GestureListener - onLongPress()");
//			Shared.p("GestureListener - onLongPress()", me);
		}
	}

	@Override
	public boolean onScroll(MotionEvent me1, MotionEvent me2, float arg2,float arg3) {
		if(DEBUG) {
			Shared.p("GestureListener - onScroll()", me1, me2, arg2, arg3);
//			Shared.p("GestureListener - onScroll(), pointerCount:", me2.getPointerCount());
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent me) {
		if(DEBUG) {
//			Shared.p("GestureListener - onShowPress()", me);
			Shared.p("GestureListener - onShowPress()");
		}
	}

	@Override
	public boolean onSingleTapUp(MotionEvent me) {
		if(DEBUG) {
			Shared.p("GestureListener - onSingleTapUp()");
//			Shared.p("GestureListener - onSingleTapUp()", me);
		}
		return false;
	}

}
