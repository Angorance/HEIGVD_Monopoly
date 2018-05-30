package bdfh.logic.game;

import bdfh.net.server.ClientHandler;
import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.*;

/**
 * @authors Bryan Curchod, Héléna Line Reymond
 * @version 1.0
 */
public class Square {
	
	private int position;           // Position of the square
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
	
	public void manageEffect() {
		switch (family) {
			case GameProtocol.SQUA_TAX:
				
				break;
				
			case GameProtocol.SQUA_INSTITUTE:
				
				break;
				
			case GameProtocol.SQUA_COMPANY:
				
				break;
			
			case GameProtocol.SQUA_CARD:
				
				break;
			
			case GameProtocol.SQUA_START:
				
				break;
			
			case GameProtocol.SQUA_EXAM:
				
				break;
			
			case GameProtocol.SQUA_GO_EXAM:
				
				break;
			
			case GameProtocol.SQUA_BREAK:
				
				break;
				
			case GameProtocol.SQUA_RED:
			case GameProtocol.SQUA_BLUE:
			case GameProtocol.SQUA_CYAN:
			case GameProtocol.SQUA_PINK:
			case GameProtocol.SQUA_GREEN:
			case GameProtocol.SQUA_ORANGE:
			case GameProtocol.SQUA_BROWN:
			case GameProtocol.SQUA_YELLOW:
				
				break;
		}
	}
	
	public String jsonify() {
		
		JsonObject jsonSquare = new JsonObject();
		
		jsonSquare.add("position", new JsonPrimitive(position));
		jsonSquare.add("name", new JsonPrimitive(name));
		jsonSquare.add("family", new JsonPrimitive(family));
		
		jsonSquare.add("prices", new JsonPrimitive(prices.jsonify()));
		
		return GsonSerializer.getInstance().toJson(jsonSquare);
	}
}