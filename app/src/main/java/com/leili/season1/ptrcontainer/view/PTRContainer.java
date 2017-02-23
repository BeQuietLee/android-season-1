package com.leili.season1.ptrcontainer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import com.leili.season1.R;

import junit.framework.Assert;

/**
 * 下拉刷新控件
 * Created by lei.li@dianping.com on 2/23/17.
 */

public class PTRContainer extends ViewGroup {
	private static final String TAG = PTRContainer.class.getSimpleName();

	private int rawY; // 手指按下时的纵坐标
	private Scroller mScroller; // 控制窗口滑动
	private int mScreenHeight; // 屏幕高度，构造方法中初始化
	private View headerView, footerView;

	public PTRContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PTRContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mScroller = new Scroller(getContext());
		mScreenHeight = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, mScreenHeight);

		headerView = new View(getContext());
		headerView.setBackgroundResource(R.color.red);
		headerView.setLayoutParams(lp);

		footerView = new View(getContext());
		footerView.setBackgroundResource(R.color.green);
		footerView.setLayoutParams(lp);
	}

	// Y轴回弹
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int y = (int) event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				rawY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				int dY = y - rawY;
				scrollBy(0, -dY);
				Log.d(TAG, "y=" + y + ", dY=" + dY);
				rawY = y;
				break;
			case MotionEvent.ACTION_UP:
//				Toast.makeText(getContext(), "ACTION_UP", Toast.LENGTH_SHORT).show();
				mScroller.startScroll(0, getScrollY(), 0, -getScrollY()); // 回弹
				postInvalidate();
				break;
		}
		return true;
	}

	// ViewGroup需要测量每一个子View
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childCount = getChildCount();
		for (int i=0;i<childCount;i++){
			View childView = getChildAt(i);
			measureChild(childView,widthMeasureSpec,heightMeasureSpec);
		}
		measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
		measureChild(footerView, widthMeasureSpec, heightMeasureSpec);
	}

	// 在childView上下各增加一个View，作为下拉／上拉时的展示
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		Assert.assertEquals(1, childCount);
		View childView = getChildAt(0);
		childView.layout(l, t, r, b);

		headerView.layout(l, t - mScreenHeight, r, b - mScreenHeight);

		footerView.layout(l, t + mScreenHeight, r, b + mScreenHeight);
	}
}
