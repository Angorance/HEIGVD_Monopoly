package bdfh.logic.game;

import bdfh.net.server.ClientHandler;
import static  bdfh.protocol.GameProtocol.*;
import bdfh.serializable.GsonSerializer;
import com.google.gson.*;

import java.util.*;

/**
 * @authors Bryan Curchod, Héléna Line Reymond
 * @version 1.0
 */
public class Square {
	
	private int position;           // Position of the square
	private static final List<String> nonBuyable = List.of(SQUA_START, SQUA_GO_EXAM, SQUA_EXAM, SQUA_TAX, SQUA_CARD, SQUA_BREAK);
	private static final List<String> listProperty = List.of(SQUA_BLUE, SQUA_BROWN, SQUA_CYAN, SQUA_GREEN, SQUA_ORANGE, SQUA_PINK, SQUA_RED, SQUA_YELLOW);
	
	private String family;          // Family of the square
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
	public Square(int position, String family, String name, Price prices){
		this.position = position;
		this.family = family;
		this.name = name;
		this.prices = prices;
	}
	
	public int getPosition() {
		
		return position;
	}
	
	public String getFamily() {
		
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
	
	public boolean isBuyable(){
		return !nonBuyable.contains(this.family);
	}
	
	public boolean isProperty(){
		return listProperty.contains(this.family);
	}
	
	public String jsonify() {
		
		JsonObject jsonSquare = new JsonObject();
		
		jsonSquare.add("position", new JsonPrimitive(position));
		jsonSquare.add("name", new JsonPrimitive(name));
		jsonSquare.add("family", new JsonPrimitive(family));
		
		if (prices != null) {
			jsonSquare.add("prices", new JsonPrimitive(prices.jsonify()));
		}
		
		return GsonSerializer.getInstance().toJson(jsonSquare);
	}
}