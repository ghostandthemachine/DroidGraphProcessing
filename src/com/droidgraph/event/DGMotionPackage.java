package com.droidgraph.event;

import java.util.ArrayList;

import android.view.MotionEvent;

public class DGMotionPackage {
	
	private ArrayList<DGMotionEvent> pack = new ArrayList<DGMotionEvent>();
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private long time = 0;
	private MotionEvent event;
	
	
	
	/**
	 * need to update the action pointer ID it is fucked up
	 * @param e
	 */
	
	
	public DGMotionPackage(MotionEvent e) {
		this.event = e;
	}
	
	public void add(DGMotionEvent ev) {
		if(pack.size() == 0) {
			time = ev.getTime();
			pack.add(ev);
			ids.add(ev.getID());
		} else if(time == ev.getTime()){
			pack.add(ev);
			ids.add(ev.getID());
		}
	}
	
	public Integer[] getIDs() {
		Integer[] output = new Integer[ids.size()];
		return ids.toArray(output);
	}
	
	public int getPointerCount() {
		return pack.size();
	}
	
	public DGMotionEvent[] toArray() {
		DGMotionEvent[] output = new DGMotionEvent[pack.size()];
		return pack.toArray(output);
	}
	
	public DGMotionEvent[] getEvents(int...index) {
		ArrayList<DGMotionEvent> out = new ArrayList<DGMotionEvent>();
		for(int i = 0; i < index.length; i++) {
			out.add(pack.get(index[i]));
		}
		DGMotionEvent[] output = new DGMotionEvent[out.size()];
		return out.toArray(output);
	}

	public long getTime() {
		return time;
	}
	
	public void clear() {
		pack.clear();
	}
	
	public int getAction() {
		return event.getAction();
	}
	
	public int getActionIndex() {
		return event.getPointerId(event.getActionIndex());
	}
	
	public int getActionMasked() {
		return event.getActionMasked();
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("DGMotionPackage:");
		output.append(" time-stamp:" + getTime());
		output.append(" pack-size:" + getPointerCount());
		output.append(" action:" + event.getAction());
		output.append(" actionPointer:" + event.getActionIndex());
		output.append(" ");
		for(int i = 0; i < getPointerCount(); i++) {
			output.append(" - " + pack.get(i).toString());
		}
		
		return output.toString();
	}

}
