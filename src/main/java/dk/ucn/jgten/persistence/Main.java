package dk.ucn.jgten.persistence;

import java.io.IOException;

import dk.ucn.jgten.persistence.core.ConfigurationFile;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Hello, world!");
		
		ConfigurationFile config = new ConfigurationFile(
				"data/config.ini", 
				"data/config.ini.template"
		);
		
		System.out.println(config.get("db.name"));
	}
}