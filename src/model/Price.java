package model;

import java.time.LocalDate;

public class Price {

	private int id;
	private LocalDate timestamp; 
	private double price;
	
	public Price(LocalDate timestamp, double price)
	{
		this.timestamp = timestamp;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getTimestamp() {
		return timestamp;
	}
	
	public double getPrice() {
		return price;
	}
}
