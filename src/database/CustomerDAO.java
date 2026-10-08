package database;

import java.util.List;

import data.DataAccessException;
import model.Customer;

public interface CustomerDAO {

	List<Customer> findAll() throws DataAccessException;

	Customer findById(int id) throws DataAccessException;

}
