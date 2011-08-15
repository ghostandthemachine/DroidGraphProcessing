//package com.droidgraph.fx;
//
//import processing.core.PGraphics;
//
//import com.droidgraph.shape.Image;
//
//public class DGFXImage extends DGFXShape {
//
//	private boolean picking = false;
//
//	public void setPicking(boolean b) {
//		picking = b;
//	}
//
//	@Override
//	public void paint(PGraphics pg) {
//
//		pg.pushMatrix();
//
//		pg.translate(translateX + rotationCenterX,
//				translateY + rotationCenterY, translateZ + rotationCenterZ);
//
//		pg.rotateX(rotateX);
//		pg.rotateY(rotateY);
//		pg.rotateZ(rotateZ);
//
//		pg.translate(-rotationCenterX, -rotationCenterY, -rotationCenterZ);
//
//		pg.scale(scaleX, scaleY, scaleZ);
//
//		if (picking) {
//			pg.fill(getRed(), getGreen(), getBlue(), getAlpha());
//			pg.rect(0, 0, shape.getBounds().width, shape.getBounds().height);
//			picking = false;
//		} else {
//			if (shape != null) {
//				pg.fill(fill[0], fill[1], fill[2], fill[3]);
//				pg.stroke(strokeColor[0], strokeColor[1], strokeColor[2],
//						strokeColor[3]);
//				shape.paint(pg);
//			}
//		}
//
//		pg.popMatrix();
//
//	}
//
//	public void setImage(Image image) {
//		setShape(image);
//	}
//
//}
