package dk.ucn.jgten.persistence.common;

public interface Configuration {
	public String get(String key);
	public String get(String key, String defaultValue);
	
	public boolean getBoolean(String key);
	public boolean getBoolean(String key, boolean defaultValue);
	
	public int getInt(String key);
	public int getInt(String key, int defaultValue);
	
	public double getDouble(String key);
	public double getDouble(String key, double defaultValue);
}
