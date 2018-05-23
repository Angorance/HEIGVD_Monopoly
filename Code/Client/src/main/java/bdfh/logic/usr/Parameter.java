package bdfh.logic.usr;

import java.util.Map;

/**
 * Class used to create parameters linked to one game and to
 * store the limits of the game fixed by the administrator.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Parameter {
	
	private static Map<Integer, String> modes = Map.ofEntries(
			Map.entry(0, "Banqueroute"),
			Map.entry(1, "Limite de temps")
	);
	
	private static Map<String, Integer> modesValues = Map.ofEntries(
			Map.entry("Banqueroute", 0),
			Map.entry("Limite de temps", 1)
	);
	
	private int nbrDice;                             // Number of dice in the game
	private int moneyAtTheStart;                     // Money given at the start
	private boolean randomGeneration;                // True if random game generation allowed, false otherwise
	private int mode;
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int mode) {
		this(nbrDice, moneyAtTheStart, mode, false);
	}
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 * @param mode                  Game mode identifier
	 * @param randomGeneration      True if random game generation allowed, false otherwise
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int mode, boolean randomGeneration) {
		this.nbrDice = nbrDice;
		this.moneyAtTheStart = moneyAtTheStart;
		this.mode = mode;
		this.randomGeneration = randomGeneration;
	}
	
	public static Map<Integer, String> getModes() {
		
		return modes;
	}
	
	public static Map<String, Integer> getModesValues() {
		
		return modesValues;
	}
}
