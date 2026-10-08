package controller;

import java.sql.SQLException;

import database.DataAccessException;
import database.OrderDAO;
import database.OrderDB;

import model.Customer;
import model.SaleOrder;

//giver videre til dao sådan at gui ik ved noget om db - sikkerhed! -t
public class OrderController {
	
	private final OrderDAO orderDAO;
	
	public OrderController() throws SQLException {
		orderDAO = new OrderDB();
	}
	
	public SaleOrder createOrder(Customer customer) {
		return orderDAO.createOrder(customer);
	}

	public void addOrderLine(
			SaleOrder order, int productNumber, int quantity)
	throws DataAccessException {
		orderDAO.addOrderLine(order, productNumber, quantity);
	}
	
	public void saveOrder(SaleOrder order) throws DataAccessException {
		orderDAO.saveOrder(order);
	}
	
	public double calcDiscount(SaleOrder order) {
		return orderDAO.calcDiscount(order);
	}

	public double calcFreight(SaleOrder order) {
		return orderDAO.calcFreight(order);
	}

	public double calcTotal(SaleOrder order) {
		return order.calcPrice() - orderDAO.calcDiscount(order) + orderDAO.calcFreight(order);
	}
}
