package database;

import java.util.List;

import model.Customer;

public interface CustomerDAO {

	List<Customer> findAll() throws DataAccessException;

	Customer findByPhoneNumber(int phoneNo) throws DataAccessException;

	void create(Customer c) throws DataAccessException;

}
