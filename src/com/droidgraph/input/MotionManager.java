//package com.droidgraph.input;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//import android.view.MotionEvent;
//
//import com.droidgraph.picking.Pick;
//import com.droidgraph.scene.DGNode;
//import com.droidgraph.scene.DGScene;
//import com.droidgraph.util.Shared;
//
//public class MotionManager {
//
//	String TAG = "MotionManager";
//
//	private DGScene scene;
//
//	private HashMap<Integer, DGNode> pointerToNodeMap = new HashMap<Integer, DGNode>();
//
//	public MotionManager(DGScene scene) {
//		this.scene = scene;
//	}
//
//	public DGScene getScene() {
//		return scene;
//	}
//
//	public boolean processMotionEvent(MotionEvent event) {
//		MotionEvent me = event;
//		int action = me.getAction();
//		int pointerID = 0;
//		int actionPointer = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
//		int actionCode = action & MotionEvent.ACTION_MASK;
//		Set<DGNode> alreadyHandledNodes = new HashSet<DGNode>();
//
//		for (int i = 0; i < me.getPointerCount(); i++) {
//
//			pointerID = me.getPointerId(i);
//			// if the current node being iterated over is not the one that
//			// created the action pointer
//			// then the action is changed to MotionEvent.ACTION_MOVE since it is
//			// just an update
//			if (i != actionPointer && !pointerToNodeMap.containsKey(pointerID)) {
//				actionCode = MotionEvent.ACTION_MOVE;
//			}
//
////			 Shared.p("In loop", event.getPointerCount(), actionPointer,
////			 actionCode);
//
//			if (actionCode == MotionEvent.ACTION_DOWN
//					|| actionCode == MotionEvent.ACTION_POINTER_DOWN) {
//				// Que a pick in the scene. If it touches a node, the pick with
//				// update this MotionManager
//				// by attaching a DGPointer to a target node and updating that
//				// DGPointers event
//				scene.quePick(new Pick(this, me, pointerID, actionCode));
//				Shared.p("pick called");
//			} else if (actionCode == MotionEvent.ACTION_UP
//					|| actionCode == MotionEvent.ACTION_POINTER_UP) {
//				DGNode n = pointerToNodeMap.get(pointerID);
//				// Dont handle pointers ups if the pointer down was never
//				// handled
//				if (n != null) {
//					handlePointerUp(pointerToNodeMap.get(pointerID), me,
//							pointerID);
//				}
//			} else {
//				DGNode node = pointerToNodeMap.get(pointerID);
//				if (node != null) {
//					// Shared.p("MotionManager - processMotionEvent - ACTION_MOVE");
//					if (!alreadyHandledNodes.contains(node)) {
//						node.handleMotionEvent(me);
//						alreadyHandledNodes.add(node);
//					}
//				}
//			}
//		}
//		return true;
//	}
//
//	public void handlePointerDown(int nodeID, MotionEvent event, int pointerID) {
//		DGNode node = scene.getNodeByID(nodeID);
//		node = getFirstEventBlocker(node);
//		Shared.p(node);
//		if (node != null) {
//			pointerToNodeMap.put(pointerID, node);
//			node.handleMotionEvent(event);
//		}
//	}
//
//	public void handlePointerUp(DGNode node, MotionEvent me, int pointerID) {
//		node.handleMotionEvent(me);
//		pointerToNodeMap.remove(pointerID);
//
//	}
//
//	public DGNode getFirstEventBlocker(DGNode node) {
//		if (node.isEventBlocker()) {
//			return node;
//		} else if (node.getParent() != null) {
//			getFirstEventBlocker(((DGNode) node.getParent()));
//		} else {
//			return null;
//		}
//		return null;
//	}
//
//}
