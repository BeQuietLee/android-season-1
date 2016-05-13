package com.leili.season1.randomsort.entity;

/**
 * 随机用户
 * Created by lei.li on 4/29/16.
 */
public class User implements Comparable<User> {

	private static int index = 0;

	private static final int DEFAULT_NUM = -1; // -1表示未经初始化
	private int id;
	private String name;
	private int num = DEFAULT_NUM;

	public User(String name) {
		this(name, DEFAULT_NUM);
	}

	public User(String name, int num) {
		this.id = index++;
		this.name = name;
		this.num = num;
	}

	// 按照num从小到大排序
	@Override
	public int compareTo(User another) {
		return num < another.num ? -1 : (num == another.num ? 0 : 1);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
