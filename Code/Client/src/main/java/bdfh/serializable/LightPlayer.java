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
	
	private String username;
	
	private boolean inPrison;
	private boolean hasCard;
	
	public LightPlayer(int id, String username, int capital) {
		this.id = id;
		this.username = username;
		this.capital = capital;
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
		
		if (Player.getInstance().getUsername().equals(jo.get("username").getAsString())) {
			Player.getInstance().setID(jo.get("id").getAsInt());
		}
		
		return tmp;
	}
}
