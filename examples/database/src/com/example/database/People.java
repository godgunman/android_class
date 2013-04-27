package com.example.database;

import java.util.Random;

public class People {

	private final static String[] NAME = { "Jacob", "Isabella", "Ethan",
			"Sophia", "Michael", "Emma", "Jayden", "Olivia", "William", "Ava",
			"Alexander", "Emily", "Noah", "Abigail", "Daniel", "Madison",
			"Aiden", "Chloe", "Anthony", "Mia", "Joshua", "Addison", "Mason",
			"Elizabeth", "Christopher", "Ella", "Andrew", "Natalie", "David",
			"Samantha", "Matthew", "Alexis", "Logan", "Lily", "Elijah",
			"Grace", "James", "Hailey", "Joseph", "Alyssa" };

	private int id;
	private String name;
	private int age;
	private String phone;

	public People(int id, String name, int age, String phone) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.phone = phone;
	}

	public People() {
		Random r = new Random();

		this.id = r.nextInt(100000);
		this.name = NAME[r.nextInt(NAME.length)];
		this.age = r.nextInt(100);
		this.phone = String.format("09%2d-%3d-%3d", r.nextInt(99),
				r.nextInt(999), r.nextInt(999));
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String toString() {
		return String.format("Id:%d Name:%s\nAge:%d Phone:%s\n", id, name, age,
				phone);
	}

}
