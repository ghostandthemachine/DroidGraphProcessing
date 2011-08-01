package com.droidgraph.fx;

import java.util.ArrayList;

import processing.core.PGraphics;

import com.droidgraph.scene.DGShape;
import com.droidgraph.shape.DGPShape2D;

public class DGFXShape extends DGShape {

	private ArrayList<Runnable> touchActions = new ArrayList<Runnable>();

	public DGFXShape() {
	}

	public float translateX = 0;
	public float translateY = 0;
	public float translateZ = 0;

	// Honeycomb animation syntax

	/**
	 * @return the translateX
	 */
	public float getTranslateX() {
		return bounds.x;
	}

	/**
	 * @param translateX
	 *            the translateX to set
	 */
	public void setTranslateX(float translateX) {
		bounds.x = translateX;
		this.translateX = translateX;
	}

	/**
	 * @return the translateY
	 */
	public float getTranslateY() {
		return bounds.y;
	}

	/**
	 * @param translateY
	 *            the translateY to set
	 */
	public void setTranslateY(float translateY) {
		bounds.y = translateY;
		this.translateY = translateY;
	}
	
	/**
	 * @return the translateZ
	 */
	public float getTranslateZ() {
		return translateZ;
	}

	/**
	 * @param translateZ
	 *            the translateZ to set
	 */
	public void setTranslateZ(float translateZ) {
		this.translateZ = translateZ;
	}

	public void setTranslation(float tx, float ty, float tz) {
		setTranslateX(tx);
		setTranslateY(ty);
		setTranslateZ(tz);
	}

	public void setTranslation(float tx, float ty) {
		setTranslateX(tx);
		setTranslateY(ty);
	}
	
	public void translateBy(float tbx, float tby) {
		setTranslateX(bounds.x + tbx);
		setTranslateY(bounds.y + tby);
	}
	
	public void translateBy(float tbx, float tby, float tbz) {
		setTranslateX(bounds.x + tbx);
		setTranslateY(bounds.y + tby);
//		setTranslateZ(bounds.z + tbz);
	}

	public float width = 0;
	public float height = 0;
	public float depth = 0;

	/**
	 * @return the width
	 */
	public float getWidth() {
		return bounds.width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(float width) {
		bounds.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return bounds.height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(float height) {
		bounds.height = height;
	}

	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float angle = 0;

	/**
	 * 
	 * @param angle 
	 */
	public void setAngle(float a) {
		angle = a;
	}

	public float getAngle() {
		return angle;
	}

	public float rotateX = 0;
	public float rotateY = 0;
	public float rotateZ = 0;

	/**
	 * @return the rotateX
	 */
	public float getRotateX() {
		return rotateX;
	}

	/**
	 * @param rotateX
	 *            the rotateX to set
	 */
	public void setRotateX(float rotateX) {
		this.rotateX = rotateX;
	}

	/**
	 * @return the rotateY
	 */
	public float getRotateY() {
		return rotateY;
	}

	/**
	 * @param rotateY
	 *            the rotateY to set
	 */
	public void setRotateY(float rotateY) {
		this.rotateY = rotateY;
	}

	/**
	 * @return the rotateZ
	 */
	public float getRotateZ() {
		return rotateZ;
	}

	/**
	 * @param rotateZ
	 *            the rotateZ to set
	 */
	public void setRotateZ(float rotateZ) {
		this.rotateZ = rotateZ;
	}

	public void setRotation(float rx, float ry, float rz) {
		setRotateX(rx);
		setRotateY(ry);
		setRotateZ(rz);
	}

	public float rotationCenterX = 0;
	public float rotationCenterY = 0;
	public float rotationCenterZ = 0;

	public float getRotationCenterX() {
		return rotationCenterX;
	}

	public void setRotationCenterX(float rotationCenterX) {
		this.rotationCenterX = rotationCenterX;
	}

	public float getRotationCenterY() {
		return rotationCenterY;
	}

	public void setRotationCenterY(float rotationCenterY) {
		this.rotationCenterY = rotationCenterY;
	}

	public float getRotationCenterZ() {
		return rotationCenterZ;
	}

	public void setRotationCenterZ(float rotationCenterZ) {
		this.rotationCenterZ = rotationCenterZ;
	}

	public void setRotationCenter(float rcx, float rcy) {
		setRotationCenterX(rcx);
		setRotationCenterY(rcy);
	}

	public void setRotationCenter(float rcx, float rcy, float rcz) {
		setRotationCenterX(rcx);
		setRotationCenterY(rcy);
		setRotationCenterZ(rcz);
	}

	public float scaleX = 1;
	public float scaleY = 1;
	public float scaleZ = 1;

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		bounds.width = bounds.width * scaleX;
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		bounds.height = bounds.height * scaleY;
		this.scaleY = scaleY;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
	}

	public void setScale(float sx, float sy, float sz) {
		this.scaleX = sx;
		this.scaleY = sy;
		this.scaleZ = sz;
	}

	public float shearX = 0;
	public float shearY = 0;
	public float shearZ = 0;

	public float getShearX() {
		return shearX;
	}

	public void setShearX(float shearX) {
		this.shearX = shearX;
	}

	public float getShearY() {
		return shearY;
	}

	public void setShearY(float shearY) {
		this.shearY = shearY;
	}

	public float getShearZ() {
		return shearZ;
	}

	public void setShearZ(float shearZ) {
		this.shearZ = shearZ;
	}

	@Override
	public void setShape(DGPShape2D shape) {
		// set the shape
		super.setShape(shape);
	}

	@Override
	public void paint(PGraphics pg) {
		
		pg.pushMatrix();

		pg.translate(translateX + rotationCenterX, translateY + rotationCenterY, translateZ + rotationCenterZ);

		pg.rotateX(rotateX);
		pg.rotateY(rotateY);
		pg.rotateZ(rotateZ);
		
		pg.translate( -rotationCenterX, -rotationCenterY, -rotationCenterZ);

//		pg.scale(scaleX, scaleY, scaleZ);
		
//		fxPaintOverride(pg);
		
		super.paint(pg);

		pg.popMatrix();

	}

	public void paint(PGraphics pg, int[] cid) {
		
		pg.pushMatrix();

		pg.translate(translateX, translateY, translateZ);

		pg.rotateX(rotateX);
		pg.rotateY(rotateY);
		pg.rotateZ(rotateZ);

		pg.scale(scaleX, scaleY, scaleZ);

		fxPaintOverride(pg);
		
		super.paint(pg, cid);

		pg.popMatrix();
	}

	public void addTouchAction(Runnable r) {
		touchActions.add(r);
	}
	
	// Overide this to do any custom painting
	// currently used in DGFXComp for the background
	protected void fxPaintOverride(PGraphics pg) {
		
	}

}
