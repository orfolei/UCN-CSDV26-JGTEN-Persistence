package database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Customer;
import model.OrderLineItem;
import model.Product;
import model.SaleOrder;


public class OrderDB implements OrderDAO {

	private static final String insert_order =
			"INSERT INTO SaleOrder([date], deliveryState, customer_id)"
			+ " VALUES (?,?,?)";
	
	private static final String insert_orderline =
			"INSERT INTO OrderLineItem "
			+ "(quantity, saleOrder_id, product_id, price_id) "
			+ "VALUES (?,?,?,?)";
	
	// lægger "antal" til reservedQuantity, men kun hvis det samleede reserverede stadig kan dækkes af stock.
	// COALESCE gør NULL til 0 og hvis det ikke holder, ændres ingenting og saveOrder laver rollback :)))) - t
	private static final String reserve_product =
			"UPDATE Product "
			+ "SET reservedQuantity = COALESCE(reservedQuantity, 0) + ? "
			+ "WHERE id = ? "
			+ "AND COALESCE (reservedQuantity, 0) + ? <= ("
			+ "SELECT COALESCE(SUM(quantity), 0) "
			+ "FROM stock WHERE product_id = ? )";
	
	// Invoice er droppet for nu, da det er uden for use case derfor beregner vi discounts her for nu. Jeg har lagt metoderne her......
	private static final double CLUB_DISCOUNT_LIMIT = 1500; // appendix a klubber får rabat over 1500kr
	private static final double CLUB_DISCOUNT_RATE = 0.10; // har bare lavet en procent det står ik i casen
	private static final double FREE_FREIGHT_LIMIT = 2500;
	private static final double FREIGHT_COST = 50;
	
	/*private PreparedStatement selectAll;
	private PreparedStatement selectByPhoneNo;*/
	
	private final DBConnection db;
	private final Connection connection;
	private final ProductDAO productDAO;

	public OrderDB() throws SQLException {
		
		db = DBConnection.getInstance();
		connection = db.getConnection();
		
		if (connection == null || connection.isClosed()) {
			throw new SQLException("Ingen db forbindelse");
		}
		
		productDAO = new ProductDB();
		
		/*selectAll = DBConnection.getInstance().getConnection()
				.prepareStatement(createSaleOrder);
		selectByPhoneNo = DBConnection.getInstance().getConnection()
				.prepareStatement(addOrderLine);*/
	}


	/*private static final String selectAllQ = "select s.id, s.date, s.deliveryState, c.name, c.address, c.zipCode, ci.city, c.phoneNo "
			+ "from SaleOrder s " + "join customer c on c.id = s.customer_id "
			+ "join city ci on ci.zipCode = c.zipCode";*/

	private SaleOrder buildObject(ResultSet rs) throws SQLException {
		Customer customer = new Customer(rs.getString("name"), rs.getString("address"), rs.getInt("zipCode"),
				rs.getString("city"), rs.getInt("phoneNo"), rs.getString("customerType")
		);

		return new SaleOrder(rs.getInt("id"), rs.getDate("date").toLocalDate(),
				"delivered".equals(rs.getString("deliveryState")),
				null, // deliveryDate findes ikke i tabellen
				0.0, // discount findes ikke i tabellen
				customer,
				new ArrayList<OrderLineItem>()
		);
	}

	@Override
	public SaleOrder createOrder(Customer customer) {
		if(customer == null) {
			throw new IllegalArgumentException("En ordre skal have en kunde tilknyttet");
		}
		
		return new SaleOrder(
				0,
				LocalDate.now(),
				false,
				null,
				0.0,
				customer,
				new ArrayList<>()
				);
	}
	
	@Override
	public void addOrderLine(SaleOrder order, int productNumber, int quantity) throws DataAccessException {
		// ordren må ikke være gemt endnu derfor tjek om orderno 0 ellers ændrer vi på noget der allerede findes -t
		if (order == null || order.getOrderNo() != 0) { 
			throw new DataAccessException("Vælg en ordre som ikke er gemt");
		}
		
		if (quantity <= 0) {
			throw new DataAccessException("Antallet skal være større end 0");
		}
		
		Product product = productDAO.findByNumber(productNumber);
		
		if (product == null) {
			throw new DataAccessException("Varen findes ikke eller mangler en pris");
		}
		
		double price = product.getPrice().getPrice();
		
		if (!Double.isFinite(price) || price < 0 || !Double.isFinite(price * quantity)) {
			throw new DataAccessException("Ugyldig pris");
		}
		
		OrderLineItem line = new OrderLineItem();
		line.setProduct(product);
		line.setQuantity(quantity);
		line.setProductPrice(price);
		line.setLineTotalPrice(price * quantity);
		
		order.addOrderLineItem(line);
	}
	
	@Override
	public double calcDiscount(SaleOrder order) {
		double price = order.calcPrice();
		String type = order.getCustomer().getCustomerType();
		
		if ("club".equalsIgnoreCase(type) && price > CLUB_DISCOUNT_LIMIT) {
			return price * CLUB_DISCOUNT_RATE;
		}
		return 0;
	}
	
