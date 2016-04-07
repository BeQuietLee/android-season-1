package com.leili.season1.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.leili.season1.R;
import com.leili.season1.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default Launcher Activity
 * Created by lei.li on 4/7/16.
 */
public class MainActivity extends Activity {

	private ListView list;
	private List<String> titleList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initData();
		initViews();
	}

	private void initData() {
		StringBuilder sb = new StringBuilder();
		for (int i = 'A'; i <= 'Z'; i++) {
			sb.append((char) i);
			for (int j = 'A'; j <= 'Z'; j++) {
				sb.append((char) j);
				titleList.add(sb.toString());
				sb.deleteCharAt(1);
			}
			sb.deleteCharAt(0);
		}
	}

	private void initViews() {
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new SimpleAdapter(this, titleList));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(MainActivity.this, "id:" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
