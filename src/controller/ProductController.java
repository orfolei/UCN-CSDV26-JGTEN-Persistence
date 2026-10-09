package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import database.DBConnection;
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

	// nulstiller hele databasen til testdata (fylder lagre op, reserved = udgangspunkt) -t
	public void restock() throws SQLException, IOException {
		DBConnection.getInstance().executeScript("data/testdata.sql");
	}
}
