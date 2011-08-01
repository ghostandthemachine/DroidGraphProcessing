package com.droidgraph.input;

import java.util.ArrayList;

import android.view.MotionEvent;

import com.droidgraph.event.DGEventParsed;
import com.droidgraph.picking.Pick;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;
import com.droidgraph.util.Shared;

public class MotionManager {

	String TAG = "MotionManager";

	// Array to hold pointer locks and managing dispatching to correct DGNode
	// (currently up to ten touches).
	private DGMotionPointer[] pointers = new DGMotionPointer[10];

	private DGScene scene;

	public final int POINTER_DOWN = 0;
	public final int POINTER_UP = 1;
	public final int MOVE = 2;

	private int numPointers = 0;

	public MotionManager(DGScene scene) {

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
	public boolean dispatchEvent(ArrayList<DGEventParsed> eps) {

		int i = 1;
		int actionCode = 0;

		for (DGEventParsed ep : eps) {

			actionCode = ep.action;

			// Shared.p("MotionManager, dispatchMotionEvent", ep.pid);

			switch (actionCode) {

			case MotionEvent.ACTION_DOWN:
				// Only an action down if this is the first pointer
				Shared.p("MotionMananger, motionevent == down", ep.globalPointerID);
				scene.quePick(new Pick(this, ep.me, ep.globalPointerID, actionCode));
				numPointers++;
				break;

			case MotionEvent.ACTION_POINTER_DOWN:
				int did = ep.globalPointerID;
				if(pointers[i - 1].locked()) {
					did = i	;
				}
				
				Shared.p("MotionMananger, motionevent == pointer down", did);
				
				scene.quePick(new Pick(this, ep.me, did, actionCode));
				numPointers++;
				break;

			case MotionEvent.ACTION_POINTER_UP:
//				Shared.p("MotionMananger, motionevent == pointer up", ep.globalPointerID);
				if (pointers[ep.globalPointerID].locked()) {
					pointers[ep.globalPointerID].update(ep.me, actionCode);
					pointers[ep.globalPointerID].unlock();
					numPointers--;
				}
				// }
				break;

			case MotionEvent.ACTION_UP:
				Shared.p("MotionMananger, motionevent == up", ep.globalPointerID);
				int uid = ep.globalPointerID;
				if (pointers[0].locked()) {
					uid = 1;
				}
				if (pointers[uid].locked()) {
					pointers[uid].update(ep.me, actionCode);
					pointers[uid].unlock();
					numPointers--;
				}
				break;

			case MotionEvent.ACTION_MOVE:
//				Shared.p("MotionMananger, motionevent == move", ep.globalPointerID);
				if (pointers[ep.globalPointerID].locked()) {
					pointers[ep.globalPointerID].update(ep.me, actionCode);
				}
				break;

			}

			i++;
		}

		return true;
	}

	// Need to clean this up and get rid of the string building...

	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);

		int pid = 0;

		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
			pid = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		}
		sb.append("[");

		ArrayList<DGEventParsed> events = new ArrayList<DGEventParsed>();

		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));

			pid = event.getPointerId(i);

			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			sb.append(", action code = ").append(actionCode);
			if (i + 1 < event.getPointerCount())
				sb.append(";");

			DGEventParsed ep = new DGEventParsed(actionCode, pid, event);
			actionCode = action & MotionEvent.ACTION_MASK;

			switch (i) {
			case 0:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;
			case 1:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN && numPointers > 2) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP && numPointers > 2) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;

			case 2:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN && numPointers > 3) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP && numPointers > 3) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;

			case 3:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN && numPointers > 4) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP && numPointers > 4) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;

			case 4:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN && numPointers > 5) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP && numPointers > 5) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;

			case 5:
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_DOWN && numPointers > 6) ? MotionEvent.ACTION_MOVE
						: actionCode;
				actionCode = (actionCode == MotionEvent.ACTION_POINTER_UP && numPointers > 6) ? MotionEvent.ACTION_MOVE
						: actionCode;
				break;

			}
			events.add(ep);
		}
		sb.append("]");
		// Log.d(TAG, sb.toString());

		// Dispatch the events
		dispatchEvent(events);
	}

	public boolean registerLock(int nid, int pid) {
		DGNode node = Shared.touchables.get(nid);
		pointers[pid].lock(node, node.addPointer(pid));
		return true;
	}

	public void updatePointer(int id, MotionEvent me, int action) {
		pointers[id].update(me, action);

	}

	public int getNumPointers() {
		return numPointers;
	}

	public int getNumPicksQued() {
		return scene.getNumPicks();
	}

}
