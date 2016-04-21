
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Order {
	private int oid;
	private double amount;
	private double surcharge;
	private String status;
	private String order_date;
	private String delivery_date;
	private String delivery_address;
	private Customer personal_info;
	private String note;
	private List<Map<String, Object>> order_detail;
	private static List<Map<String, Object>> myorder_detail;
	
	public Order(int oid, String delivery_date, String delivery_address, Customer customer, String note,
			List<Map<String, Object>> order_detail) {
		this.oid = oid;
		this.delivery_date = delivery_date;
		this.delivery_address = delivery_address;
		this.personal_info = customer;
		this.note = note;
		this.order_detail = order_detail;
		
	}

	public Order(String delivery_date, String delivery_address, Customer customer, String note, List<Map<String, Object>> order_detail) {
		this((UniqueIdGenerator.getUniqueID3()), delivery_date, delivery_address, customer, note, order_detail);
	}

	public Order() {
		this("20160911", "10 West 31st ST,Chicago IL 60616", new Customer(), "Room SB-214", myorder_detail = new ArrayList<Map<String, Object>>());
		
		Map<String, Object> item1 = new LinkedHashMap<String, Object>();
		item1.put("id", 2);
		item1.put("count", 30);
		myorder_detail.add(item1);
		
		Map<String, Object> item2 = new LinkedHashMap<String, Object>();
		item2.put("id", 1);
		item2.put("count", 24);
		myorder_detail.add(item2);
	}

	
	public String getdelivery_date() {
		return delivery_date;
	}

	public String getdelivery_address() {
		return delivery_address;
	}

	public String getNote() {
		return note;
	}

	public int getOid() {
		return oid;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getorder_date() {
		return order_date;
	}

	public void setorder_date(String order_date) {
		this.order_date = order_date;
	}

	public List<Map<String, Object>> getorder_detail() {
		return order_detail;
	}

	public Customer getpersonal_info() {
		return personal_info;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}
	
	public boolean isMatch_delivery_date(String key) {
		return key.equals(delivery_date);
	}

	public boolean isMatch_delivery_address(String key) {
		return key.equals(delivery_address);
	}

	public boolean isMatch_note(String key) {
		return key.equals(note);
	}
	
	public boolean isMatch_order_detail(List<Map<String, Object>> key) {
		return key.equals(order_detail);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (delivery_address == null) {
			if (other.delivery_address != null)
				return false;
		} else if (!delivery_address.equals(other.delivery_address))
			return false;
		if (delivery_date == null) {
			if (other.delivery_date != null)
				return false;
		} else if (!delivery_date.equals(other.delivery_date))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		return true;
	}
}