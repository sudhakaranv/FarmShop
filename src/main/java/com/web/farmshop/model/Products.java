package com.web.farmshop.model;

import org.springframework.stereotype.Component;

@Component
public class Products {

	private int wool;
	private int milk;

	public int getWool() {
		return wool;
	}

	public void setWool(int wool) {
		this.wool = wool;
	}

	public int getMilk() {
		return milk;
	}

	public void setMilk(int milk) {
		this.milk = milk;
	}

}
