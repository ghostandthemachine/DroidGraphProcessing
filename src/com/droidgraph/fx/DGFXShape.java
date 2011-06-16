package com.droidgraph.fx;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.droidgraph.scene.DGShape;
import com.droidgraph.shape.DGPShape2D;
import com.droidgraph.util.Shared;

public class DGFXShape extends DGShape {
	
	private ArrayList<Runnable> touchActions = new ArrayList<Runnable>();
	
	public DGFXShape() {
		renderer = Shared.renderer;
	}
	
	public float translateX = 0;
	public float translateY = 0;
	public float translateZ = 0;

	// Honeycomb animation syntax

	/**
	 * @return the translateX
	 */
	public float getTranslateX() {
		return translateX;
	}

	/**
	 * @param translateX the translateX to set
	 */
	public void setTranslateX(float translateX) {
		this.translateX = translateX;
	}

	/**
	 * @return the translateY
	 */
	public float getTranslateY() {
		return translateY;
	}

	/**
	 * @param translateY the translateY to set
	 */
	public void setTranslateY(float translateY) {
		this.translateY = translateY;
	}

	/**
	 * @return the translateZ
	 */
	public float getTranslateZ() {
		return translateZ;
	}

	/**
	 * @param translateZ the translateZ to set
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
	 * @param width the width to set
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
	 * @param height the height to set
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
	 * @param depth the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float angle = 0;
	
	public void setAngle(float a) {
		angle = a;
	}
	
	public float getAngle() {
		return angle;
	}

	public float rotX = 0;
	public float rotY = 0;
	public float rotZ = 0;

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public void setRotation(float rx, float ry, float rz) {
		setRotX(rx);
		setRotY(ry);
		setRotZ(rz);
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
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
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
	
	private String renderer;
	
	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public void setShape(DGPShape2D shape) {
		//set the shape
		super.setShape(shape);
	}
	
	@Override
	public void paint(PGraphics pg) {
		pg.pushMatrix();

		if (renderer == PApplet.A3D) {

			pg.translate(translateX, translateY, translateZ);

			pg.rotateX(rotX);
			pg.rotateY(rotY);
			pg.rotateZ(rotZ);

			pg.scale(scaleX, scaleY, scaleZ);
			
			super.paint(pg);

		} else {
			pg.translate(translateX, translateY);
			
			pg.rotate(angle);
			

			super.paint(pg);
		}

		pg.popMatrix();

	}
	
	public void addTouchAction(Runnable r) {
		touchActions.add(r);
	}
	
}
