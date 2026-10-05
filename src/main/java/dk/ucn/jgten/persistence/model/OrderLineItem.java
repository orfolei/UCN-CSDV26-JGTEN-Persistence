package dk.ucn.jgten.persistence.model;

public class OrderLineItem {

	
	private int quantity;
	
	private Product product;
	
	private double productPrice;
	private double lineTotalPrice;
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public double getLineTotalPrice() {
		return lineTotalPrice;
	}
	public void setLineTotalPrice(double lineTotalPrice) {
		this.lineTotalPrice = lineTotalPrice;
	}
}
