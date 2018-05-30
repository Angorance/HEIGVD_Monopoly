package bdfh.logic.game;

import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Class used to fix prices for a square.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Price {

	private int rent;               // Basic rent of the square
	private int price;              // Price of the room
	private int priceCouch;         // Price for one couch
	private int priceHomeCinema;    // Price for one home cinema
	private int hypothec;           // Amount of the hypothec of the square
	
	/**
	 * Constructor.
	 *
	 * @param rent              Basic rent of the square.
	 * @param price             Price of the room
	 * @param priceCouch        Price for one couch.
	 * @param priceHomeCinema   Price for one home cinema.
	 * @param hypothec          Amount of the hypothec of the square.
	 */
	public Price(int rent, int price, int priceCouch, int priceHomeCinema, int hypothec) {
		this.rent = rent;
		this.price = price;
		this.priceCouch = priceCouch;
		this.priceHomeCinema = priceHomeCinema;
		this.hypothec = hypothec;
	}
	
	public int getRent() {
		
		return rent;
	}
	
	public int getPrice() {
		
		return rent;
	}
	
	public int getPriceCouch() {
		
		return priceCouch;
	}
	
	public int getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	public int getHypothec() {
		
		return hypothec;
	}
	
	public String jsonify() {
		
		JsonObject jsonPrice = new JsonObject();
		
		jsonPrice.add("rent", new JsonPrimitive(rent));
		jsonPrice.add("price", new JsonPrimitive(price));
		jsonPrice.add("couch", new JsonPrimitive(priceCouch));
		jsonPrice.add("homeCinema", new JsonPrimitive(priceHomeCinema));
		jsonPrice.add("hypothec", new JsonPrimitive(hypothec));
		
		return GsonSerializer.getInstance().toJson(jsonPrice);
	}
}
