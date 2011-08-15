package com.droidgraph.renderer;

import java.util.HashMap;

import processing.core.PGraphics;

import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGScene;

public class BufferThread implements Runnable {
	
	PGraphics main;
	PickBuffer buffer;
	DGScene scene;
	HashMap<Integer, DGNode> nodes = null;
	
	public BufferThread(PGraphics main, PickBuffer buffer) {
		this.main = main;
		this.buffer = buffer;
	}

	public void setScene(DGScene scene) {
		nodes = scene.getNodeMap();
		this.scene = scene;
	}
	
	@Override
	public void run() {
	}
	
	
	
	
	

}
