package com.example.simpleui4;

public class Message {

	public long id;
	public String text;
	public boolean isEncrypt;

	public Message(String text, boolean isEncrypt) {
		this.text = text;
		this.isEncrypt = isEncrypt;
	}

	public Message(long id, String text, boolean isEncrypt) {
		this.id = id;
		this.text = text;
		this.isEncrypt = isEncrypt;
	}
}
