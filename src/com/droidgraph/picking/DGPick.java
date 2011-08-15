//package com.droidgraph.picking;
//
//import android.graphics.Color;
//
//import com.android.multitouch.controller.MultiTouchController.PointInfo;
//import com.droidgraph.scene.DGScene;
//import com.droidgraph.util.Shared;
//
//public class DGPick {
//
//	String TAG = "Pick";
//
//	DGScene scene;
//	int globalPointerID;
//
//	public DGPick(DGScene scene, int globalPointerID, int actionCode) {
//		this.scene = scene;
//		this.globalPointerID = globalPointerID;
//	}
//
//	public void pick(PointInfo p) {
//		int bc = Shared.offscreenBuffer.get((int) p.getX(), (int) p.getY());
//		int r = Color.red(bc);
//		int g = Color.green(bc);
//		int b = Color.blue(bc);
//		int c = r | (g << 8) | (b << 16);
//		// if not touching the background rgba(0,0,0,0) then call motionManager
//		if (bc != -1) {	
//			scene.pickNode(this, p, c);
//		}
//	}
//}
