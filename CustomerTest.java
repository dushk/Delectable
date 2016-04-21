
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class CustomerTest {
	Customer c;
	
	@Before
	public void setUp() throws Exception {
		c = new Customer();
	}
    @Test
    public void test_default_name_matches_keyword() {
        assertTrue(c.isMatch_name("Dushyanth Kesavan"));
    }
    
    @Test
    public void test_default_phone_matches_keyword() {
        assertTrue(c.isMatch_phone("480-438-6736"));
    }
    
    @Test
    public void test_default_email_matches_keyword() {
        assertTrue(c.isMatch_email("dkesava1@hawk.iit.edu"));
    }
}