package bdfh.serializable;

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
}