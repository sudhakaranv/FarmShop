package com.web.farmshop.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.web.farmshop.model.FlockInput;
import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.model.Goat;
import com.web.farmshop.model.Lamb;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;
import com.web.farmshop.model.Sheep;

@Repository
public class FarmShopDAO {

	private static final Logger logger = LoggerFactory.getLogger(FarmShopDAO.class);

	private FlockInput flockInput;
	
	@Autowired
	private FlockOverview flockOverview;

	@Autowired
	private Products productsStock;

	@Autowired
	private OrderHistory orderHistory;

	@Value("${input.file}")
	private String fileLocation;

	/**
	 * This method will load input data on application startup
	 */
	@PostConstruct
	private void init() {
		mapInputFiletoObject(fileLocation);
		getProductsStockFromInput();
	}

	/**
	 * @param inputPath
	 * 
	 * This method will deserialize the inputFile in to a Java  object using Jackson XmlMapper
	 *                 
	 */
	private void mapInputFiletoObject(String inputPath) {

		XmlMapper mapper = new XmlMapper();
		Resource resource = new ClassPathResource(inputPath);

		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String xml = new String(bdata, StandardCharsets.UTF_8);
			flockInput = mapper.readValue(xml, FlockInput.class);
		} catch (IOException e) {
			logger.error("Unable to load input xml: " + e.getMessage());
		}

	}

	/**
	 * This method will calculate the Stock of products(milk&wool) from deserialize
	 * input object
	 */
	private void getProductsStockFromInput() {
		
		int totalWoolSizeinKg = 0;
		int totalMilkVolumeInLtr = 0;
		

		for(Sheep sheep : flockInput.getSheeps()) {
			
			totalWoolSizeinKg += sheep.getWool();

			if (sheep.getSex()== 'f') {
				
				totalMilkVolumeInLtr += 30;
			} 

		}
		
        for(Lamb lamb: flockInput.getLambs()) {
			
			totalWoolSizeinKg += lamb.getWool();

		}

        for(Goat goat: flockInput.getGoats())
        {
        	
          if (goat.getSex()== 'f') {
				
				totalMilkVolumeInLtr += 50;
			} 
        }
        
		productsStock.setMilk(totalMilkVolumeInLtr);
		productsStock.setWool(totalWoolSizeinKg);
	}

	public Products getProductsStock() {
		return productsStock;
	}

	public OrderHistory getOrderHistory() {
		return orderHistory;
	}
	
	public FlockOverview getFlockOverview() {
		flockOverview.setFlockInput(flockInput);
		return flockOverview;
	}

}
