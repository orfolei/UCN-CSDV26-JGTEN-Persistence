package database;

import java.util.List;

import data.DataAccessException;
import model.Product;

public interface ProductDAO {

	List<Product> findAll() throws DataAccessException;

	Product findById(int productNumber) throws DataAccessException;

}
