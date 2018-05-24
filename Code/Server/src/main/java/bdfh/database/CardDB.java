package bdfh.database;

import bdfh.logic.game.Card;
import java.sql.*;

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
	public Card[] getCards() {
		
		Card[] cards = null;
		
		String sql = "SELECT C.text, A.text "
					+ "FROM card C "
					+ "INNER JOIN action A ON C.action_id = A.id;";
		
		try {
			
			// Execute and get the result of the query
			Statement statement = db.connect().createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			// Get the cards of the logic
			int c = 0;
			
			while(result.next()) {
				
				String cardText = result.getString(0);
				String actionText = result.getString(1);
				
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
				Card card = new Card(cardText, effect);
				cards[c] = card;
				++c;
			}
			
			// Close the db
			statement.close();
			
		} catch (SQLException e) {
			System.out.print("The database can't get the cards of the logic : ");
			e.printStackTrace();
			
		} finally {
			db.disconnect();
		}
		
		return cards;
	}
}