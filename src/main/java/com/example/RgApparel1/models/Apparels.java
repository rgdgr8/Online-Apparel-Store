package com.example.RgApparel1.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Apparels {
	@Id
	private int icode;
	private String name;
	private float price;
	private String type;
	
	public int getIcode() {
		return icode;
	}
//	public void setIcode(String icode) {
//		this.icode = icode;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ItemNo: "+icode+", Name: "+name+", price: "+price;
	}
}
