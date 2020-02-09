package com.web.farmshop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.farmshop.model.FlockInput;
import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.model.Order;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;
import com.web.farmshop.model.Sheep;
import com.web.farmshop.service.FarmShopService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FarmShopController.class)
class FarmShopControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FarmShopService farmShopService;

	@Test
	public void getFlockOverviewTest() throws Exception {
		Sheep sheep = new Sheep();
		sheep.setName("John");
		sheep.setSex('m');
		sheep.setWool(10);

		List<Sheep> sheeps = new ArrayList<>();

		sheeps.add(sheep);

		FlockInput flockInput = new FlockInput();
		flockInput.setSheeps(sheeps);

		FlockOverview flockOverview = new FlockOverview();
		flockOverview.setFlockInput(flockInput);

		
		  Mockito.when(farmShopService.getFlockOverview()).thenReturn(flockOverview);
		  
		  String URI = "/farmshop/flock";
		  
		  RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(
		  MediaType.APPLICATION_JSON);
		  
		  MvcResult result = mvc.perform(requestBuilder).andReturn();
		 
		  String expectedJson = this.mapToJson(flockOverview);
		
		  String outputInJson = result.getResponse().getContentAsString();
		  
		  assertThat(outputInJson).isEqualTo(expectedJson);
		 

	}

	@Test
	public void getProductsStockTest() throws Exception {
		
		Products productStock=new Products();
		productStock.setMilk(100);
		productStock.setWool(100);
		
		Mockito.when(farmShopService.getProductsStock()).thenReturn(productStock);
		
		String URI = "/farmshop/stock";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(
				  MediaType.APPLICATION_JSON);
				  
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		 String expectedJson = this.mapToJson(productStock);
			
		 String outputInJson = result.getResponse().getContentAsString();
		  
		 assertThat(outputInJson).isEqualTo(expectedJson);

	}

	@Test
	public void placeOrderTest() throws Exception {
		
		Products products=new Products();
		products.setMilk(10);
		products.setWool(10);
		
		Order order=new Order();
		order.setCustomer("Test customer 1");
		order.setOrder(products);
		
		
      
		String inputJson = "{" + 
				"    \"customer\":\"Test customer 1\"," + 
				"     \"order\":{" + 
				"     	 \"wool\":10" + 
				"     }" + 
				"  }";
		
		String URI = "/farmshop/orders";
		
		//Successful Request
		Mockito.when(farmShopService.placeOrder(Mockito.any(Order.class))).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.TEXT_PLAIN);

		MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String actualOutput = response.getContentAsString();
		String ExpectedOutput = "Your order is successful";
		
		assertThat(ExpectedOutput).isEqualTo(actualOutput);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void placeOrderFailureTest() throws Exception
	{
		
		String inputJson = "{" + 
				"    \"customer\":\"Test customer 1\"," + 
				"     \"order\":{" + 
				"     	 \"wool\":100" + 
				"     }" + 
				"  }";
		
		String URI = "/farmshop/orders";
		
        Mockito.when(farmShopService.placeOrder(Mockito.any(Order.class))).thenReturn(false);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.TEXT_PLAIN);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String actualOutput = response.getContentAsString();
		String ExpectedOutput = "Insufficient Wool/Milk stock to process your order. Please check the available stock and place the order again";
		
		assertThat(ExpectedOutput).isEqualTo(actualOutput);
	}

	@Test
	public void getOrderHistoryTest() throws Exception {
		
		Products products=new Products();
		products.setMilk(10);
		products.setWool(10);
		
		Order order=new Order();
		order.setCustomer("Test customer 1");
		order.setOrder(products);
		
	    List<Order> orders=new ArrayList<>();
	    orders.add(order);
	   
		OrderHistory orderHistory=new OrderHistory();
		orderHistory.setOrders(orders);

		Mockito.when(farmShopService.getOrderHistory()).thenReturn(orderHistory);
		
		String URI = "/farmshop/orders";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(
				  MediaType.APPLICATION_JSON);
				  
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		
		 String expectedJson = this.mapToJson(orderHistory);
			
		 String outputInJson = result.getResponse().getContentAsString();
		  
		 assertThat(outputInJson).isEqualTo(expectedJson);	

	}

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
