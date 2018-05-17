package bdfh.game;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.LightLobby;
import bdfh.serializable.Parameter;

import java.util.ArrayList;

/**
 * Class used to simulate a game with players.
 *
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Lobby extends LightLobby {

	public static int nbLobbies = 0;

	//private int ID;
	public static final int MAX_PLAYER = 4;
	
	//private Board gameBoard;  TODO - uncomment when Board is implemented
	private Parameter param;
	private ArrayList<ClientHandler> players = new ArrayList<>(MAX_PLAYER);
	
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
		
		super.addPlayer(player.getClientUsername());
	}
	
	public synchronized void setReady(ClientHandler player) {
		
		addReady(true, players.indexOf(player));
		
		if (players.size() > 1) {
			
			boolean allR = true;
			
			for (ClientHandler cl : players) {
				if (getAreReady().get(players.indexOf(cl)) != true) {
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
	 * Start the game when all players are ready.
	 */
	public void startLobby() {
		// TODO - create GameLogic thread
		// TODO - Remove lobby from lobbies list
	}
	
	/**
	 * Remove a player from the game.
	 *
	 * @param player Player who wants to quit the game.
	 */
	public synchronized void quitLobby(ClientHandler player) {
		
		if (!getAreReady().isEmpty() && getAreReady().get(players.indexOf(player)) != null) {
			removePlayer(players.indexOf(player));
		}
		
		players.remove(player);
		
		if (players.isEmpty()) {
			Lobbies.getInstance().removeLobby(this);
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
		
		super.setID(nbLobbies++);
	}
	
	public synchronized ArrayList<ClientHandler> getPlayers() {
		
		return players;
	}

	public boolean isFull() {
		
		return (getPlayers().size() == 4);
	}
	
	public Parameter getParam() {
		
		return param;
	}
}
