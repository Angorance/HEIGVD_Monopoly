package bdfh.logic.usr;

/**
 * @version 1.0
 * @authors Daniel Gonzalez Lopez
 */
public class User {
	
	private String username;
	
	private User() {}
	
	private static class Instance {
		
		static final User instance = new User();
	}
	
	/**
	 * TODO
	 *
	 * @return TODO
	 */
	public static User getInstance() {
		
		return Instance.instance;
	}
	
	
	/**
	 * Get the username
	 *
	 * @return the username
	 */
	public String getUsername() {
		
		return username;
	}
	
	/**
	 * Set the username
	 *
	 * @param username username to set
	 */
	public void setUsername(String username) {
		
		this.username = username;
	}
	
	/**
	 * TODO
	 */
	public void createLobby() {
	
	}
	
	/**
	 * TODO
	 */
	public void joinLobby() {
	
	}
	
	/**
	 * TODO
	 */
	public void rollDice() {
	
	}
	
	/**
	 * TODO
	 */
	public void buy() {
	
	}
	
	/**
	 * TODO
	 */
	public void sell() {
	
	}
	
}
