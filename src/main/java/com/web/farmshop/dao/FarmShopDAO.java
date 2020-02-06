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
import com.web.farmshop.model.Animal;
import com.web.farmshop.model.Flock;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;

@Repository
public class FarmShopDAO {

	private static final Logger logger = LoggerFactory.getLogger(FarmShopDAO.class);

	private Flock flock;

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
			flock = mapper.readValue(xml, Flock.class);
		} catch (IOException e) {
			logger.error("Unable to load input xml: " + e.getMessage());
			flock = null;
		}

	}

	/**
	 * This method will calculate the Stock of products(milk&wool) from deserialize
	 * input object
	 */
	private void getProductsStockFromInput() {
		int totalWoolSizeinKg = 0;
		int totalMilkVolumeInLtr = 0;

		for (Animal animal : flock.getFlockOfAnimal()) {
			totalWoolSizeinKg += animal.getWool();
			char sex = animal.getSex();
			String type = animal.getType();

			if ((type.equals("sheep")) && (sex == 'f')) {
				totalMilkVolumeInLtr += 30;
			} else if ((type.equals("goat")) && (sex == 'f')) {
				totalMilkVolumeInLtr += 50;
			}

		}

		productsStock.setMilk(totalMilkVolumeInLtr);
		productsStock.setWool(totalWoolSizeinKg);
	}

	public Flock getFlock() {
		return flock;
	}

	public Products getProductsStock() {
		return productsStock;
	}

	public OrderHistory getOrderHistory() {
		return orderHistory;
	}

}
