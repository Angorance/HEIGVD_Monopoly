package bdfh.data;

import bdfh.net.server.ClientHandler;

import java.util.*;

/**
 * Class used to store all lobbies created.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobbies {
	
	private ArrayList<Lobby> lobbies = new ArrayList();
	
	private Lobbies() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * Lobbies to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final Lobbies instance = new Lobbies();
	}
	
	/**
	 * Get the only instance of Lobbies.
	 *
	 * @return the instance of Lobbies.
	 */
	public static Lobbies getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Add a new game to the list of lobbies.
	 */
	public void createLobby(ClientHandler creator){
		
		// Create the lobby
		Lobby lobby = new Lobby();
		
		// Add the lobby to the list
		lobbies.add(lobby);
		
		// Let the creator join the lobby created
		lobby.joinLobby(creator);
	}
	
	/**
	 * Retrieve all lobbies created in the lobby.
	 *
	 * @return  lobbies created in the lobby
	 */
	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}
}
