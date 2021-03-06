package bdfh.database;

import bdfh.logic.game.Card;
import java.sql.*;
import java.util.*;

/**
 * Class used to execute queries on the card table.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class CardDB {
	
	private static final DatabaseConnect db = DatabaseConnect.getInstance();
	
	private CardDB(){}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * CardDB to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final CardDB instance = new CardDB();
	}
	
	/**
	 * Get the only instance of CardDB.
	 *
	 * @return the instance of CardDB.
	 */
	protected static CardDB getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Get all cards stored in database.
	 *
	 * @return array of all cards.
	 */
	public ArrayList<Card> getCards() {
		
		ArrayList<Card> cards = new ArrayList<>();
		
		String sql = "SELECT C.text, C.quantity, A.type "
					+ "FROM card AS C "
					+ "INNER JOIN action AS A ON C.action_id = A.id;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the cards
			while(result.next()) {
				
				String text = result.getString("text");
				int quantity = result.getInt("quantity");
				String action = result.getString("type");
				
				// Create one card
				Card card = new Card(text, quantity, action);
				cards.add(card);
			}
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't get the cards : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return cards;
	}
}