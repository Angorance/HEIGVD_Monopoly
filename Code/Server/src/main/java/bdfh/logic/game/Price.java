package bdfh.logic.game;

import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonObject;

/**
 * Class used to fix prices for a square.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Price {

	private Integer rent;               // Basic rent of the square
	private Integer price;              // Price of the room
	private Integer priceCouch;         // Price for one couch
	private Integer priceHomeCinema;    // Price for one home cinema
	private Integer hypothec;           // Amount of the hypothec of the square
	
	/**
	 * Constructor.
	 *
	 * @param rent              Basic rent of the square.
	 * @param price             Price of the room
	 * @param priceCouch        Price for one couch.
	 * @param priceHomeCinema   Price for one home cinema.
	 * @param hypothec          Amount of the hypothec of the square.
	 */
	public Price(Integer rent, Integer price, Integer priceCouch, Integer priceHomeCinema, Integer hypothec) {
		this.rent = rent;
		this.price = price;
		this.priceCouch = priceCouch;
		this.priceHomeCinema = priceHomeCinema;
		this.hypothec = hypothec;
	}
	
	public Integer getRent() {
		
		return rent;
	}
	
	public Integer getPrice() {
		
		return rent;
	}
	
	public Integer getPriceCouch() {
		
		return priceCouch;
	}
	
	public Integer getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	public Integer getHypothec() {
		
		return hypothec;
	}
	
	/**
	 * Change Price Object into String JSON.
	 *
	 * @return  String JSON.
	 */
	public String jsonify() {
		
		JsonObject jsonPrice = new JsonObject();
		
		rent = rent != null ? rent : -1;
		price = price != null ? price : -1;
		priceCouch = priceCouch != null ? priceCouch : -1;
		priceHomeCinema = priceHomeCinema != null ? priceHomeCinema : -1;
		hypothec = hypothec != null ? hypothec : -1;
		
		jsonPrice.addProperty("rent", rent);
		jsonPrice.addProperty("price", price);
		jsonPrice.addProperty("couch", priceCouch);
		jsonPrice.addProperty("homeCinema", priceHomeCinema);
		jsonPrice.addProperty("hypothec", hypothec);
		
		return GsonSerializer.getInstance().toJson(jsonPrice);
	}
}
