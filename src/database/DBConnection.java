package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.Configuration;
import common.ConfigurationFile;


public class DBConnection {
	private Connection connection = null;
	private static DBConnection dbConnection;
	
	private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DEFAULT_SERVER_NAME = "127.0.0.1\\SQLEXPRESS";
	private static final String DEFAULT_DATABASE = "persistence";
	private static final String DEFAULT_PORT = "1433";
	private static final String DEFAULT_USERNAME = "sa";
	private static final String DEFAULT_PASSWORD = "secretv26!";
	
	private String database;
	private String serverAddress;
	private String port;
	private String username;
	private String password;
	
	private DBConnection(Configuration config) {
		this.serverAddress = config.get("db.server.name", DEFAULT_SERVER_NAME);
		this.port = config.get("db.port", DEFAULT_PORT);
		this.database = config.get("db.name", DEFAULT_DATABASE);
		this.username = config.get("db.username", DEFAULT_USERNAME);
		this.password = config.get("db.password", DEFAULT_PASSWORD);
		
		String connectionString = String.format(
				"jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=false", 
				serverAddress, port, database, username, password
		);
		
		try {
			Class.forName(DRIVER_CLASS);
			connection = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load JDBC driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Could not connect to database " + database + "@" + serverAddress + ":" + port + " as user " + username + " using password ******");
			System.out.println("Connection string was: " + connectionString.substring(0, connectionString.length() - password.length()) + "....");
			e.printStackTrace();
		}
	}
	
	public static DBConnection getInstance() {
		if(dbConnection == null) {
			dbConnection = new DBConnection(ConfigurationFile.getInstance());
		}
		return dbConnection;
	}
	
	public void startTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}
	
	public void commitTransaction() throws SQLException {
		connection.commit();
		connection.setAutoCommit(true);
	}
	
	public void rollbackTransaction() throws SQLException {
		connection.rollback();
		connection.setAutoCommit(true);
	}
	
	public int executeInsertWithIdentity(PreparedStatement ps) throws SQLException  {
		int res = -1;
		try {
			res = ps.executeUpdate();
			if(res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeInsertWithIdentity(String sql) throws SQLException  {
		System.out.println("DBConnection, Inserting: " + sql);
		int res = -1;
		try (Statement s = connection.createStatement()) {
			res = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if(res > 0) {
				ResultSet rs = s.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
			//s.close(); -- the try block does this for us now

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	public int executeUpdate(String sql) throws SQLException {
		System.out.println("DBConnection, Updating: " + sql);
		int res = -1;
		try (Statement s = connection.createStatement()){
			res = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}
	
	
	public Connection getConnection() {
		return connection;
	}
	
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
