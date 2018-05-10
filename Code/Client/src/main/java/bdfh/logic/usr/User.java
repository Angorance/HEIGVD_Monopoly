package bdfh.logic.usr;

import bdfh.net.client.Client;
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
	 * Create a lobby with special parameters.
	 *
	 * @param nbrDice           Number of dice in the game.
	 * @param money             Money given at the start of the game.
	 * @param randomGeneration  Random game generation.
	 *
	 * @return  true if the lobby is created, false otherwise.
	 */
	public boolean createLobby(int nbrDice, int money, boolean randomGeneration) {
		
		// Create the lobby on the server
		return Client.getInstance().createLobby(new Parameter(nbrDice, money, randomGeneration));
	}
	
	/**
	 * TODO
	 */
	public void joinLobby() {
	
	}
	
	/**
	 * Leave the current lobby.
	 *
	 * @return  true if the lobby is left, false otherwise.
	 */
	public boolean quitLobby() {
		
		return Client.getInstance().quitLobby();
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
