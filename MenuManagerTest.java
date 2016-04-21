
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MenuManagerTest {
	@Before
	public void setUp() throws Exception {
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
	}
	
	@Test
	public void test_default_addItemToMenu() {
		
		Menu m = new Menu();
		assertTrue(MenuManager.addItemToMenu(m) == 1);
	}
	
	@Test
	public void test_default_viewMenu() {
        List<Menu> lineAsList = new ArrayList<Menu>();
        lineAsList.add(new Menu());
        lineAsList.add(new Menu());
        Menu[] menu1 = lineAsList.toArray(new Menu[lineAsList.size()]);
        
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();
		MenuManager.addItemToMenu(new Menu());
		MenuManager.addItemToMenu(new Menu());
		Menu[] menu2 = MenuManager.viewMenu();
		assertTrue(Arrays.equals(menu1, menu2));
	}
	
	@Test
	public void test_default_viewMenuItemByID() {
		Menu m1 = new Menu();
		
		MenuManager.createMenu();
    	UniqueIdGenerator.resetID();

		Menu m2 = new Menu();
		MenuManager.addItemToMenu(m2);
		assertTrue(m1.equals(MenuManager.viewMenuItemByID(m2.getMid())));		
	}
	
	@Test
	public void test_default_modifyItem() {
		Menu m2 = new Menu();
		MenuManager.addItemToMenu(m2);
		MenuManager.modifyItem(m2.getMid(), 15.99);
		assertTrue(MenuManager.viewMenuItemByID(m2.getMid()).getPrice() == 15.99);
	}
}
        