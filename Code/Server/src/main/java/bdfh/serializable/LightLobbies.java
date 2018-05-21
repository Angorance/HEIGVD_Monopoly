package bdfh.serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class LightLobbies {
	
	private HashMap<Integer, LightLobby> lobbies = new HashMap<>();
	
	protected LightLobbies() {}
	
	public HashMap<Integer, LightLobby> getLobbies() {
		
		return lobbies;
	}
	
	public void addLobby(LightLobby l) {
		
		lobbies.put(l.getID(), l);
	}
	
	public void removeLobby(LightLobby l) {
		
		lobbies.remove(l.getID(), l);
	}
	
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