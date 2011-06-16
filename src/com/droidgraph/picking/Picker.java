package com.droidgraph.picking;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import android.util.Log;

/**
 * A Picker object helps you to process picking.
 * 
 * @example Simple
 * @author nicolas.clavaud <antiplastik@gmail.com>
 * 
 */
public class Picker implements PConstants{

	String TAG = "com.droidgraph.picking.Picker";
	/**
	 * Processing applet
	 */
	protected PApplet parent;

	/**
	 * Main picking buffer Direct access to the buffer is allowed so you can
	 * draw shapes that you wouldn't like to draw on the main screen (like
	 * bounding boxes).
	 */
	public Buffer buffer;

	public Picker(PApplet parent) {
		this.parent = parent;
		buffer = (Buffer) parent.createGraphics(parent.width, parent.height,
				A3D);
		buffer.callCheckSettings();
		if (parent.recorder == null)
			parent.recorder = buffer;
		buffer.background(0);
		parent.registerPre(this);
		parent.registerDraw(this);
	}

	public void pre() {
		buffer.beginDraw();
		if (parent.recorder == null)
			parent.recorder = buffer;
	}

	public void draw() {
		buffer.endDraw();
	}

	/**
	 * Begins recording object(s)
	 * 
	 * @param i
	 *            Object ID
	 */
	public void start(int i) {
		if (i < 0 || i > 16777214) {
			PApplet.println("[Picking error] start(): ID out of range");
			return;
		}
		if (parent.recorder == null)
			parent.recorder = buffer;
		buffer.setCurrentId(i);
	}

	/**
	 * Stops/pauses recording object(s)
	 */
	public void stop() {
		parent.recorder = null;
	}

	/**
	 * Resumes recording object(s)
	 */
	public void resume() {
		if (parent.recorder == null)
			parent.recorder = buffer;
	}

	/**
	 * Reads the ID of the object at point (x, y) -1 means there is no object at
	 * this point
	 * 
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @return Object ID
	 */
	public int get(int x, int y) {
		int id = buffer.getId(x, y);
		Log.d(TAG, " id = " + id);
		return id;
	}

	/**
	 * Get the buffer
	 * 
	 * @return Buffer
	 */
	public PGraphics getBuffer() {
		return buffer;
	}

}