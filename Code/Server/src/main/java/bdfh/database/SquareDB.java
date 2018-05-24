package bdfh.database;

import bdfh.logic.game.Square;
import java.sql.*;
import java.util.*;

/**
 * Class used to execute queries on the square table.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class SquareDB {
	
	private static final DatabaseConnect db = DatabaseConnect.getInstance();
	
	private SquareDB() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * SquareDB to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final SquareDB instance = new SquareDB();
	}
	
	/**
	 * Get the only instance of SquareDB.
	 *
	 * @return the instance of SquareDB.
	 */
	public static SquareDB getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Get all squares stored in database.
	 *
	 * @return array of all squares.
	 */
	public ArrayList<Square> getSquares() {
		
		ArrayList<Square> squares = null;
		
		String sql = "SELECT S.type, S.name, P.rent, P.priceHouse, P.priceHotel, P.hypothec "
				+ "FROM square S "
				+ "LEFT JOIN price P ON S.price_id = P.id;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the squares
			while(result.next()) {
				
				/*String type = result.getString(0);
				String name = result.getString(1);
				
				// Create one card
				Square square = new Square(cardText, effect);
				squares.add(square);*/
			}
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't get the squares : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return squares;
	}
}
