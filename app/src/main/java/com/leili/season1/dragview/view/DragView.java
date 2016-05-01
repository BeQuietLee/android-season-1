package com.leili.season1.dragview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 拖拽View
 * Created by lei.li on 4/29/16.
 */
public class DragView extends View {
	private static final String TAG = DragView.class.getSimpleName();
	int lastX, lastY;
	public DragView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	public DragView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX(), y = (int) event.getY();
//		int x = (int) event.getRawX(), y = (int) event.getRawY();
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = x;
				lastY = y;
//				Log.d(TAG, "lastX: " + lastX + ", lastY: " + lastY);
				break;
			case MotionEvent.ACTION_MOVE:
				int offsetX = x - lastX, offsetY = y - lastY;
//				Log.d(TAG, "offsetX: " + offsetX  + ", offsetY: " + offsetY);
				layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}
}
