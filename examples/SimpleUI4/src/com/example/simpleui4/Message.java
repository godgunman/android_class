package com.example.simpleui4;

public class Message {

	private long id;
	private String text;
	private boolean isEncrypt;

	public Message(String text, boolean isEncrypt) {
		this.text = text;
		this.setEncrypt(isEncrypt);
	}

	public Message(long id, String text, boolean isEncrypt) {
		this.id = id;
		this.text = text;
		this.setEncrypt(isEncrypt);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

}
