package com.leili.season1.homepage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.customfont.activity.CustomFontActivity;
import com.leili.season1.dragview.activity.DragViewActivity;
import com.leili.season1.flyinglabel.activity.FlyingLabelActivity;
import com.leili.season1.okhttp.activity.OkHttpActivity;
import com.leili.season1.randomsort.activity.RandomSortActivity;
import com.leili.season1.uithread.activity.UpdateUIActivity;
import com.leili.season1.util.ViewUtils;

/**
 * 首页
 * Created by lei.li on 4/13/16.
 */
public class HomepageActivity extends Activity {
	private static final String TO_DO_MSG = "紧锣密鼓开发中，敬请期待！";
	LinearLayout wholeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_activity);
		initViews();
		inflateListItems();
	}

	private void initViews() {
		wholeLayout = (LinearLayout) findViewById(R.id.whole_layout);
	}

	private void inflateListItems() {
		// 飞入标签
		wholeLayout.addView(inflateListItem("1", "飞入标签", "大麦app首页列表项滑入标签效果", FlyingLabelActivity.class));
		// OkHttp
		wholeLayout.addView(inflateListItem("2", "OkHttp", "请求百度天气API", OkHttpActivity.class));
		// 自定义字体
		wholeLayout.addView(inflateListItem("3", "自定义字体", "方块字之美", CustomFontActivity.class));
		// 非UI线程更新UI
		wholeLayout.addView(inflateListItem("4", "UI更新", "非UI线程操作UI", UpdateUIActivity.class));
		// 拖拽View
		wholeLayout.addView(inflateListItem("5", "拖拽View", "自定义拖拽View", DragViewActivity.class));
		// 俄罗斯轮盘赌
		wholeLayout.addView(inflateListItem("6", "随机排序", "搏一搏，单车变摩托", RandomSortActivity.class));
	}

	View inflateListItem(String index, String title, String desc, final Class<?> cls) {
		View item = LayoutInflater.from(this).inflate(R.layout.entry_item, null);
		TextView tvIndex = (TextView) item.findViewById(R.id.index);
		TextView tvTitle = (TextView) item.findViewById(R.id.title);
		TextView tvDesc = (TextView) item.findViewById(R.id.desc);
		Button btnGo = (Button) item.findViewById(R.id.go);

		tvIndex.setText(index);
		tvTitle.setText(title);
		tvDesc.setText(desc);
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cls == null) {
					ViewUtils.showShortToast(HomepageActivity.this, TO_DO_MSG);
				} else {
					Intent i = new Intent();
					i.setClass(HomepageActivity.this, cls);
					startActivity(i);
				}
			}
		});
		return item;
	}
}
