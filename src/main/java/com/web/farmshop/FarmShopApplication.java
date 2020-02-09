package com.web.farmshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sudhakaran Vasudevan
 *
 */
@SpringBootApplication
public class FarmShopApplication {

	private static final Logger logger = LoggerFactory.getLogger(FarmShopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FarmShopApplication.class, args);
		logger.debug("--FarmShop Application Started--");
	}

}
