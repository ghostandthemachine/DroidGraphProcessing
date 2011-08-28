package com.droidgraph.input;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.event.DGMotionPackage;
import com.droidgraph.picking.Pick;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;
import com.droidgraph.util.Shared;

public class MultiTouchManager {

	private String TAG = "MultiTouchManager - ";

	private boolean DEBUG = false;
	private boolean DEBUG_PICK = false;
	private boolean DEBUG_EVENT_BLOCKING = false;
	private boolean DEBUG_UNPICK = false;

	private DGNode selectedNode;

	private HashMap<Integer, DGNode> pointerToNodeMap = new HashMap<Integer, DGNode>();

	private HashMap<Integer, ArrayList<DGMotionEvent>> pointerHistoryMap = new HashMap<Integer, ArrayList<DGMotionEvent>>();
	private boolean[] pointerPickFlags = new boolean[20];
	

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
		
		for(int i = 0; i < 20; i++) {
			pointerPickFlags[i] = false;
		}
	}


	public boolean onTouchEvent(MotionEvent event) {
		processEvent(event);
		return true;
	}

	public int getPointerCount() {
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
				// create the new event
				DGMotionEvent event = new DGMotionEvent(pack, ev.getPointerId(p),
						action, ev.getHistoricalX(p, h),
						ev.getHistoricalY(p, h), ev.getHistoricalPressure(p, h));
				if (DEBUG || DEBUG_UNPICK || DEBUG_PICK) {
					Shared.p(TAG, "processEvenet(Historical)", event);
				}
				// dispatch the historical events
				dispatchEvent(event);
			}

		}

		for (int p = 0; p < pointerCount; p++) {
			int action = 2;
			if (ev.getActionIndex() == p) {
				action = ev.getAction();
			}
			// create the new event
			DGMotionEvent event = new DGMotionEvent(pack, ev.getPointerId(p), action,
					ev.getX(p), ev.getY(p), ev.getPressure(p));
			if (DEBUG || DEBUG_UNPICK || DEBUG_PICK) {
				Shared.p(TAG, "processEvent()", event);
			}
			// dispatch the current events
			dispatchEvent(event);
		}
	}

	private void dispatchEvent(DGMotionEvent me) {
		if(DEBUG || DEBUG_PICK || DEBUG_UNPICK) {
			Shared.p(TAG, "dispatchEvent(), begin dispatch with event:", me);
		}
		
		int id = me.getID();

		// if this event is from a pointer which is currently attached, update
		// the attachedNode variable
		// should be done in handledPickSelection now
		 DGNode attachedNode = pointerToNodeMap.get(me.getID());
			if(DEBUG || DEBUG_PICK || DEBUG_UNPICK) {
				Shared.p(TAG, "dispatchEvent(), pointerToNodeMap for this id:", attachedNode, "map size:", pointerToNodeMap.size());
			}
		 if (attachedNode != null) {
			 me.attachNode(attachedNode);
		 }

		// in this case the pointer is currently qued for a pick and we need
		// to store the pointer data to pass on to an attached node if the pick
		// finds one
		if (pointerPickFlags[id]) {
			if(DEBUG || DEBUG_PICK || DEBUG_UNPICK) {
				Shared.p(TAG, "dispatchEvent(), pointerPickFlags contains pointer id:", id, me);
			}
			
			ArrayList<DGMotionEvent> history = pointerHistoryMap.get(id);
	
			history.add(me);
		} else { // other wise it is a new or already attached pointer

			// handle move updates
			if (me.getAction() == MotionEvent.ACTION_MOVE) {
				if (attachedNode != null) {
					attachedNode.handleMotionEvent(me);
				}
			}
			if (id == me.getPackage().getActionIndex()
					|| me.getPointerCount() == 1) {

				// with the action down we need to store pointer data until the
				// pick
				// is run
				// (incase a pointer up happens before the pick in which case we
				// would loose it)
				if (me.getActionMasked() == MotionEvent.ACTION_DOWN || me.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
					
					// flag this pointer as alive but not picked yet
					pointerPickFlags[id] = true;
					handlePointerDown(me);

					// flag this pointer to track it
					ArrayList<DGMotionEvent> history = new ArrayList<DGMotionEvent>();
					history.add(me);
					pointerHistoryMap.put(id, history);

					if (DEBUG) {
						Shared.setDebug(true);
					}
				} else if (me.getAction() == MotionEvent.ACTION_UP
						|| me.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
					if (DEBUG || DEBUG_UNPICK || DEBUG_PICK) {
						Shared.p(TAG, me);
					}
					if (DEBUG) {
						Shared.setDebug(false);
					}
					
					if(!pointerPickFlags[id]); {
						handlePointerUp(me, false);
					}
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
	public void handlePickSelection(DGMotionEvent me, int id, int pid) {
		// get the node from the map based on the colorID
		selectedNode = scene.getNodeByID(id);
		
		// set the flag for this as false
		pointerPickFlags[pid] = false;
		
		if (DEBUG_PICK) {
			Shared.p(TAG, "handlePickSelection(), selectedNode", selectedNode);
		}
		// if the node is not an event blocker (has a motionListener) then
		// Recursively look up the tree for the next parent that is
		selectedNode = getFirstEventBlocker(selectedNode);
		
		if (DEBUG_PICK) {
			Shared.p("MultiTouchManager - handlePickSelection(), selectedNode after getFirstEventBlocker()", selectedNode);
		}
		
		if (selectedNode != null) {
			
			pointerToNodeMap.put(pid, selectedNode);
			me.attachNode(selectedNode);
			
			if (DEBUG_PICK) {
				Shared.p(TAG,"handlePickSelection(), attach a pointer to a node in the map, send the event. PID:",pid, "node:", selectedNode);
			}
			
			// send the initial down event
			selectedNode.handleMotionEvent(me);

			// now also pass the selected node the historical pointer data (if
			// there is any)
			ArrayList<DGMotionEvent> map = pointerHistoryMap.get(me.getID());
			if(map != null) {
				DGMotionEvent[] history = new DGMotionEvent[map.size()];
				history = pointerHistoryMap.get(me.getID()).toArray(history);
				int i = 0;
				for (DGMotionEvent event : history) {
					if(DEBUG || DEBUG_PICK || DEBUG_UNPICK) {
						Shared.p(TAG, "handlePickSelection, event history size:", history.length, "iteration number:", i, "event action:", event.getAction(), "event actionMasked:", event.getActionMasked());
					}
					if(event.getAction() == MotionEvent.ACTION_MOVE) {
						selectedNode.handleMotionEvent(event);
					}else if (event.getAction() == 1 || (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
						if(DEBUG || DEBUG_UNPICK) {
								Shared.p(TAG, "handlePickSelection(), run historic events, handle pointer up, current event:", event, "history size:", history.length);
						}
						handlePointerUp(event, false);
						} 
					i++;
				}
				// remove this event history from the map
				pointerHistoryMap.remove(me.getID());
			}
		}
	}


	public DGNode getFirstEventBlocker(DGNode node) {
		if (DEBUG_EVENT_BLOCKING) {
			Shared.p("MultiTouchManager, getFirstEventBlocker(", node, ")",
					"parent =", node.getParent());
		}
		if (node != null) {
			if (DEBUG_EVENT_BLOCKING) {
				Shared.p(
						"MultiTouchManager, getFirstEventBlocker(), isEventBlocker() =",
						node.isEventBlocker());
			}
			if (node.isEventBlocker()) {
				if (DEBUG_EVENT_BLOCKING) {
					Shared.p(
							"MultiTouchManager, getFirstEventBlocker(), isEventBlocker(). Node:",
							node, "is an event blocker");
				}
				return node;
			} else if (node.getParent() != null) {
				if (DEBUG_EVENT_BLOCKING) {
					Shared.p(
							"MultiTouchManager, getFirstEventBlocker(), isEventBlocker(), calling getFirstEventBlocker on parent",
							node.getParent());
				}
				return getFirstEventBlocker(((DGNode) node.getParent()));
			} else {
				if (DEBUG_EVENT_BLOCKING) {
					Shared.p("MultiTouchManager, getFirstEventBlocker(), is not eventBlocker and parent == null");
				}
				return null;
			}
		}
		if (DEBUG_EVENT_BLOCKING) {
			Shared.p("MultiTouchManager, getFirstEventBlocker(), node == null");
		}
		return node;
	}
	
	public void handlePointerUp(DGMotionEvent me, boolean alreadyChecked) {
		int id = me.getID();
		DGNode node = pointerToNodeMap.get(id);
		
		if (DEBUG || DEBUG_UNPICK) {
			Shared.pMap(TAG + "handlePointerUp():", pointerToNodeMap);
		}

		// check to make sure that a pointer up isn't going to come from the history map
		if(pointerPickFlags[me.getID()]) {
			if(DEBUG || DEBUG_UNPICK) {
				Shared.p(TAG, "handlePointerUp(), pointerToHistoryMap contains pointer id:", id);
			}
		} else {
			if (node != null) {
				if (DEBUG || DEBUG_UNPICK) {
					Shared.p("Removing pointer:", me.getID(), "with node:",	node);
				}
				
				// remove the node from the map
				pointerToNodeMap.remove(id);
				
				if(pointerPickFlags[id] == true) {
					if (DEBUG || DEBUG_UNPICK) {
						Shared.p(TAG, "handlePointerUp(), pointerFlagsPick[id]:", id, "is true with a pointer up, setting false");
					}
					pointerPickFlags[id] = false;
				}
				
				// then handle the last event
				node.handleMotionEvent(me);
				
			} else {
				if (DEBUG || DEBUG_UNPICK) {
					Shared.p("Can't find", id,	"in the pointerToNodeMap, map size:");
				}
	
				if (!alreadyChecked) {
					unPickTask.updateUnPick(me);
					// Post an unpick check to see if this pointer up is actually
					// attached to a node
					// but that just hadn't happend till the the next draw finished
					// (prevents missing pointer ups)
					// Shared.pApplet.runOnUiThread(unPickTask);
					scene.queUnPick(unPickTask);
				} else {
					if (DEBUG || DEBUG_UNPICK) {
						Shared.p("Pointer up did not find a node in the map, this is a problem. Not queing another UnPick");
					}
				}
			}
			
//			 clear the pointer to node map if this is the last touch up
			if (getPointerCount() == 1) {
				if(DEBUG || DEBUG_UNPICK || DEBUG_PICK) {
					Shared.p(TAG, "handlePointerUP(), just lifted the last pointer, flushing out maps...");
				}
				pointerToNodeMap.clear();
				pointerHistoryMap.clear();
				for(int i = 0; i < 20; i++) {
					pointerPickFlags[i] = false;
				}
			}
			
			if(DEBUG || DEBUG_UNPICK || DEBUG_PICK) {
				Shared.p(TAG, "handlePointerUP(), finished pointer up, me.getPointerCount():", getPointerCount());
			}
		}
	}

	public HashMap<Integer, DGNode> getPointerToNodeMap() {
		return pointerToNodeMap;
	}

	public void setGestureDetector(GestureDetector gestureDetector) {

	}

}

