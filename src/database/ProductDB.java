package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Price;
import model.Product;
import model.Stock;

public class ProductDB implements ProductDAO {
	
	private static final String select = 
			"SELECT p.id, p.productNumber, p.name,"
			+ " p.reservedQuantity, "
			+ "pr.id AS priceId, pr.timeStamp AS priceDate, pr.price " // "AS" gør at vi får et anderledes navn, sådan det er fatbart for os og for koden
			+ "FROM Product p "
			+ "JOIN Price pr ON pr.product_id = p.id AND pr.id = (" // join forbinder produktet med dets pris, hvor vi finder id'et for prisen i underforespørgslen efter (
			+ "SELECT TOP 1 id "
			+ "FROM Price "
			+ "WHERE product_id = p.id AND timestamp <= CURRENT_TIMESTAMP " // tjekker tiden NU og vælger den nyeste - hvis to priser har samme tid, vælges højeste id i stedet
			+ "ORDER BY timestamp DESC, id DESC" // sorterer efter nyeste
			+ ")";
	
	private static final String select_stock =
			"SELECT id, quantity, minStock, warehouse_id "
			+ "FROM stock WHERE product_id = ?";
	
	// kommenteret og udskiftet sry - t
	/*private static final String selectAllQ = 
			"select id, name, address, zipCode, phoneNo";
	private static final String selectByIDQ = 
			selectAllQ + " where phoneNo = ?";
	private PreparedStatement selectAll; 
	private PreparedStatement selectByPhoneNo;*/
	
	private final Connection connection;
	
	public ProductDB() throws SQLException {
		
		connection = DBConnection.getInstance().getConnection();
		
		if (connection == null || connection.isClosed()) {
			throw new SQLException("Ikke forbundet til db");
		}
	}
	
	@Override
	public List<Product> findAll() throws DataAccessException  {
		List<Product> productList = new ArrayList<>();
		
		try (PreparedStatement statement =
			connection.prepareStatement(select + " ORDER BY p.productNumber");
		ResultSet rs = statement.executeQuery()){
			
			// while = fortsæt indtil der ikke er flere products - DÅRLIGT
			while (rs.next()) {
				productList.add(buildObject(rs));
			}
			
			// nyt for at få stock med i beregningen... - t
			for (Product p : productList) {
				loadStock(p);
			}
			// her får vi den endelige liste
			return productList;
		
		} catch (SQLException e) {
			throw new DataAccessException("Kunne ikke finde produkterr", e);
		}
	}

	// fixet til at tage stock med - t
	@Override
	public Product findByNumber(int productNumber) throws DataAccessException {
		try {
				Product product;
			try(
				PreparedStatement statement =
				connection.prepareStatement(
						select + " WHERE p.productNumber = ?")) {
							statement.setInt(1, productNumber);
							
							try (ResultSet rs = statement.executeQuery()) {
								if(!rs.next()) {
									return null;
								}
								
								product = buildObject(rs);
								
								// giver lidt sig selv - men tjek efter omd er er flere med samme nummer - BAD DAYS!!!
								if (rs.next()) {
									throw new DataAccessException("Flere produkter har varenummer " + productNumber);
								}
							}
						}
				
						// for at få stock med
						loadStock(product);
						// ??? profit
						return product;
		// generic sql fejl, hvis noget ikke virker
						// note to self: spørg en med god hjerne om man kan flytte de her tjek til toppen og slippe for try-catching hele tiden, selvom eclipse meget gerne vi lha det
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not find by productNumber = " + productNumber);
		}
	}
	
	// ik perfekt - her henter vi stock til at lægge på products - t
	private void loadStock(Product product) throws SQLException{
		// preparedstatement fra toppen
		try (PreparedStatement statement = connection.prepareStatement(select_stock)) {
			statement.setInt(1, product.getId());
			
			//standard resultset
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					product.addStock(new Stock(
							rs.getInt("id"),
							rs.getInt("quantity"),
							rs.getInt("minStock"),
							rs.getInt("warehouse_id")
					));
				}
			}
		}
	}

	private Product buildObject(ResultSet rs) throws SQLException {
		Product product = new Product(
				rs.getInt("productNumber"),
				rs.getString("name"),
				rs.getInt("reservedQuantity")
				);
			
			product.setId(rs.getInt("id"));
			
			Price price = new Price(
					rs.getDate("priceDate").toLocalDate(),
					rs.getDouble("price")
					);
			
			price.setId(rs.getInt("priceId"));
			product.setPrice(price);
		
		return product;
	}

	private List<Product> buildObjects(ResultSet rs) throws SQLException {
		List<Product> res = new ArrayList<>();
		while(rs.next()) {
			res.add(buildObject(rs));
		}
		return res;
	}

}
