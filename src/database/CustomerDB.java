package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDB implements CustomerDAO {
	private static final String selectAllQ =
			"select c.id, c.name, c.address, c.zipCode, ci.city, c.phoneNo, c.customerType"
					+ " FROM customer c join city ci on ci.zipCode = c.zipCode";
	private static final String selectByPhoneNumberQ =
			selectAllQ + " where c.phoneNo = ?";
	private static final String insertQ = "INSERT INTO customer (name,address,zipCode,phoneNo,customerType)"
			+ "VALUES (?,?,?,?,?)";
	private PreparedStatement selectAll;
	private PreparedStatement selectByPhoneNo;
	private PreparedStatement insert;

	public CustomerDB() throws SQLException {
		selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(selectAllQ);
		selectByPhoneNo = DBConnection.getInstance().getConnection()
				.prepareStatement(selectByPhoneNumberQ);
		insert = DBConnection.getInstance().getConnection().prepareStatement(insertQ, Statement.RETURN_GENERATED_KEYS);
	}

	@Override
	public List<Customer> findAll() throws DataAccessException  {
		try {
			ResultSet rs = selectAll.executeQuery();
			List<Customer> res = buildObjects(rs);
			return res;
		} catch (SQLException e) {
			DataAccessException he = new DataAccessException("Could not find all", e);
			throw he;
		}
	}

	@Override
	public Customer findByPhoneNumber(int phoneNo) throws DataAccessException {
		try {
			selectByPhoneNo.setInt(1, phoneNo);
			ResultSet rs = selectByPhoneNo.executeQuery();
			Customer c = null;
			if(rs.next()) {
				c = buildObject(rs);
			}
			return c;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find by phoneNo = " + phoneNo);
		}
	}

	private Customer buildObject(ResultSet rs) throws SQLException {
		Customer c = new Customer(
				rs.getString("name"),
				rs.getString("address"),
				rs.getInt("zipCode"),
				rs.getString("city"),
				rs.getInt("phoneNo"),
				rs.getString("customerType")
				);
				c.setId(rs.getInt("id"));

		return c;
	}

	@Override
	public void create(Customer c) throws DataAccessException {
		try {
			insert.setString(1, c.getName());
			insert.setString(2, c.getAddress());
			insert.setInt(3, c.getZipCode());
			insert.setInt(4, c.getPhoneNo());
			insert.setString(5, c.getCustomerType());
			c.setId(DBConnection.getInstance().executeInsertWithIdentity(insert));
		} catch (SQLException e) {
			throw new DataAccessException("Kunne ikke lave kunden", e);
		}
	}


	private List<Customer> buildObjects(ResultSet rs) throws SQLException {
		List<Customer> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}

}
