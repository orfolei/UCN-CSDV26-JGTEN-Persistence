package model;

import java.util.ArrayList;

public class Product {
	
	

	private int id;
	private int productNumber;
	private String name;
	private int reservedQty;
	
	private Supplier supplier;
	private Price price;
	
	// liste med stock af products
	private ArrayList<Stock> stockList = new ArrayList<>();
	
	public Product(int productNumber ,  String name, int reservedQty)
	{
		this.productNumber = productNumber;
		this.name = name;
		this.reservedQty = reservedQty;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public int getReservedQty() {
		return reservedQty;
	}

	public ArrayList<Stock> getStockList(){
		return stockList;
	}
	
	public void addStock(Stock stock) {
		stockList.add(stock);
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
