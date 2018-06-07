package bdfh.serializable;

import bdfh.net.client.Client;
import com.google.gson.*;

import java.util.List;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class LightSquare {
	
	private int position;           // Position of the square
	private String family;          // Family of the square
	private String name;            // Name of the square
	private LightPrice prices;      // Prices of the square
	private String owner;           // Owner of the square
	
	public LightSquare() {}

	public int getPosition() {
		
		return position;
	}
	
	private void setPosition(int position) {
		
		this.position = position;
	}
	
	public String getFamily() {
		
		return family;
	}
	
	private void setFamily(String family) {
		
		this.family = family;
	}
	
	public String getName() {
		
		return name;
	}
	
	private void setName(String name) {
		
		this.name = name;
	}
	
	public LightPrice getPrices() {
		
		return prices;
	}
	
	private void setPrices(LightPrice prices) {
		
		this.prices = prices;
	}
	
	public String getOwner() {
		
		return owner;
	}
	
	public void setOwner(String owner) {
		
		this.owner = owner;
	}
	
	/**
	 * TODO
	 */
	public void buySquare(int pos) {
	
	}
	
	/**
	 * TODO
	 */
	public void sellSquare(int pos) {
	
	}
	
	public void buyCouch(int pos) {
		Client.getInstance().buySellImprovements(true, 0, pos);
	}
	
	public void buyHomeCinema(int pos) {
		Client.getInstance().buySellImprovements(true, 1, pos);
	}
	
	public void sellCouch(int pos) {
		Client.getInstance().buySellImprovements(false, 0, pos);
	}
	
	public void sellHomeCinema(int pos) {
		Client.getInstance().buySellImprovements(false, 1, pos);
	}
	
	/**
	 * Change a json String into a LightSquare.
	 *
	 * @param json  JSON received.
	 *
	 * @return  a LightSquare.
	 */
	public static LightSquare instancify(String json) {
		
		JsonObject jo = GsonSerializer.getInstance().fromJson(json, JsonObject.class);
		
		return instancify(jo);
	}
	
	/**
	 * Change a json Object into a LightSquare.
	 *
	 * @param jo    Json Object received.
	 *
	 * @return  a LightSquare.
	 */
	public static LightSquare instancify(JsonObject jo) {
		
		LightSquare tmp = new LightSquare();
		
		// Set the attributes
		tmp.setPosition(jo.get("position").getAsInt());
		tmp.setFamily(jo.get("family").getAsString());
		tmp.setName(jo.get("name").getAsString());
		
		// Set the prices
		try {
			String prices = jo.get("prices").getAsString();
			JsonObject jsonPrices = GsonSerializer.getInstance()
					.fromJson(prices, JsonObject.class);
			LightPrice newPrices = LightPrice.instancify(jsonPrices);
			tmp.setPrices(newPrices);
		} catch (NullPointerException e) {
			tmp.setPrices(null);
		}
		
		return tmp;
	}
}
