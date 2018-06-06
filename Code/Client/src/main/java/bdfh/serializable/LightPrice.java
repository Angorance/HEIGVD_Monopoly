package bdfh.serializable;

import bdfh.net.protocol.GameProtocol;
import com.google.gson.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class LightPrice {
	
	private Integer rent;               // Basic rent of the square
	private Integer price;              // Price of the room
	private Integer priceCouch;         // Price for one couch
	private Integer priceHomeCinema;    // Price for one home cinema
	private Integer hypothec;           // Amount of the hypothec of the square
	
	public LightPrice() {}
	
	public Integer getRent() {
		
		return rent;
	}
	
	private void setRent(Integer rent) {
		
		this.rent = (rent == -1 ? null : rent);
	}
	
	public Integer getPrice() {
		
		return price;
	}
	
	private void setPrice(Integer price) {
		
		this.price = (price == -1 ? null : price);
	}
	
	public Integer getPriceCouch() {
		
		return priceCouch;
	}
	
	private void setPriceCouch(Integer priceCouch) {
		
		this.priceCouch = (priceCouch == -1 ? null : priceCouch);
	}
	
	public Integer getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	private void setPriceHomeCinema(Integer priceHomeCinema) {
		
		this.priceHomeCinema = (priceHomeCinema == -1 ? null : priceHomeCinema);
	}
	
	public Integer getHypothec() {
		
		return hypothec;
	}
	
	private void setHypothec(Integer hypothec) {
		
		this.hypothec = (hypothec == -1 ? null : hypothec);
	}
	
	/**
	 * Get all the possible rents.
	 * - Simple rent
	 * - Rent for 1-2-3-4 couches
	 * - Rent for 1 home cinema
	 *
	 * @return  array with all rents.
	 */
	public int[] getRents() {
		
		int[] rents = new int[6];
		
		// Simple rent
		rents[0] = getRent();
		
		// Rents with couch(es)
		rents[1] = getRent() * GameProtocol.RENT_TO_1C;
		rents[2] = getRent() * GameProtocol.RENT_TO_2C;
		rents[3] = getRent() * GameProtocol.RENT_TO_3C;
		rents[4] = getRent() * GameProtocol.RENT_TO_4C;
		
		// Rent with home cinema
		rents[5] = getRent() * GameProtocol.RENT_TO_1H;
		
		return rents;
	}
	
	/**
	 * Change a json String into a LightPrice.
	 *
	 * @param json  JSON received.
	 *
	 * @return  a LightPrice.
	 */
	public static LightPrice instancify(String json) {
		
		JsonObject jo = GsonSerializer.getInstance().fromJson(json, JsonObject.class);
		
		return instancify(jo);
	}
	
	/**
	 * Change a json Object into a LightPrice.
	 *
	 * @param jo    Json Object received.
	 *
	 * @return  a LightPrice.
	 */
	public static LightPrice instancify(JsonObject jo) {
		
		LightPrice tmp = new LightPrice();
		
		// Set the attributes
		tmp.setRent(jo.get("rent").getAsInt());
		tmp.setPrice(jo.get("price").getAsInt()); // TODO - A CHECKER
		tmp.setPriceCouch(jo.get("couch").getAsInt());
		tmp.setPriceHomeCinema(jo.get("homeCinema").getAsInt());
		tmp.setHypothec(jo.get("hypothec").getAsInt());
		
		return tmp;
	}
}
