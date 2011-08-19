package com.droidgraph.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import processing.core.PApplet;
import processing.core.PGraphics;
import android.animation.Animator;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.droidgraph.input.MultiTouchManager;
import com.droidgraph.scene.DGGroup;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;

public class Shared {

	public static boolean DEBUG = false;
	public static void setDebug(boolean b) {
		DEBUG = b;
	}

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

	public static void setMotionEvent(MotionEvent me) {
		motionEvent = me;
	}

	public static DGScene scene;

	public static void setScene(DGScene s) {
		scene = s;
	}

	public static PGraphics offscreenBuffer;

	public static void setOffscreenBuffer(PGraphics buffer) {
		offscreenBuffer = buffer;
	}

	public static void p(Object... o) {
		String result = "";
		for (Object ts : o) {
			result += " ";
			String s = "";
			if (ts != null) {
				s = ts.toString();
			} else {
				s = "NULL";
			}
			result += s;
		}
		Log.d(TAG, SystemClock.uptimeMillis() + " - " + result);
	}

	public static PGraphics image;

	public static void setImage(PGraphics c) {
		image = c;
	}

//	public static MotionManager motionManager;
//
//	public static void setMotionManager(TempMotionManager mm) {
//		motionManager = mm;
//	}

	public static Activity activity;
	public static void setActivity(Activity a) {
		activity = a;
	}
	
	public static Stack<Animator> animatorQue = new Stack<Animator>();
	public static void queAnimations(Animator... animators) {
		for (Animator a : animators) {
			animatorQue.push(a);
		}
	}
	
	public static <T, S> void pMap(Map<T, S> map) {
		StringBuilder sb = new StringBuilder("Map size:" + map.size() + " _ ");
		Set<T> set = map.keySet();
		for(T t : set) {
			S s = map.get(t);
			sb.append("key:" + t + " value:" + s + ", ");
		}
		p(sb.toString());
	}
	
	
	public static HashMap<Integer, DGNode> map;
	public static void setMap(HashMap<Integer, DGNode> m) {
		map = m;
	}

	public static MultiTouchManager multiTouchManager;
	public static void setMotionManager(MultiTouchManager m) {
		multiTouchManager = m;
	}
	
	public static float minMaxF(float min, float max, float val) {
		float out = val;
		out = (out <= max) ? out : max;
		out = (out >= min) ? out : min;
		return out;
	}

	public static DGGroup rootNode;
	public static void setSceneRoot(DGGroup group) {
		rootNode = group;
	}

}
