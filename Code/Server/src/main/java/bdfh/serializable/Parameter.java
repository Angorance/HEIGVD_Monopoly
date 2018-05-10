package bdfh.serializable;

/**
 * Class used to create parameters linked to one game.
 *
 * @author Héléna Line Reymond
 * @version 2.0
 */
public class Parameter {
	
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
}
