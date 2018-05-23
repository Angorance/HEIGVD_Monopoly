package bdfh.serializable;

import java.util.Map;

/**
 * Class used to create parameters linked to one game.
 *
 * @author Héléna Line Reymond
 * @author Daniel Gonzalez Lopez
 * @version 2.0
 */
public class Parameter {
	
	private int nbrDice;                             // Number of dice in the game
	private int moneyAtTheStart;                     // Money given at the start
	private int time;
	private boolean randomGeneration;                // True if random game generation allowed, false otherwise
	private int mode;
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int time, int mode) {
		this(nbrDice, moneyAtTheStart, mode, time, false);
	}
	
	/**
	 * Set the parameter of the game.
	 *
	 * @param nbrDice               Number of dice in the game
	 * @param moneyAtTheStart       Money given at the start
	 * @param randomGeneration      True if random game generation allowed, false otherwise
	 */
	Parameter(int nbrDice, int moneyAtTheStart, int time, int mode, boolean randomGeneration) {
		this.nbrDice = nbrDice;
		this.moneyAtTheStart = moneyAtTheStart;
		this.time = time;
		this.randomGeneration = randomGeneration;
		this.mode = mode;
	}
	
	public int getMoneyAtTheStart() {
		
		return moneyAtTheStart;
	}
	
	public int getNbrDice() {
		
		return nbrDice;
	}
	
	public int getMode() {
		
		return mode;
	}
}
