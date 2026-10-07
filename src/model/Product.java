package model;

public class Product {
	
	


	private int productNumber;
	private String name;
	private int minStock;
	private int reservedQty;
	
	private Supplier supplier;
	private Price price;
	
	
	public Product(int productNumber ,  String name, int minStock, int reservedQty)
	{
		this.productNumber = productNumber;
		this.name = name;
		this.minStock = minStock;
		this.reservedQty = reservedQty;
		
	}
	
	
	public int getProductNumber() {
		return productNumber;
	}


	public void setProductNumber(int productNumber) {
		this.productNumber = productNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMinStock() {
		return minStock;
	}


	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}


	public int getReservedQty() {
		return reservedQty;
	}


	public void setReservedQty(int reservedQty) {
		this.reservedQty = reservedQty;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public Price getPrice() {
		return price;
	}


	public void setPrice(Price price) {
		this.price = price;
	}

}
