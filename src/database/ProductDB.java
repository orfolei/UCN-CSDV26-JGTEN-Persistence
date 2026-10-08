package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDB implements ProductDAO {
	private static final String selectAllQ = 
			"select id, name, address, zipCode, phoneNo";
	private static final String selectByIDQ = 
			selectAllQ + " where phoneNo = ?";
	private PreparedStatement selectAll; 
	private PreparedStatement selectByPhoneNo;
	
	public ProductDB() throws SQLException {
		selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(selectAllQ);
		selectByPhoneNo = DBConnection.getInstance().getConnection()
				.prepareStatement(selectByIDQ);
	}
	
	@Override
	public List<Product> findAll() throws DataAccessException  {
		try {
			ResultSet rs = selectAll.executeQuery();
			List<Product> res = buildObjects(rs);
			return res;
		} catch (SQLException e) {
			DataAccessException he = new DataAccessException("Could not find all", e);
			throw he;
		}
	}

	@Override
	public Product findById(int productNumber) throws DataAccessException {
		try {
			selectByPhoneNo.setInt(1, productNumber);
			ResultSet rs = selectByPhoneNo.executeQuery();
			Product p = null;
			if(rs.next()) {
				p = buildObject(rs);
			}
			return p;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find by productNumber = " + productNumber);
		}
	}

	private Product buildObject(ResultSet rs) throws SQLException {
		Product c = new Product(
				rs.getInt("productNumber"),
				rs.getString("name"),
				rs.getInt("minStock"),
				rs.getInt("reservedQty")
				);
		return c;
	}

	private List<Product> buildObjects(ResultSet rs) throws SQLException {
		List<Product> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}

}
