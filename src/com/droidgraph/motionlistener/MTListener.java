package com.droidgraph.motionlistener;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public class MTListener extends MotionListener {

	private boolean touchTwo = false;

	public MTListener(DGNode node) {
		super(node);
	}

	public boolean actionDown(DGMotionEvent me, int pid) {
		// Shared.p("action down ", pid);
		return super.actionDown(me, pid);
	}

	public boolean actionMove(DGMotionEvent me, int pid) {
		// Shared.p("action move ", pid);
		return super.actionMove(me, pid);
	}

	public boolean actionUp(DGMotionEvent me, int pid) {
		// Shared.p("action up", pid);
		return super.actionUp(me, pid);
	}

	public boolean clicked(DGMotionEvent me, int pid) {
		// Shared.p("action clicked ", pid);
		return super.clicked(me, pid);
	}

	public boolean actionPointerDown(DGMotionEvent me, int pid) {
		// Shared.p("action pointer down", pid);
		// To trigger the touchTwo clicked method
		touchTwo = (pid == 1) ? true : false;
		return super.actionPointerDown(me, pid);
	}

	public boolean actionPointerUp(DGMotionEvent me, int pid) {
		// Shared.p("action pointer up", pid);
		// touchTwo clicked
		if (touchTwo == true && pid == 1) {
			clicked(me, pid);
			touchTwo = false;
		}
		return super.actionPointerUp(me, pid);
	}

}