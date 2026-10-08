package controller;

import java.sql.SQLException;
import java.util.List;

import database.DataAccessException;
import database.ProductDAO;
import database.ProductDB;
import model.Product;

//giver videre til dao sådan at gui ik ved noget om db - sikkerhed! -t
public class ProductController {

	private final ProductDAO productDAO;
	
	public ProductController() throws SQLException{
		productDAO = new ProductDB();
	}
	
	public List<Product> findAll() throws DataAccessException {
		return productDAO.findAll();
	}
	
	public Product findbyProductNumber(int productNumber) throws DataAccessException{
		return productDAO.findByNumber(productNumber);
	}
}
