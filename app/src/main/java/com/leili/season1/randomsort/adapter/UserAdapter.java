package com.leili.season1.randomsort.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.randomsort.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随机用户Adapter
 * Created by lei.li on 4/29/16.
 */
public class UserAdapter extends BaseAdapter {

	private Context context;
	private List<User> userList;
	private User selectedUser;

	public UserAdapter(Context context, List<User> userList) {
		this.context = context;
		this.userList = userList;
	}

	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_item, null);
			convertView.setTag(new ViewHolder((TextView) convertView.findViewById(R.id.user), (TextView) convertView.findViewById(R.id.num)));
		}
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		User user = (User) getItem(position);
		viewHolder.tvUserName.setText(user.getName());
		viewHolder.tvNum.setText("" + user.getNum());
		if (user == selectedUser) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		return convertView;
	}

	static class ViewHolder {
		public ViewHolder(TextView tvUserName, TextView tvNum) {
			this.tvUserName = tvUserName;
			this.tvNum = tvNum;
		}

		TextView tvUserName, tvNum;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
