package model;

import java.time.LocalDate;

public class Price {

	private LocalDate timestamp; 
	private double price;
	
	public Price(LocalDate timestamp, double price)
	{
		this.timestamp = timestamp;
		this.price = price;
	}
}
