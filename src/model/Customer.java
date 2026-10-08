package model;

public class Customer {

	private String name;
	private String address;
	private int zipCode;
	private String city;
	private int phoneNo;
	private String customerType;
	
	private int id;
	
	


	public Customer(String name, String address, int zipCode, String city, int phoneNo, String customerType) {
		super();
		this.name = name;
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.phoneNo = phoneNo;
		this.customerType = customerType;
	}
	public Customer(int id)
	{
		this.id = id;
	}
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getZipCode() {
		return zipCode;
	}


	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	

	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

}
