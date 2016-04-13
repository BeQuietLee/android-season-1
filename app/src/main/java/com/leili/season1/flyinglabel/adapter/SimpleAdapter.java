package com.leili.season1.flyinglabel.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leili.season1.R;

import java.util.List;

/**
 * Created by lei.li on 4/7/16.
 */
public class SimpleAdapter extends BaseAdapter {
	private static final String TAG = SimpleAdapter.class.getSimpleName();
	private Context context;
	private List<String> titleList;
	private Animation pushFromRight, pushFromLeft;

	public SimpleAdapter(Context context, List<String> titleList) {
		this.context = context;
		this.titleList = titleList;
		pushFromRight = AnimationUtils.loadAnimation(context, R.anim.push_from_right);
		pushFromLeft = AnimationUtils.loadAnimation(context, R.anim.push_from_left);
	}

	@Override
	public int getCount() {
		return titleList.size();
	}

	@Override
	public Object getItem(int position) {
		return titleList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "position = " + position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			convertView.setTag(new ViewHolder());
		}
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.index = (TextView) convertView.findViewById(R.id.index);
		viewHolder.title = (TextView) convertView.findViewById(R.id.title);
		viewHolder.label = convertView.findViewById(R.id.label);

		viewHolder.index.setText("id:" + position);
		viewHolder.title.setText((String) getItem(position));
		viewHolder.id = position;

		// 注意！这里必须为每一个item分配独立的动画，不然会出现复用动画的情况！
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.push_from_left);
		viewHolder.label.startAnimation(anim);

		return convertView;
	}

	private class ViewHolder {
		int id;
		TextView index;
		TextView title;
		View label;
	}
}
