package com.droidgraph.util;

import android.animation.ObjectAnimator;

public class ObjectAnimatorCreator implements Runnable {
	ObjectAnimator target;
	ObjectAnimator source;
	
	public ObjectAnimatorCreator(ObjectAnimator target, ObjectAnimator source) {
		this.target = target;
		this.source = source;
	}

	@Override
	public void run() {
		target = source;
	}

}
