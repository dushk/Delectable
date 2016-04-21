
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenuTest {
	Menu m;
	
	@Before
    public void setUp() throws Exception {
        m = new Menu();
    }
	
    @Test
    public void test_default_name_matches_keyword() {
        assertTrue(m.isMatch_name("Veg Stir Fry"));
    }
    
    @Test
    public void test_default_price_per_person_matches_keyword() {
        assertTrue(m.isMatch_price_per_person(12.99));
    }
    
    @Test
    public void test_false_price_per_person_matches_keyword() {
        assertFalse(m.isMatch_price_per_person(12.00));
    }
    
    @Test
    public void test_default_minimumCount_matches_keyword() {
        assertTrue(m.isMatch_minimum_order(2));
    }
    
    @Test
    public void test_false_minimumCount_matches_keyword() {
        assertFalse(m.isMatch_minimum_order(5));
    }
    
    @Test
    public void test_default_categories_matches_keyword() {
    	List<Map<String, Object>> myCategories = new ArrayList<Map<String, Object>>();

        Map<String, Object> cat1 = new LinkedHashMap<String, Object>();
		cat1.put("name", "Vegetarian");
		myCategories.add(cat1);
		
		Map<String, Object> cat2 = new LinkedHashMap<String, Object>();
		cat2.put("name", "Organic");
		myCategories.add(cat2);
        assertTrue(m.isMatch_categories(myCategories));
    }
}