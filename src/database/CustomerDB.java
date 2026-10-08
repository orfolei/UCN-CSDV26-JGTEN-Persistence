package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.DataAccessException;
import model.Customer;

public class CustomerDB implements CustomerDAO {
	private static final String selectAllQ = 
			"select id, name, address, zipCode, phoneNo";
	private static final String selectByIDQ = 
			selectAllQ + " where phoneNo = ?";
	private PreparedStatement selectAll; 
	private PreparedStatement selectByPhoneNo;
	
	public CustomerDB() throws SQLException {
		selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(selectAllQ);
		selectByPhoneNo = DBConnection.getInstance().getConnection()
				.prepareStatement(selectByIDQ);
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
	public Customer findById(int phoneNo) throws DataAccessException {
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
		return c;
	}

	private List<Customer> buildObjects(ResultSet rs) throws SQLException {
		List<Customer> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}

}
