package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Customer;
import model.OrderLineItem;
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


	private static final String selectAllQ = "select s.id, s.date, s.deliveryState, c.name, c.address, c.zipCode, ci.city, c.phoneNo "
			+ "from SaleOrder s " + "join customer c on c.id = s.customer_id "
			+ "join city ci on ci.zipCode = c.zipCode";

	private SaleOrder buildObject(ResultSet rs) throws SQLException {
		Customer customer = new Customer(rs.getString("name"), rs.getString("address"), rs.getInt("zipCode"),
				rs.getString("city"), rs.getInt("phoneNo"), rs.getString("customerType")
		);

		return new SaleOrder(rs.getInt("id"), rs.getDate("date").toLocalDate(),
				"delivered".equals(rs.getString("deliveryState")),
				null, // deliveryDate findes ikke i tabellen
				0.0, // discount findes ikke i tabellen
				customer,
				new ArrayList<OrderLineItem>()
		);
	}

}
