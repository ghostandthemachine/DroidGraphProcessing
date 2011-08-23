package com.droidgraph.motionlistener;

import android.view.MotionEvent;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public abstract class MotionListener{
	
	protected DGNode node;
	protected boolean pointerShift = false;
	protected int pointerShiftValue = 1;
	
	public MotionListener(DGNode node) {
		this.node = node;
	}

	public boolean handleMotionEvent(DGMotionEvent me) {
		// Shift the pointer id to maintain our local ids
		if(pointerShift) {
			me.setLocalID(pointerShiftValue);
		}
		handleActionEvent(me);
		if((me.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && me.getID() == 0) {
			pointerShift = false;
		}
		return true;
	}
	
	public boolean handleActionEvent(DGMotionEvent me) {
		return true;
	}

	public void setPointerShift(int id) {
		pointerShift = true;
		pointerShiftValue = id;
	}

}
