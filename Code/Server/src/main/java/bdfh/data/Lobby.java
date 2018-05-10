package bdfh.data;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.Parameter;

import java.util.ArrayList;

/**
 * Class used to simulate a game with players.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobby implements Runnable {
	
	//private Board gameBoard;  TODO - uncomment when Board is implemented
	private Parameter param;
	private ArrayList<ClientHandler> players = new ArrayList<>();
	
	
	public Lobby(Parameter param) {
		this.param = param;
	}
	
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
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
	
	}
}
