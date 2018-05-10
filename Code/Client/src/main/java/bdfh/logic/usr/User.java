package bdfh.logic.usr;

import bdfh.serializable.BoundParameters;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class User {
	
	
	private static BoundParameters bounds;
	
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
	
	public static void setBounds(BoundParameters bounds) {
		
		User.bounds = bounds;
	}
	
	public static BoundParameters getBounds() {
		
		return bounds;
	}
}
