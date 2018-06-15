package bdfh.protocol;

import bdfh.database.DatabaseConnect;

import java.io.*;
import java.util.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class AppConfig {
	private final String CONFIG_FILE = "server_config.properties";
	
	private String serverIP;        // Url or ip of the server
	private int clientPort;         // Port used for the client connection
	private int notificationPort;   // Port used for the notification connection
	
	private String databaseIP;      // Url or ip of the database
	private int dbPort;             // Port used for the database connection
	private String dbName;          // Name of the database
	private String dbUsername;      // Username used to connect to the database
	private String dbPassword;      // Password used to connect to the database
	
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
			
			databaseIP = prop.getProperty("databaseIP");
			dbPort = Integer.parseInt(prop.getProperty("dbPort"));
			dbName = prop.getProperty("dbName");
			dbUsername = prop.getProperty("dbUsername");
			dbPassword = prop.getProperty("dbPassword");
			
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
	 * Initialize the connection with the database.
	 */
	public void setDatabaseConfig() {
		DatabaseConnect.getInstance().setProperties(databaseIP, dbPort, dbName, dbUsername, dbPassword);
	}
	
	/**
	 * Initialize the connection with the server.
	 */
	public void setServerConfig() {
		Protocoly.SERVER = serverIP;
		Protocoly.CPORT = clientPort;
		NotifProtocol.NPORT = notificationPort;
	}
}