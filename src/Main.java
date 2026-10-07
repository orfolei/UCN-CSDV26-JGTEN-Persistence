import common.Configuration;
import common.ConfigurationFile;

import database.DBConnection;

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