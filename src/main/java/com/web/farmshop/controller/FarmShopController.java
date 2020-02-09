
package com.web.farmshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * @author Sudhakaran Vasudevan
 *
 */
@RestController
@RequestMapping(value="/farmshop")
public class FarmShopController {

	@Autowired
	private FarmShopService farmShopService;
	
	@Value("${order.success.message}")
	private String orderSuccessful;
	
	@Value("${order.failure.message}")
	private String orderFailure;
	

	/**
	 * @return
	 */
	@GetMapping(value = "/flock", produces = "application/json")
	public FlockOverview getFlockOverview() {
		return farmShopService.getFlockOverview();

	}

	/**
	 * @return
	 */
	@GetMapping(value = "/stock", produces = "application/json")
	public Products getProductsStock() {
		return farmShopService.getProductsStock();

	}

	/**
	 * @param order
	 * @return
	 */
	@PostMapping(value = "/orders", produces = "text/plain", consumes = "application/json")
	public ResponseEntity<Object> placeOrder(@RequestBody Order order) {

		if (farmShopService.placeOrder(order)) {
			return new ResponseEntity<Object>(orderSuccessful, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>(orderFailure, HttpStatus.OK);
		}

	}

	/**
	 * @return
	 */
	@GetMapping(value = "/orders", produces = "application/json")
	public OrderHistory getOrderHistory() {
		return farmShopService.getOrderHistory();

	}

}
