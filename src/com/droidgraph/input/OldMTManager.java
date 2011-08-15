//package com.droidgraph.input;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//
//import android.app.Activity;
//import android.os.Handler;
//import android.util.Log;
//import android.view.MotionEvent;
//
//import com.droidgraph.event.DGMotionEvent;
//import com.droidgraph.picking.Pick;
//import com.droidgraph.scene.DGNode;
//import com.droidgraph.scene.DGScene;
//import com.droidgraph.util.Shared;
//
///**
// * A class that simplifies the implementation of multitouch in applications.
// * Subclass this and read the fields here as needed in subclasses.
// * 
// * @author Luke Hutchison
// */
//public class MultiTouchManager {
//
//	private DGNode selectedNode;
//	
//	private HashMap<Integer, DGNode> pointerToNodeMap = new HashMap<Integer, DGNode>();
//	
//	private long startTime = 0l;
//
//	private UnPick unPickTask;
//
//	/**
//	 * Time in ms required after a change in event status (e.g. putting down or
//	 * lifting off the second finger) before events actually do anything --
//	 * helps eliminate noisy jumps that happen on change of status
//	 */
//	private static final long EVENT_SETTLE_TIME_INTERVAL = 20;
//
//	/**
//	 * The biggest possible abs val of the change in x or y between multitouch
//	 * events (larger dx/dy events are ignored) -- helps eliminate jumps in
//	 * pointer position on finger 2 up/down.
//	 */
//	private static final float MAX_MULTITOUCH_POS_JUMP_SIZE = 30.0f;
//
//	/**
//	 * The biggest possible abs val of the change in multitouchWidth or
//	 * multitouchHeight between multitouch events (larger-jump events are
//	 * ignored) -- helps eliminate jumps in pointer position on finger 2
//	 * up/down.
//	 */
//	private static final float MAX_MULTITOUCH_DIM_JUMP_SIZE = 40.0f;
//
//	/**
//	 * The smallest possible distance between multitouch points (used to avoid
//	 * div-by-zero errors and display glitches)
//	 */
//	private static final float MIN_MULTITOUCH_SEPARATION = 30.0f;
//
//	/** The max number of touch points that can be present on the screen at once */
//	public static final int MAX_TOUCH_POINTS = 20;
//
//	/** Generate tons of log entries for debugging */
//	public static final boolean DEBUG = false;
//
//	// ----------------------------------------------------------------------------------------------------------------------
//
//	/** The current touch point */
//	private PointInfo mCurrPt;
//
//	/** The previous touch point */
//	private PointInfo mPrevPt;
//
//	/** Fields extracted from mCurrPt */
//	private float mCurrPtX, mCurrPtY, mCurrPtDiam, mCurrPtWidth, mCurrPtHeight,
//			mCurrPtAng;
//
//	/**
//	 * Extract fields from mCurrPt, respecting the update* fields of mCurrPt.
//	 * This just avoids code duplication. I hate that Java doesn't support
//	 * higher-order functions, tuples or multiple return values from functions.
//	 */
//	// private void extractCurrPtInfo() {
//	// // Get new drag/pinch params. Only read multitouch fields that are
//	// // needed,
//	// // to avoid unnecessary computation (diameter and angle are expensive
//	// // operations).
//	// mCurrPtX = mCurrPt.getX();
//	// mCurrPtY = mCurrPt.getY();
//	// mCurrPtDiam = Math.max(
//	// MIN_MULTITOUCH_SEPARATION * .71f,
//	// !mCurrXform.updateScale ? 0.0f : mCurrPt
//	// .getMultiTouchDiameter());
//	// mCurrPtWidth = Math
//	// .max(MIN_MULTITOUCH_SEPARATION,
//	// !mCurrXform.updateScaleXY ? 0.0f : mCurrPt
//	// .getMultiTouchWidth());
//	// mCurrPtHeight = Math.max(
//	// MIN_MULTITOUCH_SEPARATION,
//	// !mCurrXform.updateScaleXY ? 0.0f : mCurrPt
//	// .getMultiTouchHeight());
//	// mCurrPtAng = !mCurrXform.updateAngle ? 0.0f : mCurrPt
//	// .getMultiTouchAngle();
//	// }
//
//	// ----------------------------------------------------------------------------------------------------------------------
//
//	/**
//	 * Whether to handle single-touch events/drags before multi-touch is
//	 * initiated or not; if not, they are handled by subclasses
//	 */
//	private boolean handleSingleTouchEvents;
//
//	/** The object being dragged/stretched */
//	private DGNode selectedObject = null;
//
//	/** Current position and scale of the dragged object */
//	private PositionAndScale mCurrXform = new PositionAndScale();
//
//	/**
//	 * Drag/pinch start time and time to ignore spurious events until (to smooth
//	 * over event noise)
//	 */
//	private long mSettleStartTime, mSettleEndTime;
//
//	/** Conversion from object coords to screen coords */
//	private float startPosX, startPosY;
//
//	/**
//	 * Conversion between scale and width, and object angle and start pinch
//	 * angle
//	 */
//	private float startScaleOverPinchDiam, startAngleMinusPinchAngle;
//
//	/** Conversion between X scale and width, and Y scale and height */
//	private float startScaleXOverPinchWidth, startScaleYOverPinchHeight;
//
//	// ----------------------------------------------------------------------------------------------------------------------
//
//	/** No touch points down. */
//	private static final int MODE_NOTHING = 0;
//
//	/** One touch point down, dragging an object. */
//	private static final int MODE_DRAG = 1;
//
//	/**
//	 * Two or more touch points down, stretching/rotating an object using the
//	 * first two touch points.
//	 */
//	private static final int MODE_PINCH = 2;
//
//	/** Current drag mode */
//	private int mMode = MODE_NOTHING;
//
//	/** The scene */
//	private DGScene scene;
//
//	private int lastPointerCount = 0;
//
//	private int[] lastPointerIDs = {0};
//
//	private int lastUnusedPointer;
//
//	private boolean zeroUp;
//
//	private int lastActionPoint;
//
//	private boolean zeroCase;
//
//
//
//	// ----------------------------------------------------------------------------------------------------------------------
//
//	/** Constructor that sets handleSingleTouchEvents to true */
//	public MultiTouchManager(DGScene scene) {
//		this(scene, true);
//		this.scene = scene;
//	}
//
//	/** Full constructor */
//	public MultiTouchManager(DGScene scene, boolean handleSingleTouchEvents) {
//		this.mCurrPt = new PointInfo();
//		this.mPrevPt = new PointInfo();
//		this.handleSingleTouchEvents = handleSingleTouchEvents;
//		this.scene = scene;
//		this.unPickTask = new UnPick(this);
//	}
//
//	// ------------------------------------------------------------------------------------
//
//	/**
//	 * Whether to handle single-touch events/drags before multi-touch is
//	 * initiated or not; if not, they are handled by subclasses. Default: true
//	 */
//	protected void setHandleSingleTouchEvents(boolean handleSingleTouchEvents) {
//		this.handleSingleTouchEvents = handleSingleTouchEvents;
//	}
//
//	/**
//	 * Whether to handle single-touch events/drags before multi-touch is
//	 * initiated or not; if not, they are handled by subclasses. Default: true
//	 */
//	protected boolean getHandleSingleTouchEvents() {
//		return handleSingleTouchEvents;
//	}
//
//	// ------------------------------------------------------------------------------------
//
//	public static final boolean multiTouchSupported;
//	private static Method m_getPointerCount;
//	private static Method m_getPointerId;
//	private static Method m_getPressure;
//	private static Method m_getHistoricalX;
//	private static Method m_getHistoricalY;
//	private static Method m_getHistoricalPressure;
//	private static Method m_getX;
//	private static Method m_getY;
//	private static int ACTION_POINTER_UP = 6;
//	private static int ACTION_POINTER_INDEX_SHIFT = 8;
//
//	static {
//		boolean succeeded = false;
//		try {
//			// Android 2.0.1 stuff:
//			m_getPointerCount = MotionEvent.class.getMethod("getPointerCount");
//			m_getPointerId = MotionEvent.class.getMethod("getPointerId",
//					Integer.TYPE);
//			m_getPressure = MotionEvent.class.getMethod("getPressure",
//					Integer.TYPE);
//			m_getHistoricalX = MotionEvent.class.getMethod("getHistoricalX",
//					Integer.TYPE, Integer.TYPE);
//			m_getHistoricalY = MotionEvent.class.getMethod("getHistoricalY",
//					Integer.TYPE, Integer.TYPE);
//			m_getHistoricalPressure = MotionEvent.class.getMethod(
//					"getHistoricalPressure", Integer.TYPE, Integer.TYPE);
//			m_getX = MotionEvent.class.getMethod("getX", Integer.TYPE);
//			m_getY = MotionEvent.class.getMethod("getY", Integer.TYPE);
//			succeeded = true;
//		} catch (Exception e) {
//			Log.e("MultiTouchController", "static initializer failed", e);
//		}
//		multiTouchSupported = succeeded;
//		if (multiTouchSupported) {
//			// Android 2.2+ stuff (the original Android 2.2 consts are declared
//			// above,
//			// and these actions aren't used previous to Android 2.2):
//			try {
//				ACTION_POINTER_UP = MotionEvent.class.getField(
//						"ACTION_POINTER_UP").getInt(null);
//				ACTION_POINTER_INDEX_SHIFT = MotionEvent.class.getField(
//						"ACTION_POINTER_INDEX_SHIFT").getInt(null);
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	// ------------------------------------------------------------------------------------
//
//	private static final float[] xVals = new float[MAX_TOUCH_POINTS];
//	private static final float[] yVals = new float[MAX_TOUCH_POINTS];
//	private static final float[] pressureVals = new float[MAX_TOUCH_POINTS];
//	private static final int[] pointerIds = new int[MAX_TOUCH_POINTS];
//	private static final int[] actions = new int[MAX_TOUCH_POINTS];
//
//
//	public DGNode getFirstEventBlocker(DGNode node) {
//		if (node != null) {
//			if (node.isEventBlocker()) {
//				return node;
//			} else if (node.getParent() != null) {
//				getFirstEventBlocker(((DGNode) node.getParent()));
//			} else {
//				return null;
//			}
//		}
//		return null;
//	}
//	
//	public boolean onTouchEvent(MotionEvent event) {
//		printSamples(event);
//		return true;
//	}
//	
//	/** Show an event in the LogCat view, for debugging */
//	private void dumpEvent(MotionEvent event) {
//	   String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
//	      "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
//	   StringBuilder sb = new StringBuilder();
//	   int action = event.getAction();
//	   int actionCode = action & MotionEvent.ACTION_MASK;
//	   sb.append("event ACTION_" ).append(names[actionCode]);
//	   if (actionCode == MotionEvent.ACTION_POINTER_DOWN
//	         || actionCode == MotionEvent.ACTION_POINTER_UP) {
//	      sb.append("(pid " ).append(
//	      action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
//	      sb.append(")" );
//	   }
//	   sb.append("[" );
//	   for (int i = 0; i < event.getPointerCount(); i++) {
//	      sb.append("#" ).append(i);
//	      sb.append("(pid " ).append(event.getPointerId(i));
//	      sb.append(")=" ).append((int) event.getX(i));
//	      sb.append("," ).append((int) event.getY(i));
//	      if (i + 1 < event.getPointerCount())
//	         sb.append(";" );
//	   }
//	   sb.append("]" );
//	   Log.d("DUMP EVENT", sb.toString());
//	}
//	
//
//	 void printSamples(MotionEvent ev) {
//	     final int historySize = ev.getHistorySize();
//	     final int pointerCount = ev.getPointerCount();
//	     for (int h = 0; h < historySize; h++) {
//	         Log.d("At time %d:", ev.getHistoricalEventTime(h) + " ");
//	         for (int p = 0; p < pointerCount; p++) {
//	             Shared.p("Hist  pointer %d: (%f,%f)",
//	                 ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h), ev.getActionMasked());
//	         }
//	     }
//	     Log.d("At time %d:", ev.getEventTime() + " ");
//	     for (int p = 0; p < pointerCount; p++) {
//	         Shared.p("  pointer %d: (%f,%f)",
//	             ev.getPointerId(p), ev.getX(p), ev.getY(p), ev.getActionMasked());
//	     }
//	 }
//
//	public boolean onTouchEvent(MotionEvent event) {
//		try {
//			int pointerCount = multiTouchSupported ? (Integer) m_getPointerCount
//					.invoke(event) : 1;
//			if (DEBUG)
//				Log.i("MultiTouch", "Got here 1 - " + multiTouchSupported + " "
//						+ mMode + " " + handleSingleTouchEvents + " "
//						+ pointerCount);
//			if (mMode == MODE_NOTHING && !handleSingleTouchEvents
//					&& pointerCount == 1)
//				// Not handling initial single touch events, just pass them on
//				return false;
//			if (DEBUG)
//				Log.i("MultiTouch", "Got here 2");
//
//			// Handle history first (we sometimes get history with ACTION_MOVE
//			// events)
//			int action = event.getAction();
//			int histLen = event.getHistorySize() / pointerCount;
//			int actionPointer = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
//			for (int histIdx = 0; histIdx <= histLen; histIdx++) {
//				// Read from history entries until histIdx == histLen, then read
//				// from current event
//				boolean processingHist = histIdx < histLen;
//				if (!multiTouchSupported || pointerCount == 1) {
//					int ptrId = (Integer) m_getPointerId.invoke(event,
//							0);
//					// Use single-pointer methods -- these are needed as a
//					// special case (for some weird reason) even if
//					// multitouch is supported but there's only one touch point
//					// down currently -- event.getX(0) etc. throw
//					// an exception if there's only one point down.
//					xVals[ptrId] = processingHist ? event
//							.getHistoricalX(histIdx) : event.getX();
//					yVals[ptrId] = processingHist ? event
//							.getHistoricalY(histIdx) : event.getY();
//					pressureVals[ptrId] = processingHist ? event
//							.getHistoricalPressure(histIdx) : event
//							.getPressure();
//					actions[ptrId] = event.getAction();
////					Shared.p("Pointer ID:(", ptrId, ")", "ActionCode:(",
////							actions[0], ")");
//				} else {
//					// Read x, y and pressure of each pointer
//					if (DEBUG)
//						Log.i("MultiTouch", "Got here 4");
//					int numPointers = Math.min(pointerCount, MAX_TOUCH_POINTS);
//					for (int ptrIdx = 0; ptrIdx < numPointers; ptrIdx++) {
//						int ptrId = (Integer) m_getPointerId.invoke(event,
//								ptrIdx);
//						pointerIds[ptrIdx] = ptrId;
//						// N.B. if pointerCount == 1, then the following methods
//						// throw an array index out of range exception,
//						// and the code above is therefore required not just for
//						// Android 1.5/1.6 but also for when there is
//						// only one touch point on the screen -- pointlessly
//						// inconsistent :(
//						xVals[ptrIdx] = (Float) (processingHist ? m_getHistoricalX
//								.invoke(event, ptrIdx, histIdx) : m_getX
//								.invoke(event, ptrIdx));
//						yVals[ptrIdx] = (Float) (processingHist ? m_getHistoricalY
//								.invoke(event, ptrIdx, histIdx) : m_getY
//								.invoke(event, ptrIdx));
//						pressureVals[ptrIdx] = (Float) (processingHist ? m_getHistoricalPressure
//								.invoke(event, ptrIdx, histIdx) : m_getPressure
//								.invoke(event, ptrIdx));
//						int localAction = action;
//						if (ptrId != actionPointer
//								&& action != MotionEvent.ACTION_MOVE) {
//							localAction = MotionEvent.ACTION_MOVE;
//							// Shared.p("id = ", ptrId, "actionPointer = ",
//							// actionPointer, "local action changed from",
//							// action, "to", localAction);
//						}
//						actions[ptrIdx] = localAction;
////						Shared.p("Pointer ID:(", ptrId, ")", "ActionCode:(",
////								actions[ptrIdx], ")");
//					}
//				}
//				dispatchEvents(pointerCount, xVals, yVals, pressureVals, pointerIds, actionPointer, actions, action, processingHist ? event.getHistoricalEventTime(histIdx)
//						: event.getEventTime(), event);
//			}
//
//			return true;
//		} catch (Exception e) {
//			// In case any of the introspection stuff fails (it shouldn't)
//			Log.e("MultiTouchController", "onTouchEvent() failed", e);
//			return false;
//		}
//	}
//
//	private void dispatchEvents(int pointerCount, float[] xVals, float[] yVals, float[] pressureVals, int[] pointerIDs,	int actionPointer, int[] actions, int actionPointerAction, long eventTime, MotionEvent me) {
//		for(int i = 0; i < pointerCount; i++) {
//			int id = pointerIDs[i];
//			int action = actions[i];
//			float x = xVals[i];
//			float y = yVals[i];
//			float pressure = pressureVals[i];
//			DGMotionEvent dgmotionEvent = new DGMotionEvent(id, action, x, y, pressure, eventTime, pointerCount, pointerIDs);
//			
//			if(zeroCase) {
//				dgmotionEvent.setId(lastUnusedPointer);
//			}
//			
//			Shared.p(actionPointer, i, id, pointerCount, dgmotionEvent);
//			// handle move updates
//			if(action == MotionEvent.ACTION_MOVE) {
//				if(pointerToNodeMap.containsKey(id)){
//					pointerToNodeMap.get(id).handleMotionEvent(dgmotionEvent);
//				}
//			}
//			
//			// handle adding or removing a pointer
//			if(i == actionPointer) {
//				if(action == MotionEvent.ACTION_DOWN || (action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN){
//					handlePointerDown(dgmotionEvent);
//				} else if (action == MotionEvent.ACTION_UP || (action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
//					handlePointerUp(dgmotionEvent, false);
//					
//					if(pointerCount == 2 && actionPointer == 0){
//						lastUnusedPointer = pointerIDs[1];
//					}
//					if(zeroCase) {
//						zeroCase = false;
//					}
//				}
//			} 
//
//
//		}
//		lastActionPoint = actions[actionPointer];
//		lastPointerIDs = pointerIDs;
//		lastPointerCount = pointerCount;
//		
//	}
//	
//	public boolean handlePointerDown(DGMotionEvent me) {
//		scene.quePick(new Pick(this, me));
//		return true;
//	}
//
//	// This is called by Pick when a color is selected which is with in the 
//	// 	possible range of keys to call a node in the scene
//	public void handlePickSelection(DGMotionEvent dgme, int id, int pid) {
//		// get the node from the map based on the colorID
//		selectedNode = scene.getNodeByID(id);
//		// if the node is not an event blocker (has a motionListener) then
//		// recursivley look up the tree for the next parent that is
//		selectedNode = getFirstEventBlocker(selectedNode);
////		Shared.p(selectedNode);
//		if (selectedNode != null) {
//			pointerToNodeMap.put(pid, selectedNode);
//			selectedNode.handleMotionEvent(dgme);
//		}
//	}
//
//	public void handlePointerUp(DGMotionEvent me, boolean alreadyChecked) {
//		Shared.p("handlePointerUp", alreadyChecked, me);
//		if(me.numPointers == 2 && me.getId() == 0) {
//			Shared.p("Its happending, id =", me.getId(), "Pointers:", me.getIds()[0], me.getIds()[1]);	
////			Shared.p("Nodes:", pointerToNodeMap.get(me.getIds()[0]), pointerToNodeMap.get(me.getIds()[1]));	
//			DGNode swapNode = pointerToNodeMap.get(me.getIds()[1]);
//			swapNode.setPointerShift(me.getId());
//			pointerToNodeMap.remove(0);
//			pointerToNodeMap.put(0, swapNode);
//
//		} else {
//			
//			if(pointerToNodeMap.containsKey(me.getId())){
//				// Send on the Action Up event to the node then remove it from the map
//				pointerToNodeMap.get(me.getId()).handleMotionEvent(me);
//	//			Shared.p("handlePointerUp", me, pointerToNodeMap.get(me.getId()));
//				pointerToNodeMap.remove(me.getId());
//			}
////			else {
////				if(!alreadyChecked) {
////					Shared.p("Not Checked yet Posting UnPick task at:", startTime);
////					unPickTask.updateUnPick(me, pointerToNodeMap);
////					// Post an unpick check to see if this pointer up is actually attached to a node
////					// but that just hadn't happend till the the next draw finished (prevents missing pointer ups)
////					Shared.pApplet.runOnUiThread(unPickTask);
////				} else {
////					Shared.p("Pointer up did not find a node in the map, this is a problem. Not queing another UnPick");
////				}
////			}
//		}
//	}
//	
//	public void handleQueuedPointerUp(DGMotionEvent me) {
//		Shared.p("handleQueuedPointerUp", me);
//		if(me.numPointers == 2 && me.getId() == 0) {
////			zeroCase = true;
//			Shared.p("Its happending, id =", me.getId(), "Pointers:", me.getIds()[0], me.getIds()[1]);	
////			Shared.p("Nodes:", pointerToNodeMap.get(me.getIds()[0]), pointerToNodeMap.get(me.getIds()[1]));	
//			DGNode swapNode = pointerToNodeMap.get(me.getIds()[1]);
//			swapNode.setPointerShift(me.getId());
//			pointerToNodeMap.remove(0);
//			pointerToNodeMap.put(0, swapNode);
//
//		} else {
//			if(pointerToNodeMap.containsKey(me.getId())){
//				// Send on the Action Up event to the node then remove it from the map
//				pointerToNodeMap.get(me.getId()).handleMotionEvent(me);
//	//			Shared.p("handlePointerUp", me, pointerToNodeMap.get(me.getId()));
//				pointerToNodeMap.remove(me.getId());
//			}
//		}
//	}
//
//
//}
