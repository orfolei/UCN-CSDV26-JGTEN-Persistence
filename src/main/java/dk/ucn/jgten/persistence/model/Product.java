package dk.ucn.jgten.persistence.model;

public class Product {
	
	private int productNumber;
	private String name;
	private int minStock;
	private int reservedQty;
	
	
	public Product(int productNumber ,  String name, int minStock, int reservedQty)
	{
		this.productNumber = productNumber;
		this.name = name;
		this.minStock = minStock;
		this.reservedQty = reservedQty;
		
	}

}
