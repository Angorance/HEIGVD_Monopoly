package bdfh.data;

import bdfh.net.ClientHandler;

import java.util.ArrayList;

/**
 * Class used to simulate a game with players.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Game {
	
	//private Board gameBoard;  TODO - uncomment when Board is implemented
	private ArrayList<ClientHandler> players = new ArrayList<>();
	
	/**
	 * Add a player to the game.
	 *
	 * @param player    Player who wants to join the game.
	 */
	public void joinGame(ClientHandler player) {
		players.add(player);
	}
	
	/**
	 * Start the game when all players are ready.
	 */
	public void startGame() {
		//TODO - implement when sprint 3 is started
	}
	
	/**
	 * Remove a player from the game.
	 *
	 * @param player    Player who wants to quit the game.
	 */
	public void quitGame(ClientHandler player) {
		players.remove(player);
	}
}
