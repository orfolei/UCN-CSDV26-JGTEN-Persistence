package dk.ucn.jgten.persistence.model;

public class Equipment extends Product{

	private String material;
	private String style;
	
	
	public Equipment(int productNumber, String name, int minStock, int reservedQty) {
		super(productNumber, name, minStock, reservedQty);
		
	}
}
