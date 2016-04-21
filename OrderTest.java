
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	Order o;
	
	@Before
    public void setUp() throws Exception {
        o = new Order();
    }
	
	@Test
	public void test_default_delivery_date_matches_keyword() {
		assertTrue(o.isMatch_delivery_date("20160911"));
	}

	@Test
	public void test_default_delivery_address_matches_keyword() {
		assertTrue(o.isMatch_delivery_address("10 West 31st ST,Chicago IL 60616"));
	}

	@Test
	public void test_default__note_matches_keyword() {
		assertTrue(o.isMatch_note("Room SB-214"));
	}
	
	@Test
	public void test_default_order_detail_matches_keyword() {
		List<Map<String, Object>> myorder_detail = new ArrayList<Map<String, Object>>();

		Map<String, Object> item1 = new LinkedHashMap<String, Object>();
		item1.put("id", 2);
		item1.put("count", 30);
		myorder_detail.add(item1);
		
		Map<String, Object> item2 = new LinkedHashMap<String, Object>();
		item2.put("id", 1);
		item2.put("count", 24);
		myorder_detail.add(item2);
		assertTrue(o.isMatch_order_detail(myorder_detail));
	}
}
