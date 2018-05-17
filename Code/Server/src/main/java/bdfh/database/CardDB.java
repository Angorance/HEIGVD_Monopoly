package bdfh.database;

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
	 * ParameterDB to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final CardDB instance = new CardDB();
	}
	
	/**
	 * Get the only instance of ParameterDB.
	 *
	 * @return the instance of ParameterDB.
	 */
	protected static CardDB getInstance() {
		
		return Instance.instance;
	}

}