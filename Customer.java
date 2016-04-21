

public class Customer {
    private int cid;
    private String name, phone, email;
    private Order[] orders;
    
    public Customer(int cid, String name, String email,
                     String phone) {
    	this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

	public Customer(String name, String email,
            String phone) {
		this(UniqueIdGenerator.getUniqueID2(), name, email, phone);
    }
    	
	public Customer() {
        this("Dushyanth Kesavan", "dkesava1@hawk.iit.edu", "480-438-6736");
    }

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public boolean isMatch_name(String key) {
        return key.equals(name);
    }
    
    
    public boolean isMatch_phone(String key) {
        return key.equals(phone);
    }
    
    public boolean isMatch_email(String key) {
        return key.equals(email);
    }
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
}