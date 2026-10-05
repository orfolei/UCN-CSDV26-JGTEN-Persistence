package dk.ucn.jgten.persistence.model;

import java.time.LocalDate;

public class Invoice {

	
	private int invoiceNo;
	private LocalDate dueDate;
	private LocalDate paymentDate;
	private double deliveryPrice = 45;
	
	
	private SaleOrder saleOrder;
	
	public Invoice(int invoiceNo, LocalDate dueDate, LocalDate paymentDate, SaleOrder saleOrder)
	{
		this.invoiceNo = invoiceNo;
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
		
		this.saleOrder = saleOrder;
	}
	
	
	
	public Customer getCustomer()
	{
		return saleOrder.getCustomer();
	}

	
	public double calcPrice()
	{
		double price = 0;
		
		if(saleOrder.getCustomer().getCustomerType() == "club" && saleOrder.calcPrice() >= 1500)
		{
			//get discount
			price = saleOrder.calcPrice() + deliveryPrice;
			return price;
		}
		if(saleOrder.getCustomer().getCustomerType() == "private" && saleOrder.calcPrice() >= 2500)
		{
			//get free delivery
			deliveryPrice = 0;
			price = saleOrder.calcPrice() + deliveryPrice;
			return price;
		}
		return price;
	}
}
