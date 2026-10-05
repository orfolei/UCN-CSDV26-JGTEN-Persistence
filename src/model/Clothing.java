package model;

public class Clothing extends Product{

	private String size;
	private String color;
	
	
	public Clothing(int productNumber, String name, int minStock, int reservedQty) {
		super(productNumber, name, minStock, reservedQty);
		
	}
	
}
