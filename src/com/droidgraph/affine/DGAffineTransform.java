package com.droidgraph.affine;

import processing.core.PApplet;
import processing.core.PGraphics;

import com.droidgraph.scene.DGFilter;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public class DGAffineTransform extends DGFilter {

	private PApplet p;

	public DGAffineTransform() {

		renderer = Shared.renderer;
		p = Shared.pApplet;
	}

	public float translateX = 0;
	public float translateY = 0;
	public float translateZ = 0;

	public Vec3f getTranslation() {
		return new Vec3f(translateX, translateY, translateZ);
	}
	// Honeycomb animation syntax

	/**
	 * @return the translateX
	 */
	public float getTranslateX() {
		return translateX;
	}

	/**
	 * @param translateX
	 *            the translateX to set
	 */
	public void setTranslateX(float translateX) {
		this.translateX = translateX;
		this.bounds.setX(translateX);
	}

	/**
	 * @return the translateY
	 */
	public float getTranslateY() {
		return translateY;
	}

	/**
	 * @param translateY
	 *            the translateY to set
	 */
	public void setTranslateY(float translateY) {
		this.translateY = translateY;
		this.bounds.setY(translateY);
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
		setTranslateX(bounds.getX() + tbx);
		setTranslateY(bounds.getY() + tby);
	}
	
	public void translateBy(float tbx, float tby, float tbz) {
		setTranslateX(bounds.getX() + tbx);
		setTranslateY(bounds.getY() + tby);
//		setTranslateZ(bounds.z + tbz);
	}

	public float width = 0;
	public float height = 0;
	public float depth = 0;

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(float width) {
		bounds.setWidth(width);
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(float height) {
		bounds.setHeight(height);
		this.height = height;
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

	public void setAngle(float a) {
		angle = a;
	}

	public float getAngle() {
		return angle;
	}

	public float rotX = 0;
	public float rotY = 0;
	public float rotZ = 0;

	public float getRotationX() {
		return rotX;
	}

	public void setRotationX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotationY() {
		return rotY;
	}

	public void setRotationY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotationZ() {
		return rotZ;
	}

	public void setRotationZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void setRotation(float rx, float ry, float rz) {
		setRotationX(rx);
		setRotationY(ry);
		setRotationZ(rz);
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
	public void render() {
		if (!isVisible()) {
			return;
		}

		p.pushMatrix();
		p.pushStyle();

		p.translate(translateX, translateY, translateZ);

		p.rotateX(rotX);
		p.rotateY(rotY);
		p.rotateZ(rotZ);

		p.translate(width / 2, height / 2, depth / 2);
		
		p.scale(scaleX, scaleY, scaleZ);

		p.translate(-(width / 2), -(height / 2), -(depth / 2));
		
		super.render();
		
		p.popMatrix();
		p.popStyle();

	}
	
	@Override
	public void renderToPickBuffer(PGraphics p) {
		if (!isVisible()) {
			return;
		}

		p.pushMatrix();
		p.pushStyle();

		p.translate(translateX, translateY, translateZ);

		p.rotateX(rotX);
		p.rotateY(rotY);
		p.rotateZ(rotZ);

		p.translate(width / 2, height / 2, depth / 2);
		
		p.scale(scaleX, scaleY, scaleZ);

		p.translate(-(width / 2), -(height / 2), -(depth / 2));
		
		super.renderToPickBuffer(p);

		p.popMatrix();
		p.popStyle();
	}
	
    /**
     * Calculates the accumulated product of all transforms back to
     * the root of the tree.
     * The inherited implementation simply returns a shared value
     * from the parent, but SGTransform nodes must append their
     * individual transform to a copy of that inherited object.
     */
    @Override
    public Vec3f calculateCumulativeTransform() {
    	Vec3f xform = super.calculateCumulativeTransform();
        xform = new Vec3f(xform);
        concatenateInto(xform);
        Shared.p(xform);
        return xform;
    }
    
    public void concatenateInto(Vec3f at) {
        at.translate(translateX, translateY, translateZ);
    }
    
}
