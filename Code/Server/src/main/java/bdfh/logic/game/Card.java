package bdfh.logic.game;

import bdfh.serializable.GsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Random;

/**
 * @author Bryan Curchod
 * @author Héléna Line Reymond
 * @version 1.1
 */
public class Card {
	
	
	public enum EFFECTS {MOVE, EXAM, WIN, LOSE, GOTO, CARD, FREE}
	
	private String text;        // Text of the card
	private int quantity;       // Quantity of the card
	private EFFECTS effect;     // Effect linked to the card
	private int value;          // Value of the effect
	
	/**
	 * Constructor.
	 *
	 * @param text Text of the card.
	 * @param quantity Quantity of the card.
	 * @param effect Effect linked to the card.
	 */
	public Card(String text, int quantity, EFFECTS effect) {
		
		this.text = text;
		this.quantity = quantity;
		this.effect = effect;
		this.value = generateValue();
	}
	
	/**
	 * Generate the value associated to the card if needed.
	 *
	 * @return the value associated to the card.
	 */
	private int generateValue() {
		
		Random random = new Random();
		int value = 0;
		
		if (effect == EFFECTS.MOVE) {
			value = random.nextInt(6) + 1;
			
		} else if (effect == EFFECTS.WIN || effect == EFFECTS.LOSE) {
			value = (random.nextInt(10) + 1) * 100;
			
		} else if (effect == EFFECTS.GOTO) {
			value = random.nextInt(40);
		}
		
		return value;
	}
	
	public void setQuantity(int quantity) {
		
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		
		return quantity;
	}
	
	public EFFECTS getEffect() {
		
		return effect;
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
		
		jsonCard.add("text", new JsonPrimitive(text));
		jsonCard.add("effect", new JsonPrimitive(effect.name()));
		jsonCard.add("value", new JsonPrimitive(value));
		
		return GsonSerializer.getInstance().toJson(jsonCard);
	}
}
