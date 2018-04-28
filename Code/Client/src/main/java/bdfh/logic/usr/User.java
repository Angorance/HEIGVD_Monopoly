package bdfh.logic.usr;

/**
 * @version 1.0
 * @authors Daniel Gonzalez Lopez
 */
public class User {
	
	
	private User() {}
	
	private static class Instance {
		
		static final User instance = new User();
	}
	
	public static User getInstance() {
		
		return Instance.instance;
	}
}
