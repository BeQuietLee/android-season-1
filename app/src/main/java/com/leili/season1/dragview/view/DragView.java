package com.leili.season1.dragview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 拖拽View
 * Created by lei.li on 4/29/16.
 */
public class DragView extends View {
	private static final String TAG = DragView.class.getSimpleName();
	int lastX, lastY;
	private Scroller mScroller;
	private Thread recordingThread;
	public DragView(Context context, AttributeSet attr) {
		super(context, attr);
		mScroller = new Scroller(context);
		if (recordingThread == null) {
			recordingThread = initThread();
			recordingThread.start();
		}
	}
	public DragView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		mScroller = new Scroller(context);
		if (recordingThread == null) { // 只能初始化一次，否则会多开启一个线程
			recordingThread = initThread();
			recordingThread.start();
		}
	}

	private Thread initThread() {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				// Log scroller position
				while (true) {
					int scrollerX = mScroller.getCurrX(), scrollerY = mScroller.getCurrY();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.e(TAG, "Fail to sleep!", e);
					}
				}
			}
		});
	};

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			int curX = mScroller.getCurrX(), curY = mScroller.getCurrY();
			((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX(), y = (int) event.getY();
//		int x = (int) event.getRawX(), y = (int) event.getRawY();
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mScroller.abortAnimation();
				lastX = x;
				lastY = y;
//				Log.d(TAG, "lastX: " + lastX + ", lastY: " + lastY);
				break;
			case MotionEvent.ACTION_MOVE:
				int offsetX = x - lastX, offsetY = y - lastY;
//				Log.d(TAG, "offsetX: " + offsetX  + ", offsetY: " + offsetY);
				// 变更view位置的几种方法，只有方法2才能结合Scroller做出返回初始位置的动画效果
				// 1. layout
//				layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
				// 2. getParent().scrollBy
				((View) getParent()).scrollBy(-offsetX, -offsetY);
				// 3. offsetLeftAndRight, offsetTopAndBottom
//				offsetLeftAndRight(offsetX);
//				offsetTopAndBottom(offsetY);
				break;
			case MotionEvent.ACTION_UP:
				View viewGroup = (View) getParent();
				mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY(), 10000); // 10s
				invalidate();
				break;
		}
		return true;
	}
}
