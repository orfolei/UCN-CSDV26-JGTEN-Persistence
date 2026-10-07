package dk.ucn.jgten.persistence.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Primitive file-based configuration system using the `.properties` file format.
 * @author Nova Estrid Lautrup
 * @version 06-10-2026
 */
public class ConfigurationFile implements Configuration {
	private static final Logger LOGGER = Logger.getLogger(ConfigurationFile.class.getName());
	
	private static ConfigurationFile configuration;
	
	private final File configFile;
	private final File templateFile;
	private final Properties properties;
	
	/**
	 * @param configPath path to configuration file including file extension
	 * @param templatePath path to a template file that should be initialized as
	 * 		  the configuration file, if the configuration file does not exist.
	 */
	private ConfigurationFile(String configPath, String templatePath) {
		this.properties = new Properties();
		this.configFile = new File(configPath).getAbsoluteFile();
		
		this.templateFile = (templatePath != null && !templatePath.isEmpty())
				? new File(templatePath).getAbsoluteFile()
				: null;
		
		this.load();
		this.updateFromTemplate();
	}
	
	
	
	private ConfigurationFile(String configPath) {
		this(configPath, null);
	}
	
	public static ConfigurationFile getInstance() {
		if (configuration == null) {
			configuration = new ConfigurationFile(
					"data/config.ini", 
					"data/config.ini.template"
			);
		}
		
		return configuration;
	}
	
	@Override
	public String get(String key) {
		return properties.getProperty(key);
	}
	
	@Override
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	@Override
	public boolean getBoolean(String key) {
		return getParsed(key, null, Boolean::parseBoolean);
	}
	
	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		return getParsed(key, defaultValue, Boolean::parseBoolean);
	}
	
	@Override
	public int getInt(String key) {
		return getParsed(key, null, Integer::parseInt);
	}
	
	@Override
	public int getInt(String key, int defaultValue) {
		return getParsed(key, defaultValue, Integer::parseInt);
	}
	
	@Override
	public double getDouble(String key) {
		return getParsed(key, null, Double::parseDouble);
	}
	
	@Override
	public double getDouble(String key, double defaultValue) {
		return getParsed(key, defaultValue, Double::parseDouble);
	}
	
	private <T> T getParsed(String key, T defaultValue, Function<String, T> parser) {
		String value = get(key);
		if (value == null)
			return defaultValue;
		
		value = value.trim();
		
		try {
			return parser.apply(value);
		} catch (IllegalArgumentException e) {
			if (defaultValue != null) {
				LOGGER.log(
						Level.WARNING, 
						String.format("Config contains value for '%s', but the value '%s' does not match expected type. Returning default value.", key, value)
				);
			}

			return defaultValue;
		}
	}
	
	private void load() {
		this.properties.clear();
		FileInputStream stream = null;
		
		try {
			if (!configFile.exists())
				createFile();
			
			// load and parse
			stream = new FileInputStream(configFile.getAbsolutePath());
			this.properties.load(stream);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to load configuration file", e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "Failed to close config file input stream", e);
				}
			}
		}
	}
	
	private void createFile() throws IOException {
		File directory = configFile.getParentFile();
		
		// create configuration directory if not found
		if (!directory.exists()) {
			directory.mkdirs();
			LOGGER.info("Created new configuration directory " + directory.getAbsolutePath());
		}
	
		
		// create configuration file if not found
		if (!configFile.exists() && configFile.createNewFile()) {
			LOGGER.info("Created new configuration file " + configFile.getAbsolutePath());
		}
	}
	
	private void updateFromTemplate() {
		if (templateFile == null)
			return;
		
		try (FileInputStream templateStream = new FileInputStream(templateFile.getAbsolutePath());
			 FileOutputStream outputStream = new FileOutputStream(configFile)) {
			
			Properties templateProperties = new Properties();
			templateProperties.load(templateStream);
		
			int propertiesAdded = 0;
			for (var entry : templateProperties.entrySet()) {
				if (this.properties.containsKey(entry.getKey()))
					continue;
				
				this.properties.put(entry.getKey(), entry.getValue());
				propertiesAdded++;
			}
			
			if (propertiesAdded > 0) {
				LOGGER.info("Loaded " + propertiesAdded + " new properties from template file.");
			}
			
			this.properties.store(outputStream, "Special characters have to be escaped");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to import values from template file", e);
		}
	}
}