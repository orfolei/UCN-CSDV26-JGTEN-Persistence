package dk.ucn.jgten.persistence.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SaleOrder {

	
	


	private int orderNo;
	private LocalDate date; 
	private boolean deliveryStatus;
	private LocalDate deliveryDate; 
	private Double discountGiven;
	
	private Invoice invoice;
	private Customer customer;
	private Freight freight;
	
	private ArrayList<OrderLineItem> orderLineItems;

	
	
	public SaleOrder(int orderNo, LocalDate date, boolean deliveryStatus, LocalDate deliveryDate, Double discountGiven,
			Customer customer, List<OrderLineItem> orderLineItems) {
		super();
		
		this.orderNo = orderNo;
		this.date = date;
		this.deliveryStatus = deliveryStatus;
		this.deliveryDate = deliveryDate;
		this.discountGiven = discountGiven;
		this.customer = customer;
		
		this.orderLineItems = new ArrayList<OrderLineItem>(orderLineItems);
	
	}
	
	
	
	public void addOrderLineItem(OrderLineItem orderLineItem)
	{
		orderLineItems.add(orderLineItem);
	}
	
	
	public int getOrderNo() {
		return orderNo;
	}



	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}



	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}



	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}



	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}



	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}



	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}



	public Double getDiscountGiven() {
		return discountGiven;
	}



	public void setDiscountGiven(Double discountGiven) {
		this.discountGiven = discountGiven;
	}



	public Invoice getInvoice() {
		return invoice;
	}



	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public Freight getFreight() {
		return freight;
	}



	public void setFreight(Freight freight) {
		this.freight = freight;
	}



	public ArrayList<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}



	public void setOrderLineItems(ArrayList<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	
}
