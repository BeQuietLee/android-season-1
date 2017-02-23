package com.leili.season1.dragview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 拖拽View
 * Created by lei.li on 4/29/16.
 */
public class DragView extends View {
	private static final String TAG = DragView.class.getSimpleName();
	int lastX, lastY; // 手指按下时的坐标位置，在ACTION_MOVE时基于该坐标进行移动
	private Scroller mScroller;
	public DragView(Context context, AttributeSet attr) {
		super(context, attr);
		mScroller = new Scroller(context);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX(), y = (int) event.getY();
		Log.d(TAG, "onTouchEvent, x=" + x + ",y=" + y);
		Log.d(TAG, "parent.scrollX=" + ((FrameLayout)getParent()).getScrollX() + ",scrollY=" + ((FrameLayout)getParent()).getScrollY());
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mScroller.abortAnimation();
				lastX = x;
				lastY = y;
				Log.d(TAG, "lastX: " + lastX + ", lastY: " + lastY);
				break;
			case MotionEvent.ACTION_MOVE:
//				Log.d(TAG, "offsetX: " + offsetX  + ", offsetY: " + offsetY);
				// 变更view位置的几种方法，只有方法2才能结合Scroller做出返回初始位置的动画效果
				// 1. layout
//				layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
				// 2. getParent().scrollBy
//				scrollBy(offsetX, offsetY);

//				((View) getParent()).scrollBy(lastX - x, lastY - y);
				boundedScroll((View)getParent(), this, lastX - x, lastY - y);
				// 3. offsetLeftAndRight, offsetTopAndBottom
//				offsetLeftAndRight(offsetX);
//				offsetTopAndBottom(offsetY);
				break;
			case MotionEvent.ACTION_UP:
				View viewGroup = (View) getParent();
				mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY(), 2000); // 2s
				invalidate();
				break;
		}
		return true;
	}

	// 防止元素滑动出边界
	private void boundedScroll(View parent, View self, int x, int y) {
		int top = self.getTop(),
				left = self.getLeft(),
				bottom = self.getBottom(),
				right = self.getRight();
		int scrollX = parent.getScrollX(),
				scrollY = parent.getScrollY();
		int maxScrollRight = left,
				maxScrollLeft = -(parent.getMeasuredWidth() - right),
				maxScrollBottom = top,
				maxScrollTop = -(parent.getMeasuredHeight() - bottom);
		int currentScrollX = scrollX + x < maxScrollLeft ? maxScrollLeft : scrollX + x > maxScrollRight ? maxScrollRight : scrollX + x,
				currentScrollY = scrollY + y < maxScrollTop ? maxScrollTop : scrollY + y > maxScrollBottom ? maxScrollBottom : scrollY + y;
		parent.scrollTo(currentScrollX + 0, currentScrollY + 0);
	}
}
