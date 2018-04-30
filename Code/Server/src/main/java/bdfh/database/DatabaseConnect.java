package bdfh.database;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class DatabaseConnect {
	
	private DatabaseConnect() {}
	
	private static class Instance {
		
		static final DatabaseConnect instance = new DatabaseConnect();
	}
	
	public static DatabaseConnect getInstance() {
		
		return Instance.instance;
	}
}
