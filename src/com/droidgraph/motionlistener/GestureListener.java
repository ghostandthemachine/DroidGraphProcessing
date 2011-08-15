package com.droidgraph.motionlistener;

import java.util.ArrayList;

import processing.core.PApplet;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.scene.DGNode;

public class GestureListener extends ActionListener {

	public static int MODE_NOTHING = 0;
	public static int MODE_DRAG = 1;
	public static int MODE_PINCH = 2;

	private int mode = 0;

	private float pinchAngle = 0f;
	private float lastPinchAngle = 0f;
	private float pinchScale = 1f;
	private float lastPinchScale = 1f;
	private float initialPinchDistance;
	private float pinchDistance;
	private float lastPinchDistance;
	private DGMotionEvent pOne;
	private DGMotionEvent lastPOne;
	private DGMotionEvent pTwo;
	private DGMotionEvent lastPTwo;

	private ArrayList<DGMotionEvent> gesture = new ArrayList<DGMotionEvent>();
	private float pinchAngleVelocity = 0f;
	private float pinchDistanceVelocity = 0f;
	private float pinchScaleVelocity = 0f;

	public GestureListener(DGNode node) {
		super(node);
	}

	@Override
	public boolean actionDown(DGMotionEvent me) {
		lastPOne = pOne;
		pOne = me;
		mode = 1;
		return super.actionDown(me);
	}

	@Override
	public boolean actionPointerDown(DGMotionEvent me) {
		switch (me.getId()) {
		case 1:
			lastPTwo = pTwo;
			pTwo = me;
			pinchAngle = PApplet.atan2(pOne.getY() - pTwo.getY(), pOne.getX()
					- pTwo.getX());
			initialPinchDistance = PApplet.dist(pOne.getX(), pOne.getY(),
					pTwo.getX(), pTwo.getY());
			break;
		}
		return super.actionPointerDown(me);
	}

	@Override
	public boolean actionMove(DGMotionEvent me) {
		if (numPointers >= 2) {
			mode = MODE_DRAG;
		}

		switch (me.getId()) {

		case 0:
			lastPOne = pOne;
			pOne = me;
			pOne.setVelocity(pOne.getX() - lastPOne.getX(), pOne.getY()
					- lastPOne.getY());
			break;
		case 1:
			lastPTwo = pTwo;
			pTwo = me;
			pTwo.setVelocity(pTwo.getX() - lastPTwo.getX(), pTwo.getY()
					- lastPTwo.getY());
			calculatePointers();
			pinchGesture();
			break;
		}
		return super.actionMove(me);
	}

	public boolean pinchGesture() {

		return true;
	}

	private void calculatePointers() {
		// update the old values
		lastPinchAngle = pinchAngle;
		lastPinchDistance = pinchDistance;
		lastPinchScale = pinchScale;
		// ...and the new ones
		pinchAngle = PApplet.atan2(pOne.getY() - pTwo.getY(), pOne.getX()
				- pTwo.getX());
		pinchDistance = PApplet.dist(pOne.getX(), pOne.getY(), pTwo.getX(),
				pTwo.getY());
		pinchScale = pinchDistance / initialPinchDistance;

		pinchAngleVelocity = pinchAngle - lastPinchAngle;
		pinchDistanceVelocity = pinchDistance - lastPinchDistance;
		pinchScaleVelocity = pinchScale - lastPinchScale;
	}

	@Override
	public boolean actionUp(DGMotionEvent me) {
		mode = MODE_NOTHING;
		return super.actionUp(me);
	}

	@Override
	public boolean actionPointerUp(DGMotionEvent me) {
		return super.actionPointerUp(me);
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @return the pinchAngle
	 */
	public float getPinchAngle() {
		return pinchAngle;
	}

	/**
	 * @return the lastPinchAngle
	 */
	public float getLastPinchAngle() {
		return lastPinchAngle;
	}

	/**
	 * @return the pinchScale
	 */
	public float getPinchScale() {
		return pinchScale;
	}

	/**
	 * @return the lastPinchScale
	 */
	public float getLastPinchScale() {
		return lastPinchScale;
	}

	/**
	 * @return the initialPinchDistance
	 */
	public float getInitialPinchDistance() {
		return initialPinchDistance;
	}

	/**
	 * @return the pinchDistance
	 */
	public float getPinchDistance() {
		return pinchDistance;
	}

	/**
	 * @return the lastPinchDistance
	 */
	public float getLastPinchDistance() {
		return lastPinchDistance;
	}

	/**
	 * @return the pOne
	 */
	public DGMotionEvent getpOne() {
		return pOne;
	}

	/**
	 * @return the lastPOne
	 */
	public DGMotionEvent getLastPOne() {
		return lastPOne;
	}

	/**
	 * @return the pTwo
	 */
	public DGMotionEvent getpTwo() {
		return pTwo;
	}

	/**
	 * @return the lastPTwo
	 */
	public DGMotionEvent getLastPTwo() {
		return lastPTwo;
	}

	/**
	 * @return the pinchAngleVelocity
	 */
	public float getPinchAngleVelocity() {
		return pinchAngleVelocity;
	}

	/**
	 * @return the pinchDistanceVelocity
	 */
	public float getPinchDistanceVelocity() {
		return pinchDistanceVelocity;
	}

	/**
	 * @return the pinchScaleVelocity
	 */
	public float getPinchScaleVelocity() {
		return pinchScaleVelocity;
	}

}
