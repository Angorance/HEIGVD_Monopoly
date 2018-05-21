package bdfh.game;

import bdfh.net.notification.NotificationHandler;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.NotifProtocol;
import bdfh.serializable.*;

import java.util.*;

/**
 * Class used to store all lobbies created.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Lobbies extends LightLobbies {
	
	private LinkedList<NotificationHandler> subList = new LinkedList<>();
	
	private Lobbies() {}
	
	public Lobby getLobby(Integer lobbyID) {
		
		return (Lobby) getLobbies().get(lobbyID);
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
		addLobby(lobby);
		
		// Let the creator join the lobby created
		lobby.joinLobby(creator);
		
		notifySubs(NotifProtocol.NEW, lobby);
		
		return lobby;
	}
	
	/**
	 * Add a player to a lobby
	 * @param player payer that wants to join a lobby
	 * @param lobbyID lobby requested to join
	 * @return
	 */
	public synchronized Lobby joinLobby(ClientHandler player, int lobbyID) {
		Lobby l = (Lobby) getLobbies().get(lobbyID);
		
		if(l != null && !l.isFull()) {
			l.joinLobby(player);
			notifySubs(NotifProtocol.UPDATE, l);
		} else {
			l = null;
		}
		
		return l;
	}
	
	public void removeLobby(Lobby lobby) {
		super.removeLobby(lobby);
		
		notifySubs(NotifProtocol.DELETE, lobby);
	}
	
	
	public void addSubscriber(NotificationHandler notificationHandler) {
		if(!subList.contains(notificationHandler)){
			subList.add(notificationHandler);
		}
	}
	
	private void notifySubs(int cmd, LightLobby l) {
		for (NotificationHandler n : subList){
			n.update(cmd, l);
		}
	}
}
