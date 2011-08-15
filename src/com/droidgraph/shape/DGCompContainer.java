//package com.droidgraph.shape;
//
//import processing.core.PGraphics;
//
//import com.droidgraph.fx.DGCompGraphics;
//import com.droidgraph.fx.DGFXComp;
//import com.droidgraph.util.Shared;
//
//public class DGCompContainer extends DGPShape2D {
//
//	private DGCompGraphics comp = null;
//	private PGraphics pgraphics = null;
//
//	private DGFXComp parent;
//
//	private boolean pgraph = false;
//
//	public DGCompContainer(DGFXComp c, DGCompGraphics pg) {
//		parent = c;
//		comp = pg;
//		this.setBounds2D(0, 0, comp.width, comp.height);
//		pgraph = false;
//		
//	}
//
//	public DGCompContainer(DGFXComp c, PGraphics pg) {
//		parent = c;
//		this.pgraphics = pg;
//		this.setBounds2D(0, 0, pg.width, pg.height);
//		pgraph = true;
//		
//		comp = (DGCompGraphics) Shared.pApplet.createGraphics(pg.width, pg.height, "com.droidgraph.fx.DGCompGraphics");
//	}
//
//	public void paint(PGraphics pg) {
//
//		if (pgraph && pgraphics != null) {
//			pgraphics.beginDraw();
//			
//			if (parent.isBackground()) {
//				pgraphics.background(parent.getBackgroundRed(),
//								parent.getBackgroundGreen(),
//								parent.getBackgroundBlue(),
//								parent.getBackgroundAlpha());
//			}
//			
//			pgraphics.fill(parent.getRed(), parent.getGreen(),
//					parent.getBlue(), parent.getAlpha());
//			pgraphics.stroke(parent.getStrokeRed(), parent.getStrokeGreen(),
//					parent.getStrokeBlue(), parent.getStrokeAlpha());
//			pgraphics.endDraw();
//			// Draw that buff on screen as an image
//			pg.image(pgraphics, x, y, pgraphics.width, pgraphics.height);
//			
//		} else if (!pgraph && comp != null) {
//			// Draw to the buffer
//			comp.beginDraw();
//			comp.endDraw();
//			// Draw that buff on screen as an image
//			pg.image(comp, x, y, comp.width, comp.height);
//
//		}
//	}
//
//	public void update() {
//
//	}
//
//	public void setPicking(boolean b) {
//		if (!pgraph) {
//			comp.setPicking(b);
//		}
//	}
//
//	public boolean isPicking() {
//		if (pgraph) {
//			return false;
//		}
//		return comp.isPicking();
//	}
//
//}
