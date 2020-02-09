package com.web.farmshop.controller;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.web.farmshop.model.Animal;
import com.web.farmshop.model.FlockInput;
import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.service.FarmShopService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(FarmShopController.class)
class FarmShopControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FarmShopService farmShopService;
	
	@MockBean
	private Animal animal;
	
	@MockBean
	private FlockOverview flockOverview;
	
	@MockBean
	private FlockInput flockInput;

	
	@Test
	public void getFlockOverviewTest() throws Exception
	{
		
		
		/*
		 * animal.setType("sheep"); animal.setName("Mary"); animal.setSex('f');
		 * animal.setWool(10);
		 * 
		 * List<Animal> sheeps=new ArrayList<>(); sheeps.add(animal);
		 * 
		 * flockInput.setSheeps(); flockOverview.setFlockInput(flock);
		 * 
		 * Mockito.when(farmShopService.getFlockOverview()).thenReturn(flock);
		 */
	}
	
	public void getProductsStockTest() throws Exception
	{
		
	}
	
	public void placeOrderTest() throws Exception
	{
		
	}
	
	public void getOrderHistoryTest() throws Exception
	{
		
	}
	
	
}
