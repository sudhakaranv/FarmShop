package com.web.farmshop.model;

/**
 * @author Eigenaar
 *
 */
public class Order {

	private String customer;
	private Products orderedProducts;

	public Products getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrder(Products orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

}
