package com.web.farmshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.farmshop.dao.FarmShopDAO;
import com.web.farmshop.model.Flock;
import com.web.farmshop.model.Order;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FarmShopService {
	
	private static final Logger logger = LoggerFactory.getLogger(FarmShopService.class);

	@Autowired
	private FarmShopDAO farmShopDAO;

	public Flock getFlock() {
		return farmShopDAO.getFlock();
	}

	public Products getProductsStock() {
		return farmShopDAO.getProductsStock();

	}

	public OrderHistory getOrderHistory() {
		return farmShopDAO.getOrderHistory();
	}

	/**
	 * @param order
	 * @return true/false
	 * 
	 *         This method is to place the order from stock. if the order is
	 *         successful productStock will be updated accordingly
	 */
	public boolean placeOrder(Order order) {

		Products productsStock = getProductsStock();
		Products orderedProducts = order.getOrderedProducts();
		String customer=order.getCustomer();

		int orderedMilk = orderedProducts.getMilk();
		int orderedWool = orderedProducts.getWool();
		int milkStock = productsStock.getMilk();
		int woolStock = productsStock.getWool();

		if ((orderedMilk > milkStock) || (orderedWool > woolStock)) {
			logger.info("Not enough stock to process the order. Order failed for customer : "+customer);
			return false;
		} else {
			productsStock.setMilk(milkStock - orderedMilk);
			productsStock.setWool(woolStock - orderedWool);
			logger.info("Successful order placed. Customer( "+customer+") purchased "+ orderedMilk+"ltr of Milk and "+orderedWool+"kg of wool");
			getOrderHistory().getOrders().add(order);
			return true;
		}

	}

}
