package bdfh.logic.usr;

/**
 * Class used to create parameters linked to one game and to
 * store the limits of the game fixed by the administrator.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Parameter {
	
	public static int minDice;                      // Minimum number of dice allowed
	public static int maxDice;                      // Maximum number of dice allowed
	public static int minMoneyAtTheStart;           // Minimum money allowed at the start per player
	public static int maxMoneyAtTheStart;           // Maximum money allowed at the start per player
	public static boolean randomGameGeneration;     // True if random game generation allowed, false otherwise
	
	public int nbrDice;                             // Number of dice in the game
	public int moneyAtTheStart;                     // Money given at the start
	public boolean randomGeneration;                // True if random game generation allowed, false otherwise
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 */
	Parameter(int nbrDice, int moneyAtTheStart) {
		this(nbrDice, moneyAtTheStart, false);
	}
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 * @param randomGeneration      True if random game generation allowed, false otherwise
	 */
	Parameter(int nbrDice, int moneyAtTheStart, boolean randomGeneration) {
		this.nbrDice = nbrDice;
		this.moneyAtTheStart = moneyAtTheStart;
		this.randomGeneration = randomGeneration;
	}
	
	/**
	 * Set the limits of parameters for all game created.
	 *
	 * @param minDice                   Minimum number of dice allowed
	 * @param maxDice                   Maximum number of dice allowed
	 * @param minMoney                  Minimum money allowed at the start per player
	 * @param maxMoney                  Maximum money allowed at the start per player
	 * @param randomGameGeneration      True if randomGameGeneration allowed, false otherwise
	 */
	public static void updateLimits(int minDice, int maxDice, int minMoney, int maxMoney, boolean randomGameGeneration){
		Parameter.minDice = minDice;
		Parameter.maxDice = maxDice;
		Parameter.minMoneyAtTheStart = minMoney;
		Parameter.maxMoneyAtTheStart = maxMoney;
		Parameter.randomGameGeneration = randomGameGeneration;
	}
}
