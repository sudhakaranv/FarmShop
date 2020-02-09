package com.web.farmshop.service;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.farmshop.model.Animal;
import com.web.farmshop.model.FlockOverview;
import com.web.farmshop.model.Order;
import com.web.farmshop.model.OrderHistory;
import com.web.farmshop.model.Products;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FarmShopServiceTest {
	
	@Autowired
	private FarmShopService farmShopService;
	
	@BeforeAll
	public void loadInputXml()
	{
		farmShopService.mapInputXmltoObject("input/flock_test.xml");
		
	}
	
    @Test
	public void GetFlockOverviewTest()
	{	
    	FlockOverview flockOverview=farmShopService.getFlockOverview();
		
		assertNotNull(flockOverview);
		
		List<Animal> flock=flockOverview.getFlock();
		int flockSize=flock.size();
	
		assertEquals(3,flockSize);	
		assertEquals("TestSheep",flock.get(0).getName());
		assertEquals(5,flock.get(1).getWool());
		assertEquals('f',flock.get(2).getSex());
	}
    
    
    @Test
    public void getProductsStockTest()
    {
    	
    	Products stockBeforeOrder=farmShopService.getProductsStock();
    	int milkStock=stockBeforeOrder.getMilk();
    	int woolStock=stockBeforeOrder.getWool();
    	
    	List<Order> orderList = farmShopService.getOrderHistory().getOrders();
    	
    	if(orderList.isEmpty())
    	{
    	assertEquals(50, milkStock);
    	assertEquals(39, woolStock);
    	}
    	else
    	{
    		int orderedMilk=0;
    		int orderedWool=0;
    		for(Order order:orderList)
    		{
    			Products product=order.getOrderedProducts();
    			orderedMilk+=product.getMilk();
    			orderedWool+=product.getWool();
    		}
    		
    		assertEquals(50-orderedMilk, milkStock);
        	assertEquals(39-orderedWool, woolStock);
    	}
    }
   
    @Test
    public void PlaceOrderTest()
    {
    	//Stock before Order
    	Products stockBeforeOrder=farmShopService.getProductsStock();
    	int milkVolBfrOrdr=stockBeforeOrder.getMilk();
    	int woolSizeBfrOrdr=stockBeforeOrder.getWool();
    	
    	//Place Successful order
    	Products orderedProducts=new Products();
    	orderedProducts.setMilk(10);
    	orderedProducts.setWool(10);
    
    	Order order = new Order();
    	order.setCustomer("Test customer 1");
    	order.setOrder(orderedProducts);
    	
    	assertTrue(farmShopService.placeOrder(order));
    	
    	//Stock after successful order
        Products stockAfterOrder=farmShopService.getProductsStock();
    	int milkVolAfrOrdr=stockAfterOrder.getMilk();
    	int woolSizeAfrOrdr=stockAfterOrder.getWool();
    	
    	assertEquals((woolSizeBfrOrdr-woolSizeAfrOrdr),orderedProducts.getWool());
    	assertEquals((milkVolBfrOrdr-milkVolAfrOrdr),orderedProducts.getMilk());
    	
    	//For unsuccessful Order
    	Products orderedProducts2=new Products();
    	orderedProducts2.setMilk(100);
    	orderedProducts2.setWool(100);
    
    	Order order2 = new Order();
    	order2.setCustomer("Test customer 2");
    	order2.setOrder(orderedProducts2);
    	
    	assertFalse(farmShopService.placeOrder(order2));
    	
    }
     
    @Test
    public void getOrderHistoryTest()
    {
    	OrderHistory orderHistory=farmShopService.getOrderHistory();
    	
    	assertNotNull(orderHistory);
    	
    	List<Order> orderList=orderHistory.getOrders();
    	
    	int NoOfOrdersInHistory=orderList.size();
    	
    	
    	//place successful Order
    	Products orderedProducts3=new Products();
    	orderedProducts3.setMilk(5);
    	orderedProducts3.setWool(5);
    
    	Order order3 = new Order();
    	order3.setCustomer("Test customer 3");
    	order3.setOrder(orderedProducts3);
    	
    	assertTrue(farmShopService.placeOrder(order3));
    	assertTrue(orderList.contains(order3));
    	assertEquals(orderList.size(),NoOfOrdersInHistory+1);
    	 	
    	
    }

}
