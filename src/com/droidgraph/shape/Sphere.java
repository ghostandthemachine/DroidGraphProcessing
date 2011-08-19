package com.droidgraph.shape;

import processing.core.PGraphics;

public class Sphere extends DGPShape3D {

	private int ures = 16;
	private int vres = 16;
	
	public Sphere() {
		this(1);
	}
	
	public Sphere(float radius) {
		bounds.setBounds(0, 0, 0, radius, radius, radius);
	}
	
	public void setRadius(float radius) {
		bounds.setBounds(0, 0, 0, radius, radius, radius);
	}
	
	public float getRadius() {
		return (bounds.getWidth() + bounds.getHeight() + bounds.getDepth()) / 3;
	}
	
	/**
	 * @return the ures
	 */
	public int getUres() {
		return ures;
	}

	/**
	 * @param ures the ures to set
	 */
	public void setUres(int ures) {
		this.ures = ures;
	}

	/**
	 * @return the vres
	 */
	public int getVres() {
		return vres;
	}

	/**
	 * @param vres the vres to set
	 */
	public void setVres(int vres) {
		this.vres = vres;
	}
	
	/**
	 * @param ures the ures to set
	 * @param vres the vres to set
	 */
	public void setResolution(int ures, int vres) {
		this.ures = ures; 
		this.vres = vres;
	}

	@Override
	public void paint(PGraphics p) {
		p.sphereDetail(ures, vres);
		p.sphere((bounds.getWidth() + bounds.getHeight() + bounds.getDepth()) / 3);
	}
	
}