	@Override
	public double calcFreight(SaleOrder order) {
		double price = order.calcPrice();
		String type = order.getCustomer().getCustomerType();
		
		if ("private".equalsIgnoreCase(type) && price > FREE_FREIGHT_LIMIT) {
			return 0;
		}
		return FREIGHT_COST;
	}
	
	@Override
	public void saveOrder(SaleOrder order) throws DataAccessException {
		
		// her tjekker vi tingene som skal være i orden
		
		if (order == null || order.getOrderNo() != 0) {
			throw new DataAccessException("Der er ikke indtastet en ordre, eller også er den gemt allerede");
		}
			
		if (order.getCustomer() == null || order.getCustomer().getId() <= 0) {
			throw new DataAccessException("Kunden skal være eksistere først.");
		}
		
		if (order.getDate() == null || order.getOrderLineItems().isEmpty()) {
			throw new DataAccessException("Ordren skal have en dato og mindst et produkt");
		}
		
		// TODO: Kan laves, men er ikke i Place Order use casen!!! - t
		/*if (order.getFreight() != null || order.getInvoice() != null || order.getDiscountGiven() == null || order.getDiscountGiven() != 0.0) {
			throw new DataAccessException("Fragt, faktura og rabat mangler!");
		}*/

		for (OrderLineItem line : order.getOrderLineItems()) {
			if (line == null || line.getQuantity() <= 0 || line.getProduct() == null || line.getProduct().getId() <= 0 || line.getProduct().getPrice() == null || line.getProduct().getPrice().getId() <= 0) {
				throw new DataAccessException("Hele orderline er ugyldig...");
			}
		}
		
		// Her starter db delen
		
			try {
				if (!connection.getAutoCommit()) {
					throw new DataAccessException("Der er allerede en aktiv transaktion");
				}
				
				// !!!!!
				/// DISCOUNT!"!!!!
				order.setDiscountGiven(calcDiscount(order));
				
				/// HER SKER DET!!!!!
				db.startTransaction();
			}
			
			catch (SQLException e) {
				throw new DataAccessException("DB startede ikke", e);
			}
			
			int orderId;
			
	        try {
	            // luk statements før commit, så fejl her stadig kan rulles tilbage.
	            try (PreparedStatement orderStatement =
	                     connection.prepareStatement(
	                         insert_order, Statement.RETURN_GENERATED_KEYS);
	                 PreparedStatement lineStatement =
	                     connection.prepareStatement(insert_orderline);
	                 PreparedStatement reserveStatement =
	                     connection.prepareStatement(reserve_product)) {

	                orderStatement.setDate(
	                    1, Date.valueOf(order.getDate()));
	                orderStatement.setString(2, "pending");
	                orderStatement.setInt(
	                    3, order.getCustomer().getId());

	                orderId = db.executeInsertWithIdentity(orderStatement);

	                if (orderId <= 0) {
	                    throw new SQLException(
	                        "Ordren fik ikke et database-id");
	                }
	                
	                // items ind i statements
	                for (OrderLineItem line : order.getOrderLineItems()) {
	                    int productId = line.getProduct().getId();
	                    int quantity = line.getQuantity();

	                    reserveStatement.setInt(1, quantity);
	                    reserveStatement.setInt(2, productId);
	                    reserveStatement.setInt(3, quantity);
	                    reserveStatement.setInt(4, productId);
	                    
	                    // tjek om der er nok på lager FØR der udføres
	                    if (reserveStatement.executeUpdate() != 1) {
	                        throw new SQLException(
	                            "Ikke tilstrækkeligt lager til varenummer " +
	                            line.getProduct().getProductNumber());
	                    }
	                    
	                    lineStatement.setInt(1, quantity);
	                    lineStatement.setInt(2, orderId);
	                    lineStatement.setInt(3, productId);
	                    lineStatement.setInt(
	                        4, line.getProduct().getPrice().getId());

	                    if (lineStatement.executeUpdate() != 1) {
	                        throw new SQLException(
	                            "Ordrelinjen blev ikke gemt");
	                    }
	                }
	            }

	            connection.commit();

	        } catch (SQLException | RuntimeException e) {
	            try {
	                db.rollbackTransaction();
	            } catch (SQLException rollbackError) {
	                e.addSuppressed(rollbackError);

	                // luk forbindelsen hvis den har et mislykkes rollback, sådan den ik genbruges
	                try {
	                    connection.close();
	                } catch (SQLException closeError) {
	                    e.addSuppressed(closeError);
	                }
	            }

	            throw new DataAccessException(
	                "Ordren kunne ikke gemmes", e);
	        }

	        order.setOrderNo(orderId);

	        try {
	            connection.setAutoCommit(true);
	        } catch (SQLException e) {
	            throw new DataAccessException(
	                "Ordre " + orderId +
	                " er gemt, men forbindelsen kunne ikke nulstilles", e);
	        }
	    }
}
