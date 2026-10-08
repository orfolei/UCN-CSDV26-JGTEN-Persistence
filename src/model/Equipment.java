package model;

public class Equipment extends Product{

	private String material;
	private String style;
	
	
	public Equipment(int productNumber, String name, int reservedQty) {
		super(productNumber, name, reservedQty);
		
	}
}
