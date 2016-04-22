package com.leili.season1.uithread.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.util.ViewUtils;

/**
 * 非UI线程更新UI
 * 开发闪惠广告功能时翻了这个愚蠢的错误，引以为戒！
 * Created by lei.li on 4/21/16.
 */
public class UpdateUIActivity extends Activity {
	private ViewGroup containerLayout;
	private Button btnGo;

	private String s = "New Thread!";
	private int index = 0;

	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_ui_activity);
		initData();
		initViews();
	}

	private void initData() {
		runnable = new Runnable() {
			@Override
			public void run() {
				containerLayout.addView(makeTextView(index++));
			}
		};
	}

	private void initViews() {
		containerLayout = (ViewGroup) findViewById(R.id.container_layout);
		btnGo = (Button) findViewById(R.id.go);

		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runOnUiThread(runnable);
			}
		});
	}

	private TextView makeTextView(int index) {
		TextView tv = new TextView(this);
		tv.setText("TextView, index: " + index);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_18sp));
		tv.setTextColor(getResources().getColor(R.color.black));
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		return tv;
	}

}
