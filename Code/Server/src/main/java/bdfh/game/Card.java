package bdfh.game;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Card {
	
	public enum EFFECTS {MOVE, EXAM, WIN, LOSE, GOTO, CARD;}
	
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
		return 0;
	}
	
}
