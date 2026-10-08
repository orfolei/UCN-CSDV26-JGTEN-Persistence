package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Breed;
import model.Dog;
import model.Group;

public class CustomerDB implements CustomerDAO {
	private static final String selectAllQ = 
			"select id, name, description from groups";
	private static final String selectByIDQ = 
			selectAllQ + " where id = ?";
	private PreparedStatement selectAll; 
	private PreparedStatement selectByID;
	
	public GroupDB() throws SQLException {
		selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(selectAllQ);
		selectByID = DBConnection.getInstance().getConnection()
				.prepareStatement(selectByIDQ);
	}
	
	@Override
	public List<Group> findAll() throws DataAccessException  {
		try {
			ResultSet rs = selectAll.executeQuery();
			List<Group> res = buildObjects(rs);
			return res;
		} catch (SQLException e) {
			DataAccessException he = new DataAccessException(e, "Could not find all");
			throw he;
		}
	}

	@Override
	public Group findById(int id) throws DataAccessException {
		try {
			selectByID.setInt(1, id);
			ResultSet rs = selectByID.executeQuery();
			Group g = null;
			if(rs.next()) {
				g = buildObject(rs);
			}
			return g;
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find by id = " + id);
		}
	}

	private Group buildObject(ResultSet rs) throws SQLException {
		Group g = new Group(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("description")
				);
		return g;
	}

	private List<Group> buildObjects(ResultSet rs) throws SQLException {
		List<Group> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}

}
