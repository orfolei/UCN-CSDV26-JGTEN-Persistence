package dk.ucn.jgten.persistence.model;

public class Price {

	private String timestamp; //lav det her til en localdate (idk how) - emil
	private double price;
	
	public Price(String timestamp, double price)
	{
		this.timestamp = timestamp;
		this.price = price;
	}
}
