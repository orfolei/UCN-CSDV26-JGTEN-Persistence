package controller;

import java.sql.SQLException;

import database.CustomerDAO;
import database.CustomerDB;
import database.DataAccessException;
import model.Customer;

// giver videre til dao sådan at gui ik ved noget om db - sikkerhed! -t
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

	public Customer createCustomer(String name, String address, int zipCode, int phoneNo, String customerType)
			throws DataAccessException {
		Customer customer = new Customer(name, address, zipCode, null, phoneNo, customerType);
		customerDAO.create(customer);
		// hent den igen, så modellen bygges af db-laget med bynavn fra city-tabellen
		return customerDAO.findByPhoneNumber(phoneNo);
	}
}
