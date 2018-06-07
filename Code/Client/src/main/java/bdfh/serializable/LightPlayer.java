package bdfh.serializable;

import bdfh.logic.usr.Player;
import com.google.gson.JsonObject;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class LightPlayer {
	private int id;
	private int capital;
	private int freeCards;
	private int position;
	
	private String username;
	
	private boolean inPrison;
	
	public LightPlayer(int id, String username, int capital) {
		this.id = id;
		this.username = username;
		this.capital = capital;
		this.position = 0;
	}
	
	public int getId() {
		
		return id;
	}
	
	public String getUsername() {
		
		return username;
	}
	
	public int getCapital() {
		
		return capital;
	}
	
	public boolean isInExam() {
		
		return inPrison;
	}
	
	public int getFreeCards() {
		
		return freeCards;
	}
	
	public int getPosition() {
		
		return position;
	}
	
	public void setCapital(int capital) {
		
		this.capital = capital;
	}
	
	public void addCapital(int val) {
		
		capital += val;
	}
	
	public void setInPrison(boolean inPrison) {
		
		this.inPrison = inPrison;
	}
	
	public void setFreeCards(int val) {
		
		this.freeCards += val;
	}
	
	public void setPosition(int position) {
		
		this.position = position;
	}
	
	/**
	 * Change a json Object into a LightPrice.
	 *
	 * @param jo    Json Object received.
	 *
	 * @return  a LightPrice.
	 */
	public static LightPlayer instancify(JsonObject jo) {
		
		LightPlayer tmp = new LightPlayer(
				jo.get("id").getAsInt(),
				jo.get("username").getAsString(),
				jo.get("capital").getAsInt()
		);
		
		if (Player.getInstance().getUsername().equals(tmp.username)) {
			Player.getInstance().setID(tmp.id);
		}
		
		return tmp;
	}
}