package bdfh.data;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.Parameter;

import java.util.ArrayList;

/**
 * Class used to store all lobbies created.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobbies /*extends Observable*/ {
	
	private ArrayList<Lobby> lobbies = new ArrayList();
	
	private Lobbies() {}
	
	public void removeLobby(Lobby lobby) {
		
		lobbies.remove(lobby);
	}
	
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
	public Lobby createLobby(ClientHandler creator, Parameter param) {
		
		// Create the lobby
		Lobby lobby = new Lobby(param);
		
		// Add the lobby to the list
		lobbies.add(lobby);
		
		// Let the creator join the lobby created
		lobby.joinLobby(creator);
		
		return lobby;
	}
	
	public Lobby joinLobby(ClientHandler player, int lobbyID) {
		
		for (Lobby lo : getLobbies()) {
			if (lo.getID() == lobbyID && !lo.isFull()) {
				lo.joinLobby(player);
				
				return lo;
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve all lobbies created in the lobby.
	 *
	 * @return  lobbies created in the lobby
	 */
	public synchronized ArrayList<Lobby> getLobbies() {
		return lobbies;
	}
}
