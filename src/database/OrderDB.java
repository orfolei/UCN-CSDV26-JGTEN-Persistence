package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataAccessException;
import model.Product;
import model.Customer;
import model.SaleOrder;


public class OrderDB {

	private static final String createSaleOrder = 
			"select id, date, customer_id, freight_id, invoice_id";
	private static final String addOrderLine = 
			createSaleOrder + " where orderNo = ?";
	private PreparedStatement selectAll; 
	private PreparedStatement selectByPhoneNo;
	
	
	public OrderDB() throws SQLException {
		selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(createSaleOrder);
		selectByPhoneNo = DBConnection.getInstance().getConnection()
				.prepareStatement(addOrderLine);
	}
	
	
	private Product buildObject(ResultSet rs) throws SQLException {
		

		SaleOrder saleOrder = new SaleOrder(
				rs.getInt("orderNo"),
				rs.getDate("date"),
				rs.getBoolean("deliveryStatus"),
				rs.getDate("deliveryDate"),
				rs.getDouble("discountGiven"),
				new Customer(rs.getInt("id")),
				new ArrayList<OrderLineItem>()
				)
		
	}
	
}
