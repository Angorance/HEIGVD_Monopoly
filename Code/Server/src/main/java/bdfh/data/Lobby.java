package bdfh.data;

import bdfh.net.ClientHandler;

import java.util.*;

/**
 * Class used to store all games created.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobby {
	
	private ArrayList<Game> games = new ArrayList();
	
	private Lobby() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * Lobby to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final Lobby instance = new Lobby();
	}
	
	/**
	 * Get the only instance of Lobby.
	 *
	 * @return the instance of Lobby.
	 */
	public static Lobby getInstance() {
		
		return Instance.instance;
	}
	
	/**
	 * Add a new game to the list of games.
	 */
	public void createGame(ClientHandler creator){
		
		// Create the game
		Game game = new Game();
		
		// Add the game to the list
		games.add(game);
		
		// Let the creator join the game created
		game.joinGame(creator);
	}
	
	/**
	 * Retrieve all games created in the lobby.
	 *
	 * @return  games created in the lobby
	 */
	public ArrayList<Game> getGames() {
		return games;
	}
}
