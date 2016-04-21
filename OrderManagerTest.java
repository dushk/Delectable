
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OrderManagerTest {
	
	@Before
	public void setUp() throws Exception {
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
		MenuManager.addItemToMenu(new Menu());
		MenuManager.addItemToMenu(new Menu());
	}
	
	@Test
	public void test_default_addOrderToOrders() {
		Order o = new Order();
		OrderManager.createOrders();
		OrderManager.createCustomers();
    	UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();

		assertTrue(OrderManager.addOrderToOrders(o) == 1);
	}
	
	@Test
	public void test_default_cancelOrder() {
		Order o = new Order();
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(o);
		OrderManager.cancelOrder(o.getOid());
		assertTrue(OrderManager.viewOrdersByID(o.getOid()).getStatus().equals("cancelled"));
	}
	
	@Test
	public void test_default_viewOrders() {		
		List<Order> lineAsList = new ArrayList<Order>();
        lineAsList.add(new Order());
        lineAsList.add(new Order());
        Order[] order1 = lineAsList.toArray(new Order[lineAsList.size()]);
        
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		OrderManager.addOrderToOrders(new Order());
		Order[] order2 = OrderManager.viewOrders();
		assertTrue(order1[0].equals(order2[0]));
		assertTrue(order1[1].equals(order2[1]));	
	}
	
	@Test
	public void test_default_viewOrdersByID() {
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
		assertTrue(o1.equals(OrderManager.viewOrdersByID(o2.getOid())));		
	}
	
	@Test
	public void test_default_viewOrdersByDelDate() {		
		List<Order> lineAsList = new ArrayList<Order>();
        lineAsList.add(new Order());
        lineAsList.add(new Order());
        Order[] order1 = lineAsList.toArray(new Order[lineAsList.size()]);
        		
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		OrderManager.addOrderToOrders(new Order());
		Order[] order2 = OrderManager.viewOrders();
		assertTrue(order1[0].equals(OrderManager.viewOrdersByDelDate(order2[0].getdelivery_date())[0]));
		assertTrue(order1[1].equals(OrderManager.viewOrdersByDelDate(order2[1].getdelivery_date())[1]));
	}
	
	@Test
	public void test_default_viewOrdersBwDelDate() {		
		List<Order> lineAsList = new ArrayList<Order>();
        lineAsList.add(new Order());
        lineAsList.add(new Order());
        Order[] order1 = lineAsList.toArray(new Order[lineAsList.size()]);
        		
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		OrderManager.addOrderToOrders(new Order());
		Order[] order2 = OrderManager.viewOrdersBwDelDate("20160901", "20160930");
		assertTrue(order1[0].equals(order2[0]));
	}
	
	@Test
	public void test_default_getSurcharge() {
		assertTrue(OrderManager.getSurcharge() == 0.0);
	}
	
	@Test
	public void test_default_setSurcharge() {
		OrderManager.setSurcharge(10.0);
		assertTrue(OrderManager.getSurcharge() == 10.0);
		OrderManager.setSurcharge(0.0);
	}
}
