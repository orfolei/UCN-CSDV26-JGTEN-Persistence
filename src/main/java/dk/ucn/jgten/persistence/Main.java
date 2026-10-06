package dk.ucn.jgten.persistence;

import java.io.IOException;

import dk.ucn.jgten.persistence.core.Config;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Hello, world!");
		Config config = new Config("data", "config.ini");
		config.initialize();
	}
}