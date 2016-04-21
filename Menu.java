
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private int mid;
    private String name;
    private double price_per_person;
    private int minimum_order;
	private List<Map<String, Object>> categories;
	private static List<Map<String, Object>> myCategories;
    
    public Menu (int mid, String name, double price_per_person, 
    		int minimum_order, List<Map<String, Object>> categories) {
        this.mid = mid;
        this.name = name;
        this.price_per_person = price_per_person;
        this.minimum_order = minimum_order;
        this.categories = categories;
    }
    
    public Menu (String name, double price_per_person, 
    		int minimum_order, List<Map<String, Object>> categories) {
        this(UniqueIdGenerator.getUniqueID(), name, 
        		price_per_person, minimum_order, categories);
    }
    
    public Menu() {
		this("Veg Stir Fry", 12.99, 2, myCategories = new ArrayList<Map<String, Object>>());
    	Map<String, Object> cat1 = new LinkedHashMap<String, Object>();
		cat1.put("name", "Vegetarian");
		myCategories.add(cat1);
		
		Map<String, Object> cat2 = new LinkedHashMap<String, Object>();
		cat2.put("name", "Organic");
		myCategories.add(cat2);
    }
    
    public int getMid() {
        return mid;
    }
    
    public List<Map<String, Object>> getCategories() {
		return categories;
	}
	
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price_per_person;
    }
    
    public int getminimum_order() {
        return minimum_order;
    }
    
    public boolean isMatch_name(String key) {
        return key.equals(name);
    }
    
    public boolean isMatch_price_per_person(double key) {
        return key == price_per_person;
    }
     
    public boolean isMatch_minimum_order(int key) {
        return key == minimum_order;
    }
    
    public boolean isMatch_categories(List<Map<String, Object>> key) {
        return key.equals(categories);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (mid != other.mid)
			return false;
		if (minimum_order != other.minimum_order)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price_per_person) != Double.doubleToLongBits(other.price_per_person))
			return false;
		return true;
	}
    
    
}