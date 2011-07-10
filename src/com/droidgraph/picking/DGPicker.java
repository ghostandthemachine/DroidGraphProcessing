package com.droidgraph.picking;

import processing.core.PApplet;
import processing.core.PImage;
import android.view.MotionEvent;

import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;
import com.droidgraph.util.Shared;

public class DGPicker {

	String TAG = "DGPicker";
	
	public PImage image;

	private DGScene scene;
	private PApplet pa;
	
	public PImage pi;

	public DGPicker(DGScene scene) {
		this.scene = scene;
		pa = Shared.pApplet;
	}

	public DGNode pick(final MotionEvent me, final int pid) {
		DGNode returnNode = null;
		
//		scene.quePick(new Pick(me, pid));

		return returnNode;
	}


}
