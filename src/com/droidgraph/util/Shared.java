package com.droidgraph.util;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PGraphicsAndroid3D;
import android.util.Log;
import android.view.MotionEvent;

import com.droidgraph.input.MotionManager;
import com.droidgraph.input.TempMotionManager;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;

public class Shared {
	
	public static String TAG = "Shared";
	
	public static PApplet pApplet = null;
	public static void setPApplet(PApplet p) {
		pApplet = p;
	}
	
	public static String renderer;
	public static void setRenderer(String r) {
		renderer = r;
	}
	
	public static DGNode curenntTouchedNode;
	public static void setCurrentTouchedNode(DGNode node) {
		curenntTouchedNode = node;
	}
	
	public static MotionEvent motionEvent;
	public static void setMotionEvent( MotionEvent me) {
		motionEvent = me;
	}
	
	public static ArrayList<DGNode> touchables = new ArrayList<DGNode>();
	public static void addListenerNode( DGNode node ) {
		touchables.add(node);
	}
	
	public static DGScene scene;
	public static void setScene(DGScene s) {
		scene = s;
	}
	
	public static PGraphicsAndroid3D offscreenBuffer;
	public static void setOffscreenBuffer(PGraphicsAndroid3D buffer) {
		offscreenBuffer = buffer;
	}
	
	public static void p(Object ... o) {
		String result = "";
		for(Object ts : o) {
			result += " ";
			result += ts.toString();
		}
		Log.d(TAG, result);
	}
	
	public static PGraphics image;
	public static void setImage(PGraphics c) {
		image = c;
	}

	public static MotionManager motionManager;
	public static void setMotionManager(TempMotionManager mm) {
		motionManager = mm;
	}
	
	
	
}
