package com.leili.season1.ptrcontainer.activity;

import android.app.Activity;
import android.os.Bundle;

import com.leili.season1.R;

/**
 * 自定义下拉刷新控件
 * Created by lei.li@dianping.com on 2/23/17.
 */

public class PTRContainerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.ptr_container_activity);
	}
}
