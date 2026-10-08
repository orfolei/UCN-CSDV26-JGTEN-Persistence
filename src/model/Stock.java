package model;

public class Stock {
	
	private int id;
	private int quantity;
	private int minStock;
	private int warehouseId;
	
	public Stock(int id, int quantity, int minStock, int warehouseId) {
		this.id = id;
		this.quantity = quantity;
		this.minStock = minStock;
		this.warehouseId = warehouseId;
	}
	
	public int getId() {
		return id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getMinStock() {
		return minStock;
	}
	
	public int getWarehouseId() {
		return warehouseId;
	}

}
