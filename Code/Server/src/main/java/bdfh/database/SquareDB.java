package bdfh.database;

import bdfh.logic.game.Price;
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
	protected static SquareDB getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Get all squares stored in database.
	 *
	 * @return array of all squares.
	 */
	public ArrayList<Square> getSquares() {
		
		ArrayList<Square> squares = new ArrayList<>();
		
		String sql = "SELECT S.position, S.family, S.name, P.rent, P.priceHouse, P.priceHotel, P.hypothec "
				+ "FROM square S "
				+ "LEFT JOIN price P ON S.price_id = P.id;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the squares
			while(result.next()) {
				
				int position = result.getInt(1);
				String type = result.getString(2);
				String name = result.getString(3);
				int rent = result.getInt(4);
				int priceHouse = result.getInt(5);
				int priceHotel = result.getInt(6);
				int hypothec = result.getInt(7);
				
				// Get the family of the card
				Square.FAMILY family = Square.FAMILY.valueOf(type);

				// Create the price if needed
				Price prices = null;
				
				if(priceHouse != 0 && priceHotel != 0 && hypothec != 0) {
					prices = new Price(rent, priceHouse, priceHotel, hypothec);
				}
				
				// Create one card
				Square square = new Square(position, family, name, prices);
				squares.add(square);
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
