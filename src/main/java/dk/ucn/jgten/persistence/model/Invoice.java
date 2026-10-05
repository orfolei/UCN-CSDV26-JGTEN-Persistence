package dk.ucn.jgten.persistence.model;

import java.time.LocalDate;

public class Invoice {

	
	private int invoiceNo;
	private LocalDate dueDate;
	private LocalDate paymentDate;
	
	private SaleOrder saleOrder;
	
	public Invoice(int invoiceNo, LocalDate dueDate, LocalDate paymentDate, SaleOrder saleOrder)
	{
		this.invoiceNo = invoiceNo;
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
		
		this.saleOrder = saleOrder;
	}
	
}
