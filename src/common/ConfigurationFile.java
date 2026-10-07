package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Primitive file-based configuration system using the `.properties` file format.
 * Modified to fit the "Persistence" project, with verbose explanitory comments.
 * 
 * @author Nova Estrid Lautrup
 * @version 07-10-2026
 * 
 * @hidden No AI has been used, neither directly nor indirectly.
 */
public class ConfigurationFile implements Configuration {
	private static final String CONFIG_PATH = "data/config.ini";
	private static final String TEMPLATE_PATH = "data/config.ini.template";
	
	private static ConfigurationFile configuration;
	
	private final File configFile;
	private final File templateFile;
	private final Properties properties;
	
	// constructor private bcs singleton
	/**
	 * @param configPath path to configuration file including file extension
	 * @param templatePath path to a template file that should be initialized as
	 * 		  the configuration file, if the configuration file does not exist.
	 */
	private ConfigurationFile(String configPath, String templatePath) {
		this.properties = new Properties();
		this.configFile = new File(configPath).getAbsoluteFile();
		
		// we use null checks to see if template is provided, so our
		// field here should either be the file, or null. using ternary
		// operator to handle this logic.
		this.templateFile = (templatePath != null && !templatePath.isEmpty())
				? new File(templatePath).getAbsoluteFile()
				: null;
		
		// grab values from config file
		this.load();
		
		// add any missing values from the template to our existing config file
		// saves the changes afterward
		this.updateFromTemplate();
	}
	
	// can also use this system without a template. expects
	// the configuration file to already exist at location.
	private ConfigurationFile(String configPath) {
		this(configPath, null);
	}
	
	
	// singleton
	public static ConfigurationFile getInstance() {
		if (configuration == null) {
			configuration = new ConfigurationFile(CONFIG_PATH, TEMPLATE_PATH);
		}
		
		return configuration;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	/**
	 * Refresh all properties from the config file, create file if not exists.
	 */
	private void load() {
		// refresh entirely, clear existing values
		this.properties.clear();
		
		try (FileInputStream stream = new FileInputStream(configFile.getAbsolutePath())) {
			// if the configuration file doesn't exist already
			// we should create it here...
			if (!configFile.exists())
				createFile();
			
			// java properties has a method to load properties from
			// files by default. this adds all the key value pairs
			// to our local field in our object.
			this.properties.load(stream);
			
		} catch (IOException e) {
			System.err.println("Failed to load configuration file");
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Creates the configuration file on disk, including the directory in which it's
	 * supposed to be located.
	 * @throws IOException if directories or file couldn't be created - check folder permissions.
	 */
	private void createFile() throws IOException {
		File directory = configFile.getParentFile();
		
		// create configuration directory if not found
		if (!directory.exists()) {
			directory.mkdirs();
			System.out.printf("Created new configuration directory %s\n", directory.getAbsolutePath());
		}
	
		
		// create configuration file if not found
		if (!configFile.exists() && configFile.createNewFile()) {
			System.out.printf("Created new configuration file %s\n", configFile.getAbsolutePath());
		}
	}
	
	/**
	 * Read template file, add properties from template if they are missing
	 * from the config file. Saves the changes to the config file when done.
	 */
	private void updateFromTemplate() {
		// guard clause, we don't need to run this code if a template file
		// hasn't been provided during configuration. 
		if (this.templateFile == null)
			return;
		
		// input and output streams may fail, but they're auto-closeable.
		// if defined in the "try-with-resources" statement, we don't
		// have to close them in case of exceptions.
		try (FileInputStream templateStream = new FileInputStream(templateFile.getAbsolutePath());
			 FileOutputStream outputStream = new FileOutputStream(configFile)) {
			
			Properties templateProperties = new Properties();
			templateProperties.load(templateStream);
		
			// keep track of properties added. nice to know if anything has changed
			// when executing the program.
			int propertiesAdded = 0;
			
			// loop through the properties in our template file, and check if
			// a property with the same key already exists in our properties.
			// if not exists, add it to our properties.
			for (Entry<Object, Object> entry : templateProperties.entrySet()) {
				if (this.properties.containsKey(entry.getKey()))
					continue;
				
				this.properties.put(entry.getKey(), entry.getValue());
				propertiesAdded++;
			}
			
			// log the number of changes, should be visible in console.
			if (propertiesAdded > 0) {
				System.out.printf("Loaded %d new properties from template file.\n", propertiesAdded);
			}
			
			// save configuration file
			this.properties.store(outputStream, "Special characters have to be escaped");
		} catch (IOException e) {
			System.err.println("Failed to import values from template file");
			System.err.println(e.getMessage());
		}
	}
}