
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ CustomerManagerTest.class, CustomerTest.class, MenuManagerTest.class, MenuTest.class,
		OrderManagerTest.class, OrderTest.class, ReportManagerTest.class, ReportTest.class })
public class AllTests {
	public static void main(String args[]) {
	      org.junit.runner.JUnitCore.main("AllTests");
	    }
}
