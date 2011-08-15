package com.droidgraph.scene;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PGraphics;
import android.app.Activity;
import android.view.MotionEvent;

import com.droidgraph.fx.DGFXShape;
import com.droidgraph.input.MultiTouchManager;
import com.droidgraph.picking.Pick;
import com.droidgraph.renderer.PickBuffer;
import com.droidgraph.transformation.Bounds2D;
import com.droidgraph.util.Shared;

public class DGScene {

	// String TAG = "DGScene";

	public static int ANDROID = 0;
	public static int PROCESSING = 1;

	/*
	 * The 2D Bounds of this scene
	 */
	private Bounds2D bounds;

	/*
	 * The main Processing Applet
	 */
	private PApplet parent;

	/*
	 * The root group that makes up the scene
	 */
	private DGGroup root;

	private DGNode selectedNode;

	/*
	 * The scene background values
	 */
	private int[] bg = { 0, 0, 0, 255 };

	/*
	 * The Map which stores references to all of the nodes in the graph Map <
	 * Key : BitSet id, Value : the node >
	 */
	private HashMap<Integer, DGNode> nodeMap = new HashMap<Integer, DGNode>();

	/*
	 * Bit set to be used for unique node keys
	 */
	private BitSet bitSet = new BitSet();

	/*
	 * The list of off screen buffer picks to be run before the next frame
	 */
	private ArrayList<Pick> picks = new ArrayList<Pick>();

	/*
	 * The off screen buffer for Picking
	 */
	PGraphics offScreenBuffer;

	private MultiTouchManager manager;
//	private ArrayList<ControlRun> controlQue = new ArrayList<ControlRun>();

	public DGScene(PApplet parent) {
		this.parent = parent;

		bounds = new Bounds2D(0, 0, parent.screenWidth, parent.screenHeight);

		// Set the global variables in the Shared class
		Shared.setPApplet(parent);
		Shared.setScene(this);
		Shared.setActivity(parent);

		// Create the off screen buffer for picking
		offScreenBuffer = (PickBuffer) parent.createGraphics(
				parent.screenWidth, parent.screenHeight,
				"com.droidgraph.renderer.PickBuffer");

		Shared.setOffscreenBuffer(offScreenBuffer);

		// Construct the root group
		root = new DGGroup();
		// Set this null so that the event blocking stops within the scene
		root.setParent(null);
		root.bounds = bounds;

		manager = new MultiTouchManager(this);
	}

	public void add(DGNode node) {
		root.add(node);
	}

	// Setting first bit index to start at 1 so that the color id
	// does not start with 0 which would paint a transparent object
	public int registerNode(DGNode node) {
		Integer id = bitSet.nextClearBit(1);
		bitSet.set(id);
		nodeMap.put(id, node);
		// Shared.p("Node registered, id = ", id, nodeMap.size(),
		// nodeMap.entrySet());
		return id;
	}

	public void unregisterNode(DGNode node) {
		bitSet.clear(node.getSceneID());
	}

	public DGNode getNodeByID(int id) {
		return nodeMap.get(id);
	}
	
	public MultiTouchManager getMotionManager() {
		return manager;
	}

	public boolean sceneContains(int id) {
		Integer i = id;
		return nodeMap.containsKey(i);
	}

	public HashMap<Integer, DGNode> getNodeMap() {
		return nodeMap;
	}

	public PApplet getParentApplet() {
		return parent;
	}

	public DGGroup getRoot() {
		return root;
	}

	void clearScene() {
		root = null;
	}

	public void draw() {
		/*
		 * If there are pending picks, draw to the off screen buffer and then
		 * run the picks
		 */
		while (!picks.isEmpty()) {
			drawNodesToPickBuffer();
			pickNodes();
		}

		drawScene();
	}

	/*
	 * This is where we actually draw the off screen buffer of the touchable
	 * (from Shared.touchables) nodes in the scene.
	 */
	private void drawNodesToPickBuffer() {
		// Begin drawing to the off screen buffer
		offScreenBuffer.beginDraw();
		// clear the last buffered rendering
		offScreenBuffer.background(0, 0, 0, 0);
		// return the number of set bits which in this case is the number of
		// nodes in the scene
		root.renderToPickBuffer(offScreenBuffer);
		offScreenBuffer.endDraw();
	}

	/*
	 * Here the picks which have been qued for the next frame are run before the
	 * next frame is drawn.
	 */
	private void pickNodes() {
		while (!picks.isEmpty()) {
			Pick p = picks.remove(0);
			p.pick();
		}
	}

	private void drawScene() {
		// Clear the main surface background
		parent.background(bg[0], bg[1], bg[2], bg[3]);
		// Draw the DGScene
		root.render();
	}

	public void setBackground(int r, int g, int b, int a) {
		bg[0] = r;
		bg[1] = g;
		bg[2] = b;
		bg[3] = a;
	}

	public void quePick(Pick pick) {
		picks.add(pick);
	}

	public void setNodeDepth(DGFXShape node, int zdepth) {

	}

	public void setBackground(int i) {
		setBackground(i, i, i, i);
	}


	public boolean handleMotionEvent(MotionEvent me) {
		return manager.onTouchEvent(me);
	}

}
