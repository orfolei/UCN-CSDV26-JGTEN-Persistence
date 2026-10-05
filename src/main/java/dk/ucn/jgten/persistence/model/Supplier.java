package dk.ucn.jgten.persistence.model;

public class Supplier {

	private String name;
	private String address;
	private String country;
	private int phoneNo;
	private String email;
	
	public Supplier(String name ,String address ,String country ,int phoneNo ,String email)
	{
		this.name = name;
		this.address = address;
		this.country = country;
		this.phoneNo = phoneNo;
		this.email = email;
	}
}
