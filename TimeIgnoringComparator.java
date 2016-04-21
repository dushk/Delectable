

import java.util.Date;

public class TimeIgnoringComparator {
	@SuppressWarnings("deprecation")
	public static int compare(Date d1, Date d2) {
	    if (d1.getYear() != d2.getYear()) 
	        return d1.getYear() - d2.getYear();
	    if (d1.getMonth() != d2.getMonth()) 
	        return d1.getMonth() - d2.getMonth();
	    return d1.getDate() - d2.getDate();
	  }
}
