package com.leili.season1.randomsort.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.leili.season1.R;
import com.leili.season1.randomsort.adapter.UserAdapter;
import com.leili.season1.randomsort.entity.User;
import com.leili.season1.util.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 抽签并排序
 * Created by lei.li on 4/29/16.
 */
public class RandomSortActivity extends Activity {
	private static final String TAG = RandomSortActivity.class.getSimpleName();

	private ListView lvUsers; // 用户列表
	private TextView tvRandNum; // 两位随机数
	private Button btnGetRand, btnSortUsers; // 取号，排序
	private Random rand = new Random(System.currentTimeMillis()); // SEED
	private static final int MAX = 100; // 获取0~99之间的随机数
	private UserAdapter userAdapter;
	private List<User> userList;
	private User selectedUser;
	private AlertDialog.Builder addUserDialogBuilder;

	// 新增User
	private View.OnClickListener addUserListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// Important! Don't show the same dialog, create a new one.
			addUserDialogBuilder = new AlertDialog.Builder(RandomSortActivity.this);
			addUserDialogBuilder.setTitle("请输入用户姓名");
			final EditText etInput = new EditText(RandomSortActivity.this);
			addUserDialogBuilder.setView(etInput);
			addUserDialogBuilder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					addUser(new User(etInput.getText().toString()));
				}
			});
			addUserDialogBuilder.show();
		}
	};

	// 抽取随机数
	private View.OnClickListener getRandListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int mRand = rand.nextInt(MAX);
			if (selectedUser == null) {
				ViewUtils.showShortToast(RandomSortActivity.this, "请先在左侧选择一名用户");
			} else {
				selectedUser.setNum(mRand);
				tvRandNum.setText("" + mRand);
				userAdapter.notifyDataSetChanged();
			}
		}
	};

	// 排序
	private View.OnClickListener sortListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Collections.sort(userList);
			userAdapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_sort_activity);
		initData();
		initViews();
	}

	private void initViews() {
		lvUsers = (ListView) findViewById(R.id.users_list);
		lvUsers.setAdapter(userAdapter);
		lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedUser = userList.get(position);
				userAdapter.setSelectedUser(selectedUser);
				userAdapter.notifyDataSetChanged();
				tvRandNum.setText("" + ((User) userAdapter.getItem(position)).getNum());
			}
		});
		lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				AlertDialog.Builder removeDlg = new AlertDialog.Builder(RandomSortActivity.this);
				removeDlg.setTitle("确认删除“" + ((User) userAdapter.getItem(position)).getName() + "”？");
				final int mPosition = position;
				removeDlg.setPositiveButton("是的", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (selectedUser == userList.get(mPosition)) {
							selectedUser = null;
							userAdapter.setSelectedUser(null);
						}
						userList.remove(mPosition);
						userAdapter.notifyDataSetChanged();
					}
				});
				removeDlg.show();
				return true;
			}
		});
		tvRandNum = (TextView) findViewById(R.id.rand_num);
		btnGetRand = (Button) findViewById(R.id.get_rand);
		btnGetRand.setOnClickListener(getRandListener);
		btnSortUsers = (Button) findViewById(R.id.sort_users);
		btnSortUsers.setOnClickListener(sortListener);
		TextView tvAddUser = new TextView(this);
		tvAddUser.setText("+");
		tvAddUser.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_32sp));
		tvAddUser.setGravity(Gravity.CENTER);
		tvAddUser.setOnClickListener(addUserListener);
		lvUsers.addFooterView(tvAddUser);
	}

	private void initData() {
		userList = new ArrayList<>();
		userAdapter = new UserAdapter(this, userList);
	}

	// 更新随机数tv
	private void updateRandNum(int randNum) {
		if (tvRandNum != null) {
			tvRandNum.setText("" + randNum);
		}
	}

	private void addUser(User user) {
		userList.add(user);
		userAdapter.notifyDataSetChanged();
	}

	private void removeUser(int i) {
		userList.remove(i);
		userAdapter.notifyDataSetChanged();
	}

}
