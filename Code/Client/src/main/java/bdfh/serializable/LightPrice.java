package bdfh.serializable;

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
		
		this.rent = rent;
	}
	
	public Integer getPrice() {
		
		return price;
	}
	
	private void setPrice(Integer price) {
		
		this.price = price;
	}
	
	public Integer getPriceCouch() {
		
		return priceCouch;
	}
	
	private void setPriceCouch(Integer priceCouch) {
		
		this.priceCouch = priceCouch;
	}
	
	public Integer getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	private void setPriceHomeCinema(Integer priceHomeCinema) {
		
		this.priceHomeCinema = priceHomeCinema;
	}
	
	public Integer getHypothec() {
		
		return hypothec;
	}
	
	private void setHypothec(Integer hypothec) {
		
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
		tmp.setRent(GsonSerializer.getInstance().fromJson("rent", Integer.class));
		tmp.setPrice(GsonSerializer.getInstance().fromJson("price", Integer.class));
		tmp.setPriceCouch(GsonSerializer.getInstance().fromJson("priceCouch", Integer.class));
		tmp.setPriceHomeCinema(GsonSerializer.getInstance().fromJson("priceHomeCinema", Integer.class));
		tmp.setHypothec(GsonSerializer.getInstance().fromJson("hypothec", Integer.class));
		
		return tmp;
	}
}
