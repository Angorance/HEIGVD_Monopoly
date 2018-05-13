package bdfh.data;

import bdfh.net.notification.NotificationHandler;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.Protocoly;
import bdfh.serializable.*;

import java.util.*;

/**
 * Class used to store all lobbies created.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
	public class Lobbies {
	
	private HashMap<Integer, Lobby> lobbies = new HashMap<>();
	private LinkedList<NotificationHandler> subList = new LinkedList<>();
	
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
	public Lobby createLobby(ClientHandler creator, Parameter param) {
		
		// Create the lobby
		Lobby lobby = new Lobby(param);
		
		// Add the lobby to the list
		lobbies.put(lobby.getID(), lobby);
		
		// Let the creator join the lobby created
		lobby.joinLobby(creator);
		
		//notifySubs(NOT_UPDATE + " " + GsonSerializer.getInstance().toJson(new LightLobby(lobby)));
		
		return lobby;
	}
	
	/**
	 * Add a player to a lobby
	 * @param player payer that wants to join a lobby
	 * @param lobbyID lobby requested to join
	 * @return
	 */
	public synchronized Lobby joinLobby(ClientHandler player, int lobbyID) {
		Lobby l = lobbies.get(lobbyID);
		
		if(l != null && !l.isFull()) {
			l.joinLobby(player);
			notifySubs(Protocoly.NOT_UPDATE + " " + GsonSerializer.getInstance().toJson(new LightLobby(l)));
		}
		
		return l;
	}
	
	public void addSubscriber(NotificationHandler notificationHandler) {
		if(!subList.contains(notificationHandler)){
			subList.add(notificationHandler);
		}
	}
	
	private void notifySubs(String s) {
		for(NotificationHandler n : subList){
			n.sendData(s);
		}
	}
	
	public void removeLobby(Lobby lobby) {
		lobbies.remove(lobby.getID());
		notifySubs("DELETED " + lobby.getID());
	}
	
	/**
	 * Retrieve all lobbies created in the lobby.
	 *
	 * @return  lobbies created in the lobby
	 */
	public synchronized HashMap<Integer, Lobby> getLobbies() {
		return lobbies;
	}
}
