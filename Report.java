

public class Report {
	private String start_date;
	private String end_date;
	private double food_revenue, surcharge_revenue;
	private int order_count;
	
	public Report(String start_date, String end_date, int order_count,
			double food_revenue, double surcharge_revenue) {
		this.start_date = start_date;
		this.end_date = end_date;
		this.order_count = order_count;
		this.food_revenue = food_revenue;
		this.surcharge_revenue = surcharge_revenue;
	}
	
	public Report() {
		this("20160401", "20160530", 2, 1402.92, 0.0);
	}

	public boolean isMatch_start_date(String key) {
		return key.equals(start_date);
	}
	
	public boolean isMatch_end_date(String key) {
		return key.equals(end_date);
	}
	
	public boolean isMatch_order_count(int key) {
		return key == order_count;
	}
	
	public boolean isMatch_food_revenue(double key) {
		return key == food_revenue;
	}	
	
	public boolean isMatch_surcharge_revenue(double key) {
		return key == surcharge_revenue;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (Double.doubleToLongBits(food_revenue) != Double.doubleToLongBits(other.food_revenue))
			return false;
		if (order_count != other.order_count)
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (Double.doubleToLongBits(surcharge_revenue) != Double.doubleToLongBits(other.surcharge_revenue))
			return false;
		return true;
	}
}
