package bdfh.database;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.BoundParameters;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to execute queries on the parameter table.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class ParameterDB {
	
	private List<ClientHandler> subs = new ArrayList<>();
	
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
			
			notifySubs();
			
		} catch (SQLException e) {
			System.out
					.print("The database can't get the limits : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
	}
	
	/**
	 * Set the limits of the logic in the database.
	 *
	 * @param p parameters to use
	 */
	public void setLimits(BoundParameters p) {
		
		String sql = "UPDATE parameter " + "SET minDice = ?, "
				+ "maxDice = ?, " + "minMoneyAtTheStart = ?, "
				+ "maxMoneyAtTheStart = ?, "
				+ "minTime = ?, " + "maxTime = ?, "
				+ "randomGameGeneration = ? " + " WHERE id = 1;";
		
		try {
			PreparedStatement statement = db.connect().prepareStatement(sql);
			
			// Execute the query
			statement.setInt(1, p.getMinDice());
			statement.setInt(2, p.getMaxDice());
			statement.setInt(3, p.getMinMoneyAtTheStart());
			statement.setInt(4, p.getMaxMoneyAtTheStart());
			statement.setInt(5, p.getMinTime());
			statement.setInt(6, p.getMaxTime());
			statement.setBoolean(7, p.isRandomGameGeneration());
			statement.execute();
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out
					.print("The database can't set the limits : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
	}
	
	public void notifySubs() {
		for (ClientHandler c : subs) {
			c.update();
		}
	}
	
	public void addSubscriber(ClientHandler c) {
		
		subs.add(c);
	}
}