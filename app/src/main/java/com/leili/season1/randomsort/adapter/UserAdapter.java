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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * 随机用户Adapter
 * Created by lei.li on 4/29/16.
 */
public class UserAdapter extends BaseAdapter {

	private Context context;
	private List<User> userList;
	private Map<Integer, User> userMap;
	private User selectedUser;

	public UserAdapter(Context context, List<User> userList) {
		this.context = context;
		this.userList = userList;
	}

	public void addUser(User user) {
		initData();
		addUserIntoListAndMap(user);
		notifyDataSetChanged();
	}

	public void updateUser(int userId, int userNum) {
		if (userId == selectedUser.getId()) {
			selectedUser.setNum(userNum);
		}
		User mUser = userMap.get(userId);
		mUser.setNum(userNum);
		notifyDataSetChanged();
	}

	private void addUserIntoListAndMap(User user) {
		userList.add(user);
		userMap.put(user.getId(), user);
	}

	private void initData() {
		if (userList == null) {
			userList = new ArrayList<>();
		}
		if (userMap == null) {
			userMap = new HashMap<>();
		}
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
			convertView.setBackgroundColor(context.getResources().getColor(R.color.red));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.green));
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

	public void setSelectedUser(int position) {
		setSelectedUser(userList.get(position));
		notifyDataSetChanged();
	}

	public void removeUser(int position) {
		userList.remove(position);
		notifyDataSetChanged();
	}

	public void sortUsersByNumUp() {
		Collections.sort(userList);
		notifyDataSetChanged();
	}

}
