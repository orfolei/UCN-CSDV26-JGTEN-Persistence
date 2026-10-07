import common.Configuration;
import common.ConfigurationFile;

import database.DBConnection;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, world!");

		Configuration config = ConfigurationFile.getInstance();
		DBConnection connection = DBConnection.getInstance();
		
		connection.disconnect();
	}
}