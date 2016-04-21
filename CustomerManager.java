
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private static final String customersFileName = new File("customers.csv").getAbsolutePath();
	public static Customer[] viewCustomers() {
	    BufferedReader fileReader = null;
        List<Customer> lineAsList = new ArrayList<Customer>();
	    try {
	        String line = "";
	        fileReader = new BufferedReader(new FileReader(customersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] customer = line.split(",");
                lineAsList.add(new Customer(Integer.parseInt(customer[0]), customer[1],
                		customer[2], customer[3]));
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
	    return lineAsList.toArray(new Customer[lineAsList.size()]);
	}
	
	public static Customer viewCustomersByID(int cid) {
	    BufferedReader fileReader = null;
	    Customer cust = null;
	    try {
	        String line = "";
	        fileReader = new BufferedReader(new FileReader(customersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] customer = line.split(",");   
                if(Integer.parseInt(customer[0]) == cid) {
                	cust = new Customer(Integer.parseInt(customer[0]), customer[1],
                    		customer[2], customer[3]);
                	cust.setOrders(OrderManager.viewOrdersByCID(cid));
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
	    return cust;
	}
	
	public static Customer[] viewCustomersByKeyword(String keyword) {
	    BufferedReader fileReader = null;
        List<Customer> lineAsList = new ArrayList<Customer>();
	    try {
	        String line = "";
	        fileReader = new BufferedReader(new FileReader(customersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] customer = line.split(","); 
                if(customer[1].toLowerCase().contains(keyword.toLowerCase())) {
                	lineAsList.add(new Customer(Integer.parseInt(customer[0]), customer[1],
                    		customer[2], customer[3]));               }  
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
	    return lineAsList.toArray(new Customer[lineAsList.size()]);
	}
	
	public static Customer viewCustomersByEmail(String email) {
	    BufferedReader fileReader = null;
	    Customer cust = null;
	    try {
	        String line = "";
	        boolean found = false;
	        fileReader = new BufferedReader(new FileReader(customersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] customer = line.split(",");   
                if(customer[2].equals(email)) {
                	found = true;
                	cust = new Customer(Integer.parseInt(customer[0]), customer[1],
                    		customer[2], customer[3]);
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
	    return cust;
	}
}
