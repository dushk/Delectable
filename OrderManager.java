
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class OrderManager {
	private static String status = "open";
    private static Date dateOrder;
    private static Date dateDelivery;
    private static Date startDate;
    private static Date endDate;
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER_ORDERS = "id,amount,surcharge,"
    		+ "status,order_date,delivery_date,customer_id,customer_name,customer_email,"
    		+ "customer_phone,delivery_address,delivery_city,note,order_detail";
    private static final String FILE_HEADER_CUSTOMERS = "id,name,email,phone";
    private static final String ordersFileName = new File("orders.csv").getAbsolutePath();
    private static final String customersFileName = new File("customers.csv").getAbsolutePath();
    private static final String menuFileName = new File("menu.csv").getAbsolutePath();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static double surcharge = 0.0;
       
    public static void createOrders() {
        FileWriter fileWriter = null;
    
        try {

            fileWriter = new FileWriter(ordersFileName, false);
            fileWriter.append(FILE_HEADER_ORDERS.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        
        catch (Exception e) {
            e.printStackTrace();
        } finally {   
            try {
                fileWriter.flush();
                fileWriter.close();
            } 
            
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void createCustomers() {
        FileWriter fileWriter = null;
    
        try {
            fileWriter = new FileWriter(customersFileName, false);
            fileWriter.append(FILE_HEADER_CUSTOMERS.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        
        catch (Exception e) {
            e.printStackTrace();
        } finally {   
            try {
                fileWriter.flush();
                fileWriter.close();
            } 
            
            catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }
    
    public static int addOrderToOrders(Order order) {
	    if(!isCreatedOrders()) {
	        createOrders();
	    }
	    
	    if(!isCreatedCustomers()) {
	        createCustomers();
	    }
	    dateOrder = new Date();
	    double amount = 0.0;
	    double surcharge = OrderManager.getSurcharge();
	    try {
			dateDelivery = dateFormat.parse(order.getdelivery_date());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    if(TimeIgnoringComparator.compare(dateDelivery, dateOrder) < 0) return -1;
	    amount = calcTotal(order);
	    
	    if(amount == -1.0) return -1;
	    if(amount == -2.0) return -1;
	    
	    //Checking if Delivery Date is a weekend
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dateDelivery);
	    if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
	    		cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		    amount += surcharge;
	    }
	    else
	    	surcharge = 0.0;
	    
	    FileWriter fileWriterOrder = null;
	    FileWriter fileWriterCustomer = null;
	
	    try {
	        fileWriterOrder = new FileWriter(ordersFileName, true);
	        fileWriterCustomer = new FileWriter(customersFileName, true);
	        
	        fileWriterOrder.append(String.valueOf(order.getOid()));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(String.valueOf(amount));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(String.valueOf(surcharge));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(status);
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(String.valueOf(dateFormat.format(dateOrder)));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(String.valueOf(dateFormat.format(dateDelivery)));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        Customer cust = CustomerManager.viewCustomersByEmail(order.getpersonal_info().getEmail());
	        if(cust != null) {
	        	order.getpersonal_info().setCid(cust.getCid());
	        	order.getpersonal_info().setEmail(cust.getEmail());
	        }
	        fileWriterOrder.append(String.valueOf(order.getpersonal_info().getCid()));
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(order.getpersonal_info().getName());
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(order.getpersonal_info().getEmail());
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(order.getpersonal_info().getPhone());
	        fileWriterOrder.append(COMMA_DELIMITER);
	        
	        fileWriterOrder.append(order.getdelivery_address());
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(order.getNote());
	        fileWriterOrder.append(COMMA_DELIMITER);
	        fileWriterOrder.append(ListMapAsString(order));
	        fileWriterOrder.append(NEW_LINE_SEPARATOR);
	        
	        if(cust == null) {
	            fileWriterCustomer.append(String.valueOf(order.getpersonal_info().getCid()));
	            fileWriterCustomer.append(COMMA_DELIMITER);
	            fileWriterCustomer.append(order.getpersonal_info().getName());
	            fileWriterCustomer.append(COMMA_DELIMITER);
	            fileWriterCustomer.append(order.getpersonal_info().getEmail());
	            fileWriterCustomer.append(COMMA_DELIMITER);
	            fileWriterCustomer.append(order.getpersonal_info().getPhone());
	            fileWriterCustomer.append(NEW_LINE_SEPARATOR);
	        }
	        
	    } 
	    
	    catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            fileWriterOrder.flush();
	            fileWriterCustomer.flush();
	            fileWriterOrder.close();
	            fileWriterCustomer.close();
	        } 
	        
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return order.getOid();
	}

    public static double calcTotal(Order order) {
		BufferedReader fileReader = null;
        double amount = 0.0;

        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(menuFileName));
                     
            for(Map<String, Object> items : order.getorder_detail()) {
                fileReader.readLine();
                
                boolean found = false;
                boolean count = false;
                
	            while ((line = fileReader.readLine()) != null) {
	                String[] menuItems = line.split(",");   
	                if(items.get("id") instanceof Double) {
		                if((Double.parseDouble(menuItems[0])) == (Double)items.get("id")) {
		                	found = true;
		                	if((Double.parseDouble(menuItems[4])) <= (Double)items.get("count")) {
		                		count = true;
		                		amount = amount + (Double.parseDouble(menuItems[3]) * (Double)items.get("count"));
		                	}
		                }
	                }
	                else {
	                	if((Integer.parseInt(menuItems[0])) == (Integer)items.get("id")) {
		                	found = true;
		                	if((Integer.parseInt(menuItems[4])) <= (Integer)items.get("count")) {
		                		count = true;
		                		amount = amount + (Double.parseDouble(menuItems[3]) * (Integer)items.get("count"));
		                	}
		                }
	                }
	            }
	            if(!found) return -1.0;
	            if(!count) return -2.0;
	            fileReader = new BufferedReader(new FileReader(menuFileName));
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return amount;
	}
    
    public static String ListMapAsString(Order order) {
    	String order_detail = "";
    	for(Map<String, Object> item : order.getorder_detail() ) {
    		for(Map.Entry<String, Object> entry : item.entrySet()) {
	    		order_detail = order_detail + entry.getKey() + ":" +entry.getValue() + "|"; 
    		}
    		order_detail +=  "|";
    	}
    return order_detail;
    }
    
	public static int cancelOrder(int oid) {
	        
			try {
				if(viewOrdersByID(oid) != null) {
					Date dateOrd = new Date();;
					Date dateDel = dateFormat.parse(OrderManager.viewOrdersByID(oid).getdelivery_date());
					if(TimeIgnoringComparator.compare(dateDel, dateOrd) < 1) return -1;
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		
	        BufferedReader fileReader = null;
	        String line = "";
	        List<String> lineAsList = new ArrayList<String>();
	            
	        try {
	            fileReader = new BufferedReader(new FileReader(ordersFileName));
	            fileReader.readLine();
	            while ((line = fileReader.readLine()) != null) {
	                lineAsList.add(line);
	            }  
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                fileReader.close();
	            } 
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        FileWriter fileWriter = null;
	    
	        try {
	            fileWriter = new FileWriter(ordersFileName, true);
	            boolean found = false;
	            for(String lines : lineAsList) {
	                String[] orders = lines.split(",");  
	                
	                if(Integer.parseInt(orders[0]) == oid) {
	                    createOrders();
	                    found = true;
	                }
	            }
	            if(!found) return -1;
	            
	            for(String lines : lineAsList) {
	                String[] orders = lines.split(",");  
	                if(Integer.parseInt(orders[0]) == oid) {
	                    
	                    orders[3] = "cancelled";
	                }                   
	                
	                fileWriter.append(orders[0]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[1]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[2]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[3]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[4]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[5]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[6]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[7]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[8]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[9]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[10]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[11]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[12]);
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(orders[13]);
	                fileWriter.append(NEW_LINE_SEPARATOR);
	                    
	            } 
	        }  catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                fileWriter.flush();
	                fileWriter.close();
	            } 
	            
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return oid;
	    }
	
	public static Order[] viewOrders() {
	    BufferedReader fileReader = null;
        List<Order> lineAsList = new ArrayList<Order>();
	    try {
	        String line = "";
	        
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");  
	        	Order o = new Order(Integer.parseInt(orders[0]), orders[5], orders[10] 
                		+ "," + orders[11], new Customer(Integer.parseInt(orders[6]), 
                		orders[7], orders[8], orders[9]), orders[12], parseToMap(orders[13]));
                o.setAmount(Double.parseDouble(orders[1]));
                o.setSurcharge(Double.parseDouble(orders[2]));
                o.setStatus(orders[3]);
                o.setorder_date(orders[4]);
	        	lineAsList.add(o);
                
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
	    return lineAsList.toArray(new Order[lineAsList.size()]);
	}
	
	public static List<Map<String, Object>> parseToMap(String detail) {
		List<Map<String, Object>> myorder_detail = new ArrayList<Map<String, Object>>();
		String[] str1 = detail.split("\\|\\|");
		for(String _str1 : str1) {
			String[] str2 = _str1.split("\\|");
			Map<String, Object> item = new LinkedHashMap<String, Object>();
			item.put("id", (int)Double.parseDouble(str2[0].split(":")[1]));
			item.put("name", MenuManager.viewMenuItemByID((int)Double.parseDouble(str2[0].split(":")[1])).getName());
			item.put("count", (int)Double.parseDouble(str2[1].split(":")[1]));
			myorder_detail.add(item);
		}
		return myorder_detail;
	}
	
	public static Order viewOrdersByID(int oid) {
	    BufferedReader fileReader = null;
        Order o = null;
	    try {
	        String line = "";
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");   
                if(Integer.parseInt(orders[0]) == oid) {
                	o = new Order(Integer.parseInt(orders[0]), orders[5], orders[10] 
                    		+ "," + orders[11], new Customer(Integer.parseInt(orders[6]), 
                    		orders[7], orders[8], orders[9]), orders[12], parseToMap(orders[13]));
                    o.setAmount(Double.parseDouble(orders[1]));
                    o.setSurcharge(Double.parseDouble(orders[2]));
                    o.setStatus(orders[3]);
                    o.setorder_date(orders[4]);
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
	    return o;
	}
	
	public static Order[] viewOrdersByDelDate(String date) {
	    BufferedReader fileReader = null;
        List<Order> lineAsList = new ArrayList<Order>();
	    try {
	        String line = "";
	        boolean found = false;
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");   
                if(orders[5].equals(date) && orders[3].equals("open")) {
                	found = true;
                	Order o = new Order(Integer.parseInt(orders[0]), orders[5], orders[10] 
                    		+ "," + orders[11], new Customer(Integer.parseInt(orders[6]), 
                    		orders[7], orders[8], orders[9]), orders[12], parseToMap(orders[13]));
                    o.setAmount(Double.parseDouble(orders[1]));
                    o.setSurcharge(Double.parseDouble(orders[2]));
                    o.setStatus(orders[3]);
                    o.setorder_date(orders[4]);
    	        	lineAsList.add(o);
    	        }
	        } 
	        if(!found) return null;
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
	    return lineAsList.toArray(new Order[lineAsList.size()]);
	}
	
	public static Order[] viewOrdersBwDelDate(String start_date, String end_date) {
	    BufferedReader fileReader = null;
        List<Order> lineAsList = new ArrayList<Order>();
	    try {
	        String line = "";
	        boolean found = false;
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        try {
				startDate = dateFormat.parse(start_date);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			try {
				endDate = dateFormat.parse(end_date);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			Date del_date;
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");   
	        	del_date = dateFormat.parse(orders[5]);
                if(del_date.compareTo(startDate) > -1 && del_date.compareTo(endDate) < 1 && orders[3].equals("open")) {
                	found = true;
                	Order o = new Order(Integer.parseInt(orders[0]), orders[5], orders[10] 
                    		+ "," + orders[11], new Customer(Integer.parseInt(orders[6]), 
                    		orders[7], orders[8], orders[9]), orders[12], parseToMap(orders[13]));
                    o.setAmount(Double.parseDouble(orders[1]));
                    o.setSurcharge(Double.parseDouble(orders[2]));
                    o.setStatus(orders[3]);
                    o.setorder_date(orders[4]);
    	        	lineAsList.add(o);
    	        }
	        } 
	        if(!found) return null;
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
	    return lineAsList.toArray(new Order[lineAsList.size()]);
	}
	
	public static Order[] viewOrdersByCID(int cid) {
	    BufferedReader fileReader = null;
        List<Order> lineAsList = new ArrayList<Order>();
	    try {
	        String line = "";
	        boolean found = false;
	        fileReader = new BufferedReader(new FileReader(ordersFileName));
	        fileReader.readLine();
	        while ((line = fileReader.readLine()) != null) {
	        	String[] orders = line.split(",");   
                if(Integer.parseInt(orders[6]) == cid) {
                	found = true;
                	Order o = new Order(Integer.parseInt(orders[0]), orders[5], orders[10] 
                    		+ "," + orders[11], new Customer(Integer.parseInt(orders[6]), 
                    		orders[7], orders[8], orders[9]), orders[12], parseToMap(orders[13]));
                    o.setAmount(Double.parseDouble(orders[1]));
                    o.setSurcharge(Double.parseDouble(orders[2]));
                    o.setStatus(orders[3]);
                    o.setorder_date(orders[4]);
    	        	lineAsList.add(o);
    	        }
	        } 
	        if(!found) return null;
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
	    return lineAsList.toArray(new Order[lineAsList.size()]);
	}
	
	public static double getSurcharge() {
		return surcharge ;
	}
	
	public static void setSurcharge(double surcharge) {
		OrderManager.surcharge = surcharge;
	}
	
	
	public static boolean isCreatedOrders() {
		File f = new File(ordersFileName);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}
	
	public static boolean isCreatedCustomers() {
		File f = new File(customersFileName);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}
}
