package bdfh.database;

import java.sql.*;

/**
 * Class used to execute queries on the player table.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class PlayerDB {
	
	private static final DatabaseConnect db = DatabaseConnect.getInstance();
	
	private PlayerDB(){}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * PlayerDB to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final PlayerDB instance = new PlayerDB();
	}
	
	/**
	 * Get the only instance of PlayerDB.
	 *
	 * @return the instance of PlayerDB.
	 */
	protected static PlayerDB getInstance() {
		
		return Instance.instance;
	}

	/**
	 * Check if a username already exists.
	 *
	 * @param username  Username to check in the database.
	 * @return  true (the username already exists), false (the username is available).
	 */
	private boolean usernameExists(String username) {
		
		String sql = "SELECT COUNT(id) FROM player WHERE username = '" + username + "';";
		boolean usernameExists = false;
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Check if the player exists
			result.next();
			usernameExists = result.getInt(1) == 0 ? false : true;
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't check if the username " + username + " exists : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return usernameExists;
	}
	
	/**
	 * Create a player into the database.
	 *
	 * @param username  Username of the player.
	 * @param password  Password of the player.
	 *
	 * @return  false [DENIED] (the username already exists), true [OK] (the username is created).
	 */
	public boolean createPlayer(String username, String password){
		
		String sql = "INSERT INTO player VALUES(NULL, ?, ?);";
		boolean isCreated = false;
		
		try {
			
			if(usernameExists(username)){
				
				// The player already exists
				isCreated = false;
				
			} else {
				
				// The player can be created
				PreparedStatement statement = db.connect().prepareStatement(sql);
				
				// Execute the query
				statement.setString(1, username);
				statement.setString(2, password);
				statement.execute();
				
				isCreated = true;
				
				// Close the db
				statement.close();
			}
			
		} catch (SQLException e) {
			System.out.print("The database can't create the player " + username + " : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return isCreated;
	}
	
	/**
	 * Check if a player exists.
	 *
	 * @param username  Username to check in the database.
	 * @param password  Password to check in the database.
	 *
	 * @return  -1 if the password is wrong [DENIED], 0 if the username is unknown [UNKNOWN], ID of the player if he is found [OK]
	 */
	public int playerExists(String username, String password) {
		
		String sql = "SELECT COUNT(id), id FROM player WHERE username = '" + username + "' " + "AND password = '" + password + "';";
		int ID = -2;
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the ID of the player
			result.next();
			
			if (result.getInt(1) == 1) {
				
				// The player exists in the database
				ID = result.getInt("id");
				
			} else {
				
				if (usernameExists(username)) {
					
					// The player set the wrong password
					ID = -1;
					
				} else {
					
					// The player set an unknown username
					ID = 0;
				}
			}
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't check if the player " + username + " exists : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return ID;
	}
}
