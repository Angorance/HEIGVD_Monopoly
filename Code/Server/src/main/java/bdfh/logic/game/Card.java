package bdfh.logic.game;

import java.util.Random;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Card {
	
	public enum EFFECTS {MOVE, EXAM, WIN, LOSE, GOTO, CARD}
	
	private String text;
	private EFFECTS effect;
	private int value;
	
	public Card(String text, EFFECTS effect){
		this.text = text;
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
		
		if(effect == EFFECTS.MOVE) {
			value = random.nextInt(6) + 1;
		
		} else if (effect == EFFECTS.WIN || effect == EFFECTS.LOSE) {
			value = (random.nextInt(10) + 1) * 100;
		
		} else if (effect == EFFECTS.GOTO) {
			value = random.nextInt(40);
		}
		
		return value;
	}
	
}
