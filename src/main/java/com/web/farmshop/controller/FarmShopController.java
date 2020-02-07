
package com.web.farmshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.model.Order;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;
import com.web.farmshop.service.FarmShopService;

@RestController
@RequestMapping("farmshop")
public class FarmShopController {

	@Autowired
	private FarmShopService farmShopService;

	@GetMapping(value = "/flock", produces = "application/json")
	public FlockOverview getFlockOverview() {
		return farmShopService.getFlockOverview();

	}

	@GetMapping(value = "/stock", produces = "application/json")
	public Products getProductsStock() {
		return farmShopService.getProductsStock();

	}

	@PostMapping(value = "/orders", produces = "text/plain", consumes = "application/json")
	public ResponseEntity<Object> placeOrder(@RequestBody Order order) {

		if (farmShopService.placeOrder(order)) {
			return new ResponseEntity<Object>("Your order is successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Not enough stock", HttpStatus.OK);
		}

	}

	@GetMapping(value = "/orders", produces = "application/json")
	public OrderHistory getOrderHistory() {
		return farmShopService.getOrderHistory();

	}

}
