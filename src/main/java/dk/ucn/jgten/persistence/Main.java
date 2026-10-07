package dk.ucn.jgten.persistence;

import dk.ucn.jgten.persistence.common.Configuration;
import dk.ucn.jgten.persistence.common.ConfigurationFile;
import dk.ucn.jgten.persistence.database.DBConnection;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, world!");

		Configuration config = ConfigurationFile.getInstance();
		DBConnection connection = DBConnection.getInstance();
		
		connection.disconnect();
	}
}