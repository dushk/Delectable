
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuManager {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String fileName = new File("menu.csv").getAbsolutePath();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final String FILE_HEADER = "id,item_name,categories,price_per_person,min_quantity,created_date,last_modified_date";
   
    public static void createMenu() {
        FileWriter fileWriter = null;
    
        try {
            fileWriter = new FileWriter(fileName, false);
            fileWriter.append(FILE_HEADER.toString());
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
    
    public static int addItemToMenu(Menu item) {
                
        if(!isCreatedMenu()) {
            createMenu();
        }
        
        Date date = new Date();

        FileWriter fileWriter = null;
    
        try {
            fileWriter = new FileWriter(fileName, true);
            
            fileWriter.append(String.valueOf(item.getMid()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(item.getName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(ListAsString(item));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(item.getPrice()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(item.getminimum_order()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(dateFormat.format(date)));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(dateFormat.format(date)));
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
        
        return item.getMid();
    }
    
    public static String ListAsString(Menu item) {
	    String categories = "";
		for(Map<String, Object> cat : item.getCategories() ) {
			for(Map.Entry<String, Object> entry : cat.entrySet()) {
	    		categories = categories + entry.getValue() + "|"; 
			}
		}
		return categories;
    }
    
    public static List<Map<String, Object>> parseToList(String cats) {
		List<Map<String, Object>> categories = new ArrayList<Map<String, Object>>();
		String[] str1 = cats.split("\\|");
		for(String _str1 : str1) {
			Map<String, Object> cat = new LinkedHashMap<String, Object>();
			cat.put("name", _str1);
			categories.add(cat);
		}
		return categories;
	}
    
    public static Menu[] viewMenu() {
        BufferedReader fileReader = null;
        List<Menu> lineAsList = new ArrayList<Menu>();

        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(fileName));
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] menuItems = line.split(",");   
                lineAsList.add(new Menu(Integer.parseInt(menuItems[0]), menuItems[1],
                		Double.parseDouble(menuItems[3]), Integer.parseInt(menuItems[4]), parseToList(menuItems[2])));
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
	    return lineAsList.toArray(new Menu[lineAsList.size()]);
    }
    
    public static Menu viewMenuItemByID(int mid) {
        BufferedReader fileReader = null;
        Menu item = null;

        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(fileName));
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] menuItems = line.split(",");   
                if(Integer.parseInt(menuItems[0]) == mid) {
                	item = new Menu(Integer.parseInt(menuItems[0]), menuItems[1],
                    		Double.parseDouble(menuItems[3]), Integer.parseInt(menuItems[4]), parseToList(menuItems[2]));
                }  
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
        return item;
    }
    
    public static int modifyItem(int mid, double price_per_person) {
        
        BufferedReader fileReader = null;
        String line = "";
        List<String> lineAsList = new ArrayList<String>();
            
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
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
        int modifiedId = -1;
        try {
        	fileWriter = new FileWriter(fileName, true);
            Date date;
            boolean found = false;
            for(String lines : lineAsList) {
                String[] menuItems = lines.split(",");  
                if(Integer.parseInt(menuItems[0]) == mid) {
                    createMenu();
                    found = true;
                }
            }
            if(!found) return modifiedId;
         
            for(String lines : lineAsList) {
                String[] menuItems = lines.split(",");  
                if(Integer.parseInt(menuItems[0]) == mid) {
                	createMenu();
                    modifiedId = Integer.parseInt(menuItems[0]);
                    menuItems[3] = String.valueOf(price_per_person);
                    date = new Date();
                    menuItems[6] = String.valueOf(dateFormat.format(date));
                }                   
                
                fileWriter.append(menuItems[0]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[1]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[2]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[3]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[4]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[5]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(menuItems[6]);
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
        return modifiedId;
    }

	public static boolean isCreatedMenu() {
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}    
}