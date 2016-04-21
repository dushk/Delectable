
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CustomerManagerTest {
	
	@Before
	public void setUp() throws Exception {
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
		MenuManager.addItemToMenu(new Menu());
		MenuManager.addItemToMenu(new Menu());
	}
	
	@Test
	public void test_default_viewCustomers() {		
		List<Order> lineAsList = new ArrayList<Order>();
        lineAsList.add(new Order());
        Order[] order1 = lineAsList.toArray(new Order[lineAsList.size()]);
        
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		Customer[] cust = CustomerManager.viewCustomers();
		assertTrue(cust[0].equals(order1[0].getpersonal_info()));	
	}
	
	@Test
	public void test_default_viewCustomersByID() {
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		Order o1 = new Order();
		
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
		MenuManager.addItemToMenu(new Menu());
		MenuManager.addItemToMenu(new Menu());
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
    	Order o2 = new Order();
		OrderManager.addOrderToOrders(o2);
		assertTrue(o1.getpersonal_info().equals(CustomerManager.viewCustomersByID(o2.getpersonal_info().getCid())));
	}
	
	@Test
	public void test_default_viewCustomersByKeyword() {		        		
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		OrderManager.addOrderToOrders(new Order());
		Order[] order2 = OrderManager.viewOrders();
		assertTrue(order2[0].getpersonal_info().equals(CustomerManager.viewCustomersByKeyword("Dush")[0]));
	}
}
