package com.droidgraph.fx;

import java.util.ArrayList;

import processing.core.PGraphics;

import com.droidgraph.affine.DGAffineTransform;
import com.droidgraph.scene.DGFilter;
import com.droidgraph.scene.DGNode;
import com.droidgraph.scene.DGParent;
import com.droidgraph.scene.DGWrapper;
import com.droidgraph.transformation.Vec3f;
import com.droidgraph.util.Shared;

public class DGFXNode extends DGWrapper {

	private ArrayList<DGNode> children = new ArrayList<DGNode>();

	private DGNode rootNode;

	private DGAffineTransform affineNode;
	private DGNode leafNode;

	private float opacity = 1.0f;

	public DGFXNode(DGNode leaf) {
		leafNode = leaf;
		
		affineNode = new DGAffineTransform();
		
		updateTree();
//		affineNode = new DGAffineTransform();
//		affineNode.setChild(leafNode);
//		if (this.rootNode != leafNode) {
//			this.rootNode = leafNode;
//			initParent();
//		}
////		children.add(affineNode);
	}
	
	private void updateTree() {
        DGNode root = leafNode;
        root = addFilter(affineNode, root);
        if (this.rootNode != root) {
        	Shared.p("rootNode != root", this);
            this.rootNode = root;
            initParent();
        }
	}

	private DGNode addFilter(DGFilter filter, DGNode child) {
		if (filter != null) {
			filter.setChild(child);
			child = filter;
		} else {
			Shared.p("addFilter(), filter == null", this);
		}
		return child;
	}
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void renderToPickBuffer(PGraphics p) {
		if (this.isEventBlocker()) {
			super.renderToPickBuffer(p);
		}
	}

	public DGNode getLeaf() {
		return leafNode;
	}

	@Override
	protected DGNode getRoot() {
		return rootNode;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	public Vec3f getGlobalTranslation(Vec3f global) {
//		// add this nodes translation
//		global.add(getTranslation());
//		
//		DGParent parent = getParent();
//		
//		if(parent != Shared.rootNode && parent != null) {
//			
//			if(parent instanceof DGFXNode) {
//				return global
//			}
//			
//			return global;
//		}
		return null;
	}
	
	public Vec3f getTranslation() {
		return new Vec3f(getTranslateX(), getTranslateY(), getTranslateZ());
	}
	

	public float getTranslateX() {
		return affineNode.getTranslateX();
	}

	public void setTranslateX(float tx) {
		affineNode.setTranslateX(tx);
	}

	public float getTranslateY() {
		return affineNode.getTranslateY();
	}

	public void setTranslateY(float ty) {
		affineNode.setTranslateY(ty);
	}
	
	public float getTranslateZ() {
		return affineNode.getTranslateZ();
	}

	public void setTranslateZ(float tz) {
		affineNode.setTranslateZ(tz);
	}

	public float[] getTranslationArray() {
		return new float[] { affineNode.getTranslateX(),
				affineNode.getTranslateY(), affineNode.getTranslateZ() };
	}

	public void setTranslation(float[] t) {
		affineNode.setTranslation(t[0], t[1], t[2]);
	}

	public float getRotationX() {
		return affineNode.getRotationX();
	}

	public float getRotationY() {
		return affineNode.getRotationY();
	}

	public float getRotationZ() {
		return affineNode.getRotationZ();
	}

	public float[] getRotation() {
		return new float[] { affineNode.getRotationX(),
				affineNode.getRotationY(), affineNode.getRotationZ() };
	}

	public void setRotationX(float rx) {
		affineNode.setRotationX(rx);
	}

	public void setRotationY(float ry) {
		affineNode.setRotationY(ry);
	}

	public void setRotationZ(float rz) {
		affineNode.setRotationZ(rz);
	}

	public void setRotation(float[] r) {
		affineNode.setRotation(r[0], r[1], r[2]);
	}

	public void setRotationCenter(float[] rc) {
		affineNode.setRotationCenter(rc[0], rc[1], rc[2]);
	}

	public void setRotationCenter(float rcx, float rcy) {
		affineNode.setRotationCenter(rcx, rcy);
	}

	public float[] getRotationCenter() {
		return new float[] { affineNode.getRotationCenterX(),
				affineNode.getRotationCenterY(),
				affineNode.getRotationCenterZ() };
	}

	public float getScaleX() {
		return affineNode.getScaleX();
	}

	public float getScaleY() {
		return affineNode.getScaleY();
	}

	public float getScaleZ() {
		return affineNode.getScaleZ();
	}

	public float[] getScale() {
		return new float[] { affineNode.getScaleX(), affineNode.getScaleY(),
				affineNode.getScaleZ() };
	}

	public void setScaleX(float sx) {
		affineNode.setScaleX(sx);
	}

	public void setScaleY(float sy) {
		affineNode.setScaleY(sy);
	}

	public void setScaleZ(float sz) {
		affineNode.setScaleZ(sz);
	}

	public void setScale(float[] s) {
		affineNode.setScaleX(s[0]);
		affineNode.setScaleY(s[1]);
		affineNode.setScaleZ(s[2]);
	}

	public DGAffineTransform getTransform() {
		return affineNode;
	}

	@Override
	public void remove(DGNode node) {

	}

}
