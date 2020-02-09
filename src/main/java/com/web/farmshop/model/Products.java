package com.web.farmshop.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@Component
public class Products {
    
	@JsonInclude(Include.NON_DEFAULT)
	private int wool;
	@JsonInclude(Include.NON_DEFAULT)
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
