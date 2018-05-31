package bdfh.serializable;

import com.google.gson.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class LightPrice {
	
	private int rent;               // Basic rent of the square
	private int price;              // Price of the room
	private int priceCouch;         // Price for one couch
	private int priceHomeCinema;    // Price for one home cinema
	private int hypothec;           // Amount of the hypothec of the square
	
	public LightPrice() {}
	
	public int getRent() {
		
		return rent;
	}
	
	private void setRent(int rent) {
		
		this.rent = rent;
	}
	
	public int getPrice() {
		
		return price;
	}
	
	private void setPrice(int price) {
		
		this.price = price;
	}
	
	public int getPriceCouch() {
		
		return priceCouch;
	}
	
	private void setPriceCouch(int priceCouch) {
		
		this.priceCouch = priceCouch;
	}
	
	public int getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	private void setPriceHomeCinema(int priceHomeCinema) {
		
		this.priceHomeCinema = priceHomeCinema;
	}
	
	public int getHypothec() {
		
		return hypothec;
	}
	
	private void setHypothec(int hypothec) {
		
		this.hypothec = hypothec;
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
		tmp.setPrice(jo.get("price").getAsInt());
		tmp.setPriceCouch(jo.get("priceCouch").getAsInt());
		tmp.setPriceHomeCinema(jo.get("priceHomeCinema").getAsInt());
		tmp.setHypothec(jo.get("hypothec").getAsInt());
		
		return tmp;
	}
}
