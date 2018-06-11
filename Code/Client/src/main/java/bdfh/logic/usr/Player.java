package bdfh.logic.usr;

import bdfh.net.client.Client;
import bdfh.net.client.GameHandler;
import bdfh.net.client.Notification;
import bdfh.net.protocol.GameProtocol;
import bdfh.serializable.BoundParameters;

/**
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 *
 * @version 1.0
 */
public class Player {
	
	private static BoundParameters bounds;
	
	private int id;
	private String username;
	private boolean isReady;
	private int lobbyID = -1;
	
	private boolean myTurn = false;
	private boolean hasFreedomCard = false;
	
	
	private Player() {}
	
	private static class Instance {
		
		static final Player instance = new Player();
	}
	
	public static Player getInstance() {
		
		return Instance.instance;
	}
	
	public int getID() {
		
		return id;
	}
	
	public void setID(int id) {
		
		this.id = id;
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
	
	public boolean isMyTurn() {
		
		return myTurn;
	}
	
	public void setMyTurn(boolean myTurn) {
		
		this.myTurn = myTurn;
	}
	
	public boolean hasFreedomCard() {
		
		return hasFreedomCard;
	}
	
	public void setHasFreedomCard(boolean hasFreedomCard) {
		
		this.hasFreedomCard = hasFreedomCard;
	}
	
	/**
	 * Create a lobby with special parameters.
	 *
	 * @param nbrDice           Number of dice in the game.
	 * @param money             Money given at the start of the game.
	 * @param mode
	 * @param time
	 * @param randomGeneration  Random game generation.
	 *
	 * @return  true if the lobby is created, false otherwise.
	 */
	public boolean createLobby(int nbrDice, int money, int mode, int time, boolean randomGeneration) {
		
		return Client.getInstance().createLobby(new Parameter(nbrDice, money, mode, time, randomGeneration));
	}
	
	/**
	 * Join a lobby.
	 *
	 * @param lobbyID   ID of the lobby to join.
	 *
	 * @return true if the lobby is joined, false otherwise.
	 */
	public boolean joinLobby(int lobbyID) {
		
		return Client.getInstance().joinLobby(lobbyID);
	}
	
	/**
	 * Get the ID of the player's lobby.
	 *
	 * @return  ID >= 0 if the player has a lobby, -1 otherwise.
	 */
	public int getLobbyID() {
		
		return lobbyID;
	}
	
	/**
	 * Set the ID of the player's lobby.
	 *
	 * @param lobbyID   ID of the lobby (-1 if no lobby).
	 */
	public void setLobbyID(int lobbyID) {
		
		this.lobbyID = lobbyID;
	}
	
	/**
	 * The player is ready to play.
	 *
	 * @return  true if the player is ready, false otherwise.
	 */
	public boolean setReady() {
		
		return Client.getInstance().setReady();
	}
	
	/**
	 * Check if the player is ready.
	 *
	 * @return  true if the player is ready, false otherwise.
	 */
	public boolean isReady() {
		
		return isReady;
	}
	
	/**
	 * Leave the current lobby.
	 *
	 * @return  true if the lobby is left, false otherwise.
	 */
	public boolean quitLobby() {
		
		boolean result = Client.getInstance().quitLobby();
		isReady = !result;
		
		return result;
	}
	
	/**
	 * Call the rollDice method of the GameHandler and return the result.
	 *
	 * @return Result of dice.
	 */
	public void rollDice() {
		
		GameHandler.getInstance().rollDice();
	}
	
	public void endTurn() {
		
		GameHandler.getInstance().endTurn();
	}
	
	/**
	 * Use a card the leave the exam.
	 */
	public void useFreedomCard() {
		
		GameHandler.getInstance().useFreedomCard();
	}
	
	/**
	 * Pay the tax to leave the exam.
	 */
	public void payExamTax() {
		
		GameHandler.getInstance().payExamTax();
	}
	
	/**
	 * Set the bounds of the parameters.
	 *
	 * @param bounds    Bounds to set.
	 */
	public static void setBounds(BoundParameters bounds) {
		
		Player.bounds = bounds;
	}
	
	/**
	 * Get the bounds of the parameters.
	 *
	 * @return  bounds of the parameters.
	 */
	public static BoundParameters getBounds() {
		
		return bounds;
	}
	
	public void exitGame() {
		
		if (GameHandler.getInstance().isGameOver()) {
			GameHandler.getInstance().interrupt();
			Notification.getInstance().start();
		} else {
			
			Client.getInstance().exitGame();
			GameHandler.getInstance().interrupt();
			Notification.getInstance().start();
		}
	}
}