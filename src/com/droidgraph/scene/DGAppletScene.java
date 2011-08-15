//package com.droidgraph.scene;
//
//import java.util.ArrayList;
//
//import processing.core.PApplet;
//import processing.core.PGraphics;
//
//import com.droidgraph.affine.DGAffineTransform;
//import com.droidgraph.event.DGEventParsed;
//import com.droidgraph.fx.DGFXComp;
//import com.droidgraph.fx.DGFXImage;
//import com.droidgraph.fx.DGFXShape;
//import com.droidgraph.transformation.Bounds2D;
//import com.droidgraph.util.Shared;
//
//public class DGAppletScene extends DGScene{
//
//	String TAG = "DGScene";
//
//	public static int ANDROID = 0;
//	public static int PROCESSING = 1;
//
//	private Bounds2D bounds;
//
//	private PApplet parent;
//	private DGGroup root;
//	private int[] bg = { 0, 0, 0, 255 };
//
////	private TempMotionManager motionManager;
//
//	private ArrayList<Runnable> picks = new ArrayList<Runnable>();
//
//	PGraphics buff;
//
//	// private boolean updateNodes = false;
//
//	private ArrayList<DGEventParsed> motionEventQue = new ArrayList<DGEventParsed>();
//
//	public DGAppletScene(PApplet parent, String renderMode) {
//		this.parent = parent;
//		bounds = new Bounds2D(0, 0, parent.screenWidth, parent.screenHeight);
//
//		// Set the global variables in the Helper class
//		Shared.setPApplet(parent);
//		Shared.setRenderer(renderMode);
//		Shared.setScene(this);
//		// Create the offscreen buffer for picking
//		buff = parent.createGraphics(parent.screenWidth,
//				parent.screenHeight, PApplet.P3D);
//
//		Shared.setOffscreenBuffer(buff);
//
//		root = new DGGroup();
//		root.setParent(this);
//		root.bounds = bounds;
//
////		motionManager = new TempMotionManager(this);
////		Shared.setMotionManager(motionManager);
//	}
//
//	public void add(DGNode node) {
//		root.add(node);
//	}
//
//	public PApplet getParentApplet() {
//		return parent;
//	}
//
//	public DGGroup getRoot() {
//		return root;
//	}
//
//	void clearScene() {
//		root = null;
//	}
//
//	// public void setUpdate(boolean b) {
//	// updateNodes = b;
//	// }
//
//	public void draw() {
//		/*
//		 * If there are pending picks, draw to the off screen buffer and then
//		 * run the picks
//		 */
//		while (!picks.isEmpty()) {
//			drawPickingNodes();
//			runPicks();
//		}
//
//		/*
//		 * Run any animations that are in the que
//		 */
//		// while(!Shared.animatorQue.isEmpty()) {
//		// Shared.animatorQue.pop().start();
//		// }
//
//		drawScene();
//	}
//
//	/*
//	 * This is where we actually draw the off screen buffer of the touchable
//	 * (from Shared.touchables) nodes in the scene.
//	 */
//	private void drawPickingNodes() {
//
//		final ArrayList<DGNode> touchables = Shared.touchables;
//
//		int[] id = { 1, 0, 0, 255 };
//		int i = 0;
//
//		Shared.offscreenBuffer.beginDraw();
//		// clear the last buffered rendering
//		Shared.offscreenBuffer.background(0, 0, 0, 0);
//
//		for (DGNode node : touchables) {
//			// if it the node is either a DGFXGroup or an extension of one
//			if (node instanceof DGAffineTransform
//					|| node.getClass().getGenericSuperclass() instanceof DGAffineTransform) {
//				DGAffineTransform n = ((DGAffineTransform) node);
//
//				/*
//				 * there should be a more efficient way to do this but I am
//				 * having issues assigning the unique color ids any other way
//				 * than getting and setting between the original color and the
//				 * id color for the buffer
//				 */
//
//				int[] oc = { n.getRed(), n.getGreen(), n.getBlue(),
//						n.getAlpha() };
//				n.setFill(id[0], id[1], id[2], id[3]);
//				n.render(Shared.offscreenBuffer);
//				n.setFill(oc[0], oc[1], oc[2], oc[3]);
//			} else if (node instanceof DGFXShape) {
//
//				if (node instanceof DGFXComp) {
//					DGFXComp n = ((DGFXComp) node);
//
//					// Just for DGFXComp, set picking true
//					n.setPicking(true);
//					n.setFill(id[0], id[1], id[2], id[3]);
//					n.paint(Shared.offscreenBuffer);
//					// turn the picking off
//					n.setPicking(false);
//
//				} else if (node instanceof DGFXImage) {
//					DGFXImage n = ((DGFXImage) node);
//					// Just for DGFXComp, set picking true
//					n.setPicking(true);
//					n.setFill(id[0], id[1], id[2], id[3]);
//					n.paint(Shared.offscreenBuffer);
//					// turn the picking off
//					n.setPicking(false);
//
//				} else {
//					DGFXShape n = ((DGFXShape) node);
//					float[] oc = { n.getRed(), n.getGreen(), n.getBlue(),
//							n.getAlpha() };
//					n.setFill(id[0], id[1], id[2], id[3]);
//					n.paint(Shared.offscreenBuffer);
//					n.setFill(oc[0], oc[1], oc[2], oc[3]);
//				}
//			}
//			udpateID(id);
//			i++;
//		}
//
//		Shared.offscreenBuffer.endDraw();
//	}
//
//	/*
//	 * Here the picks which have been qued for the next frame are run before the
//	 * next frame is drawn.
//	 */
//	private void runPicks() {
//		while (!picks.isEmpty()) {
//			Runnable p = picks.remove(0);
//			p.run();
//		}
//	}
//
//	// private void updateNodes() {
//	// if (updateNodes) {
//	// root.updateNode();
//	// }
//	// }
//
//	private void drawScene() {
//		// Clear the main surface background
//		parent.background(bg[0], bg[1], bg[2], bg[3]);
//		// Draw the DGScene
//		root.render();
//	}
//
//	public void setBackground(int r, int g, int b, int a) {
//		bg[0] = r;
//		bg[1] = g;
//		bg[2] = b;
//		bg[3] = a;
//	}
//
////	public void updateMotionEvent(MotionEvent me) {
////		motionManager.processMotionEvent(me);
////	}
//
//	public void quePick(Runnable runnable) {
//		picks.add(runnable);
//	}
//
//	public void queMotionEvents(ArrayList<DGEventParsed> events) {
//		motionEventQue.addAll(events);
//	}
//
//	public int getNumPicks() {
//		return picks.size();
//	}
//
//	private void udpateID(int[] id) {
//		id[0]++;
//		if (id[0] > 255) {
//			id[0] = 255;
//			id[1]++;
//			if (id[1] > 255) {
//				id[1] = 255;
//				id[2]++;
//				if (id[2] > 255) {
//					id[2] = 255;
////					Log.d("DGPicker", "too many picking nodes");
//				}
//			}
//		}
//	}
//
//	public void setNodeDepth(DGFXShape node, int zdepth) {
//
//	}
//
//	public void setBackground(int i) {
//		setBackground(i, i, i, i);
//	}
//
//}
