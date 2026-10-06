package dk.ucn.jgten.persistence.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Primitive configuration manager based on configuration files
 * @author Nova Estrid Lautrup
 * @version 23-09-2026
 */
public class Config {
	private final String fileName;
	private final String directoryName;
	private HashMap<String, String> configValues;
	
	/**
	 * @param directoryName configuration directory relative to working space
	 * @param fileName name of configuration file including file extension
	 */
	public Config(String directoryName, String fileName) {
		this.directoryName = directoryName;
		this.fileName = fileName;
		this.configValues = new HashMap<String, String>();
	}
	
	/**
	 * @param fileName fileName name of configuration file including file extension
	 */
	public Config(String fileName) { this(".", fileName); }
	
	/**
	 * Loads configuration file from path specified in constructor
	 * @return
	 */
	public Config initialize() throws IOException {
		// Read file and parse configuration
		String contents = readConfigFile();
		HashMap<String, String> map = parseConfig(contents);
		
		// Add all values from configuration file
		this.configValues.putAll(map);
		
		return this;
	}
	
	public String getOrDefault(String key, String defaultValue) {
		return configValues.getOrDefault(key, defaultValue);
	}
	
	public String get(String key) {
		return configValues.get(key);
	}
	
	private HashMap<String, String> parseConfig(String contents) {
		HashMap<String, String> configMap = new HashMap<String, String>();
		
		for (String line : contents.split("\n")) {
			String[] split = line.split("=");
			if (split.length < 2) {
				if (!line.isBlank())
					System.out.printf("Config key '%s' lacks a value - skipping\n", line);
				
				continue;
			}
			
			String key = split[0];
			
			// combine splits to allow for equal signs in value
			String value = String.join("=", Arrays.copyOfRange(split, 1, split.length));
			value = value.replaceAll("[\r\n]+$", "");
			
			configMap.put(key, value); 
		} 
		
		return configMap;
	}
	
	private String readConfigFile() throws IOException {
		// load file
		File directory = new File(directoryName);
		File file = new File(directory + "/" + fileName);
		
		// create configuration directory if not found
		if (!directory.exists()) {
			directory.mkdirs();
			System.out.printf("Created new configuration directory '%s'\n", directory.getAbsolutePath());
		}
	
		// create configuration file if not found
		if (!file.exists() && file.createNewFile()) {
			System.out.printf("Created new configuration file '%s'\n", file.getAbsolutePath());
		}
		
		return Files.readString(
				Path.of(file.getAbsolutePath())
		);
	}
}