package bdfh.data;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.Parameter;

import java.util.ArrayList;

/**
 * Class used to simulate a game with players.
 *
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Lobby implements Runnable {

	public static int nbLobbies = 0;

	private int ID;
	public static final int MAX_PLAYER = 4;
	
	//private Board gameBoard;  TODO - uncomment when Board is implemented
	private Parameter param;
	private ArrayList<ClientHandler> players = new ArrayList<>(MAX_PLAYER);
	private ArrayList<Boolean> areReady = new ArrayList(MAX_PLAYER);
	
	boolean isRunning = false;
	
	
	public Lobby(Parameter param) {
		
		this.param = param;
		setID();
	}
	
	/**
	 * Add a player to the game.
	 *
	 * @param player Player who wants to join the game.
	 */
	public synchronized void joinLobby(ClientHandler player) {
		
		players.add(player);
		areReady.add(false);
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
	 * @param player Player who wants to quit the game.
	 */
	public synchronized void quitLobby(ClientHandler player) {
		
		if (!areReady.isEmpty() && areReady.get(players.indexOf(player)) != null) {
			areReady.remove(players.indexOf(player));
		}
		
		players.remove(player);
		
		if (players.isEmpty()) {
			Lobbies.getInstance().removeLobby(this);
		}
	}
	
	public synchronized void setReady(ClientHandler player) {
		
		areReady.set(players.indexOf(player), true);
		
		if (players.size() > 1) {
			
			boolean allR = true;
			
			for (ClientHandler cl : players) {
				if (areReady.get(players.indexOf(cl)) != true) {
					allR = false;
					break;
				}
			}
			
			if (allR) {
				System.out.println("ALL READY");
			}
		}
	}
	
	/**
	 * TODO
	 *
	 * @return
	 */
	public boolean isRunning() {
		
		return isRunning;
	}
	
	public synchronized void setID() {
		
		ID = nbLobbies++;
	}
	
	public int getID() {
		
		return ID;
	}
	
	public synchronized ArrayList<ClientHandler> getPlayers() {
		
		return players;
	}

	public ArrayList<Boolean> getAreReady() {
		return areReady;
	}

	public boolean isFull() {
		
		return (getPlayers().size() == 4);
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
