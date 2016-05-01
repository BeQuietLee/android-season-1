package com.leili.season1.randomsort.entity;

import java.util.Comparator;

/**
 * 随机用户
 * Created by lei.li on 4/29/16.
 */
public class User implements Comparable<User> {

	private static int count = 0;
	private int id;
	private String name;
	private int num;

	public User(String name, int num) {
		this.id = count++;
		this.name = name;
		this.num = num;
	}

	@Override
	public int compareTo(User another) {
		return num < another.num ? -1 : (num == another.num ? 0 : 1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
