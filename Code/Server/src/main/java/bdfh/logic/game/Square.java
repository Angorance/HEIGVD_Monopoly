package bdfh.logic.game;

import bdfh.net.server.ClientHandler;
import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * @authors Bryan Curchod, Héléna Line Reymond
 * @version 1.0
 */
public class Square {
	
	public enum FAMILY {STATION, CARD, COMPANY, RED, GREEN, BLUE, YELLOW, ORANGE, PINK, PURPLE, CYAN}
	
	private int position;           // Position of the square
	private FAMILY family;          // Family of the square
	private String name;            // Name of the square
	private Price prices;           // Prices of the square
	
	private ClientHandler owner;    // Owner of the square
	
	/**
	 * Constructor.
	 *
	 * @param position  Position of the square
	 * @param family    Family of the square
	 * @param name      Name of the square
	 * @param prices    Prices of the square
	 */
	public Square(int position, FAMILY family, String name, Price prices){
		this.position = position;
		this.family = family;
		this.name = name;
		this.prices = prices;
	}
	
	public int getPosition() {
		
		return position;
	}
	
	public FAMILY getFamily() {
		
		return family;
	}
	
	public String getName() {
		
		return name;
	}
	
	public Price getPrices() {
		
		return prices;
	}
	
	public ClientHandler getOwner() {
		
		return owner;
	}
	
	public void setOwner(ClientHandler owner) {
		
		this.owner = owner;
	}
	
	public String jsonify() {
		
		JsonObject jsonSquare = new JsonObject();
		
		jsonSquare.add("position", new JsonPrimitive(position));
		jsonSquare.add("name", new JsonPrimitive(name));
		jsonSquare.add("family", new JsonPrimitive(family.name()));
		
		jsonSquare.add("prices", new JsonPrimitive(prices.jsonify()));
		
		return GsonSerializer.getInstance().toJson(jsonSquare);
	}
}