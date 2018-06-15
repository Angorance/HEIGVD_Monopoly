package bdfh.logic.usr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class used to create parameters linked to one game and to
 * store the limits of the game fixed by the administrator.
 *
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class Parameter {
	
	private static final int NB_MODES = 2;
	
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
	private int time;
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int mode, int time) {
		this(nbrDice, moneyAtTheStart, mode, time, false);
	}
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 * @param mode                  Game mode identifier
	 * @param time
	 * @param randomGeneration      True if random game generation allowed, false otherwise
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int mode, int time, boolean randomGeneration) {
		this.nbrDice = nbrDice;
		this.moneyAtTheStart = moneyAtTheStart;
		this.randomGeneration = randomGeneration;
		this.mode = mode;
		this.time = time;
	}
	
	/**
	 * Return the list of modes in Strings in order.
	 *
	 * @return List of Strings.
	 */
	public static List<String> getStringModes() {
		
		ArrayList<String> tmp = new ArrayList<>();
		
		for (int i = 0; i < NB_MODES; ++i) {
		
			tmp.add(modes.get(i));
		}
		
		return tmp;
	}
	
	public static Map<String, Integer> getModesValues() {
		
		return modesValues;
	}
}
