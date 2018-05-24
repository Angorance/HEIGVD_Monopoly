package bdfh.database;

import bdfh.logic.game.Square;
import java.sql.*;

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
	public Square[] getSquares() {
		
		Square[] squares = null;
		
		String sql = "SELECT S.type, S.name, S.price_id, P.rent, P.priceHouse, P.priceHotel, P.hypothec "
				+ "FROM square S "
				+ "INNER JOIN price P ON S.price_id = P.id;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the squares
			int s = 0;
			
			while(result.next()) {
				
				/*String type = result.getString(0);
				String name = result.getString(1);
				
				// Get the effect of the card
				Card.EFFECTS effect = null;
				
				switch(actionText) {
					case "MOVE":
						effect = Card.EFFECTS.MOVE;
						break;
					case "EXAM":
						effect = Card.EFFECTS.EXAM;
						break;
					case "WIN":
						effect = Card.EFFECTS.WIN;
						break;
					case "LOSE":
						effect = Card.EFFECTS.LOSE;
						break;
					case "GOTO":
						effect = Card.EFFECTS.GOTO;
						break;
					case "CARD":
						effect = Card.EFFECTS.CARD;
						break;
				}
				
				// Create one card of the logic
				Square square = new Square(cardText, effect);
				squares[c] = square;
				++c;*/
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
