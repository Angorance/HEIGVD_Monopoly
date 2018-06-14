package bdfh.database;

import java.sql.*;
import java.util.Properties;

/**
 * Class used to open a connection to the database MySQL and execute queries.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class DatabaseConnect {
	
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static String DATABASE_URL;
	private static String USERNAME;
	private static String PASSWORD;
	
	private Connection connection;
	private Properties properties;
	
	private DatabaseConnect() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * DatabaseConnect to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final DatabaseConnect instance = new DatabaseConnect();
	}
	
	/**
	 * Get the only instance of DatabaseConnect.
	 *
	 * @return the instance of DatabaseConnect.
	 */
	public static DatabaseConnect getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Get the properties of the MySQL database connection.
	 *
	 * @return  properties of the connection.
	 */
	private Properties getProperties() {
		
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
		}
		
		return properties;
	}
	
	/**
	 * Set the connection properties to be connected to the database.
	 *
	 * @param ip        Url or ip of the databasse.
	 * @param port      Port of the database.
	 * @param name      Name of the database.
	 * @param username  Username used to connect to the database.
	 * @param password  Password used to connect to the database.
	 */
	public void setProperties(String ip, int port, String name, String username, String password) {
		DATABASE_URL = "jdbc:mysql://" + ip + ":" + port + "/" + name;
		USERNAME = username;
		PASSWORD = password;
	}
	
	/**
	 * Connect the server to the database.
	 *
	 * @return the connection created.
	 */
	protected Connection connect() {
		
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
				
			} catch (ClassNotFoundException | SQLException e) {
				System.out.print("The server can't connect to the database : ");
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	/**
	 * Disconnect the server from the database.
	 */
	protected void disconnect() {
		
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				
			} catch (SQLException e) {
				System.out.print("The server can't disconnect from the database : ");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the Player DB object used to send queries about the player.
	 * @return  the Player DB object
	 */
	public PlayerDB getPlayerDB(){
		return PlayerDB.getInstance();
	}
	
	/**
	 * Get the Parameter DB object used to send queries about the parameters.
	 * @return  the Parameter DB object
	 */
	public ParameterDB getParameterDB(){
		return ParameterDB.getInstance();
	}
	
	/**
	 * Get the Card DB object used to send queries about the cards.
	 * @return  the Card DB object
	 */
	public CardDB getCardDB(){
		return CardDB.getInstance();
	}
	
	/**
	 * Get the Square DB object used to send queries about the cards.
	 * @return  the Square DB object
	 */
	public SquareDB getSquareDB(){
		return SquareDB.getInstance();
	}
}
