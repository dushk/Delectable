
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReportManagerTest {

	@Test
	public void test_default_viewCustomersByID() {
		Report r1 = new Report();
		
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
		MenuManager.addItemToMenu(new Menu());
		MenuManager.addItemToMenu(new Menu());
		OrderManager.createOrders();
		OrderManager.createCustomers();
		UniqueIdGenerator.resetID3();
    	UniqueIdGenerator.resetID2();
    	
		OrderManager.addOrderToOrders(new Order());
		OrderManager.addOrderToOrders(new Order());
		Report r2 = ReportManager.viewReport("20160401", "20160530");
		assertTrue(r1.equals(r2));
	}

}
