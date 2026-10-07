package dk.ucn.jgten.persistence;

import java.io.IOException;

import dk.ucn.jgten.persistence.common.Configuration;
import dk.ucn.jgten.persistence.common.ConfigurationFile;
import dk.ucn.jgten.persistence.database.DBConnection;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, world!");

		Configuration config = new ConfigurationFile(
				"data/config.ini", 
				"data/config.ini.template"
		);
		
		DBConnection connection = new DBConnection(config);
		connection.disconnect();
	}
}