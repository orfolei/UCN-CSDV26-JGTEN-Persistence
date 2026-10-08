package controller;

import java.sql.SQLException;

import database.CustomerDAO;
import database.CustomerDB;
import database.DataAccessException;
import model.Customer;


public class CustomerController {

	private CustomerDAO customerDAO;

	public CustomerController() throws SQLException
	{
		customerDAO = new CustomerDB();
	}

	public Customer findCustomerByPhone(int phoneNo) throws DataAccessException {
		Customer customer = customerDAO.findByPhoneNumber(phoneNo);
		return customer;
	}

	public void createCustomer(Customer customer) throws DataAccessException {
		customerDAO.create(customer);
	}
}
