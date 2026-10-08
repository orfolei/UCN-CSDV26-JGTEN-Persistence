package database;

import java.util.List;

import model.Product;

public interface ProductDAO {

	List<Product> findAll() throws DataAccessException;

	Product findByNumber(int productNumber) throws DataAccessException;

}
