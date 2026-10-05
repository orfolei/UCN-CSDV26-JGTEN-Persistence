package dk.ucn.jgten.persistence.model;

public class Customer {

	
	
	private String name;
	private String address;
	private int zipCode;
	private String city;
	private int phoneNo;
	private String customerType;
	
	
	public Customer(String name, String address, int zipCode, String city, int phoneNo, String customerType) {
		super();
		this.name = name;
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.phoneNo = phoneNo;
		this.customerType = customerType;
	}
}
