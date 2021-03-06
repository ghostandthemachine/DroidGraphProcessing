package com.droidgraph.motionlistener;

import android.view.MotionEvent;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;
import com.droidgraph.util.Shared;

public abstract class ActionListener extends MotionListener {

	String TAG = "MotionListener";
	protected boolean DEBUG = false;
	
	protected boolean touchOne = false;
	
	protected boolean toggled = false;

	protected int numPointers = 0;
	// Pointer ids are between 0 and 20 (max touches). -1 == not registered,
	// otherwise the positino in the array
	// corresponds to the globalPointerID, the value is the new localID.
	protected int[] globalToLocalPointerID = { -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	
	protected DGMotionEvent[] lastEvents = new DGMotionEvent[20];
	
	public ActionListener(DGNode node) {
		super(node);
	}

	@Override
	public boolean handleActionEvent(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("HandleMotionEvent:", node, me);
		}
		int action = me.getAction();
		int unmaskedAction = action & MotionEvent.ACTION_MASK;

		// dispatch the event to the proper function for easy implementation
		switch (unmaskedAction) {

		case MotionEvent.ACTION_MOVE:
			updateEventID(me);
			if(lastEvents[me.getID()] != null) {
				me.setVelocity(lastEvents[me.getID()].getX() - me.getX(), lastEvents[me.getID()].getX() - me.getX());
			}
			actionMove(me);
			break;
			
		case MotionEvent.ACTION_DOWN:
			touchOne = true;
			addNewPointer(me);
			actionDown(me);
			break;

		case MotionEvent.ACTION_POINTER_DOWN:
			// if this is globally an ACTION_POINTER_DOWN, but new to this node
			// then reset the action to ACTION_DOWN
			if(me.getLocalID() == 0) {
				touchOne = true;
				me.setAction(MotionEvent.ACTION_DOWN);
				addNewPointer(me);
				actionDown(me);
			} else {
				addNewPointer(me);
				actionPointerDown(me);
			}
			break;

		case MotionEvent.ACTION_UP:
			updateEventID(me);

			// create the click call
			if (me.getLocalID() == 0) {
				touchOne = false;
				// call the click
				clicked(me);
			}	
			me.setVelocity(lastEvents[me.getID()].getX() - me.getX(), lastEvents[me.getID()].getX() - me.getX());
			actionUp(me);
			// remove the pointer
			removePointer(me);
//			flushPointerData();
			break;

		case MotionEvent.ACTION_POINTER_UP:
			updateEventID(me);
			if(me.getLocalID() == 0){ 
				me.setAction(MotionEvent.ACTION_UP);
				touchOne = false;
				// call the click
				clicked(me);
				me.setVelocity(lastEvents[me.getID()].getX() - me.getX(), lastEvents[me.getID()].getX() - me.getX());
				actionUp(me);
				removePointer(me);
			} else {
				me.setVelocity(lastEvents[me.getID()].getX() - me.getX(), lastEvents[me.getID()].getX() - me.getX());
				actionPointerUp(me);
				removePointer(me);
			}
			break;
		}
		lastEvents[me.getID()] = me;
		return true;
	}

	// add a new pointer by updating the counter and assigning a new local id
	private void addNewPointer(DGMotionEvent me) {
		if ((numPointers) <= me.getPointerCount()) {
			numPointers++;
			globalToLocalPointerID[me.getID()] = (numPointers - 1);
			me.setLocalID(globalToLocalPointerID[me.getID()]);
		}
		if(DEBUG) {
			Shared.p(node, "Add pointer with id:", me.getID(), "numPointers was: ", numPointers - 1, "now is: ", (numPointers), "Changing id array globalToLocalPointerID[me.getId()] = (numPointers - 1)", globalToLocalPointerID[me.getID()]);
		}
	
	}

	// remove the pointer and set the local id of it to -1 to signify it is free
	private void removePointer(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p(node, "Remove pointer id:", me.getID(), "numPointers was: ", numPointers, "now is: ", (numPointers - 1), "Changing event lid from: ", me.getLocalID(), "to: -1");
		}
		numPointers--;
		if (me.getLocalID() >= 0) {
			globalToLocalPointerID[me.getID()] = -1;
			me.setLocalID(-1);
		}
	}
	
	private void updateEventID(DGMotionEvent me) {
		me.setLocalID(globalToLocalPointerID[me.getID()]);
	}
	

	public DGNode getParent() {
		return node;
	}

	/*
	 * These are the ActionListener methods which should be @Overriden when
	 * creating new ActionListeners
	 */

	public boolean actionDown(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("ACTION_DOWN:", node, me);
		}
		touchOne = true;
		return true;
	}

	public boolean actionMove(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("ACTION_MOVE:", node, me);
		}
		return true;
	}

	public boolean actionUp(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("ACTION_UP:", node, me);
		}
		return true;
	}

	public boolean clicked(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("Clicked():", node, me);
		}
		if(toggled) {
			toggled = false;
			toggled(me);
		} else {
			toggled = true;
			toggled(me);
		}
		return true;
	}
	
	public boolean toggled(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("toggled():", node, me);
		}
		return true;
	}

	public boolean actionPointerDown(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("ACTION_POINTER_DOWN:", node, me);
		}
		return true;
	}

	public boolean actionPointerUp(DGMotionEvent me) {
		if(DEBUG) {
			Shared.p("ACTION_POINTER_UP:", node, me);
		}
		return true;
	}
}
