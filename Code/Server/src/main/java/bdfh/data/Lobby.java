package bdfh.data;

import bdfh.net.server.ClientHandler;

import java.util.ArrayList;

/**
 * Class used to simulate a game with players.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobby {
	
	//private Board gameBoard;  TODO - uncomment when Board is implemented
	private ArrayList<ClientHandler> players = new ArrayList<>();
	
	/**
	 * Add a player to the game.
	 *
	 * @param player    Player who wants to join the game.
	 */
	public void joinLobby(ClientHandler player) {
		players.add(player);
	}
	
	/**
	 * Start the game when all players are ready.
	 */
	public void startLobby() {
		//TODO - implement when sprint 3 is started
	}
	
	/**
	 * Remove a player from the game.
	 *
	 * @param player    Player who wants to quit the game.
	 */
	public void quitLobby(ClientHandler player) {
		players.remove(player);
	}
}
