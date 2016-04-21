
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReportTest {
	Report r;
	
	@Before
	public void setUp() {
		r = new Report();
	}
	
	@Test
	public void test_default_start_date_matches_keyword() {
		assertTrue(r.isMatch_start_date("20160401"));
	}
	
	@Test
	public void test_default_end_date_matches_keyword() {
		assertTrue(r.isMatch_end_date("20160530"));
	}
	
	@Test
	public void test_default_order_count_matches_keyword() {
		assertTrue(r.isMatch_order_count(2));
	}
	
	@Test
	public void test_false_order_count_mathes_keyword() {
		assertFalse(r.isMatch_order_count(10));
	}
	
	@Test
	public void test_default_food_revenue_matches_keyword() {
		assertTrue(r.isMatch_food_revenue(1402.92));
	}
	
	@Test
	public void test_false_food_revenue_matches_keyword() {
		assertFalse(r.isMatch_food_revenue(1400.90));
	}
	
	@Test
	public void test_default_surcharge_revenue_matches_keyword() {
		assertTrue(r.isMatch_surcharge_revenue(0.0));
	}
	
	@Test
	public void test_false_surcharge_revenue_matches_keyword() {
		assertFalse(r.isMatch_surcharge_revenue(5.0));
	}

}
