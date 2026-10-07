package common;

/**
 * Configuration source, providing values mapped to keys, regardless of source.
 * See {@link ConfigurationFile} for a concrete implementation.
 * 
 * @see ConfigurationFile
 */
public interface Configuration {
	/**
	 * Searches for the property with the specified key in this property list. 
	 * The method returns null if the property is not found.
	 * 
	 * @param key the hashtable key
	 * @return the value in the configuration with the specified key, or null if not found.
	 */
	public String get(String key);
	
	/**
	 * Searches for the property with the specified key in this property list. 
	 * The method returns the provided default value if property is not found.
	 * 
	 * @param key the hashtable key
	 * @return the value in the configuration with the specified key, or default value.
	 */
	public String get(String key, String defaultValue);
}
