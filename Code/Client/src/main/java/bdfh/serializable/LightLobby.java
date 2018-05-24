package bdfh.serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

public class LightLobby {
	
	private int ID;
	private ArrayList<String> usernames = new ArrayList<>(4);
	private ArrayList<Boolean> areReady = new ArrayList<>(4);
	
	
	public LightLobby() {}
	
	public int getID() {
		
		return ID;
	}
	
	public ArrayList<String> getUsernames() {
		
		return usernames;
	}
	
	public List<Boolean> getAreReady() {
		
		return areReady;
	}
	
	public void setID(int ID) {
		
		this.ID = ID;
	}
	
	public void addPlayer(String username/*, int index*/) {
	
		usernames.add(/*index, */username);
	}
	
	public void addReady(boolean ready, int index) {
		
		areReady.add(index, ready);
	}
	
	private void addR(boolean ready) {
		
		areReady.add(ready);
	}
	
	public void removePlayer(int index) {
		
		usernames.remove(index);
		areReady.remove(index);
	}
	
	public static LightLobby instancify(String json) {
		
		JsonObject jo = new JsonObject();
		jo = jo.getAsJsonObject(json);
		
		return instancify(jo);
	}
	
	public static LightLobby instancify(JsonObject jo) {
		
		LightLobby tmp = new LightLobby();
		
		tmp.setID(jo.get("ID").getAsInt());
		
		for (JsonElement je : jo.get("Users").getAsJsonArray()) {
			
			tmp.addPlayer(je.getAsString());
		}
		
		for (JsonElement je : jo.get("Ready").getAsJsonArray()) {
			
			tmp.addR(je.getAsBoolean());
		}
		
		return tmp;
	}
}
