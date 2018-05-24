package bdfh.logic.saloon;

import bdfh.net.notification.NotificationHandler;
import bdfh.net.server.ClientHandler;
import bdfh.protocol.NotifProtocol;
import bdfh.serializable.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;

/**
 * Class Lobbies.
 * Used to create, store and remove Lobby instances.
 * Used to add, remove or set ready players on those Lobby instances.
 * Has a method to serialise itself.
 *
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Lobbies {
	
	private HashMap<Integer, Lobby> lobbies = new HashMap<>();
	private LinkedList<NotificationHandler> subList = new LinkedList<>();
	
	
	// SINGLETON ---------------------------------------------------------------
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
	
	// -------------------------------------------------------------------------
	// HASHMAP -----------------------------------------------------------------
	
	public HashMap<Integer, Lobby> getLobbies() {
		
		return lobbies;
	}
	
	public void addLobby(Lobby lobby) {
		
		lobbies.put(lobby.getID(), lobby);
	}
	
	public void removeLobby(Lobby lobby) {
		
		lobbies.remove(lobby.getID(), lobby);
		
		notifySubs(NotifProtocol.NOTIF_DELETE, lobby);
	}
	
	public Lobby getLobby(Integer lobbyID) {
		
		return (Lobby) getLobbies().get(lobbyID);
	}
	
	// -------------------------------------------------------------------------
	
	/**
	 * Add a new logic to the list of lobbies.
	 */
	public Lobby createLobby(ClientHandler creator, Parameter param) {
		
		// Create the lobby
		Lobby lobby = new Lobby(param);
		
		// Add the lobby to the list
		addLobby(lobby);
		
		// Let the creator join the lobby created
		lobby.joinLobby(creator);
		
		notifySubs(NotifProtocol.NOTIF_NEW, lobby);
		
		return lobby;
	}
	
	/**
	 * Add a player to a lobby
	 * @param player payer that wants to join a lobby
	 * @param lobbyID lobby requested to join
	 * @return
	 */
	public synchronized Lobby joinLobby(ClientHandler player, int lobbyID) {
		
		Lobby l = getLobby(lobbyID);
		
		if(l != null && !l.isFull()) {
			l.joinLobby(player);
			notifySubs(NotifProtocol.NOTIF_UPDATE, l);
		} else {
			l = null;
		}
		
		return l;
	}
	
	public synchronized void setReady(Lobby lobby, ClientHandler player) {
		if (lobby.setReady(player)) {
			notifySubs(NotifProtocol.NOTIF_UPDATE, lobby);
		}
	}
	
	
	// OBSERVABLE --------------------------------------------------------------
	
	public void addSubscriber(NotificationHandler notificationHandler) {
		if(!subList.contains(notificationHandler)){
			subList.add(notificationHandler);
		}
	}
	
	private void notifySubs(String cmd, Lobby l) {
		for (NotificationHandler n : subList){
			n.update(cmd, l);
		}
	}
	
	// -------------------------------------------------------------------------
	// SERIALISATION -----------------------------------------------------------
	
	public String jsonify() {
		
		JsonArray jsonMap = new JsonArray();
		
		for (Integer id : lobbies.keySet()) {
			
			JsonObject jsonKV = new JsonObject();
			
			JsonElement jsonId = new JsonPrimitive(id);
			JsonElement jsonLobby = new JsonPrimitive(lobbies.get(id).jsonify());
			
			jsonKV.add("key", jsonId);
			jsonKV.add("value", jsonLobby);
			
			jsonMap.add(jsonKV);
		}
		
		return GsonSerializer.getInstance().toJson(jsonMap);
	}
}
