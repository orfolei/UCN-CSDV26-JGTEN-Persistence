package controller;
import model.Customer;
import database.CustomerDB;
import java.util.*;


public class CustomerController {
	
	private CustomerDB customerDB;
	
	public CustomerController() 
	{
		
	}
	
	public Customer findCustomerByPhone(int phoneNo){
		Customer customer = customerDB.findCustomerByPhone();
	}
}
