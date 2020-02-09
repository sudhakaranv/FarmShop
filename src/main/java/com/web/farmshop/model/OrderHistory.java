package com.web.farmshop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Eigenaar
 *
 */
@Component
public class OrderHistory {

	@JsonValue
	private List<Order> orders;

	public List<Order> getOrders() {
		if (orders == null) {
			orders = new ArrayList<>();
		}
		return orders;
	}
	
	public List<Order> setOrders(List<Order> orders) {	
		return this.orders=orders;
	}

}
