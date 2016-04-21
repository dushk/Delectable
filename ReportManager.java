
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private static final String ordersFileName = new File("orders.csv").getAbsolutePath();
    private static Date start_date;
    private static Date end_date;
    
	public static Report viewReport(String startDate, String endDate) {
		double revenue = 0.0;
		double surcharge = 0.0;
		int order_count = 0;

		try {
			start_date = dateFormat.parse(startDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			end_date = dateFormat.parse(endDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date orderDate;
		BufferedReader fileReader = null;
		
	    try {
	        String line = "";
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");   
	        	orderDate = dateFormat.parse(orders[4]);
                if(orderDate.compareTo(start_date) > -1 && orderDate.compareTo(end_date) < 1) {
                	if(orders[3].equals("open")) {
                		revenue += Double.parseDouble(orders[1]);
                		surcharge += Double.parseDouble(orders[2]);
                		++order_count;
                	}
                }  
                
	        }   
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            fileReader.close();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return new Report(startDate, endDate, order_count, revenue, surcharge);
	}
}
