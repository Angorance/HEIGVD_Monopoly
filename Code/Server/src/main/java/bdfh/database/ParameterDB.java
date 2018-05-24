package bdfh.database;

import bdfh.serializable.BoundParameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to execute queries on the parameter table.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class ParameterDB {
	
	private static final DatabaseConnect db = DatabaseConnect.getInstance();
	
	private ParameterDB() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * ParameterDB to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final ParameterDB instance = new ParameterDB();
	}
	
	/**
	 * Get the only instance of ParameterDB.
	 *
	 * @return the instance of ParameterDB.
	 */
	protected static ParameterDB getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Update the limits of the logic.
	 */
	public void updateLimits() {
		// TODO - call this method each time the administrator update the limits
		
		String sql = "SELECT * FROM parameter WHERE id = 1;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the limits of the logic
			result.next();
			
			int minDice = result.getInt("minDice");
			int maxDice = result.getInt("maxDice");
			int minMoney = result.getInt("minMoneyAtTheStart");
			int maxMoney = result.getInt("maxMoneyAtTheStart");
			int minTime = result.getInt("minTime");
			int maxTime = result.getInt("maxTime");
			boolean randomGameGeneration =
					result.getInt("randomGameGeneration") == 0 ? false : true;
			
			// Update the limits of the logic
			BoundParameters.getInstance()
					.updateLimits(minDice, maxDice, minMoney, maxMoney, minTime,
							maxTime, randomGameGeneration);
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out
					.print("The database can't get the limits of the logic : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
	}
	
	/**
	 * Set the limits of the logic in the database.
	 *
	 * @param minDice Minimum number of dice allowed
	 * @param maxDice Maximum number of dice allowed
	 * @param minMoney Minimum money allowed at the start per player
	 * @param maxMoney Maximum money allowed at the start per player
	 * @param minTime
	 * @param maxTime
	 * @param randomGameGeneration True if randomGameGeneration allowed, false
	 * 		otherwise
	 */
	public void setLimits(int minDice, int maxDice, int minMoney, int maxMoney,
			int minTime, int maxTime, boolean randomGameGeneration) {
		
		String sql = "UPDATE parameter " + "SET minDice = ?, "
				+ "SET maxDice = ?, " + "SET minMoneyAtTheStart = ?, "
				+ "SET maxMoneyAtTheStart = ?, "
				+ "SET minTime = ?, " + "SET maxTime = ?, "
				+ "SET randomGameGeneration = ? " + "WHERE id = 1;";
		
		try {
			PreparedStatement statement = db.connect().prepareStatement(sql);
			
			// Execute the query
			statement.setInt(1, minDice);
			statement.setInt(2, maxDice);
			statement.setInt(3, minMoney);
			statement.setInt(4, maxMoney);
			statement.setInt(5, minTime);
			statement.setInt(6, maxTime);
			statement.setBoolean(7, randomGameGeneration);
			statement.execute();
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out
					.print("The database can't set the limits of the logic : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
	}
}