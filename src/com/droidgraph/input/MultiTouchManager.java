package com.droidgraph.input;

import java.util.HashMap;

import android.view.MotionEvent;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.event.DGMotionPackage;
import com.droidgraph.picking.Pick;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;
import com.droidgraph.util.Shared;

public class MultiTouchManager {
	
	private boolean DEBUG = false;

	private DGNode selectedNode;

	private HashMap<Integer, DGNode> pointerToNodeMap = new HashMap<Integer, DGNode>();

	private UnPick unPickTask;

	/** The max number of touch points that can be present on the screen at once */
	public static final int MAX_TOUCH_POINTS = 20;

	private DGScene scene;

	private int numPointers;

	// ----------------------------------------------------------------------------------------------------------------------

	/** Constructor that sets handleSingleTouchEvents to true */
	public MultiTouchManager(DGScene scene) {
		this.scene = scene;
		unPickTask = new UnPick(this);
		Shared.setMotionManager(this);
		Shared.setMap(pointerToNodeMap);
	}

	public DGNode getFirstEventBlocker(DGNode node) {
		if (node != null) {
			if (node.isEventBlocker()) {
				return node;
			} else if (node.getParent() != null) {
				getFirstEventBlocker(((DGNode) node.getParent()));
			} else {
				return null;
			}
		}
		return null;
	}

	public boolean onTouchEvent(MotionEvent event) {
		processEvent(event);
		return true;
	}
	
	public int getNumPointers() {
		return numPointers;
	}

	void processEvent(MotionEvent ev) {
		numPointers = ev.getPointerCount();
		final DGMotionPackage pack = new DGMotionPackage(ev);
		final int historySize = ev.getHistorySize();
		final int pointerCount = ev.getPointerCount();
		for (int h = 0; h < historySize; h++) {
			for (int p = 0; p < pointerCount; p++) {
				int action = 2;
				if (ev.getActionIndex() == p) {
					action = ev.getAction();
				}
				pack.add(new DGMotionEvent(pack, ev.getPointerId(p), action, ev
						.getHistoricalX(p, h), ev.getHistoricalY(p, h), ev
						.getHistoricalPressure(p, h)));

			}
			// dispatch the historical events
			if(DEBUG) {
			 	Shared.p("FisrtProcess (H)", pack);
			}
			dispatchEvents(pack);
			pack.clear();
		}

		for (int p = 0; p < pointerCount; p++) {
			int action = 2;
			if (ev.getActionIndex() == p) {
				action = ev.getAction();
			}
			pack.add(new DGMotionEvent(pack, ev.getPointerId(p), action, ev
					.getX(p), ev.getY(p), ev.getPressure(p)));
		}
		// dispatch the current events
		if(DEBUG) {
			Shared.p("FisrtProcess", pack);
		}
		dispatchEvents(pack);
	}

	private void dispatchEvents(DGMotionPackage pack) {
		DGMotionEvent[] events = pack.toArray();
		for (DGMotionEvent ev : events) {
			// handle move updates
			if (ev.getAction() == MotionEvent.ACTION_MOVE) {
				if (pointerToNodeMap.containsKey(ev.getId())) {
					pointerToNodeMap.get(ev.getId()).handleMotionEvent(ev);
				}
			}
			if (ev.getId() == ev.getPackage().getActionIndex() || pack.getPointerCount() == 1) {
				if (ev.getActionMasked() == MotionEvent.ACTION_DOWN
						|| ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
					handlePointerDown(ev);
				} else if (ev.getAction() == MotionEvent.ACTION_UP
						|| ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
					if(DEBUG) {
						Shared.p(ev);
					}
					handlePointerUp(ev, false);
				}
			}
		}
	}

	public boolean handlePointerDown(DGMotionEvent me) {
		scene.quePick(new Pick(this, me));
		return true;
	}

	// This is called by Pick when a color is selected which is with in the
	// possible range of keys to call a node in the scene
	public void handlePickSelection(DGMotionEvent dgme, int id, int pid) {
		// get the node from the map based on the colorID
		selectedNode = scene.getNodeByID(id);
		// if the node is not an event blocker (has a motionListener) then
		// recursivley look up the tree for the next parent that is
		selectedNode = getFirstEventBlocker(selectedNode);
//		 Shared.p("Selected node:", selectedNode);
		if (selectedNode != null) {
			pointerToNodeMap.put(pid, selectedNode);
//			Shared.p("Adding pointer:", pid, "with node:", selectedNode);
			selectedNode.handleMotionEvent(dgme);
		}
	}

	public void handlePointerUp(DGMotionEvent me, boolean alreadyChecked) {
		if(DEBUG) {
			Shared.pMap(pointerToNodeMap);
		}
		
		if (pointerToNodeMap.containsKey(me.getId())) {
			// Send on the Action Up event to the node then remove it from the
			// map
			pointerToNodeMap.get(me.getId()).handleMotionEvent(me);
			if(DEBUG) {
				Shared.p("Removing pointer:", me.getId(), "with node:",
					pointerToNodeMap.get(me.getId()));
					pointerToNodeMap.remove(me.getId());
			}
		} else {
			if(DEBUG){
				Shared.p("Can't find", me.getId(), "in the pointerToNodeMap, map size:");
			}
	
			
			if (!alreadyChecked) {
				unPickTask.updateUnPick(me, pointerToNodeMap);
				// Post an unpick check to see if this pointer up is actually
				// attached to a node
				// but that just hadn't happend till the the next draw finished
				// (prevents missing pointer ups)
				Shared.pApplet.runOnUiThread(unPickTask);
			} else {
				if(DEBUG) {
					Shared.p("Pointer up did not find a node in the map, this is a problem. Not queing another UnPick");
				}
			}
		}
		
		// clear the pointer to node map if this is the last touch up
		if(me.getPointerCount() == 1 ) {
			pointerToNodeMap.clear();
		}

	}
	
	public HashMap<Integer, DGNode> getPointerToNodeMap() {
		return pointerToNodeMap;
	}

}
