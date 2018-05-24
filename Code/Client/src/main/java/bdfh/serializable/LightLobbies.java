package bdfh.serializable;

import com.google.gson.*;

import java.util.HashMap;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class LightLobbies {
	
	private HashMap<Integer, LightLobby> lobbies = new HashMap<>();
	
	private LightLobbies() {}
	
	private static class Instance {
		
		private static LightLobbies instance = new LightLobbies();
	}
	
	public static LightLobbies getInstance() {
		
		return Instance.instance;
	}
	
	public HashMap<Integer, LightLobby> getLobbies() {
		
		return lobbies;
	}
	
	public void addLobby(String json) {
		
		JsonObject jo = GsonSerializer.getInstance().fromJson(json, JsonObject.class);
		
		LightLobby lobby = LightLobby.instancify(jo);
		
		lobbies.put(lobby.getID(), lobby);
	}
	
	public void removeLobby(String json) {
		
		LightLobby lobby = LightLobby.instancify(json);
		
		lobbies.remove(lobby.getID(), lobby);
	}
	
	/**
	 * Remove all the lobbies.
	 */
	public void clearLobbies() {
		lobbies.clear();
	}
	
	public void instancify(String s) {
		
		JsonArray jsonMap = GsonSerializer.getInstance().fromJson(s, JsonArray.class);
		
		for (JsonElement je : jsonMap) {
			
			JsonObject jo = je.getAsJsonObject();
			
			int lobbyID = jo.get("key").getAsInt();
			String l = jo.get("value").getAsString();
			
			JsonObject jsonLobby = GsonSerializer.getInstance().fromJson(l, JsonObject.class);
			
			LightLobby newLobby = LightLobby.instancify(jsonLobby);
			
			lobbies.put(lobbyID, newLobby);
		}
	}
}