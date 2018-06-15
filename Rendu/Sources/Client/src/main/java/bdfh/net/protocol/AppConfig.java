package bdfh.net.protocol;

import java.io.*;
import java.util.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class AppConfig {
	
	private final String CONFIG_FILE = "client_config.properties";
	
	private String serverIP;        // Url or ip of the server
	private int clientPort;         // Port used for the client connection
	private int notificationPort;   // Port used for the notification connection
	
	/**
	 * Constructor
	 */
	public AppConfig() {
		readConfig();
	}
	
	/**
	 * Read the configuration file and extract the config from it.
	 */
	private void readConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(CONFIG_FILE);
			
			// Load the properties file
			prop.load(input);
			
			// Get the properties values
			serverIP = prop.getProperty("serverIP");
			clientPort = Integer.parseInt(prop.getProperty("clientPort"));
			notificationPort = Integer.parseInt(prop.getProperty("notificationPort"));
			
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}
	
	/**
	 * Initialize the connection with the server.
	 */
	public void setClientConfig() {
		Protocoly.SERVER = serverIP;
		Protocoly.CPORT = clientPort;
		Protocoly.NPORT = notificationPort;
	}
}
