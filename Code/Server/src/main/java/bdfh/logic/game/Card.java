package bdfh.logic.game;

import bdfh.protocol.GameProtocol;
import bdfh.serializable.GsonSerializer;
import com.google.gson.*;

/**
 * @author Bryan Curchod
 * @author Héléna Line Reymond
 * @version 1.1
 */
public class Card {
	
	private String text;        // Text of the card
	private int quantity;       // Quantity of the card
	private String action;      // Action linked to the card
	
	/**
	 * Constructor.
	 *
	 * @param text Text of the card.
	 * @param quantity Quantity of the card.
	 * @param action Action linked to the card.
	 */
	public Card(String text, int quantity, String action) {
		
		this.text = text;
		this.quantity = quantity;
		this.action = action;
	}
	
	public void setQuantity(int quantity) {
		
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		
		return quantity;
	}
	
	public String getAction() {
		
		return action.split(" ")[0];
	}
	
	public String getFullAction() {
		
		return action;
	}
	
	@Override
	public String toString() {
		
		return "{\"text\" : \"" + text + "\"}";
	}
	
	/**
	 * Serialise the object.
	 * Allow sending item to the client.
	 * Serialise only text, effect and value of card.
	 *
	 * @return Json String.
	 */
	public String jsonify() {
		
		JsonObject jsonCard = new JsonObject();
		
		String cardText = text + "\n";
		String[] infos = getFullAction().split(" ");
		
		switch(getAction()) {
			
			case GameProtocol.CARD_MOVE:
				cardText += "Avance de " + infos[1] + " cases";
				break;
				
			case GameProtocol.CARD_BACK:
				cardText += "Recule de " + infos[1] + " cases";
				break;
				
			case GameProtocol.CARD_WIN:
				cardText += "Tu reçois " + infos[1] + " francs";
				break;
				
			case GameProtocol.CARD_LOSE:
				cardText += "Tu paies " + infos[1] + " francs";
				break;
				
			case GameProtocol.CARD_GOTO:
				cardText += "Vas sur la case " + infos[1];
				break;
				
			case GameProtocol.CARD_FREE:
				cardText += "Tu peux conserver cette carte";
				break;
				
			case GameProtocol.CARD_EACH:
				cardText += "Chaque joueur te donne " + infos[1] + " francs";
				break;
		}
		
		jsonCard.add("text", new JsonPrimitive(cardText));
		
		return GsonSerializer.getInstance().toJson(jsonCard);
	}
}
