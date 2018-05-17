package bdfh.serializable;

import bdfh.game.Lobby;

import java.util.ArrayList;

public class LightLobby {
	
	private int ID;
	private ArrayList<String> usernames = new ArrayList<>(Lobby.MAX_PLAYER);
	private ArrayList<Boolean> areReady = new ArrayList<>(Lobby.MAX_PLAYER);
	
	
	public LightLobby() {}
	
	public int getID() {
		
		return ID;
	}
	
	public ArrayList<String> getUsernames() {
		
		return usernames;
	}
	
	public ArrayList<Boolean> getAreReady() {
		
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
	
	public void removePlayer(int index) {
		
		usernames.remove(index);
		areReady.remove(index);
	}
}
