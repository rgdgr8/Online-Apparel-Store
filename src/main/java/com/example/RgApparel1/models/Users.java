package com.example.RgApparel1.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
	@Id
	private int id;
	private String type;
	private float rangeTop = Float.MAX_VALUE;
	private float rangeBot = Float.MIN_VALUE;
	
	public Users() {}
	
	public Users(int id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public float getRangeTop() {
		return rangeTop;
	}

	public void setRangeTop(float rangeTop) {
		this.rangeTop = rangeTop;
	}

	public float getRangeBot() {
		return rangeBot;
	}

	public void setRangeBot(float rangeBot) {
		this.rangeBot = rangeBot;
	}
}
