package com.web.farmshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.web.farmshop.model.FlockInput;
import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.model.Goat;
import com.web.farmshop.model.Lamb;
import com.web.farmshop.model.Order;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;
import com.web.farmshop.model.Sheep;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@Service
public class FarmShopService {
	
	private static final Logger logger = LoggerFactory.getLogger(FarmShopService.class);

	private FlockInput flockInput;
	
	@Autowired
	private FlockOverview flockOverview;

	@Autowired
	private OrderHistory orderHistory;
	
	private Products productsStock=null;

	
	@Value("${application.input.file}")
	private String fileLocation;
	
	@Value("${goat.milk.volume}")
	private int milkPerGoat;
	
	@Value("${sheep.milk.volume}")
	private int milkPerSheep;
	
	
	/**
	 * This method will load input data on application startup
	 */
	@PostConstruct
	public void loadInputOnApplicationStartUp() {
		ObjectMapper mapper = new XmlMapper();
		Resource resource = new ClassPathResource(fileLocation);

		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String xml = new String(bdata, StandardCharsets.UTF_8);
			flockInput = mapper.readValue(xml, FlockInput.class);
		} catch (IOException e) {
			logger.error("Unable to load input xml: " + e.getMessage());
		}

	}
	

	
	/**
	 * @return
	 */
	public Products getProductsStock() {
		
		if(productsStock==null)
		{
			productsStock = new Products();	
			
			List<Sheep> sheeps=flockInput.getSheeps();
			List<Lamb> lambs=flockInput.getLambs();
			List<Goat> goats=flockInput.getGoats();
			
			AtomicInteger totalWoolSizeinKg = new AtomicInteger(0);
			AtomicInteger totalMilkVolumeInLtr = new AtomicInteger(0);		
			
			//Calculate total wool size and milk volume of sheeps
			sheeps.parallelStream()
			.forEach(sheep ->
			{
				totalWoolSizeinKg.set(totalWoolSizeinKg.get()+sheep.getWool());
				
				if (sheep.getSex()== 'f') 
					totalMilkVolumeInLtr.set(totalMilkVolumeInLtr.get()+30); 			  
				
			});
					
			// Calculate total wool size of lambs and add it to total wool size of flock
			lambs.parallelStream().
			forEach(lamb ->
			{
				totalWoolSizeinKg.set(totalWoolSizeinKg.get()+lamb.getWool());
			});
				
			//Calculate total milk volume of goats and add it total flock volume
			Long goatMilkVol=(goats.parallelStream()
					.filter(goat -> goat.getSex()=='f')
					.count()) * 50;
			
			totalMilkVolumeInLtr.set(totalMilkVolumeInLtr.get()+goatMilkVol.intValue());	
			
			//set Products stock with total milk and wool values
			
			productsStock.setMilk(totalMilkVolumeInLtr.get());
			productsStock.setWool(totalWoolSizeinKg.get());
		}
		
		return productsStock;
	}
	
	

	/**
	 * @return
	 */
	public OrderHistory getOrderHistory() {
		return orderHistory;
	}
	
	
	
	/**
	 * @return
	 */
	public FlockOverview getFlockOverview() {
		flockOverview.setFlockInput(flockInput);
		return flockOverview;
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
     
		//Get size/volume of ordered products
		int orderedMilk = orderedProducts.getMilk();
		int orderedWool = orderedProducts.getWool();
		
		//Get available stock
		int milkStock = productsStock.getMilk();
		int woolStock = productsStock.getWool();

		if ((orderedMilk > milkStock) || (orderedWool > woolStock)) {
			logger.info("Not enough stock to process the order. Order failed for customer : "+customer);
			return false;
		} else {
			//Reduce available stock based on successful order
			productsStock.setMilk(milkStock - orderedMilk);
			productsStock.setWool(woolStock - orderedWool);
			logger.info("Successful order placed. Customer( "+customer+") purchased "+ orderedMilk+"ltr of Milk and "+orderedWool+"kg of wool");
			getOrderHistory().getOrders().add(order);
			return true;
		}

	}
	
	

}
