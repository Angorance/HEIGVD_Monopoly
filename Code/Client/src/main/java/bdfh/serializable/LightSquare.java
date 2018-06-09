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
	private LightPlayer owner;           // Owner of the square
	
	private int nbCouches = 0;
	private boolean hasCine = false;
	private boolean isMortgaged = false;
	
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
	
	public LightPlayer getOwner() {
		
		return owner;
	}
	
	public void setOwner(LightPlayer owner) {
		
		this.owner = owner;
	}
	
	/**
	 * TODO
	 */
	public void buySquare() {
		Client.getInstance().buySellSquare(true, position);
	}
	
	/**
	 * TODO
	 */
	public void sellSquare() {
		Client.getInstance().buySellSquare(false, position);
	}
	
	/**
	 * put a square in mortgage
	 */
	public void setMortgage() {
		Client.getInstance().setCancelMortgage(true, position);
	}
	
	/**
	 * put a square in mortgage
	 */
	public void cancelMortgage() {
		Client.getInstance().setCancelMortgage(false, position);
	}
	
	public void buyCouch() {
		Client.getInstance().buySellImprovements(true, 0, position);
	}
	
	public void buyHomeCinema() {
		Client.getInstance().buySellImprovements(true, 1, position);
	}
	
	public void sellCouch() {
		Client.getInstance().buySellImprovements(false, 0, position);
	}
	
	public void sellHomeCinema() {
		Client.getInstance().buySellImprovements(false, 1, position);
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
	
	public void toggleCouch(int val) {
		
		nbCouches += val;
	}
	
	public void toggleHCine(boolean has) {
	
		hasCine = has;
	}
	
	public int getNbCouches() {
		
		return nbCouches;
	}
	
	public boolean hasCine() {
		
		return hasCine;
	}
	
	public boolean isMortgaged() {
		
		return isMortgaged;
	}
	
	public void setMortgaged(boolean mortgaged) {
		
		isMortgaged = mortgaged;
	}
}
