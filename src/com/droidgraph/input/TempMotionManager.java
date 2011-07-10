package com.droidgraph.input;

import java.util.ArrayList;

import android.view.MotionEvent;

import com.droidgraph.event.DGEventParsed;
import com.droidgraph.picking.Pick;
import com.droidgraph.picking.UnPick;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;
import com.droidgraph.util.Shared;

public class TempMotionManager extends MotionManager {

	String TAG = "MotionManager";

	// Array to hold pointer locks and managing dispatching to correct DGNode
	// (currently up to ten touches).
	private DGMotionPointer[] pointers = new DGMotionPointer[10];

	private DGScene scene;

	public final int POINTER_DOWN = 0;
	public final int POINTER_UP = 1;
	public final int MOVE = 2;

	private int numPointers = 0;

	public TempMotionManager(DGScene scene) {
		super(scene);
		this.scene = scene;
		// init Event managers with id
		for (int i = 0; i < 10; i++) {
			pointers[i] = new DGMotionPointer(this, i);
		}
	}

	public boolean processMotionEvent(MotionEvent me) {
		dumpEvent(me);
		return true;
	}

	// Take a list as a param which can contain multiple events for multiple
	// nodes
	public boolean dispatchEvent(DGEventParsed ep) {

		if (ep != null) {

			int actionCode = ep.action;

			switch (actionCode) {

			case MotionEvent.ACTION_MOVE:
				if (pointers[ep.globalPointerID].locked()) {
					pointers[ep.globalPointerID].update(ep.me, actionCode);
				}
				break;

			case MotionEvent.ACTION_DOWN:
				// Only an action down if this is the first pointer

				scene.quePick(new Pick(this, ep.me, ep.globalPointerID,
						actionCode));
				numPointers++;
				break;

			case MotionEvent.ACTION_POINTER_DOWN:
				scene.quePick(new Pick(this, ep.me, ep.globalPointerID,
						actionCode));
				numPointers++;
				break;

			case MotionEvent.ACTION_POINTER_UP:
				if (pointers[ep.globalPointerID].locked()) {
					pointers[ep.globalPointerID].update(ep.me, actionCode);
				}
				if (scene.getNumPicks() > 0) {
					// Que an ACTION_UP_POINTER UnPick
					scene.quePick(new UnPick(this, pointers[ep.globalPointerID]));
					if (numPointers > 0) {
						numPointers--;
					}
				}
				break;

			case MotionEvent.ACTION_UP:
				int uid = ep.globalPointerID;
				if (pointers[uid].locked()) {
					pointers[uid].update(ep.me, actionCode);
				}
				// Que an ACTION_UP_POINTER UnPick
				scene.quePick(new UnPick(this, pointers[ep.globalPointerID]));

				if (numPointers > 0) {
					numPointers--;
				}
				break;
			}
		}
		return true;
	}

	// Need to clean this up and get rid of the string building...

	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
		// String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
		// "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		// StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		// sb.append("event ACTION_").append(names[actionCode]);

		int pid = 0;

		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			pid = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		}

		for (int i = 0; i < event.getPointerCount(); i++) {

			pid = event.getPointerId(i);

			int tempAction = (i == event.getPointerCount() - 1) ? actionCode
					: MotionEvent.ACTION_MOVE;

			// Dispatch the events
			dispatchEvent(new DGEventParsed(tempAction, pid, event));
		}

		// scene.queMotionEvents(events);
		if (actionCode == MotionEvent.ACTION_UP) {
			clearPointers();
		}
	}

	public boolean registerLock(int nid, int pid) {
		// Shared.p(this, "register lock, nodeID =", nid, "pointer id =", pid);
		DGNode node = Shared.touchables.get(nid);

		// Register a lock on the this pointer by passing a node to lock to
		// and the local node pointer id (it might be pointer 3 globally
		// but pointer 1 locally etc)
		pointers[pid].lock(node, node.getPointerCount());

		return true;
	}

	public void updatePointer(int id, MotionEvent me, int action) {
		// Shared.p(this, "id =", id, "motionevent (me), action = ", action);
		// Update the pointer with a motion event and an action.
		// Use this action param instead of the MotionEvent action since
		// it might have been modified
		pointers[id].update(me, action);

	}

	public int getNumPointers() {
		return numPointers;
	}

	// A way to clear all flush pointer data and reset them
	// Right now a way to flush buggs in pointer up issues. Shouldn't be needed
	private void clearPointers() {
		numPointers = 0;
		for (DGMotionPointer p : pointers) {
			if (p != null) {
				p.clear();
			}
		}
		for (DGNode node : Shared.touchables) {
			node.clearPointers();
		}
	}

	public String s() {
		return "TempMotionManager : " + this;
	}
}
