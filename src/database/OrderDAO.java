package database;

import model.Customer;
import model.SaleOrder;

// kontrakten for ordrer.. hva kan gøres.. orderdb sørger for hvordan-t
public interface OrderDAO {
	SaleOrder createOrder(Customer customer);

	void addOrderLine(SaleOrder order, int productNumber, int quantity) throws DataAccessException;

	void saveOrder(SaleOrder order) throws DataAccessException;
	
	double calcDiscount(SaleOrder order);

	double calcFreight(SaleOrder order);
}
