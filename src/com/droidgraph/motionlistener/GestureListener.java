package com.droidgraph.motionlistener;

import java.util.ArrayList;

import processing.core.PApplet;

import com.droidgraph.event.DGMotionEvent;
import com.droidgraph.fx.DGFXShape;
import com.droidgraph.scene.DGNode;
import com.droidgraph.util.Shared;

public class GestureListener extends ActionListener {
	
	public boolean DEBUG = true;

	public static int MODE_NOTHING = 0;
	public static int MODE_DRAG = 1;
	public static int MODE_PINCH = 2;
	public static int MODE_MULTI_THREE = 3;
	public static int MODE_MULTI_FOUR = 4;
	public static int MODE_MULTI_FIVE = 5;
	public static int MODE_MULTI_MANY = 6;
	
	private int mode = 0;
	
	

	private float pinchAngle = 0f;
	private float lastPinchAngle = 0f;
	private float pinchAngleVelocity = 0f;
	
	private float pinchScale = 1f;
	private float lastPinchScale = 1f;
	private float pinchScaleVelocity = 0f;

	
	private float initialPinchDistance;
	
	private float pinchDistance;
	private float lastPinchDistance;
	private float pinchDistanceVelocity = 0f;
	
	
	private DGMotionEvent pOne;
	private DGMotionEvent lastPOne;
	
	private DGMotionEvent pTwo;
	private DGMotionEvent lastPTwo;
	
	private float anchorX;
	private float anchorY;

	private ArrayList<DGMotionEvent> gesture = new ArrayList<DGMotionEvent>();
	
	protected DGFXShape fxshape;


	public GestureListener(DGFXShape node) {
		super(((DGNode)node));
		fxshape = node;
		
		// Set the rotation pivot to the center
		fxshape.setRotationCenter(fxshape.getWidth() / 2, fxshape.getHeight() / 2);
	}

	@Override
	public boolean actionDown(DGMotionEvent me) {
		lastPOne = pOne;
		pOne = me;
		updateModeAfterDown();
		return super.actionDown(me);
	}

	@Override
	public boolean actionPointerDown(DGMotionEvent me) {
		switch (me.getID()) {
		case 1:
			lastPTwo = pTwo;
			pTwo = me;
			pinchAngle = PApplet.atan2(pOne.getY() - pTwo.getY(), pOne.getX() - pTwo.getX());
			initialPinchDistance = PApplet.dist(pOne.getX(), pOne.getY(), pTwo.getX(), pTwo.getY());
			break;
		}
		updateModeAfterDown();
		return super.actionPointerDown(me);
	}

	@Override
	public boolean actionMove(DGMotionEvent me) {
		switch (mode) {

		case 1:
			lastPOne = pOne;
			pOne = me;
			
			// since this is a drag, set it as the anchor too
			anchorX = pOne.getX();
			anchorY = pOne.getY();

			// Call the drag method
			drag();
			
			break;
			
		case 2:
			lastPTwo = pTwo;
			pTwo = me;
			
			calculateFisrtAndSecondPointers();
			
			// Call the pinch gesture which can be overridden
			pinchGesture();				
			break;
		}
		
		return super.actionMove(me);
	}

	@Override
	public boolean actionUp(DGMotionEvent me) {
		updateModeAfterUp();
		return super.actionUp(me);
	}

	@Override
	public boolean actionPointerUp(DGMotionEvent me) {
		updateModeAfterUp();
		return super.actionPointerUp(me);
	}
	
	private void updateModeAfterUp() {
		switch(numPointers) {
		case 1:
			mode = MODE_NOTHING;
			break;
			
		case 2:
			mode = MODE_DRAG;
			break;	
			
		case 3:
			mode = MODE_PINCH;
			break;
		
		default :
			mode = (numPointers >= 3 && numPointers <= 5) ? numPointers - 1 : MODE_MULTI_MANY;
			break;
		
		}
	}
	
	private void updateModeAfterDown() {
		switch(numPointers) {
		case 0:
			mode = MODE_NOTHING;
			break;
			
		case 1:
			mode = MODE_DRAG;
			break;
			
		case 2:
			mode = MODE_PINCH;
			break;	
		
		default :
			mode = (numPointers >= 3 && numPointers <= 5) ? numPointers : MODE_MULTI_MANY;
			break;
		
		}
	}
	
	public boolean drag() {
		if(DEBUG) {
			Shared.p("Drag gesture", "anchorX:", anchorX, "anchorY:", anchorY);
		}
		// handled
		return true;
	}

	public boolean pinchGesture() {
		if(DEBUG) {
			Shared.p("angle:", pinchAngle, " scale:", pinchScale);
		}
		// handled
		return true;
	}

	private void calculateFisrtAndSecondPointers() {
		// update the old values
		lastPinchAngle = pinchAngle;
		lastPinchDistance = pinchDistance;
		lastPinchScale = pinchScale;
		// ...and the new ones
		pinchAngle = PApplet.atan2(pOne.getY() - pTwo.getY(), pOne.getX() - pTwo.getX());
		pinchDistance = PApplet.dist(pOne.getX(), pOne.getY(), pTwo.getX(),	pTwo.getY());
		pinchScale = pinchDistance / initialPinchDistance;

		pinchAngleVelocity = pinchAngle - lastPinchAngle;
		pinchDistanceVelocity = pinchDistance - lastPinchDistance;
		pinchScaleVelocity = pinchScale - lastPinchScale;
		

		if(pTwo != null) {
			anchorX = (pOne.getX() + pTwo.getX()) / 2;
			anchorY = (pOne.getY() + pTwo.getY()) / 2;
		}

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
	public DGMotionEvent getPointerOne() {
		return pOne;
	}

	/**
	 * @return the lastPOne
	 */
	public DGMotionEvent getLastPointerOne() {
		return lastPOne;
	}

	/**
	 * @return the pTwo
	 */
	public DGMotionEvent getPointerTwo() {
		return pTwo;
	}

	/**
	 * @return the lastPTwo
	 */
	public DGMotionEvent getLastPointerTwo() {
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
	
	/**
	 * @return the gestures anchor X
	 */
	public float getAnchorX() {
		return anchorX;
	}
	
	/**
	 * @return the gestures anchor Y
	 */
	public float getAnchorY() {
		return anchorY;
	}

}
