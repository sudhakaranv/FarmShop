package com.web.farmshop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderHistory {

	private List<Order> orders;

	public List<Order> getOrders() {
		if (orders == null) {
			orders = new ArrayList<>();
		}
		return orders;
	}

}
