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
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/cheseaux-poly";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
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
	 * Connect the server to the database.
	 *
	 * @return the connection created.
	 */
	private Connection connect() {
		
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
	private void disconnect() {
		
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
	
	/**************************************************************************
	 * Methods provided to the server about the player
	 *************************************************************************/
	
	/**
	 * Create a player into the database.
	 *
	 * @param username  Username of the player.
	 * @param password  Password of the player.
	 */
	public void createPlayer(String username, String password){
		
		String sql = "INSERT INTO player VALUES(NULL, ?, ?);";
		
		try {
			PreparedStatement statement = connect().prepareStatement(sql);
			
			// Execute the query
			statement.setString(1, username);
			statement.setString(2, password);
			statement.execute();
			
			// Close the connection
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't create the player " + username + " : ");
			e.printStackTrace();
			
		} finally {
			disconnect();
		}
	}
	
	/**
	 * Get all informations of a player with his ID.
	 *
	 * @param id    ID of the player to retrieve.
	 * @return      Player got.
	 */
	public Player selectPlayer(int id){
		
		String sql = "SELECT * FROM player WHERE id = " + id + ";";
		Player player = null;
		
		try {
			
			// Execute and get the result of the query
			Statement statement = connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Create the player
			//while(result.next()){
			result.next();
			String username = result.getString("username");
			String password = result.getString("password");
			
			player = new Player(id, username, password);
			//}
			
			// Close the connection
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't select the player with ID " + id + " : ");
			e.printStackTrace();
			
		} finally {
			disconnect();
		}
		
		return player;
	}
}
